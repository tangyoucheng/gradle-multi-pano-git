package cn.com.pano.panovr.form.panovr03;

import java.io.Serializable;
import cn.com.pano.panovr.dto.panovr01.PanoVr0104Dto;
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
public class PanoVr0310Form extends AbstractForm implements Serializable {
    private static final long serialVersionUID = 1L;
    // 场景Id
    public String vr0310panoramaId;
    // 场景path
    public String vr0310panoramaPath;
    // 当前热点的 Id
    public String vr0310selectedHotspotId;
    // 热点的第一张素材图
    public PanoVr0104Dto firstImageInfo;
    // 热点的第二张素材图
    public PanoVr0104Dto secondImageInfo;
    // 视角参数
    public String positionAthForEdit;
    public String positionAtvForEdit;
    
}
