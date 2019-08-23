package cn.com.pano.pano.service.pano03;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.pano.common.utils.FileServiceUtil;
import cn.com.pano.pano.form.pano03.Pano0302Form;
import cn.com.pano.pano.mapper.common01.PanoMaterial01Mapper;
import cn.com.pano.pano.model.common.PanoMaterial;
import cn.com.pano.pano.model.common01.PanoMaterial01Model;
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
        for (PanoMaterial01Model  pano0302Dto : _inForm.materialInfo) {
          if ( pano0302Dto.selected && !ObjectUtils.isEmpty( pano0302Dto.materialId)
              && !ObjectUtils.isEmpty( pano0302Dto.materialTypeId)) {
            // 排除文字浮动信息的情况
            if (!"浮动信息(文字)".equals( pano0302Dto.materialTypeId)) {
              if ("音乐".equals( pano0302Dto.materialTypeId)) {
                // 声音素材
                srcPath = "file_w/material/common_material/sounds/" +  pano0302Dto.materialId;
                destPath = "file_w/material/" + _inForm.expositionId + "/sounds/"
                    +  pano0302Dto.materialId + "/";
              } else if ("视频".equals( pano0302Dto.materialTypeId)) {
                // 视频
                srcPath = "file_w/material/common_material/videos/" +  pano0302Dto.materialId;
                destPath = "file_w/material/" + _inForm.expositionId + "/videos/"
                    +  pano0302Dto.materialId + "/";
              } else {
                // 其他素材
                srcPath = "file_w/material/common_material/images/" +  pano0302Dto.materialId;
                destPath = "file_w/material/" + _inForm.expositionId + "/images/"
                    +  pano0302Dto.materialId + "/";
              }
              // 转移文件
              FileServiceUtil.copyDirInPublicStorage(srcPath, destPath);
            }
            // 改变素材expositionId
            PanoMaterial material = panoMaterial01Mapper.selectByPrimaryKey( pano0302Dto.materialId);
            if (material != null) {
              material.expositionId = _inForm.expositionId;
              panoMaterial01Mapper.updateByPrimaryKey(material);
              if (!"浮动信息(文字)".equals( pano0302Dto.materialTypeId)) {
                // 旧文件删除
                FileServiceUtil.deletePublicStorageFolder(srcPath);
              }
            }
          }
        }
      }
      // 展览素材转移到公共素材
      if (PanoConstantsIF.MATERIAL_BELONGTYPE_EXPOSITION.equals(_inForm.materialBelongType)) {
        for (PanoMaterial01Model  pano0302Dto : _inForm.materialInfo) {
          if ( pano0302Dto.selected && !ObjectUtils.isEmpty( pano0302Dto.materialId)
              && !ObjectUtils.isEmpty( pano0302Dto.materialTypeId)) {
            // 排除文字浮动信息的情况
            if (!"浮动信息(文字)".equals( pano0302Dto.materialTypeId)) {
              if ("音乐".equals( pano0302Dto.materialTypeId)) {
                // 声音素材
                srcPath =
                    "file_w/material/" + _inForm.expositionId + "/sounds/" +  pano0302Dto.materialId;
                destPath = "file_w/material/common_material/sounds/" +  pano0302Dto.materialId + "/";
              } else if ("视频".equals( pano0302Dto.materialTypeId)) {
                // 视频
                srcPath =
                    "file_w/material/" + _inForm.expositionId + "/videos/" +  pano0302Dto.materialId;
                destPath = "file_w/material/common_material/videos/" +  pano0302Dto.materialId + "/";
              } else {
                // 其他素材
                srcPath =
                    "file_w/material/" + _inForm.expositionId + "/images/" +  pano0302Dto.materialId;
                destPath = "file_w/material/common_material/images/" +  pano0302Dto.materialId + "/";
              }
              // 转移文件
              FileServiceUtil.copyDirInPublicStorage(srcPath, destPath);
            }
            // 如果是文字浮动信息，改变其expositionId
            PanoMaterial material = panoMaterial01Mapper.selectByPrimaryKey( pano0302Dto.materialId);
            if (material != null) {
              material.expositionId = "common_material";
              panoMaterial01Mapper.updateByPrimaryKey(material);
              if (!"浮动信息(文字)".equals( pano0302Dto.materialTypeId)) {
                // 旧文件删除
                FileServiceUtil.deletePublicStorageFolder(srcPath);
              }
            }
          }
        }
      }
    }
  }
}
