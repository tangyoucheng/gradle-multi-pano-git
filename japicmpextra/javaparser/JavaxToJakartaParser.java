package javaparser;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.printer.lexicalpreservation.LexicalPreservingPrinter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class JavaxToJakartaParser {

    public static void main(String[] args) throws Exception {
//        File rootDir = new File("C:\\workspace_kyousera\\CM003\\src");
//        File rootDir = new File("C:\\workspace_kyousera\\CM093\\src");
//        File rootDir = new File("C:\\workspace_kyousera\\unicorn3.framework\\src");

        File rootDir = new File("C:\\Kyocera_src\\UnicornSI\\WorkspaceVerup\\Unicorn3\\src");

        if (!rootDir.exists() || !rootDir.isDirectory()) {
            System.out.println("目录不存在: " + rootDir.getAbsolutePath());
            return;
        }

        File backupDir = new File(rootDir.getParentFile(), "_backup");
        if (!backupDir.exists()) backupDir.mkdirs();

        processDirectory(rootDir, backupDir);
        System.out.println("处理完成！");
    }

    private static void processDirectory(File dir, File backupDir) throws Exception {
        File[] files = dir.listFiles();
        if (files == null) return;

        for (File file : files) {
            if (file.isDirectory()) {
                File subBackup = new File(backupDir, file.getName());
                if (!subBackup.exists()) subBackup.mkdirs();
                processDirectory(file, subBackup);
            } else if (file.getName().endsWith(".java")) {
                processJavaFile(file, backupDir);
            }
        }
    }

    private static void processJavaFile(File file, File backupDir) throws Exception {
        // 备份原文件
        File backupFile = new File(backupDir, file.getName());
//        Files.copy(file.toPath(), backupFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

        boolean modified = false;

        try (FileInputStream in = new FileInputStream(file)) {
            CompilationUnit cu = StaticJavaParser.parse(in);
            // 开启 LexicalPreserving
            LexicalPreservingPrinter.setup(cu);

            for (ImportDeclaration imp : cu.getImports()) {
                String name = imp.getNameAsString();
                if (name.startsWith("javax.servlet")) {
                    String newName = name.replaceFirst("javax\\.servlet", "jakarta.servlet");
                    imp.setName(newName);
                    modified = true;
                }
                if (name.startsWith("javax.annotation")) {
                	String newName = name.replaceFirst("javax\\.annotation", "jakarta.annotation");
                	imp.setName(newName);
                	modified = true;
                }
                if (name.startsWith("javax.ws")) {
                	String newName = name.replaceFirst("javax\\.ws", "jakarta.ws");
                	imp.setName(newName);
                	modified = true;
                }
                if (name.contains("fileupload.FileItem")) {
                	String newName = name.replaceFirst("fileupload\\.FileItem", "fileupload2.core.FileItem");
                	imp.setName(newName);
                	modified = true;
                }
                if (name.contains("org.codehaus.jackson.map.ObjectMapper")) {
                	String newName = name.replaceFirst("org\\.codehaus\\.jackson\\.map\\.ObjectMapper", "com.fasterxml.jackson.databind.ObjectMapper");
                	imp.setName(newName);
                	modified = true;
                }
            }

            if (modified) {
                // 写回原文件
                try (FileOutputStream out = new FileOutputStream(file)) {
                    out.write(LexicalPreservingPrinter.print(cu).getBytes());
                    System.out.println("已修改: " + file.getAbsolutePath());
                }
            }
        } catch (Exception e) {
            System.out.println("处理失败: " + file.getAbsolutePath());
        }
    }
}
