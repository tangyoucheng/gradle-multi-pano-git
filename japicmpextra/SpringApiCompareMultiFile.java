package japicmpextra;

import japicmp.cmp.JarArchiveComparator;
import japicmp.cmp.JarArchiveComparatorOptions;
import japicmp.cmp.JApiCmpArchive;
import japicmp.model.JApiAnnotation;
import japicmp.model.JApiAnnotationElement;
import japicmp.model.JApiChangeStatus;
import japicmp.model.JApiClass;
import japicmp.model.JApiCompatibilityChange;
import japicmp.model.JApiCompatibilityChangeType;
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
import java.util.stream.Collectors;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import cn.com.platform.framework.file.ExcelMakeFile;

public class SpringApiCompareMultiFile {

    public static void main(String[] args) throws Exception {
        String oldDir = "C:\\Users\\yusei.to\\Desktop\\担当部分\\migrate\\springframework4.1.6";
        String newDir = "C:\\Users\\yusei.to\\Desktop\\担当部分\\migrate\\springframework5.3.31";
        String reportDir = "spring4to5reports"; // 存放报告的目录
//        String oldDir = "C:\\Users\\yusei.to\\Desktop\\担当部分\\migrate\\springframework5.3.31";
//        String newDir = "C:\\Users\\yusei.to\\Desktop\\担当部分\\migrate\\springframework6.2.15";
//        String reportDir = "spring5to6reports"; // 存放报告的目录

//      String oldDir = "C:\\Users\\yusei.to\\Desktop\\担当部分\\migrate\\springboot1.1.9";
//      String newDir = "C:\\Users\\yusei.to\\Desktop\\担当部分\\migrate\\springboot2.7.8";
//      String reportDir = "springboot1to2reports"; // 存放报告的目录
//      String oldDir = "C:\\Users\\yusei.to\\Desktop\\担当部分\\migrate\\springboot2.7.8";
//      String newDir = "C:\\Users\\yusei.to\\Desktop\\担当部分\\migrate\\springboot3.5.9";
//      String reportDir = "springboot2to3reports"; // 存放报告的目录

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
//        for (int i = 0; i < 1; i++) {
        for (int i = 0; i < n; i++) {
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

            String oldName = oldJar.getName().replace(".jar", "");
            String newName = newJar.getName().replace(".jar", "");
//            String reportFile = reportDir + File.separator + oldName + "_vs_" + newName + ".html";
            
            String sheetNewName = oldName.replaceFirst("-\\d.*", "");
            analyzeApis(jApiClasses,oldName + "_vs_" + newName,sheetNewName);
//            ModifiedBreakingAnalyzer.analyzeBreakingModified(jApiClasses);

            // 5. 写入单独报告
//            String oldName = oldJar.getName().replace(".jar", "");
//            String newName = newJar.getName().replace(".jar", "");
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
    
    public static void analyzeApis(List<JApiClass> jApiClasses, String reportFile, String sheetNewName) throws Exception {
        String projectPath = Paths.get("").toAbsolutePath().toString();
//      URL url = Main.class.getClassLoader().getResource(projectPath+"/src/main/webapp/templates/excel/demo.xlsx");
      ExcelMakeFile excelMakeFile = new ExcelMakeFile(new File(projectPath+"/resources/template.xlsx"));
      excelMakeFile.setSheetName(1, sheetNewName);
      XSSFSheet outputSheet =  excelMakeFile.workbook.getSheetAt(1);
      String sheetName = outputSheet.getSheetName();

      excelMakeFile.setCellValue(sheetName, "B" + 2, "モジュール：" + reportFile);
      
        for (JApiClass cls : jApiClasses) {
//        	boolean clsOutputFlag = true;
            String className = cls.getFullyQualifiedName();
            String classModifiers = OutputExcelGenerator.modifiers(cls);
            String classClassType = OutputExcelGenerator.classType(cls);
            className = classModifiers + classClassType + " " + className;
            // 排除内部类
            if (className.contains("$")) {
				continue;
			}

//            printCompatibilityChanges(
//                    "[CLASS]",
//                    className,
//                    cls.getCompatibilityChanges()
//            );


            /* =========================
             * 1. 类级别，整个类都被删除
             * ========================= */
            if (cls.getChangeStatus() == JApiChangeStatus.REMOVED) {
                System.out.println("[CLASS REMOVED] " + className);
                int sheetLastRowNum =  outputSheet.getLastRowNum()+2;
                excelMakeFile.setCellValue(sheetName, "B" + sheetLastRowNum, sheetLastRowNum-3);
                excelMakeFile.setCellValue(sheetName, "C" + sheetLastRowNum, className);
                if (classClassType.equals("annotation")) {
                    excelMakeFile.setCellValue(sheetName, "D" + sheetLastRowNum, "アノテーション");
				} else if (classClassType.equals("interface")) {
	                    excelMakeFile.setCellValue(sheetName, "D" + sheetLastRowNum, "インターフェース");
					} else {
	                excelMakeFile.setCellValue(sheetName, "D" + sheetLastRowNum, "クラス");
				}
                excelMakeFile.setCellValue(sheetName, "F" + sheetLastRowNum, "削除");
                
                setBorderStyle(excelMakeFile, sheetName, sheetLastRowNum);
//                continue;
            }

            if (cls.getChangeStatus() == JApiChangeStatus.MODIFIED) {
            	boolean deprecatedAdded =   hasDeprecatedAdded(cls.getCompatibilityChanges());
            	if (deprecatedAdded) {
            		System.out.println("[CLASS MODIFIED] " + className);
	                int sheetLastRowNum =  outputSheet.getLastRowNum()+2;
	                excelMakeFile.setCellValue(sheetName, "B" + sheetLastRowNum, sheetLastRowNum-3);
	                excelMakeFile.setCellValue(sheetName, "C" + sheetLastRowNum, className);
	                if (classClassType.equals("annotation")) {
	                    excelMakeFile.setCellValue(sheetName, "D" + sheetLastRowNum, "アノテーション");
					} else if (classClassType.equals("interface")) {
		                    excelMakeFile.setCellValue(sheetName, "D" + sheetLastRowNum, "インターフェース");
						} else {
		                excelMakeFile.setCellValue(sheetName, "D" + sheetLastRowNum, "クラス");
					}
	                excelMakeFile.setCellValue(sheetName, "F" + sheetLastRowNum, "変更");
	                excelMakeFile.setCellValue(sheetName, "G" + sheetLastRowNum, "有");
	                
	                setBorderStyle(excelMakeFile, sheetName, sheetLastRowNum);
				}
                
            }

            /* =========================
             * 2. 字段
             * ========================= */
            for (JApiField field : cls.getFields()) {
//                String fieldName = className + "#" + field.getName();
//                String fieldName = field.getName();

            	// 删除的场合
                String fieldName = OutputExcelGenerator.field(field,JApiChangeStatus.REMOVED);

                if (field.getChangeStatus() == JApiChangeStatus.REMOVED) {
                    System.out.println("[FIELD REMOVED] " + fieldName);
//                    clsOutputFlag = false;
                    int sheetLastRowNum =  outputSheet.getLastRowNum()+2;
                    excelMakeFile.setCellValue(sheetName, "B" + sheetLastRowNum, sheetLastRowNum-3);
                    excelMakeFile.setCellValue(sheetName, "C" + sheetLastRowNum, className);
                    excelMakeFile.setCellValue(sheetName, "D" + sheetLastRowNum, "フィールド");
                    excelMakeFile.setCellValue(sheetName, "E" + sheetLastRowNum, fieldName);
                    excelMakeFile.setCellValue(sheetName, "F" + sheetLastRowNum, "削除");
	                
	                setBorderStyle(excelMakeFile, sheetName, sheetLastRowNum);
                }

            	// 修改的场合
                fieldName = OutputExcelGenerator.field(field,JApiChangeStatus.MODIFIED);

                if (field.getChangeStatus() == JApiChangeStatus.MODIFIED) {
                    System.out.println("[FIELD MODIFIED] " + fieldName);
//                    clsOutputFlag = false;
                    int sheetLastRowNum =  outputSheet.getLastRowNum()+2;
                    excelMakeFile.setCellValue(sheetName, "B" + sheetLastRowNum, sheetLastRowNum-3);
                    excelMakeFile.setCellValue(sheetName, "C" + sheetLastRowNum, className);
                    excelMakeFile.setCellValue(sheetName, "D" + sheetLastRowNum, "フィールド");
                    excelMakeFile.setCellValue(sheetName, "E" + sheetLastRowNum, fieldName);
                    excelMakeFile.setCellValue(sheetName, "F" + sheetLastRowNum, "変更");
                	boolean deprecatedAdded =   hasDeprecatedAdded(field.getCompatibilityChanges());
                    if (deprecatedAdded) {
                    	excelMakeFile.setCellValue(sheetName, "G" + sheetLastRowNum, "有");
    				} else {
    					excelMakeFile.setCellValue(sheetName, "G" + sheetLastRowNum, "無");
    				}
	                
	                setBorderStyle(excelMakeFile, sheetName, sheetLastRowNum);
                }

//                printCompatibilityChanges(
//                        "[FIELD]",
//                        fieldName,
//                        field.getCompatibilityChanges()
//                );
            }

            /* =========================
             * 3. 构造函数
             * ========================= */
            for (JApiConstructor constructor : cls.getConstructors()) {
//                String ctorName = className + buildConstructorSignature(constructor);
            	// 删除的场合
                String ctorName = OutputExcelGenerator.constructor(constructor,JApiChangeStatus.REMOVED);
//                String methodName = MethodSignatureBuilder025.buildFullMethodSignature(method);
//                String ctorName = className.substring(className.lastIndexOf('.') + 1) + buildConstructorSignature(constructor);

                if (constructor.getChangeStatus() == JApiChangeStatus.REMOVED) {
                    System.out.println("[CONSTRUCTOR REMOVED] " + ctorName);
                    int sheetLastRowNum =  outputSheet.getLastRowNum()+2;
//                    clsOutputFlag = false;
                    excelMakeFile.setCellValue(sheetName, "B" + sheetLastRowNum, sheetLastRowNum-3);
                    excelMakeFile.setCellValue(sheetName, "C" + sheetLastRowNum, className);
                    excelMakeFile.setCellValue(sheetName, "D" + sheetLastRowNum, "コンストラクタ");
                    excelMakeFile.setCellValue(sheetName, "E" + sheetLastRowNum, ctorName);
                    excelMakeFile.setCellValue(sheetName, "F" + sheetLastRowNum, "削除");
	                
	                setBorderStyle(excelMakeFile, sheetName, sheetLastRowNum);
                }
            	
            	// 修改的场合
                ctorName = OutputExcelGenerator.constructor(constructor,JApiChangeStatus.MODIFIED);
//                String methodName = MethodSignatureBuilder025.buildFullMethodSignature(method);
//                String ctorName = className.substring(className.lastIndexOf('.') + 1) + buildConstructorSignature(constructor);

                if (constructor.getChangeStatus() == JApiChangeStatus.MODIFIED) {
                    System.out.println("[CONSTRUCTOR REMOVED] " + ctorName);
                    int sheetLastRowNum =  outputSheet.getLastRowNum()+2;
//                    clsOutputFlag = false;
                    excelMakeFile.setCellValue(sheetName, "B" + sheetLastRowNum, sheetLastRowNum-3);
                    excelMakeFile.setCellValue(sheetName, "C" + sheetLastRowNum, className);
                    excelMakeFile.setCellValue(sheetName, "D" + sheetLastRowNum, "コンストラクタ");
                    excelMakeFile.setCellValue(sheetName, "E" + sheetLastRowNum, ctorName);
                    excelMakeFile.setCellValue(sheetName, "F" + sheetLastRowNum, "変更");
                	boolean deprecatedAdded =   hasDeprecatedAdded(constructor.getCompatibilityChanges());
                    if (deprecatedAdded) {
                    	excelMakeFile.setCellValue(sheetName, "G" + sheetLastRowNum, "有");
    				} else {
    					excelMakeFile.setCellValue(sheetName, "G" + sheetLastRowNum, "無");
    				}
	                
	                setBorderStyle(excelMakeFile, sheetName, sheetLastRowNum);
                }

//                printCompatibilityChanges(
//                        "[CONSTRUCTOR]",
//                        ctorName,
//                        constructor.getCompatibilityChanges()
//                );
            }

            /* =========================
             * 4. 方法
             * ========================= */
            for (JApiMethod method : cls.getMethods()) {
//                String methodName = className + "#" + buildMethodSignature(method);
            	
            	// 删除的场合
                String methodName = OutputExcelGenerator.methodTBody(method,JApiChangeStatus.REMOVED);
//              String methodName = MethodSignatureBuilder025.buildFullMethodSignature(method);
                // 排除aspectjs的方法
                if (methodName.contains("ajc$")) {
					continue;
				}

                if (method.getChangeStatus() == JApiChangeStatus.REMOVED) {
                    System.out.println("[METHOD REMOVED] " + methodName);
//                    clsOutputFlag = false;
                    int sheetLastRowNum =  outputSheet.getLastRowNum()+2;
                    excelMakeFile.setCellValue(sheetName, "B" + sheetLastRowNum, sheetLastRowNum-3);
                    excelMakeFile.setCellValue(sheetName, "C" + sheetLastRowNum, className);
                    excelMakeFile.setCellValue(sheetName, "D" + sheetLastRowNum, "メソッド");
                    excelMakeFile.setCellValue(sheetName, "E" + sheetLastRowNum, methodName);
                    excelMakeFile.setCellValue(sheetName, "F" + sheetLastRowNum, "削除");
	                
	                setBorderStyle(excelMakeFile, sheetName, sheetLastRowNum);
                }
            	
            	// 修改的场合
                methodName = OutputExcelGenerator.methodTBody(method,JApiChangeStatus.MODIFIED);
//                Strig methodName = MethodSignatureBuilder025.buildFullMethodSignature(method);
                // 排除aspectjs的方法
                if (methodName.contains("ajc$")) {
					continue;
				}

                if (method.getChangeStatus() == JApiChangeStatus.MODIFIED) {
                    System.out.println("[METHOD REMOVED] " + methodName);
//                    clsOutputFlag = false;
                    int sheetLastRowNum =  outputSheet.getLastRowNum()+2;
                    excelMakeFile.setCellValue(sheetName, "B" + sheetLastRowNum, sheetLastRowNum-3);
                    excelMakeFile.setCellValue(sheetName, "C" + sheetLastRowNum, className);
                    excelMakeFile.setCellValue(sheetName, "D" + sheetLastRowNum, "メソッド");
                    excelMakeFile.setCellValue(sheetName, "E" + sheetLastRowNum, methodName);
                    excelMakeFile.setCellValue(sheetName, "F" + sheetLastRowNum, "変更");
                	boolean deprecatedAdded =   hasDeprecatedAdded(method.getCompatibilityChanges());
                    if (deprecatedAdded) {
                    	excelMakeFile.setCellValue(sheetName, "G" + sheetLastRowNum, "有");
    				} else {
    					excelMakeFile.setCellValue(sheetName, "G" + sheetLastRowNum, "無");
    				}
	                
	                setBorderStyle(excelMakeFile, sheetName, sheetLastRowNum);
                }

//                printCompatibilityChanges(
//                        "[METHOD]",
//                        methodName,
//                        method.getCompatibilityChanges()
//                );
            }
        }

        
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
//        String fileName = "demo".concat(df.format(LocalDateTime.now()));
//        fileName = fileName.concat(".xlsx");
        String fileName = reportFile.concat(".xlsx");
        Files.write(Paths.get(projectPath+"/outputspring4to5files/" +fileName), excelMakeFile.getBytes());
//        Files.write(Paths.get(projectPath+"/outputspring5to6files/" +fileName), excelMakeFile.getBytes());
//        Files.write(Paths.get(projectPath+"/outputspringboot1to2files/" +fileName), excelMakeFile.getBytes());
//      Files.write(Paths.get(projectPath+"/outputspringboot2to3files/" +fileName), excelMakeFile.getBytes());
        
    }
    
    private static boolean hasDeprecatedAdded(
            List<JApiCompatibilityChange> changes) {

        for (JApiCompatibilityChange change : changes) {
            if (change.getType()
                    == JApiCompatibilityChangeType.ANNOTATION_DEPRECATED_ADDED) {
                return true;
            }
        }
        return false;
    }
    
    private static void setBorderStyle(ExcelMakeFile excelMakeFile,String sheetName,int sheetLastRowNum){

        // 设置列范围 B~I
        String[] cols = {"B","C","D","E","F","G","H","I"};
        for (String colLetter : cols) {
            excelMakeFile.setBorder(sheetName, colLetter+sheetLastRowNum, BorderStyle.THIN);
        }
    }
}

