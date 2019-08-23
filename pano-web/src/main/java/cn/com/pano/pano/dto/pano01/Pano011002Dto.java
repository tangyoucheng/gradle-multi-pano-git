package cn.com.pano.pano.dto.pano01;

import java.io.Serializable;
import java.util.List;
import cn.com.pano.pano.form.pano01.Pano0104Form;
import cn.com.pano.pano.model.common.PanoExposition;
import cn.com.pano.pano.model.common.PanoExpositionMapHotspot;
import cn.com.pano.pano.model.common.PanoMaterial;
import cn.com.pano.pano.model.common.PanoPolygonHotspot;
import cn.com.platform.framework.common.CodeValueRecord;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * @author 
 * 
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class Pano011002Dto extends PanoExposition implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /* 热点Id */
    public String hotspotId;
    /* 素材Path */
    public String materialPath;
    /* 文字信息 */
    public String textInfo;
    /*  */
    public String materialId;
}
