package cn.com.pano.pano.dto.pano03;

import java.io.Serializable;
import cn.com.pano.pano.model.common.PanoMaterial;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 素材一览Dto
 * 
 * @author ouyangzidou
 * 
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class Pano0302Dto extends PanoMaterial implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    // 素材归属转移时，判断勾选框是否选中
    public boolean isSelected;
}
