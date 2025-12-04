package javaparser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParserConfiguration;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.comments.JavadocComment;

import jp.co.nttdatakansai.scaw.fw.csv.reader.CsvColumn;
import jp.co.nttdatakansai.scaw.sebn.AB.ABR0105.bean.ABD0105CSVData;
import jp.co.nttdatakansai.scaw.sebn.BA.BAR0113.bean.BAD0113CSVData;
import jp.co.nttdatakansai.scaw.sebn.BA.BAR0138.bean.BAD0138CSVData;
import jp.co.nttdatakansai.scaw.sebn.BA.BAR0139.bean.BAD0139CSVData;
import jp.co.nttdatakansai.scaw.sebn.BA.BAR3061.bean.BAD3061CSVData;
import jp.co.nttdatakansai.scaw.sebn.CA.CAR0117.bean.CAD0117CSVData;
import jp.co.nttdatakansai.scaw.sebn.CA.CAR0409.bean.CAD0409CSVData;
import jp.co.nttdatakansai.scaw.sebn.CB.CBR0906.bean.CBD0906CSVData;
import jp.co.nttdatakansai.scaw.sebn.CC.CCR0305.bean.CCD0305CSVData;
import jp.co.nttdatakansai.scaw.sebn.CC.CCR0306.bean.CCD0306CSVData;
import jp.co.nttdatakansai.scaw.sebn.CD.CDR0113.bean.CDD0113CSVData;
import jp.co.nttdatakansai.scaw.sebn.CD.CDR1909.bean.CDD1909CSVData;
import jp.co.nttdatakansai.scaw.sebn.CD.CDR2106.bean.CDD2106CSVData;
import jp.co.nttdatakansai.scaw.sebn.CE.CER0013.bean.CED0013CSVData;
import jp.co.nttdatakansai.scaw.sebn.CE.CER1904.bean.CED1904CSVData;
import jp.co.nttdatakansai.scaw.sebn.CE.CER3104.bean.CED3104CSVData;
import jp.co.nttdatakansai.scaw.sebn.CE.CER3105.bean.CED3105CSVData;
import jp.co.nttdatakansai.scaw.sebn.EA.EAR3011.bean.EAD3011CSVData;

