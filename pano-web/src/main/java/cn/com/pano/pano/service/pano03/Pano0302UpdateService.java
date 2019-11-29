package cn.com.pano.pano.service.pano03;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.google.common.collect.Maps;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.pano.common.utils.FileServiceUtil;
import cn.com.pano.pano.form.pano03.Pano0302Form;
import cn.com.pano.pano.mapper.common.PanoExpositionMapper;
import cn.com.pano.pano.mapper.common01.PanoHotspotUrl01Mapper;
import cn.com.pano.pano.mapper.common01.PanoMaterial01Mapper;
import cn.com.pano.pano.mapper.common01.PanoPanoramaHotspot01Mapper;
import cn.com.pano.pano.model.common.PanoHotspotUrl;
import cn.com.pano.pano.model.common.PanoMaterial;
import cn.com.pano.pano.model.common.PanoPanoramaHotspot;
import cn.com.pano.pano.model.common01.PanoMaterial01Model;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseService;

/**
 * 素材归属变更逻辑
 * 
 * @author ouyangzidou
 * 
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class Pano0302UpdateService extends BaseService {

  @Autowired
  public PanoMaterial01Mapper panoMaterial01Mapper;
  @Autowired
  public PanoPanoramaHotspot01Mapper panoPanoramaHotspot01Mapper;
  @Autowired
  public PanoExpositionMapper panoExpositionMapper;
  @Autowired
  public PanoHotspotUrl01Mapper panoHotspotUrl01Mapper;

  /**
   * 移动素材归属
   * 
   * @param _inForm
   * @throws Exception
   */
  public void doMoveMaterials(Pano0302Form _inForm) throws Exception {
    if (_inForm.materialInfo != null && _inForm.materialInfo.size() > 0
        && !ObjectUtils.isEmpty(_inForm.materialBelongType)) {
      // 文件路径
      String srcPath = "";
      String destPath = "";
      // 公共素材转移到展览素材
      if (PanoConstantsIF.MATERIAL_BELONGTYPE_COMMON.equals(_inForm.materialBelongType)) {
        for (PanoMaterial01Model pano0302Dto : _inForm.materialInfo) {
          if (pano0302Dto.selected && !ObjectUtils.isEmpty(pano0302Dto.materialId)
              && !ObjectUtils.isEmpty(pano0302Dto.materialTypeId)) {
            // 排除文字浮动信息的情况
            if (!"浮动信息(文字)".equals(pano0302Dto.materialTypeId)) {
              if ("音乐".equals(pano0302Dto.materialTypeId)) {
                // 声音素材
                srcPath = "file_w/material/common_material/sounds/" + pano0302Dto.materialId;
                destPath = "file_w/material/" + _inForm.expositionId + "/sounds/"
                    + pano0302Dto.materialId + "/";
              } else if ("视频".equals(pano0302Dto.materialTypeId)) {
                // 视频
                srcPath = "file_w/material/common_material/videos/" + pano0302Dto.materialId;
                destPath = "file_w/material/" + _inForm.expositionId + "/videos/"
                    + pano0302Dto.materialId + "/";
              } else {
                // 其他素材
                srcPath = "file_w/material/common_material/images/" + pano0302Dto.materialId;
                destPath = "file_w/material/" + _inForm.expositionId + "/images/"
                    + pano0302Dto.materialId + "/";
              }
              // 转移文件
              FileServiceUtil.copyDirInPublicStorage(srcPath, destPath);
            }
            // 改变素材expositionId
            PanoMaterial material = panoMaterial01Mapper.selectByPrimaryKey(pano0302Dto.materialId);
            if (material != null) {
              material.expositionId = _inForm.expositionId;
              panoMaterial01Mapper.updateByPrimaryKey(material);
              if (!"浮动信息(文字)".equals(pano0302Dto.materialTypeId)) {
                // 旧文件删除
                FileServiceUtil.deletePublicStorageFolder(srcPath);
              }
            }
          }
        }
      }
      // 展览素材转移到公共素材
      if (PanoConstantsIF.MATERIAL_BELONGTYPE_EXPOSITION.equals(_inForm.materialBelongType)) {
        for (PanoMaterial01Model pano0302Dto : _inForm.materialInfo) {
          if (pano0302Dto.selected && !ObjectUtils.isEmpty(pano0302Dto.materialId)
              && !ObjectUtils.isEmpty(pano0302Dto.materialTypeId)) {
            // 排除文字浮动信息的情况
            if (!"浮动信息(文字)".equals(pano0302Dto.materialTypeId)) {
              if ("音乐".equals(pano0302Dto.materialTypeId)) {
                // 声音素材
                srcPath =
                    "file_w/material/" + _inForm.expositionId + "/sounds/" + pano0302Dto.materialId;
                destPath = "file_w/material/common_material/sounds/" + pano0302Dto.materialId + "/";
              } else if ("视频".equals(pano0302Dto.materialTypeId)) {
                // 视频
                srcPath =
                    "file_w/material/" + _inForm.expositionId + "/videos/" + pano0302Dto.materialId;
                destPath = "file_w/material/common_material/videos/" + pano0302Dto.materialId + "/";
              } else {
                // 其他素材
                srcPath =
                    "file_w/material/" + _inForm.expositionId + "/images/" + pano0302Dto.materialId;
                destPath = "file_w/material/common_material/images/" + pano0302Dto.materialId + "/";
              }
              // 转移文件
              FileServiceUtil.copyDirInPublicStorage(srcPath, destPath);
            }
            // 如果是文字浮动信息，改变其expositionId
            PanoMaterial material = panoMaterial01Mapper.selectByPrimaryKey(pano0302Dto.materialId);
            if (material != null) {
              material.expositionId = "common_material";
              panoMaterial01Mapper.updateByPrimaryKey(material);
              if (!"浮动信息(文字)".equals(pano0302Dto.materialTypeId)) {
                // 旧文件删除
                FileServiceUtil.deletePublicStorageFolder(srcPath);
              }
            }
          }
        }
      }
    }
  }

  /**
   * 删除该素材
   * 
   * @param pano0303Form
   */
  public EasyJson<Object> doDeleteMaterial(Pano0302Form _inForm) throws Exception {

    if (!ObjectUtils.isEmpty(_inForm.uniqueKeyList)) {
      for (Object materialIdObject : _inForm.uniqueKeyList) {
        String materialId = Objects.toString(materialIdObject, "");

        PanoMaterial panoMaterial = panoMaterial01Mapper.selectByPrimaryKey(materialId);
        HashMap<String, Object> _condition = Maps.newHashMap();
        _condition.put("materialId", materialId);

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
                    panoHotspotUrl01Mapper.selectByPrimaryKey(hotspot.hotspotId, materialId);
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
                List<PanoHotspotUrl> hotspotUrls =
                    panoHotspotUrl01Mapper.selectByHotspotId(condition);
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


        // 旧文件删除
        doDeleteMaterialFiles(panoMaterial);

      }
    }

    EasyJson<Object> easyJson = new EasyJson<Object>();
    easyJson.setMsg("删除成功！");
    return easyJson;
  }


  /**
   * 删除素材文件
   * 
   * @param _inForm
   * @throws Exception
   */
  public void doDeleteMaterialFiles(PanoMaterial panoMaterial) throws Exception {
    // 文件路径
    String srcPath = "";
    // 公共素材的场合
    if ("common_material".equals(panoMaterial.expositionId)) {

      // 排除文字浮动信息的情况
      if (!PanoConstantsIF.MATERIAL_TYPE_FLOW_INFO_TEXT.equals(panoMaterial.materialTypeId)) {
        if (PanoConstantsIF.MATERIAL_TYPE_SOUND.equals(panoMaterial.materialTypeId)) {
          // 声音素材
          srcPath = "file_w/material/common_material/sounds/" + panoMaterial.materialId;
        } else if (PanoConstantsIF.MATERIAL_TYPE_VIDEO.equals(panoMaterial.materialTypeId)) {
          // 视频
          srcPath = "file_w/material/common_material/videos/" + panoMaterial.materialId;
        } else {
          // 其他素材
          srcPath = "file_w/material/common_material/images/" + panoMaterial.materialId;
        }
      }
      if (!PanoConstantsIF.MATERIAL_TYPE_FLOW_INFO_TEXT.equals(panoMaterial.materialTypeId)) {
        // 旧文件删除
        FileServiceUtil.deletePublicStorageFolder(srcPath);
      }

    } else { // 展览素材转的场合

      // 排除文字浮动信息的情况
      if (!PanoConstantsIF.MATERIAL_TYPE_FLOW_INFO_TEXT.equals(panoMaterial.materialTypeId)) {
        if (PanoConstantsIF.MATERIAL_TYPE_SOUND.equals(panoMaterial.materialTypeId)) {
          // 声音素材
          srcPath =
              "file_w/material/" + panoMaterial.expositionId + "/sounds/" + panoMaterial.materialId;
        } else if (PanoConstantsIF.MATERIAL_TYPE_VIDEO.equals(panoMaterial.materialTypeId)) {
          // 视频
          srcPath =
              "file_w/material/" + panoMaterial.expositionId + "/videos/" + panoMaterial.materialId;
        } else {
          // 其他素材
          srcPath =
              "file_w/material/" + panoMaterial.expositionId + "/images/" + panoMaterial.materialId;
        }
      }
      if (!PanoConstantsIF.MATERIAL_TYPE_FLOW_INFO_TEXT.equals(panoMaterial.materialTypeId)) {
        // 旧文件删除
        FileServiceUtil.deletePublicStorageFolder(srcPath);
      }

    }
  }
}
