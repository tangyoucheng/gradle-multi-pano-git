package freemarker;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.File;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.*;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FontUnderline;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import cn.com.platform.framework.file.ExcelMakeFile;

public class GenerateJavaCsv {

	public static final String DATATYPE_STRING = "String";
	public static final String DATATYPE_NUMBER = "Number";
	public static final String DATATYPE_DATE = "Date";

	// 类型映射表
	public static final Map<String, String> TYPE_MAP = Map.of(
			"String", DATATYPE_STRING,
			"Num", DATATYPE_NUMBER,
			"Date", DATATYPE_DATE);

	/**
	 * 通过输入关键字取得对应的数据类型
	 */
	public static String getDataType(String key) {
		return TYPE_MAP.get(key);
	}

	public static void main(String[] args) throws Exception {

		// 1. FreeMarker 配置
		String projectPath = Paths.get("").toAbsolutePath().toString();
		String templateDir = projectPath + "/src/main/webapp/templates/freemarker";
		Configuration cfg = new Configuration(Configuration.VERSION_2_3_32);
		cfg.setDirectoryForTemplateLoading(new File(templateDir));
		cfg.setDefaultEncoding(StandardCharsets.UTF_8.name());

		// 2. 加载模板
		Template template = cfg.getTemplate("classcsv.ftl");

		// 3. 数据模型
		Map<String, Object> data = new HashMap<>();
		data.put("packageName", "com.example.generated");
		data.put("className", "UserCsv");

		// 字段列表
		ExcelMakeFile excelMakeFile = new ExcelMakeFile(
				new File(projectPath + "/src/main/webapp/templates/excel/importcsv/ab_020209_入出力ファイル定義書.xlsx"));
		XSSFSheet outputSheet = excelMakeFile.workbook.getSheet("受注（取込）");

		List<Map<String, Object>> fields = new ArrayList<>();
		for (int i = 8; i <= 146; i++) {
			String comment = excelMakeFile.getStringCellValue(outputSheet.getSheetName(), "C" + i);
			int columnIndex = excelMakeFile.getDoubleCellValue(outputSheet.getSheetName(), "A" + i).intValue();

			// 属性
			XSSFCell typeCell = excelMakeFile.getCell(outputSheet, "I" + i);
			String nonStrikeUnderlineText = getCleanText(typeCell, excelMakeFile.workbook);
			String dataType = getDataType(nonStrikeUnderlineText);
			System.out.println(comment + " 无删除线的文本: " + dataType);
			
			// 必須
			String require = "false";
			typeCell = excelMakeFile.getCell(outputSheet, "N" + i);
			nonStrikeUnderlineText = getCleanText(typeCell, excelMakeFile.workbook);
			if("○".equals(nonStrikeUnderlineText)) {
				require = "true";
			}
			
			
			fields.add(createField(comment, "id", "String", columnIndex, dataType,require));
			//fields.add(createField("名", "name", "String", "2"));
			//fields.add(createField("メール", "email", "String", "3"));

			data.put("fields", fields);
		}

		// 4. 输出文件
		File outputDir = new File("out/com/example/generated");
		outputDir.mkdirs();

		File javaFile = new File(outputDir, "UserCsv.java");

		try (FileWriter writer = new FileWriter(javaFile)) {
			template.process(data, writer);
		}

		System.out.println("生成成功：" + javaFile.getAbsolutePath());
	}

	private static Map<String, Object> createField(String comment, String name, String type, int columnIndex,
			String dataType,
			String require) {
		Map<String, Object> map = new HashMap<>();
		map.put("comment", comment);
		map.put("name", name);
		map.put("type", type);
		map.put("columnIndex", columnIndex);
		map.put("dataType", dataType);
		map.put("require", require);
		return map;
	}
	/**
	 * 获取 Excel 单元格中无删除线、无下划线、无换行符的文本内容
	 */
	public static String getCleanText(XSSFCell cell, XSSFWorkbook workbook) {
	    StringBuilder result = new StringBuilder();

	    if (cell == null || cell.getCellType() != CellType.STRING) {
	        return "";
	    }

	    XSSFRichTextString richText = cell.getRichStringCellValue();
	    int numRuns = richText.numFormattingRuns();

	    if (numRuns > 0) {
	        // 处理带格式的片段
	        for (int i = 0; i < numRuns; i++) {
	            int startIndex = richText.getIndexOfFormattingRun(i);
	            int endIndex = (i + 1 < numRuns)
	                    ? richText.getIndexOfFormattingRun(i + 1)
	                    : richText.length();

	            XSSFFont font = richText.getFontOfFormattingRun(i);
	            String segment = richText.getString().substring(startIndex, endIndex);

	            // 去掉所有换行符
	            segment = segment.replace("\n", "").replace("\r", "");

	            boolean hasStrike = font != null && font.getStrikeout();
	            boolean hasUnderline = font != null && font.getUnderline() != FontUnderline.NONE.getByteValue();

	            if (!hasStrike && !hasUnderline) {
	                result.append(segment);
	            }
	        }

	    } else {
	        // 无格式化片段：取整段字体
	        XSSFFont font = workbook.getFontAt(cell.getCellStyle().getFontIndex());

	        boolean hasStrike = font != null && font.getStrikeout();
	        boolean hasUnderline = font != null && font.getUnderline() != FontUnderline.NONE.getByteValue();

	        if (!hasStrike && !hasUnderline) {
	            String whole = richText.getString();
	            whole = whole.replace("\n", "").replace("\r", "");
	            result.append(whole);
	        }
	    }

	    return result.toString();
	}
}
