package japicmpextra;

import japicmp.cmp.JarArchiveComparator;
import japicmp.cmp.JarArchiveComparatorOptions;
import japicmp.cmp.JApiCmpArchive;
import japicmp.model.JApiAnnotation;
import japicmp.model.JApiAnnotationElement;
import japicmp.model.JApiChangeStatus;
import japicmp.model.JApiClass;
import japicmp.model.JApiCompatibilityChange;
import japicmp.model.JApiConstructor;
import japicmp.model.JApiField;
import japicmp.model.JApiMethod;
import japicmp.model.JApiParameter;
import japicmp.output.OutputFilter;
import japicmp.output.html.HtmlOutput;
import japicmp.output.html.HtmlOutputGenerator;
import japicmp.output.html.HtmlOutputGeneratorOptions;
import japicmp.config.Options;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import cn.com.platform.framework.file.ExcelMakeFile;

public class SpringApiCompare {

    public static void main(String[] args) throws Exception {
        String oldDir = "C:\\Users\\yusei.to\\Desktop\\担当部分\\migrate\\old";
        String newDir = "C:\\Users\\yusei.to\\Desktop\\担当部分\\migrate\\new";
        String reportDir = "reports"; // 存放报告的目录

        File reportFolder = new File(reportDir);
        if (!reportFolder.exists()) {
            reportFolder.mkdirs();
        }

        List<File> oldJarFiles = listJarFiles(oldDir);
        List<File> newJarFiles = listJarFiles(newDir);

        if (oldJarFiles.isEmpty() || newJarFiles.isEmpty()) {
            System.out.println("请确保目录下有 JAR 文件");
            return;
        }

        int n = Math.min(oldJarFiles.size(), newJarFiles.size());
        if (oldJarFiles.size() != newJarFiles.size()) {
            System.out.println("警告：old/new JAR 数量不一致，只比较对应顺序的 " + n + " 个 JAR");
        }

        // 1. 创建 Options
        Options options = Options.newDefault();
        options.setIgnoreMissingClasses(true);
        options.setOutputOnlyBinaryIncompatibleModifications(true);

        // 遍历每对 JAR
        for (int i = 0; i < 1; i++) {
        //for (int i = 0; i < n; i++) {
            File oldJar = oldJarFiles.get(i);
            File newJar = newJarFiles.get(i);

            JApiCmpArchive oldArchive = new JApiCmpArchive(oldJar, "0");
            JApiCmpArchive newArchive = new JApiCmpArchive(newJar, "1");

            // 2. 创建比较器
            JarArchiveComparatorOptions comparatorOptions = JarArchiveComparatorOptions.of(options);
            JarArchiveComparator comparator = new JarArchiveComparator(comparatorOptions);

            // 3. 执行比较
            List<JApiClass> jApiClasses = comparator.compare(
                    Arrays.asList(oldArchive),
                    Arrays.asList(newArchive)
            );

            // 4. 生成 HTML
            HtmlOutputGenerator htmlGen = new HtmlOutputGenerator(jApiClasses, options, new HtmlOutputGeneratorOptions());
            HtmlOutput output = htmlGen.generate();


//    		OutputFilter outputFilter = new OutputFilter(options);
//    		outputFilter.filter(jApiClasses);
//    		for (JApiClass jApiClass : jApiClasses) {
//    		    if (jApiClass.getChangeStatus() == JApiChangeStatus.REMOVED) {
//    		        System.out.println("被删除的 API 类: " + jApiClass.getFullyQualifiedName());
//    		    }
//    			System.out.println(jApiClass.getChangeStatus().name()+ "  " + jApiClass.getFullyQualifiedName());
//    		}
            
            analyzeRemoved(jApiClasses);
            ModifiedBreakingAnalyzer.analyzeBreakingModified(jApiClasses);

            // 5. 写入单独报告
            String oldName = oldJar.getName().replace(".jar", "");
            String newName = newJar.getName().replace(".jar", "");
            String reportFile = reportDir + File.separator + oldName + "_vs_" + newName + ".html";

            try (FileWriter writer = new FileWriter(reportFile)) {
                writer.write(output.getHtml());
            }

            System.out.println("报告生成完成：" + reportFile);
        }
    }

