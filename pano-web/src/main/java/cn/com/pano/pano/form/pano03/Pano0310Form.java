package cn.com.pano.pano.form.pano03;

import java.io.Serializable;
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

/**
 * 展览目录编辑Form
 * 
 * @author yangyuzhen
 * 
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class Pano0310Form extends AbstractForm implements Serializable {
    private static final long serialVersionUID = 1L;
    //场景Id
    public String  pano0310panoramaId;
    // 场景path
    public String  pano0310panoramaPath;
    //当前热点的 Id
    public String  pano0310selectedHotspotId;
    //热点的第一张素材图
    public Pano0104Dto firstImageInfo;
    //热点的第二张素材图
    public Pano0104Dto secondImageInfo;
    // 视角参数
    public String positionAthForEdit;
    public String positionAtvForEdit;
    
}
