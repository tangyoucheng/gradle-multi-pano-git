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
import japicmp.model.JApiModifier;
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
//        String oldDir = "C:\\Users\\yusei.to\\Desktop\\担当部分\\migrate\\springframework4.1.6";
//        String newDir = "C:\\Users\\yusei.to\\Desktop\\担当部分\\migrate\\springframework5.3.31";
//        String reportDir = "spring4to5reports"; // 存放HTML报告的目录
//        String outputDir = "outputspring4to5files"; // 存放EXCEL报告的目录
//        String oldDir = "C:\\Users\\yusei.to\\Desktop\\担当部分\\migrate\\springframework5.3.31";
//        String newDir = "C:\\Users\\yusei.to\\Desktop\\担当部分\\migrate\\springframework6.2.15";
//        String reportDir = "spring5to6reports"; // 存放报告的目录
//        String outputDir = "outputspring5to6files"; // 存放EXCEL报告的目录

//      String oldDir = "C:\\Users\\yusei.to\\Desktop\\担当部分\\migrate\\springboot1.1.9";
//      String newDir = "C:\\Users\\yusei.to\\Desktop\\担当部分\\migrate\\springboot2.7.18";
//      String reportDir = "springboot1to2reports"; // 存放HTML报告的目录
//      String outputDir = "outputspringboot1to2files"; // 存放EXCEL报告的目录
//      String oldDir = "C:\\Users\\yusei.to\\Desktop\\担当部分\\migrate\\springboot2.7.18";
//      String newDir = "C:\\Users\\yusei.to\\Desktop\\担当部分\\migrate\\springboot3.5.9";
//      String reportDir = "springboot2to3reports"; // 存放HTML报告的目录
//      String outputDir = "outputspringboot2to3files"; // 存放EXCEL报告的目录

