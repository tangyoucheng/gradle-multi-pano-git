package javaparser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;

import cn.com.platform.framework.common.exception.SystemException;
import cn.com.platform.framework.file.ExcelMakeFile;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;

public class Main {
    public static void main(String[] args) throws InvalidFormatException, IOException, SystemException {

        String code = ""
                + "class Hello {"
                + "    void sayHello() { System.out.println(\"Hello\"); }"
                + "}";

        // 解析源码
        CompilationUnit cu = StaticJavaParser.parse(code);

        // 获取类名
        cu.findAll(ClassOrInterfaceDeclaration.class).forEach(cls ->
                System.out.println("类名：" + cls.getNameAsString())
        );

        // 获取方法名
        cu.findAll(MethodDeclaration.class).forEach(method ->
                System.out.println("方法名：" + method.getNameAsString())
        );
        

        String projectPath = Paths.get("").toAbsolutePath().toString();
//        URL url = Main.class.getClassLoader().getResource(projectPath+"/src/main/webapp/templates/excel/demo.xlsx");
        ExcelMakeFile excelMakeFile = new ExcelMakeFile(new File(projectPath+"/src/main/webapp/templates/excel/demo.xlsx"));
        XSSFSheet outputSheet =  excelMakeFile.workbook.getSheetAt(0);
        
        // excel文件的详细内容
        excelMakeFile.setCellValue(outputSheet.getSheetName(), "A1", "类名：");
        excelMakeFile.setCellValue(outputSheet.getSheetName(), "B1", "方法名：");
        
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        String fileName = "demo".concat(df.format(LocalDateTime.now()));
        fileName = fileName.concat(".xlsx");
        Files.write(Paths.get(projectPath+"/out/" +fileName), excelMakeFile.getBytes());
   }
}