    private static List<File> listJarFiles(String dir) {
        File folder = new File(dir);
        File[] files = folder.listFiles((d, name) -> name.endsWith(".jar"));
        List<File> jars = new ArrayList<>();
        if (files != null) {
            Arrays.sort(files);
            jars.addAll(Arrays.asList(files));
        }
        return jars;
    }
    /**
     * 分析被修改（MODIFIED）的 API，并输出破坏兼容性信息
     */
    public static void analyzeModified(List<JApiClass> jApiClasses) {

        for (JApiClass cls : jApiClasses) {
        	boolean clsOutputFlag = true;
            String className = cls.getFullyQualifiedName();

            /* =========================
             * 2. 字段修改
             * ========================= */
            for (JApiField field : cls.getFields()) {
                if (field.getChangeStatus() == JApiChangeStatus.MODIFIED) {
                    String fieldName = className + "#" + field.getName();
                    System.out.println("[FIELD MODIFIED] " + fieldName);
                    clsOutputFlag = false;
                    printCompatibilityChanges("[FIELD]", fieldName, field.getCompatibilityChanges());
                }
            }

            /* =========================
             * 3. 方法修改
             * ========================= */
            for (JApiMethod method : cls.getMethods()) {
                if (method.getChangeStatus() == JApiChangeStatus.MODIFIED) {
                    String methodName = className + "#" + buildMethodSignature(method);
                    System.out.println("[METHOD MODIFIED] " + methodName);
                    clsOutputFlag = false;
                    printCompatibilityChanges("[METHOD]", methodName, method.getCompatibilityChanges());
                }
            }

            /* =========================
             * 4. 构造函数修改
             * ========================= */
            for (JApiConstructor constructor : cls.getConstructors()) {
                if (constructor.getChangeStatus() == JApiChangeStatus.MODIFIED) {
                    String ctorName = className + buildConstructorSignature(constructor);
                    System.out.println("[CONSTRUCTOR MODIFIED] " + ctorName);
                    clsOutputFlag = false;
                    printCompatibilityChanges("[CONSTRUCTOR]", ctorName, constructor.getCompatibilityChanges());
                }
            }

            /* =========================
             * 1. 类级别修改，类里面没有任何修改的时候才输出本被修改的类本身
             * ========================= */
            if (clsOutputFlag && cls.getChangeStatus() == JApiChangeStatus.MODIFIED) {
                System.out.println("[CLASS MODIFIED] " + className);
                clsOutputFlag = false;
                printCompatibilityChanges("[CLASS]", className, cls.getCompatibilityChanges());
            }
        }
    }
    
