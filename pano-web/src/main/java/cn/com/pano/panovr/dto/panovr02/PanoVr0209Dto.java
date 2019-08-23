package cn.com.pano.panovr.dto.panovr02;

import java.io.Serializable;
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
public class PanoVr0209Dto implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    // 按钮名
    public String buttonName;
    // 按钮中文显示名称
    public String buttonName_CN;
    
}
