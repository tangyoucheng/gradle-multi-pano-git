package cn.com.pano.pano.service.pano01;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.google.common.collect.Maps;
import cn.com.pano.pano.common.code.ExpositionStatus;
import cn.com.pano.pano.form.pano01.Pano0102Form;
import cn.com.pano.pano.mapper.pano01.Pano0102Mapper;
import cn.com.pano.pano.model.common01.PanoExposition01Model;
import cn.com.platform.framework.code.FlagStatus;
import cn.com.platform.framework.common.CodeValueRecord;
import cn.com.platform.framework.common.session.UserSessionUtils;
import cn.com.platform.framework.util.FwCodeUtil;
import cn.com.platform.framework.util.FwStringUtils;
import cn.com.platform.util.EasyJson;
import cn.com.platform.util.MessageUtils;
import cn.com.platform.web.BaseService;

/**
 * 展览一览画面处理检索处理
 * 
 * @author 唐友成
 * @date 2019-08-01
 * 
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class Pano0102SearchService extends BaseService {

  @Autowired
  public Pano0102Mapper pano0102Mapper;

  /**
   * 从数据库中检索展览信息
   * 
   * @param inForm
   * @throws Exception
   */
  public EasyJson<Object> doSearchExpositionInfo(Pano0102Form inForm) throws Exception {
    List<String> roleIds = new ArrayList<String>();
    roleIds.add("customer_leader");
    roleIds.add("customer_manager");
    // 从数据库检索展览信息
    HashMap<String, Object> condition = Maps.newHashMap();
    condition.put("expositionId", FwStringUtils.getMatchParameter(inForm.expositionId));
    condition.put("expositionName", FwStringUtils.getMatchParameter(inForm.expositionName));
    // TODO
    // condition.put("role_expo", FrameworkConstants.ROLE_EXPO_ADMIN_ID);
    // condition.put("role_scene", FrameworkConstants.ROLE_SCENE_ADMIN_ID);
    condition.put("user", UserSessionUtils.getUserName());
    condition.put("roleIds", roleIds);
    if (ObjectUtils.isNotEmpty(inForm.expositionStartDate)) {
      condition.put("expositionStartDate", LocalDate.parse(inForm.expositionStartDate));
    }
    if (ObjectUtils.isNotEmpty(inForm.expositionEndDate)) {
      condition.put("expositionEndDate", LocalDate.parse(inForm.expositionEndDate));
    }
    condition.put("expositionStatus", inForm.expositionStatus);
    condition.put("deleteFlag", FlagStatus.Disable.toString());
    // 数据索引
    condition.put("offSet", (inForm.pageNumber - 1) * inForm.pageSize);
    // 每页的条数
    condition.put("pageSize", inForm.pageSize);

    long resultCount = pano0102Mapper.selectExpositionInfoCount(condition);
    if (resultCount > 0) {
      inForm.expositionInfo = pano0102Mapper.selectExpositionInfo(condition);
      for (PanoExposition01Model panoExposition01Model : inForm.expositionInfo) {
        ExpositionStatus expositionStatus =
            FwCodeUtil.stringToEnum(ExpositionStatus.class, panoExposition01Model.status);
        panoExposition01Model.expositionStatusName =
            MessageUtils.getMessage(expositionStatus.getMessageId());
      }
    }
    EasyJson<Object> easyJson = new EasyJson<Object>();
    easyJson.setRows(inForm.expositionInfo);
    easyJson.setTotal(resultCount);
    return easyJson;
  }
}
