package cn.com.pano.pano.service.pano01;


import java.math.BigDecimal;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.NumberUtils;
import org.springframework.util.ObjectUtils;
import com.google.common.collect.Maps;
import cn.com.pano.pano.form.pano01.Pano0104Form;
import cn.com.pano.pano.mapper.common01.PanoMaterial01Mapper;
import cn.com.pano.pano.mapper.common01.PanoPanorama01Mapper;
import cn.com.pano.pano.model.common.PanoPanorama;
import cn.com.platform.framework.code.FlagStatus;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseService;

/**
 * 场景编辑页面更新处理。
 * 
 * @author 唐友成
 * @date 2019-08-08
 *
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class Pano0104UpdateService extends BaseService {

  @Autowired
  public PanoPanorama01Mapper panoPanorama01Mapper;
  @Autowired
  public PanoMaterial01Mapper panoMaterial01Mapper;

  // TODO 废弃
  /**
   * 更新场景信息。
   * 
   * @param inForm Pano0104Form
   * @throws Exception 异常的场合
   */
  public String updateFirstSence(Pano0104Form inForm) throws Exception {
    // 取得loginUserId
    // 更新场景基本信息
    if (!ObjectUtils.isEmpty(inForm.panoramaId)) {
      PanoPanorama panoPanorama = panoPanorama01Mapper.selectByPrimaryKey(inForm.panoramaId);
      if (panoPanorama != null) {
        HashMap<String, Object> condition = Maps.newHashMap();
        condition.put("expositionId", inForm.expositionId);
        // 检索全景图首图
        PanoPanorama startPanoPanorama = panoPanorama01Mapper.selectStartScenePanoInfo(condition);
        // 检索当前展览下无全景图首图
        if (startPanoPanorama != null) {
          // 该展览下已有首图,修改其首图标识为NO
          startPanoPanorama.startSceneFlag = FlagStatus.Disable.toString();
          updateAudit(startPanoPanorama);
          panoPanorama01Mapper.updateByPrimaryKey(startPanoPanorama);
        }
        panoPanorama.startSceneFlag = FlagStatus.Enable.toString();
        panoPanorama01Mapper.updateByPrimaryKey(panoPanorama);
        return "yes";
      }
    }
    return "false";
  }

  /**
   * 更新场景信息。
   * 
   * @param inForm Pano0104Form
   */
  public EasyJson<Object> doUpdatePanoOrder(Pano0104Form inForm) {

    if (!ObjectUtils.isEmpty(inForm.uniqueKeyList)) {
      for (int i = 0; i < inForm.uniqueKeyList.size(); i++) {
        String panoramaId = ObjectUtils.getDisplayString(inForm.uniqueKeyList.get(i));
        // 检索场景
        PanoPanorama panoPanorama = panoPanorama01Mapper.selectByPrimaryKey(panoramaId);
        if (ObjectUtils.isEmpty(panoPanorama)) {
          continue;
        }
        BigDecimal panoramaSortKey =
            NumberUtils.convertNumberToTargetClass(i + 1, BigDecimal.class);
        panoPanorama.setPanoramaSortKey(panoramaSortKey);
        updateAudit(panoPanorama);
        // 更新房屋设施类型信息
        panoPanorama01Mapper.updateByPrimaryKey(panoPanorama);
      }
    }

    EasyJson<Object> easyJson = new EasyJson<Object>();
    easyJson.setSuccess(true);
    easyJson.setMsg("排序保存成功");
    return easyJson;
  }
}
