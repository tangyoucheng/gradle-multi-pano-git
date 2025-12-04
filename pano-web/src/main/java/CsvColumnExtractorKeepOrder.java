
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jp.co.nttdatakansai.scaw.fw.csv.reader.CsvColumn;
import jp.co.nttdatakansai.scaw.sebn.CA.CAR0117.bean.CAD0117CSVData;

public class CsvColumnExtractorKeepOrder {
    public static void main(String[] args) {
        try (Writer writer = new OutputStreamWriter(new FileOutputStream("CsvColumnData.csv"), "UTF-8")) {
            Class<?> clazz = CAD0117CSVData.class;
            Field[] fields = clazz.getDeclaredFields();

            boolean headerWritten = false;

            List<String> csvColumnList = new ArrayList<String>();
            csvColumnList.add("CSVのカラムのインデックス");
            csvColumnList.add("ダミー列かどうか");
            csvColumnList.add("入力必須");
            csvColumnList.add("データ型");
            csvColumnList.add("最大項目長");
            csvColumnList.add("最大小数点以下桁数");
            csvColumnList.add("全角許可");
            csvColumnList.add("マイナス許可");
            csvColumnList.add("ゼロ許可");
            csvColumnList.add("編集マスク");
            csvColumnList.add("コードフォーマット");
            
            // 你也可以硬编码注解属性名顺序，保证稳定：
            String[] annotationPropsOrder = {
                "columnIndex",
                "isDummy",
                "require",
                "dataType",
                "maxLength",
                "maxScale",
                "allowZenkaku",
                "allowMinus",
                "allowZero",
                "editMask",
                "codeFormat"
            };

            for (Field field : fields) {
                if (field.isAnnotationPresent(CsvColumn.class)) {
                    CsvColumn annotation = field.getAnnotation(CsvColumn.class);
                    Map<String, String> values = new LinkedHashMap<>();

                    for (String prop : annotationPropsOrder) {
                        Method method = CsvColumn.class.getDeclaredMethod(prop);
                        Object value = method.invoke(annotation);
                        values.put(prop, String.valueOf(value));
                    }

                    if (!headerWritten) {
                        writer.write(String.join(",", csvColumnList) + "\n");
                        writer.write(String.join(",", values.keySet()) + "\n");
                        headerWritten = true;
                    }

                    writer.write(String.join(",", values.values()) + "\n");
                }
            }

            System.out.println("CSVファイルをUTF-8で生成し、注解属性の順序を維持しました: CsvColumnData.csv");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
