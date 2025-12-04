package freemarker;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.File;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.*;

public class GenerateJava {

    public static void main(String[] args) throws Exception {

        // 1. FreeMarker 配置
        String projectPath = Paths.get("").toAbsolutePath().toString();
        String templateDir = projectPath + "/src/main/webapp/templates/freemarker";
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_32);
        cfg.setDirectoryForTemplateLoading(new File(templateDir));
        cfg.setDefaultEncoding(StandardCharsets.UTF_8.name());

        // 2. 加载模板
        Template template = cfg.getTemplate("class.ftl");

        // 3. 数据模型
        Map<String, Object> data = new HashMap<>();
        data.put("packageName", "com.example.generated");
        data.put("className", "User");

        // 字段列表
        List<Map<String, Object>> fields = new ArrayList<>();
        fields.add(Map.of("name", "id","name", "id", "type", "int"));
        fields.add(Map.of("name", "id","name", "name", "type", "String"));
        fields.add(Map.of("name", "id","name", "email", "type", "String"));
        data.put("fields", fields);

        // 4. 输出文件
        File outputDir = new File("out/com/example/generated");
        outputDir.mkdirs();

        File javaFile = new File(outputDir, "User.java");

        try (FileWriter writer = new FileWriter(javaFile)) {
            template.process(data, writer);
        }

        System.out.println("生成成功：" + javaFile.getAbsolutePath());
    }
}

