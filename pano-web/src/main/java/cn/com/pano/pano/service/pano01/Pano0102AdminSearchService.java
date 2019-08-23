package cn.com.pano.pano.service.pano01;

import java.time.LocalDate;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.google.common.collect.Maps;
import cn.com.pano.pano.common.code.ExpositionStatus;
import cn.com.pano.pano.form.pano01.Pano0102Form;
import cn.com.pano.pano.mapper.common01.PanoGroupRoleUser01Mapper;
import cn.com.pano.pano.mapper.common01.PanoPanorama01Mapper;
import cn.com.pano.pano.mapper.pano01.Pano0102AdminMapper;
import cn.com.pano.pano.model.common01.PanoExposition01Model;
import cn.com.platform.framework.util.FwCodeUtil;
import cn.com.platform.framework.util.FwStringUtils;
import cn.com.platform.util.EasyJson;
import cn.com.platform.util.MessageUtils;
import cn.com.platform.web.BaseService;

/**
 * 展览会一览画面处理检索处理
 * 
 * @author shiwei
 * 
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class Pano0102AdminSearchService extends BaseService {

  @Autowired
  public Pano0102AdminMapper pano0102AdminMapper;
  @Autowired
  public PanoPanorama01Mapper panoPanorama01Mapper;
  @Autowired
  public PanoGroupRoleUser01Mapper panoGroupRoleUser01Mapper;

  /**
   * 从数据库中检索展览会信息
   * 
   * @param inForm
   * @throws Exception
   */
  public EasyJson<Object> doSearchExpositionInfo(Pano0102Form inForm) throws Exception {
    // 从数据库检索展览会信息
    HashMap<String, Object> condition = Maps.newHashMap();

    condition.put("expositionId", FwStringUtils.getMatchParameter(inForm.expositionId));
    condition.put("expositionName", FwStringUtils.getMatchParameter(inForm.expositionName));
    condition.put("expositionStartDate", LocalDate.parse(inForm.expositionStartDate));
    condition.put("expositionEndDate", LocalDate.parse(inForm.expositionEndDate));
    condition.put("expositionStatus", inForm.expositionStatus);
    // 数据索引
    condition.put("offSet", (inForm.pageNumber - 1) * inForm.pageSize);
    // 每页的条数
    condition.put("pageSize", inForm.pageSize);

    long resultCount = pano0102AdminMapper.selectExpositionInfoCount(condition);
    if (resultCount > 0) {
      inForm.expositionInfo =
          pano0102AdminMapper.selectExpositionInfo(condition, inForm.pageStartRowNo);
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
