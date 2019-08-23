package cn.com.pano.pano.service.pano03;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.google.common.collect.Maps;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.pano.common.utils.FileServiceUtil;
import cn.com.pano.pano.form.pano03.Pano0303Form;
import cn.com.pano.pano.mapper.common.PanoExpositionMapper;
import cn.com.pano.pano.mapper.common01.PanoHotspotUrl01Mapper;
import cn.com.pano.pano.mapper.common01.PanoMaterial01Mapper;
import cn.com.pano.pano.mapper.common01.PanoPanoramaHotspot01Mapper;
import cn.com.pano.pano.model.common.PanoHotspotUrl;
import cn.com.pano.pano.model.common.PanoMaterial;
import cn.com.pano.pano.model.common.PanoPanoramaHotspot;
import cn.com.platform.web.BaseService;

/**
 * 
 * @author tangzhenzong
 * 
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class Pano0303UpdateService extends BaseService {
  @Autowired
  public PanoMaterial01Mapper panoMaterial01Mapper;
  @Autowired
  public PanoPanoramaHotspot01Mapper panoPanoramaHotspot01Mapper;
  @Autowired
  public PanoExpositionMapper panoExpositionMapper;
  @Autowired
  public PanoHotspotUrl01Mapper panoHotspotUrl01Mapper;

  /**
   * 更新素材信息
   * 
   * @param _inForm
   * @throws Exception
   */

  public void doUpdataMaterial(Pano0303Form _inForm) throws Exception {

    String returnfilePath;
    String fileName;
    // 更新素材信息
    PanoMaterial panoMaterial = panoMaterial01Mapper.selectByPrimaryKey(_inForm.materialId);
    // 文件备份
    if (PanoConstantsIF.MATERIAL_TYPE_SOUND.equals(_inForm.materialTypeId)) {
      // 如果是音乐文件
      String _destPath = MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W_SOUND,
          PanoConstantsIF.MATERIAL_FOLDER_NAME + panoMaterial.expositionId,
          _inForm.materialId + "/");
      fileName = FileServiceUtil.saveImageToPublicStorage(_inForm.upload_key, _destPath);
      returnfilePath = _inForm.materialId + "/" + fileName;

    } else if (PanoConstantsIF.MATERIAL_TYPE_VIDEO.equals(_inForm.materialTypeId)) {
      // 视频
      String _destPath = MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W_VIDEO,
          PanoConstantsIF.MATERIAL_FOLDER_NAME + panoMaterial.expositionId,
          _inForm.materialId + "/");
      fileName = FileServiceUtil.saveImageToPublicStorage(_inForm.upload_key, _destPath);
      returnfilePath = _inForm.materialId + "/" + fileName;

    } else {
      // 文字浮动信息
      if (PanoConstantsIF.MATERIAL_TYPE_FLOW_INFO_TEXT.equals(_inForm.materialTypeId)) {
        fileName = "";
        returnfilePath = "";
        panoMaterial.flowTextInfo = _inForm.textflowInfo;
      } else {
        // 其他
        String _destPath = MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W_IMAGE,
            PanoConstantsIF.MATERIAL_FOLDER_NAME + panoMaterial.expositionId,
            _inForm.materialId + "/");
        fileName = FileServiceUtil.saveImageToPublicStorage(_inForm.upload_key, _destPath);
        returnfilePath = _inForm.materialId + "/" + fileName;

        // 判断是否是gif图，如果是则再做拆分拼接操作
        if (fileName.toLowerCase().endsWith(".gif")) {
          String gifPath = MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W_IMAGE,
              PanoConstantsIF.MATERIAL_FOLDER_NAME + panoMaterial.expositionId, returnfilePath);

          fileName = fileName.substring(0, fileName.lastIndexOf(".")) + ".png";
          String gifDestPath = MessageFormat.format(PanoConstantsIF.VAL_PUBLIC_DIRECTORY_W_IMAGE,
              PanoConstantsIF.MATERIAL_FOLDER_NAME + panoMaterial.expositionId,
              _inForm.materialId + "/" + fileName);
          // 返回生成小png图片的宽，高，帧数，延迟时间
          String[] result = FileServiceUtil.splitAndSaveGif(gifPath, gifDestPath);
          if (result.length == 4) {
            panoMaterial.gifWidth = result[0];
            panoMaterial.gifHeight = result[1];
            panoMaterial.gifFrameCount = result[2];
            panoMaterial.gifDelayTime = result[3];
          }
        } else {
          panoMaterial.gifWidth = "";
          panoMaterial.gifHeight = "";
          panoMaterial.gifFrameCount = "";
          panoMaterial.gifDelayTime = "";
        }

        // 图文的时候,保存文字信息
        if (PanoConstantsIF.MATERIAL_TYPE_IMAGE_TEXT.equals(_inForm.materialTypeId)) {
          panoMaterial.textInfo = _inForm.textInfo;
        }
      }
    }

    if (!ObjectUtils.isEmpty(fileName)) {
      panoMaterial.materialPath = returnfilePath;
    }
    panoMaterial.materialName = _inForm.materialName;
    panoMaterial.notes = _inForm.notes;
    updateAudit(panoMaterial);
    panoMaterial01Mapper.updateByPrimaryKey(panoMaterial);
  }

  /**
   * 删除该素材
   * 
   * @param  pano0303Form
   */
  public void doDeleteMaterial(Pano0303Form _inForm) throws Exception {
    PanoMaterial panoMaterial = panoMaterial01Mapper.selectByPrimaryKey(_inForm.materialId);
    HashMap<String, Object> _condition = Maps.newHashMap();
    _condition.put("materialId", _inForm.materialId);

    // 如果该素材是音乐素材
    if (PanoConstantsIF.MATERIAL_TYPE_SOUND.equals(panoMaterial.materialTypeId)) {
      // 判断该素材被用作展览会场景音乐 或 场景上音乐热点链接的音乐
      List<PanoHotspotUrl> result = panoHotspotUrl01Mapper.findByMaterialId(_condition);
      if (result != null && result.size() > 0) {
        // 场景上音乐热点链接的音乐
        for (PanoHotspotUrl hotspotUrl : result) {
          if ("0".equals(hotspotUrl.sortKey.toString())) {
            // 音乐热点链接的音乐,至连接至该素材的场景热点的热点url类型为空
            PanoPanoramaHotspot hotspot =
                panoPanoramaHotspot01Mapper.selectByPrimaryKey(hotspotUrl.hotspotId);
            hotspot.hotspotUrlType = "";
            panoPanoramaHotspot01Mapper.updateByPrimaryKey(hotspot);
            // 从热点url表中更新该音乐的信息
            PanoHotspotUrl url =
                panoHotspotUrl01Mapper.selectByPrimaryKey(hotspot.hotspotId, _inForm.materialId);
            url.hotspotUrlObjectId = "musicMaterialId";
            panoHotspotUrl01Mapper.updateByPrimaryKey(url);
          }
        }
      }
      // 该素材被用作展览会场景音乐
      panoHotspotUrl01Mapper.updateExpoSoundIdByMaterial(_condition);
      panoHotspotUrl01Mapper.updatePanoSoundIdByMaterial(_condition);

    } else {
      // 该素材为图片
      if (PanoConstantsIF.MATERIAL_TYPE_HOTSPOT_IMAGE.equals(panoMaterial.materialTypeId)) {

        List<PanoHotspotUrl> result = panoHotspotUrl01Mapper.findByMaterialId(_condition);
        if (result != null && result.size() > 0) {
          // 该素材为音乐热点的某一图片
          for (PanoHotspotUrl hotspotUrl : result) {
            HashMap<String, Object> condition = Maps.newHashMap();
            condition.put("hotspotId", hotspotUrl.hotspotId);
            List<PanoHotspotUrl> hotspotUrls = panoHotspotUrl01Mapper.selectByHotspotId(condition);
            if (hotspotUrls != null && hotspotUrls.size() > 0) {
              for (PanoHotspotUrl url : hotspotUrls) {
                if ("0".equals(url.sortKey.toString())) {
                  // 该素材为音乐热点的某一图片
                  for (PanoHotspotUrl urls : hotspotUrls) {
                    panoHotspotUrl01Mapper.deleteByPrimaryKey(urls.hotspotId,
                        urls.hotspotUrlObjectId);
                  }
                  PanoPanoramaHotspot hotspot =
                      panoPanoramaHotspot01Mapper.selectByPrimaryKey(url.hotspotId);
                  if (hotspot != null) {

                    panoPanoramaHotspot01Mapper.deleteByPrimaryKey(hotspot.hotspotId);
                  }
                  break;
                }
              }
            }
          }
        }
      }

      // 通过素材删除对应的热点URL信息
      panoHotspotUrl01Mapper.deleteUrlInfoByMaterial(_condition);
      // 通过素材更新对应的全景图上热点URL种类信息
      panoPanoramaHotspot01Mapper.updateHotspotUrlType(_condition);
      // 通过素材删除对应的全景图上热点信息
      panoPanoramaHotspot01Mapper.deleteHotSpotByMaterial(_condition);
    }
    // 删除该素材
    panoMaterial01Mapper.deleteByPrimaryKey(panoMaterial.materialId);

  }
}
