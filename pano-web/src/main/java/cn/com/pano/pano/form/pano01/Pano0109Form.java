package cn.com.pano.pano.form.pano01;

import java.io.Serializable;
import java.util.List;
import cn.com.pano.pano.dto.pano01.Pano0104Dto;
import cn.com.pano.pano.model.common.PanoExpositionMapHotspot;
import cn.com.pano.pano.model.common.PanoMaterial;
import cn.com.pano.pano.model.common.PanoPolygonHotspot;
import cn.com.platform.framework.common.CodeValueRecord;
import cn.com.platform.framework.form.AbstractForm;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 展览编辑Formクラス.
 * 
 * @version $Revision$
 * @author shiwei
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class Pano0109Form extends AbstractForm implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /* 展览编号 */
    public String expositionId;
    
    /* 展览名称 */
    public String expositionName;
    
    /* 展览音乐 */
    public String expositionSoundName;
    
    /* 场景编号 */
    public String panoramaId;
    
    /* 展览类型 */
    public String expositionType;
    
    // 预加载文件种类
    public String preloadFileType;
    
    // 原素材路径
    public String oldmaterialPath;
    
    /* 预加载文件类型选框 */
    public List<CodeValueRecord> preloadFileTypeList;
    
    /* 展览类型选框 */
    public List<CodeValueRecord> expositionTypeList;
    
    // 预加载文件选择与否
    public String preloadFlag;
    
    // 展览类型勾选框
    public String expoTypeFlag;
    
    // 展览音乐勾选框
    public String expoSoundFlag;
    
    // 由音乐素材画面返回的音乐ID
    public String materialIdFromPano0208;
    
    // 是否成功上传了新的预加载文件
    public String preloadFileUploadSuccess;
    
//    /**
//     * 关联验证
//     * 
//     * @return
//     */
//    public ActionMessages validateDoUpdate() throws Exception {
//        ActionMessages errors = new ActionMessages();
//        
//        // 是否选择音乐文件
//        if (!ObjectUtils.isEmpty(expoSoundFlag) && ObjectUtils.isEmpty(materialIdFromPano0208)) {
//            errors.add("soundNameLabel", new ActionMessage("errors.required", "展览音乐"));
//        }
//        
//        return errors;
//    }
    
}