    public static void analyzeRemoved(List<JApiClass> jApiClasses) throws Exception {
        String projectPath = Paths.get("").toAbsolutePath().toString();
//      URL url = Main.class.getClassLoader().getResource(projectPath+"/src/main/webapp/templates/excel/demo.xlsx");
      ExcelMakeFile excelMakeFile = new ExcelMakeFile(new File(projectPath+"/resources/template.xlsx"));
      XSSFSheet outputSheet =  excelMakeFile.workbook.getSheetAt(0);
      String sheetName = outputSheet.getSheetName();
      
      
        for (JApiClass cls : jApiClasses) {
        	boolean clsOutputFlag = true;

            String className = cls.getFullyQualifiedName();

//            printCompatibilityChanges(
//                    "[CLASS]",
//                    className,
//                    cls.getCompatibilityChanges()
//            );

            /* =========================
             * 2. 字段
             * ========================= */
            for (JApiField field : cls.getFields()) {
                String fieldName = className + "#" + field.getName();

                if (field.getChangeStatus() == JApiChangeStatus.REMOVED) {
                    System.out.println("[FIELD REMOVED] " + fieldName);
                    clsOutputFlag = false;
                    int sheetLastRowNum =  outputSheet.getLastRowNum()+2;
                    excelMakeFile.setCellValue(outputSheet.getSheetName(), "B" + sheetLastRowNum, className);
                    excelMakeFile.setCellValue(outputSheet.getSheetName(), "C" + sheetLastRowNum, "フィールド");
                    excelMakeFile.setCellValue(outputSheet.getSheetName(), "D" + sheetLastRowNum, fieldName);
                    excelMakeFile.setCellValue(outputSheet.getSheetName(), "E" + sheetLastRowNum, "削除");
                }

//                printCompatibilityChanges(
//                        "[FIELD]",
//                        fieldName,
//                        field.getCompatibilityChanges()
//                );
            }

            /* =========================
             * 3. 方法
             * ========================= */
            for (JApiMethod method : cls.getMethods()) {
                String methodName =
                        className + "#" + buildMethodSignature(method);

                if (method.getChangeStatus() == JApiChangeStatus.REMOVED) {
                    System.out.println("[METHOD REMOVED] " + methodName);
                    clsOutputFlag = false;
                    int sheetLastRowNum =  outputSheet.getLastRowNum()+2;
                    excelMakeFile.setCellValue(outputSheet.getSheetName(), "B" + sheetLastRowNum, className);
                    excelMakeFile.setCellValue(outputSheet.getSheetName(), "C" + sheetLastRowNum, "メソッド");
                    excelMakeFile.setCellValue(outputSheet.getSheetName(), "D" + sheetLastRowNum, methodName);
                    excelMakeFile.setCellValue(outputSheet.getSheetName(), "E" + sheetLastRowNum, "削除");
                }

//                printCompatibilityChanges(
//                        "[METHOD]",
//                        methodName,
//                        method.getCompatibilityChanges()
//                );
            }

            /* =========================
             * 4. 构造函数
             * ========================= */
            for (JApiConstructor constructor : cls.getConstructors()) {
                String ctorName =
                        className + buildConstructorSignature(constructor);

                if (constructor.getChangeStatus() == JApiChangeStatus.REMOVED) {
                    System.out.println("[CONSTRUCTOR REMOVED] " + ctorName);
                    int sheetLastRowNum =  outputSheet.getLastRowNum()+2;
                    clsOutputFlag = false;
                    excelMakeFile.setCellValue(outputSheet.getSheetName(), "B" + sheetLastRowNum, className);
                    excelMakeFile.setCellValue(outputSheet.getSheetName(), "C" + sheetLastRowNum, "コンストラクタ");
                    excelMakeFile.setCellValue(outputSheet.getSheetName(), "D" + sheetLastRowNum, ctorName);
                    excelMakeFile.setCellValue(outputSheet.getSheetName(), "E" + sheetLastRowNum, "削除");
                }

//                printCompatibilityChanges(
//                        "[CONSTRUCTOR]",
//                        ctorName,
//                        constructor.getCompatibilityChanges()
//                );
            }


            /* =========================
             * 1. 类级别，类里面没有任何错误的时候才输出本删除的类
             * ========================= */
            if (clsOutputFlag && cls.getChangeStatus() == JApiChangeStatus.REMOVED) {
                System.out.println("[CLASS REMOVED] " + className);
                int sheetLastRowNum =  outputSheet.getLastRowNum()+2;
                excelMakeFile.setCellValue(outputSheet.getSheetName(), "B" + sheetLastRowNum, className);
                excelMakeFile.setCellValue(outputSheet.getSheetName(), "C" + sheetLastRowNum, "クラス");
                excelMakeFile.setCellValue(outputSheet.getSheetName(), "E" + sheetLastRowNum, "削除");
//                continue;
            }
        }

        
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        String fileName = "demo".concat(df.format(LocalDateTime.now()));
        fileName = fileName.concat(".xlsx");
        Files.write(Paths.get(projectPath+"/outputfiles/" +fileName), excelMakeFile.getBytes());
        
    }

    /* =========================
     * 方法签名构造
     * ========================= */
    private static String buildMethodSignature(JApiMethod method) {
        StringBuilder sb = new StringBuilder();
        sb.append(method.getName()).append("(");

        boolean first = true;
        for (JApiParameter param : method.getParameters()) {
            if (!first) {
                sb.append(", ");
            }
            sb.append(param.getType());
            first = false;
        }

        sb.append(")");
        return sb.toString();
    }

    /* =========================
     * 构造函数签名构造
     * ========================= */
    private static String buildConstructorSignature(JApiConstructor ctor) {
        StringBuilder sb = new StringBuilder();
        sb.append("(");

        boolean first = true;
        for (JApiParameter param : ctor.getParameters()) {
            if (!first) {
                sb.append(", ");
            }
            sb.append(param.getType());
            first = false;
        }

        sb.append(")");
        return sb.toString();
    }

    /* =========================
     * CompatibilityChange 输出
     * ========================= */
    private static void printCompatibilityChanges(
            String level,
            String location,
            List<JApiCompatibilityChange> changes) {

        for (JApiCompatibilityChange change : changes) {

            // 只输出真正破坏兼容性的
            if (change.isBinaryCompatible() && change.isSourceCompatible()) {
                continue;
            }

            System.out.println(
                    level + " BREAKING: " +
                    location +
                    " | type=" + change.getType() +
                    " | binaryCompatible=" + change.isBinaryCompatible() +
                    " | sourceCompatible=" + change.isSourceCompatible()
            );
        }
    }
}
