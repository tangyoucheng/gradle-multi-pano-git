package cn.com.pano.pano.form.pano03;

import java.util.List;
import cn.com.pano.pano.dto.pano01.Pano0104Dto;
import cn.com.pano.pano.form.pano01.Pano0104Form;
import cn.com.pano.pano.model.common.PanoExpositionMapHotspot;
import cn.com.pano.pano.model.common.PanoMaterial;
import cn.com.pano.pano.model.common.PanoPolygonHotspot;
import cn.com.platform.framework.common.CodeValueRecord;
import cn.com.platform.framework.form.AbstractForm;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Pano0303Form extends AbstractForm {
    
    private static final long serialVersionUID = 1L;
    /* 素材ID */
    public String materialId;
    /* 素材名称 */
    public String materialName;
    /* 素材种类 */
    public String materialTypeId;
    /* 备注 */
    public String notes;
    // 展览ID
    public String expositionId;
    // 展览名称
    public String expositionName;
    // 父画面当前全景图Id
    public String currentPanoramaId;
    // 素材图
    public String materialPath;
    // 文字浮动信息内容
    public String textflowInfo;
    // 原有文字浮动信息
    public String oldTextflowInfo;
    /* 素材种类单选框 */
    public List<CodeValueRecord> panoMaterialList;
    /* 原有图的路径 */
    public String oldmaterialPath;
    // upload_key
    public String upload_key;
    // 图文的文字信息
    public String textInfo;
    // 编辑完成时返回0302画面时的检索数据
    public String materiaIdForSearch;
    public String materialNameForSearch;
    public String materialNameTypeForSearch;
    public String hiddenPanoramaId;
    public String hiddenExpositionName;
    public String hiddenExpositionId;
    public String hiddenPageStartRowNo;
    public String hiddenMaterialBelongType;
}