public class CsvAnnotationExporter {
	public static void main(String[] args) {
		try {
			Class<?>[] csvClasses = new Class<?>[] {
					ABD0105CSVData.class,
					BAD0113CSVData.class,
					BAD0138CSVData.class,
					BAD0139CSVData.class,
					BAD3061CSVData.class,
					CAD0117CSVData.class,
					CAD0409CSVData.class,
					CBD0906CSVData.class,
					CCD0305CSVData.class,
					CCD0306CSVData.class,
					CDD0113CSVData.class,
					CDD1909CSVData.class,
					CDD2106CSVData.class,
					CED0013CSVData.class,
					CED1904CSVData.class,
					CED3104CSVData.class,
					CED3105CSVData.class,
					EAD3011CSVData.class
			};
			for (Class<?> clazz : csvClasses) {
				Field[] fields = clazz.getDeclaredFields();

				List<String> csvColumnList = new ArrayList<String>();
				csvColumnList.add("CSV項目漢字名");
				csvColumnList.add("CSV項目変数名");
				csvColumnList.add("CSVのカラムのインデックス");
				//                csvColumnList.add("ダミー列かどうか");
				csvColumnList.add("データ型");
				csvColumnList.add("最大項目長");
				csvColumnList.add("最大小数点以下桁数");
				csvColumnList.add("入力必須");
				//                csvColumnList.add("全角許可");
				//                csvColumnList.add("マイナス許可");
				//                csvColumnList.add("ゼロ許可");
				//                csvColumnList.add("編集マスク");
				//                csvColumnList.add("コードフォーマット");

				// 固定注解属性顺序
				String[] annotationPropsOrder = {
						"columnIndex"
						//                    ,"isDummy"
						, "dataType", "maxLength", "maxScale", "require"
						//                    ,"allowZenkaku"
						//                    ,"allowMinus"
						//                    ,"allowZero"
						//                    ,"editMask"
						//                    ,"codeFormat"
				};

				// ✅ 构建 CSV 行数据
				List<String> csvLines = new ArrayList<>();

				// ✅ 构建表头
				csvLines.add(String.join(",", csvColumnList));
				csvLines.add("fieldComment,fieldName," + String.join(",", annotationPropsOrder));

				// 1) 拼接文件路径
				String packagePath = clazz.getPackageName().replace('.', File.separatorChar);
				String sourcePath = Paths.get("C:\\workspace_phs\\phs-scaw\\Serv\\Sebn\\sources", packagePath,
						clazz.getSimpleName() + ".java").toString();
				File sourceFile = new File(sourcePath);
		        // 2) 按行读取文件，并过滤掉空白 + // 开头的行
//		        List<String> cleanedLines = new ArrayList<String>();
//		        BufferedReader reader = new BufferedReader(new FileReader(sourceFile,Charset.forName("MS932")));
//		        String line;
//		        while ((line = reader.readLine()) != null) {
//		            // 若整行以 空白(空格/Tab) + // 开头，则跳过
//		            if (line.trim().startsWith("//") || line.trim().matches("^\\s*//.*")) {
//		                continue;
//		            }
//		            cleanedLines.add(line);
//		        }
//		        reader.close();
		        List<String> cleanedLines = new ArrayList<>();

		        // 使用 Files.readAllLines 读取整个文件
		        List<String> allLines = Files.readAllLines(Path.of(sourcePath), Charset.forName("MS932"));
		        for (String line : allLines) {
		            // 若整行以 空白(空格/Tab) + // 开头，则跳过
		            if (line.trim().startsWith("//") || line.trim().matches("^\\s*//.*")) {
		                continue;
		            }
		            cleanedLines.add(line);
		        }
		        
		        // 3) 拼接成完整源码
		        StringBuilder cleanedSource = new StringBuilder();
		        for (int i = 0; i < cleanedLines.size(); i++) {
		            cleanedSource.append(cleanedLines.get(i)).append("\n");
		        }
		        StringBuilder cleanedSourceBuilder = new StringBuilder(String.join("\n", cleanedLines)).append("\n");

		        // 4) 用 JavaParser 解析
		        //JavaParser parser = new JavaParser();
		        //CompilationUnit cu = parser.parse(cleanedSource.toString()).getResult().orElse(null);

		        
		        
				// コンパイル単位を解析
				//CompilationUnit cu = StaticJavaParser.parse(sourceFile, StandardCharsets.UTF_8);
				ParserConfiguration config = new ParserConfiguration();
				config.setCharacterEncoding(Charset.forName("MS932"));

				JavaParser parser = new JavaParser(config);
				//CompilationUnit cu = parser.parse(sourceFile).getResult().get();

		        CompilationUnit cu = parser.parse(cleanedSource.toString()).getResult().orElse(null);
				List<FieldDeclaration> parserFields = cu.findAll(FieldDeclaration.class);

				// ✅ 遍历字段并处理注解
				for (Field field : fields) {
					if (field.isAnnotationPresent(CsvColumn.class)) {

						// 找到该类的字段
						String text = field.getName();// 默认值为字段名
						outerLoop: // 标签
						for (FieldDeclaration parserField : parserFields) {
							for (int i = 0; i < parserField.getVariables().size(); i++) {
								String fieldName = parserField.getVariable(i).getNameAsString();
								//parserField.getTokenRange().get().toString()
								if (field.getName().equals(fieldName)) {

							        
									// 如果有 Javadoc 注释
									if (parserField.getJavadocComment().isPresent()) {
										String javadocContent = parserField.getJavadocComment().get().getContent();
										String[] lines = javadocContent.split("\n");

										for (String javadocLine : lines) {
											javadocLine = javadocLine.trim();
											if (javadocLine.startsWith("*")) {
												javadocLine = javadocLine.substring(1).trim();
											}
											if (!javadocLine.isEmpty()) {
												text = javadocLine; // 取第一行非空内容
												break;
											}
										}

										// 去掉最后的句号
										if (text.endsWith("。")) {
											text = text.substring(0, text.length() - 1);
										}
									}

									// 跳出两个 for 循环
									break outerLoop;
								}

							}
						}

						CsvColumn annotation = field.getAnnotation(CsvColumn.class);
						List<String> row = new ArrayList<>();
//						System.out.println(field.getName() + ": " + text);
						row.add(text); // 字段注释名
						row.add(field.getName()); // 字段名

						for (String prop : annotationPropsOrder) {
							Method method = CsvColumn.class.getDeclaredMethod(prop);
							Object value = method.invoke(annotation);
							row.add(String.valueOf(value));
						}

						csvLines.add(String.join(",", row));
					}
				}

				// ✅ 写入 CSV 文件（UTF-8）
				//                Files.write(Paths.get(clazz.getSimpleName() + "CsvColumnData.csv"), csvLines, StandardCharsets.UTF_8);
				Path outputCsvPath = Paths.get(
						"C:\\Users\\yusei.to\\Desktop\\担当部分\\CSV横展開\\CsvColumn\\" + clazz.getSimpleName() + ".csv");
				//Files.write(outputCsvPath, csvLines, Charset.forName("Shift_JIS"));
				Files.write(outputCsvPath, csvLines, Charset.forName("MS932"));

				System.out.println("✅ CSVファイル (Shift_JIS) を出力しました: " + clazz.getSimpleName() + ".csv");

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
