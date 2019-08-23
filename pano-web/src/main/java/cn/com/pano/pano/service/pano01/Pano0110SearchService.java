package cn.com.pano.pano.service.pano01;

import java.time.LocalDate;
import java.util.HashMap;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.google.common.collect.Maps;
import cn.com.pano.pano.common.code.ExpositionStatus;
import cn.com.pano.pano.common.code.ExpositionType;
import cn.com.pano.pano.form.pano01.Pano0110Form;
import cn.com.pano.pano.mapper.common.PanoExpositionMapper;
import cn.com.pano.pano.mapper.common.PanoGroupRoleUserMapper;
import cn.com.pano.pano.mapper.common01.PanoPanorama01Mapper;
import cn.com.pano.pano.mapper.pano01.Pano0110Mapper;
import cn.com.pano.pano.model.common.PanoExposition;
import cn.com.pano.pano.model.common01.PanoExposition01Model;
import cn.com.platform.framework.code.FlagStatus;
import cn.com.platform.framework.common.session.UserSessionUtils;
import cn.com.platform.framework.util.FwCodeUtil;
import cn.com.platform.framework.util.FwStringUtils;
import cn.com.platform.util.EasyJson;
import cn.com.platform.util.MessageUtils;
import cn.com.platform.web.BaseService;

/**
 * 
 * @author yangyuzhen
 * 
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class Pano0110SearchService extends BaseService {
  @Autowired
  public Pano0110Mapper pano0110Mapper;
  @Autowired
  public PanoPanorama01Mapper panoPanorama01Mapper;
  @Autowired
  public PanoGroupRoleUserMapper panoGroupRoleUserMapper;
  @Autowired
  public PanoExpositionMapper panoExpositionMapper;

  /**
   * 从数据库中检索展览信息
   * 
   * @param inForm
   * @throws Exception
   */
  public EasyJson<Object> doSearchExpositionInfo(Pano0110Form inForm) throws Exception {
    // 从数据库检索展览信息
    HashMap<String, Object> condition = Maps.newHashMap();
    condition.put("expositionId", FwStringUtils.getMatchParameter(inForm.expositionId));
    condition.put("expositionName", FwStringUtils.getMatchParameter(inForm.expositionName));
    if (ObjectUtils.isNotEmpty(inForm.expositionStartDate)) {
      condition.put("expositionStartDate", LocalDate.parse(inForm.expositionStartDate));
    }
    if (ObjectUtils.isNotEmpty(inForm.expositionEndDate)) {
      condition.put("expositionEndDate", LocalDate.parse(inForm.expositionEndDate));
    }
    condition.put("expositionStatus", inForm.expositionStatus);
    // condition.put("role_expo", FrameworkConstants.ROLE_EXPO_ADMIN_ID);
    // condition.put("role_scene", FrameworkConstants.ROLE_SCENE_ADMIN_ID);
    condition.put("user", UserSessionUtils.getUserName());
    condition.put("deleteFlag", FlagStatus.Disable.toString());
    // 数据索引
    condition.put("offSet", (inForm.pageNumber - 1) * inForm.pageSize);
    // 每页的条数
    condition.put("pageSize", inForm.pageSize);

    long resultCount = pano0110Mapper.selectExpositionInfoCount(condition);
    if (resultCount > 0) {
      inForm.expositionInfo = pano0110Mapper.selectExpositionInfo(condition);
    }
    // 检查检索出的展览中状态和时间显示的格式
    if (inForm.expositionInfo != null) {
      for (PanoExposition01Model panoExposition01Model : inForm.expositionInfo) {
        ExpositionStatus expositionStatus =
            FwCodeUtil.stringToEnum(ExpositionStatus.class, panoExposition01Model.status);
        panoExposition01Model.expositionStatusName =
            MessageUtils.getMessage(expositionStatus.getMessageId());

        ExpositionType expositionType =
            FwCodeUtil.stringToEnum(ExpositionType.class, panoExposition01Model.expositionType);
        panoExposition01Model.expositionTypeName =
            MessageUtils.getMessage(expositionType.getMessageId());
      }
    }

    // 重置0104传回的pageStartRowNo
    // _inForm.pageStartRowNoFromPano0104 = "";

    EasyJson<Object> easyJson = new EasyJson<Object>();
    easyJson.setRows(inForm.expositionInfo);
    easyJson.setTotal(resultCount);
    return easyJson;
  }

  /**
   * 从数据库中检索某一展览信息
   * 
   * @param _inForm
   * @throws Exception
   */
  public String doVrFlag(Pano0110Form _inForm) throws Exception {
    PanoExposition panoExposition =
        panoExpositionMapper.selectByPrimaryKey(_inForm.selectedExpositionId);
    String result = "0";
    if (panoExposition != null) {
      result = panoExposition.vrFlag;
    }
    return result;
  }
}
