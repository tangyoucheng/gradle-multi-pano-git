package cn.com.pano.panovr.service.panovr03;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.com.pano.pano.mapper.common01.PanoExpositionMapHotspot01Mapper;
import cn.com.pano.pano.model.common.PanoExpositionMapHotspot;
import cn.com.pano.panovr.form.panovr03.PanoVr0307Form;
import cn.com.platform.web.BaseService;

/**
 * 给地图上热点链接全景图信息
 * 
 * @author ouyangzidou
 * 
 */
@Service
@Transactional(rollbackFor=Throwable.class)
public class PanoVr0307EntryService extends BaseService{

  @Autowired
    public PanoExpositionMapHotspot01Mapper panoExpositionMapHotspot01Mapper;
    
    /**
     * 给地图上热点链接全景图信息
     * 
     * @param _inForm
     * @throws Exception
     */
    public void doEntry(PanoVr0307Form _inForm) throws Exception {
        // 检索热点是否存在
        if (!ObjectUtils.isEmpty(_inForm.selectedHotspotId) && !ObjectUtils.isEmpty(_inForm.selectedPanoramaId)) {
            PanoExpositionMapHotspot panoExpositionMapHotspot = panoExpositionMapHotspot01Mapper
                    .selectByPrimaryKey(_inForm.selectedHotspotId);
            if (panoExpositionMapHotspot != null) {
                // 更新地图热点信息
                panoExpositionMapHotspot.panoramaId = _inForm.selectedPanoramaId;
                updateAudit(panoExpositionMapHotspot);
                panoExpositionMapHotspot01Mapper.updateByPrimaryKey(panoExpositionMapHotspot);
            }
        }
    }
}
