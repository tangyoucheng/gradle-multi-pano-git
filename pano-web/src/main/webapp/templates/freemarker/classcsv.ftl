package ${packageName};

import jp.co.nttdatakansai.scaw.fw.csv.reader.CsvColumn;

public class ${className} {

<#list fields as f>
    /**
     * ${f.comment}。
     */
    @CsvColumn(columnIndex = ${f.columnIndex}, isDummy = false, require = ${f.require}
    , dataType = "${f.dataType}", maxLength = 10, maxScale = 0, allowZenkaku = false
    , allowMinus = false, allowZero = false, editMask = false, codeFormat = "")
    private ${f.type} ${f.name};
    
</#list>

<#list fields as f>
    /**
     * ${f.comment}を取得します。
     * @param ${f.name} ${f.comment}
     */
    public ${f.type} get${f.name?cap_first}() {
        return ${f.name};
    }

    /**
     * ${f.comment}を設定します。
     * @param ${f.name} ${f.comment}
     */
    public void set${f.name?cap_first}(${f.type} ${f.name}) {
        this.${f.name} = ${f.name};
    }
    
</#list>

}