//      String oldDir = "C:\\Users\\yusei.to\\Desktop\\担当部分\\migrate\\spring4other";
//      String newDir = "C:\\Users\\yusei.to\\Desktop\\担当部分\\migrate\\spring5other";
//      String reportDir = "spring4to5otherreports"; // 存放HTML报告的目录
//      String outputDir = "outputspring4to5otherfiles"; // 存放EXCEL报告的目录
      String oldDir = "C:\\Users\\yusei.to\\Desktop\\担当部分\\migrate\\spring5other";
      String newDir = "C:\\Users\\yusei.to\\Desktop\\担当部分\\migrate\\spring6other";
      String reportDir = "spring5to6otherreports"; // 存放HTML报告的目录
      String outputDir = "outputspring5to6otherfiles"; // 存放EXCEL报告的目录

        // 指定的不存在的场合就做成。
        File reportFolder = new File(reportDir);
        if (!reportFolder.exists()) {
            reportFolder.mkdirs();
        }
        File outputFolder = new File(outputDir);
        if (!outputFolder.exists()) {
        	outputFolder.mkdirs();
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
        options.setOutputOnlyBinaryIncompatibleModifications(false); // 只输出二进制不兼容变化
//        options.setNoAnnotations(false); // 一定要启用，否则注解变化会被忽略

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
            
            // excel的sheet名称长度不能超过31。所以把去掉小版本信息
//            String sheetNewName = newName.replace(".7.18", "");//2.7.18
//            sheetNewName = sheetNewName.replace(".5.9", "");//3.5.9
//            sheetNewName = sheetNewName.replace(".3.31", "");//5.3.31
//            sheetNewName = sheetNewName.replace(".2.15", "");//6.2.15
            
            String sheetNewName = getDiffJarName(oldJar.getName(), newJar.getName());
            
            // 生成excel
            analyzeApis(jApiClasses,oldName + "_vs_" + newName,sheetNewName,outputDir);
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
    
    public static void analyzeApis(List<JApiClass> jApiClasses, String reportFile, String sheetNewName, String outputDir) throws Exception {
      String projectPath = Paths.get("").toAbsolutePath().toString();
      ExcelMakeFile excelMakeFile = new ExcelMakeFile(new File(projectPath+"/resources/template.xlsx"));
      excelMakeFile.setSheetName(1, sheetNewName);
      XSSFSheet outputSheet =  excelMakeFile.workbook.getSheetAt(1);
      String sheetName = outputSheet.getSheetName();

      excelMakeFile.setCellValue(sheetName, "B" + 2, "モジュール：" + reportFile);
      
        for (JApiClass cls : jApiClasses) {
            String className = cls.getFullyQualifiedName();

//            if (className.contains("StringUtils")) {
//				System.out.println(1);
//			}
            
            String classModifiers = OutputExcelGenerator.modifiers(cls);
            String classClassType = OutputExcelGenerator.classType(cls);
            className = classModifiers + classClassType + " " + className;
            // 排除内部类
            if (className.contains("$")) {
				continue;
			}

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
				System.out.println("[CLASS MODIFIED] " + className);
				int sheetLastRowNum = outputSheet.getLastRowNum() + 2;
				excelMakeFile.setCellValue(sheetName, "B" + sheetLastRowNum, sheetLastRowNum - 3);
				excelMakeFile.setCellValue(sheetName, "C" + sheetLastRowNum, className);
				if (classClassType.equals("annotation")) {
					excelMakeFile.setCellValue(sheetName, "D" + sheetLastRowNum, "アノテーション");
				} else if (classClassType.equals("interface")) {
					excelMakeFile.setCellValue(sheetName, "D" + sheetLastRowNum, "インターフェース");
				} else {
					excelMakeFile.setCellValue(sheetName, "D" + sheetLastRowNum, "クラス");
				}
				excelMakeFile.setCellValue(sheetName, "F" + sheetLastRowNum, "変更");

				boolean deprecatedAdded = hasDeprecatedAdded(cls.getCompatibilityChanges());
				if (deprecatedAdded) {
					excelMakeFile.setCellValue(sheetName, "G" + sheetLastRowNum, "有");
				} else {
					excelMakeFile.setCellValue(sheetName, "G" + sheetLastRowNum, "無");
				}

				setBorderStyle(excelMakeFile, sheetName, sheetLastRowNum);

			}
            
            // 只检测是否是被弃用的构造函数，也就是是否有Deprecated注解
            if (cls.getChangeStatus() == JApiChangeStatus.UNCHANGED
					&& hasDeprecatedAdded(cls.getCompatibilityChanges())) {

				System.out.println("[CLASS MODIFIED] " + className);
				//                      clsOutputFlag = false;
				int sheetLastRowNum = outputSheet.getLastRowNum() + 2;
				excelMakeFile.setCellValue(sheetName, "B" + sheetLastRowNum, sheetLastRowNum - 3);
				excelMakeFile.setCellValue(sheetName, "C" + sheetLastRowNum, className);
				if (classClassType.equals("annotation")) {
					excelMakeFile.setCellValue(sheetName, "D" + sheetLastRowNum, "アノテーション");
				} else if (classClassType.equals("interface")) {
					excelMakeFile.setCellValue(sheetName, "D" + sheetLastRowNum, "インターフェース");
				} else {
					excelMakeFile.setCellValue(sheetName, "D" + sheetLastRowNum, "クラス");
				}
				excelMakeFile.setCellValue(sheetName, "F" + sheetLastRowNum, "廃止予定");
				//                  	boolean deprecatedAdded =   hasDeprecatedAdded(method.getCompatibilityChanges());
				//                      if (deprecatedAdded) {
				excelMakeFile.setCellValue(sheetName, "G" + sheetLastRowNum, "有");
				//      				} else {
				//      					excelMakeFile.setCellValue(sheetName, "G" + sheetLastRowNum, "無");
				//      				}

				setBorderStyle(excelMakeFile, sheetName, sheetLastRowNum);
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
                
                // 只检测是否是被弃用的构造函数，也就是是否有Deprecated注解
                if (field.getChangeStatus() == JApiChangeStatus.UNCHANGED
						&& hasDeprecatedAdded(field.getCompatibilityChanges())) {

					// 签名不变的场合
					fieldName = OutputExcelGenerator.field(field, JApiChangeStatus.UNCHANGED);

					System.out.println("[FIELD MODIFIED] " + fieldName);
					//                          clsOutputFlag = false;
					int sheetLastRowNum = outputSheet.getLastRowNum() + 2;
					excelMakeFile.setCellValue(sheetName, "B" + sheetLastRowNum, sheetLastRowNum - 3);
					excelMakeFile.setCellValue(sheetName, "C" + sheetLastRowNum, className);
					excelMakeFile.setCellValue(sheetName, "D" + sheetLastRowNum, "フィールド");
					excelMakeFile.setCellValue(sheetName, "E" + sheetLastRowNum, fieldName);
					excelMakeFile.setCellValue(sheetName, "F" + sheetLastRowNum, "廃止予定");
					//                      	boolean deprecatedAdded =   hasDeprecatedAdded(method.getCompatibilityChanges());
					//                          if (deprecatedAdded) {
					excelMakeFile.setCellValue(sheetName, "G" + sheetLastRowNum, "有");
					//          				} else {
					//          					excelMakeFile.setCellValue(sheetName, "G" + sheetLastRowNum, "無");
					//          				}

					setBorderStyle(excelMakeFile, sheetName, sheetLastRowNum);
				}

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
                
                // 只检测是否是被弃用的构造函数，也就是是否有Deprecated注解
                if (constructor.getChangeStatus() == JApiChangeStatus.UNCHANGED
						&& hasDeprecatedAdded(constructor.getCompatibilityChanges())) {

					// 签名不变的场合
					ctorName = OutputExcelGenerator.constructor(constructor, JApiChangeStatus.UNCHANGED);

					System.out.println("[METHOD MODIFIED] " + constructor);
					//                          clsOutputFlag = false;
					int sheetLastRowNum = outputSheet.getLastRowNum() + 2;
					excelMakeFile.setCellValue(sheetName, "B" + sheetLastRowNum, sheetLastRowNum - 3);
					excelMakeFile.setCellValue(sheetName, "C" + sheetLastRowNum, className);
					excelMakeFile.setCellValue(sheetName, "D" + sheetLastRowNum, "コンストラクタ");
					excelMakeFile.setCellValue(sheetName, "E" + sheetLastRowNum, ctorName);
					excelMakeFile.setCellValue(sheetName, "F" + sheetLastRowNum, "廃止予定");
					//                      	boolean deprecatedAdded =   hasDeprecatedAdded(method.getCompatibilityChanges());
					//                          if (deprecatedAdded) {
					excelMakeFile.setCellValue(sheetName, "G" + sheetLastRowNum, "有");
					//          				} else {
					//          					excelMakeFile.setCellValue(sheetName, "G" + sheetLastRowNum, "無");
					//          				}

					setBorderStyle(excelMakeFile, sheetName, sheetLastRowNum);
				}

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
                    System.out.println("[METHOD MODIFIED] " + methodName);
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
                

            	
                // 只检测是否是被弃用的方法，也就是是否有Deprecated注解
                if (method.getChangeStatus() == JApiChangeStatus.UNCHANGED
						&& hasDeprecatedAdded(method.getCompatibilityChanges())) {

					// 签名不变的场合
					methodName = OutputExcelGenerator.methodTBody(method, JApiChangeStatus.UNCHANGED);
					//                    Strig methodName = MethodSignatureBuilder025.buildFullMethodSignature(method);
					// 排除aspectjs的方法
					if (methodName.contains("ajc$")) {
						continue;
					}

					System.out.println("[METHOD MODIFIED] " + methodName);
					//                          clsOutputFlag = false;
					int sheetLastRowNum = outputSheet.getLastRowNum() + 2;
					excelMakeFile.setCellValue(sheetName, "B" + sheetLastRowNum, sheetLastRowNum - 3);
					excelMakeFile.setCellValue(sheetName, "C" + sheetLastRowNum, className);
					excelMakeFile.setCellValue(sheetName, "D" + sheetLastRowNum, "メソッド");
					excelMakeFile.setCellValue(sheetName, "E" + sheetLastRowNum, methodName);
					excelMakeFile.setCellValue(sheetName, "F" + sheetLastRowNum, "廃止予定");
					//                      	boolean deprecatedAdded =   hasDeprecatedAdded(method.getCompatibilityChanges());
					//                          if (deprecatedAdded) {
					excelMakeFile.setCellValue(sheetName, "G" + sheetLastRowNum, "有");
					//          				} else {
					//          					excelMakeFile.setCellValue(sheetName, "G" + sheetLastRowNum, "無");
					//          				}

					setBorderStyle(excelMakeFile, sheetName, sheetLastRowNum);

				}
                
                
                
                // 只考虑新增方法
                if (method.getChangeStatus() == JApiChangeStatus.NEW) {
                	// //TODO 没有必须实现的新增方法
                	boolean isInterface = classClassType.equals("interface")?true:false;
                    methodName = OutputExcelGenerator.methodTBody(method,JApiChangeStatus.NEW);
                    // 不是接口的缺省方法或抽象类的抽象方法
                    if (isInterface && !methodName.contains("default ") || methodName.contains("abstract ")) {
                    	System.out.println(methodName);
                    }

                }


            }
        }

        
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
//        String fileName = "demo".concat(df.format(LocalDateTime.now()));
//        fileName = fileName.concat(".xlsx");
        String fileName = reportFile.concat(".xlsx");
		Files.write(Paths.get(projectPath + "/" + outputDir + "/" + fileName), excelMakeFile.getBytes());
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
    
    
    // 提取 jar 名称前缀和版本号
    public static String[] splitJarName(String jarName) {
        int lastDash = jarName.lastIndexOf('-');
        int dotJar = jarName.lastIndexOf(".jar");
        if (lastDash >= 0 && dotJar > lastDash) {
            String prefix = jarName.substring(0, lastDash);   // spring-boot
            String version = jarName.substring(lastDash + 1, dotJar); // 1.1.9.RELEASE
            return new String[]{prefix, version};
        }
        return new String[]{jarName, ""}; // 提取失败
    }

    /*
     * String oldJar = "spring-boot-1.1.9.RELEASE.jar";
     * String newJar = "spring-boot-1.7.18.jar";
     * 输出 "spring-boot-1.7"
     */
    public static String getDiffJarName(String oldJar, String newJar) {
        String[] oldSplit = splitJarName(oldJar);
        String[] newSplit = splitJarName(newJar);

        String oldVersion = oldSplit[1];
        String newVersion = newSplit[1];
        String prefix = newSplit[0];

        String[] oldParts = oldVersion.split("\\.");
        String[] newParts = newVersion.split("\\.");

        int diffIndex = newParts.length;

        for (int i = 0; i < newParts.length; i++) {
            int newNum;
            try {
                newNum = Integer.parseInt(newParts[i].replaceAll("\\D.*", ""));
            } catch (NumberFormatException e) {
                newNum = -1;
            }

            int oldNum = -1;
            if (i < oldParts.length) {
                try {
                    oldNum = Integer.parseInt(oldParts[i].replaceAll("\\D.*", ""));
                } catch (NumberFormatException e) {
                    oldNum = -1;
                }
            }

            if (newNum != oldNum) {
                diffIndex = i + 1;
                break;
            }
        }

        // 拼接前缀 + 版本号部分
        StringBuilder result = new StringBuilder(prefix);
        if (diffIndex > 0) {
            result.append("-"); // 前缀和版本号之间用 -
            for (int i = 0; i < diffIndex; i++) {
                if (i > 0) result.append("."); // 版本号段之间用 .
                result.append(newParts[i]);
            }
        }

        return result.toString();
    }

    
}
