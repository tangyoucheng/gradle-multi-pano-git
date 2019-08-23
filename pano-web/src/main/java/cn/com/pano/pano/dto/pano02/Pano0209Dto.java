package cn.com.pano.pano.dto.pano02;

import java.io.Serializable;
import java.util.List;
import cn.com.pano.pano.dto.pano01.Pano0104Dto;
import cn.com.pano.pano.form.pano01.Pano0104Form;
import cn.com.pano.pano.model.common.PanoExpositionMapHotspot;
import cn.com.pano.pano.model.common.PanoMaterial;
import cn.com.pano.pano.model.common.PanoPolygonHotspot;
import cn.com.platform.framework.common.CodeValueRecord;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Pano0209用于显示按钮一览表信息的dto
 * 
 * @author ouyangzidu
 * 
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class Pano0209Dto implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    // 按钮名
    public String buttonName;
    // 按钮中文显示名称
    public String buttonName_CN;
    
}
