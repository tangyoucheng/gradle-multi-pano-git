package cn.com.pano.pano.service.pano01;

import java.time.LocalDate;
import java.util.HashMap;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.google.common.collect.Maps;
import cn.com.pano.pano.common.code.ExpositionStatus;
import cn.com.pano.pano.form.pano01.Pano0110Form;
import cn.com.pano.pano.mapper.common.PanoGroupRoleUserMapper;
import cn.com.pano.pano.mapper.common01.PanoPanorama01Mapper;
import cn.com.pano.pano.mapper.pano01.Pano0110AdminMapper;
import cn.com.pano.pano.model.common01.PanoExposition01Model;
import cn.com.platform.framework.util.FwCodeUtil;
import cn.com.platform.framework.util.FwStringUtils;
import cn.com.platform.util.EasyJson;
import cn.com.platform.util.MessageUtils;
import cn.com.platform.web.BaseService;

@Service
@Transactional(rollbackFor = Throwable.class)
public class Pano0110AdminSearchService extends BaseService {
  @Autowired
  public Pano0110AdminMapper pano0110AdminMapper;
  @Autowired
  public PanoPanorama01Mapper panoPanorama01Mapper;
  @Autowired
  public PanoGroupRoleUserMapper panoGroupRoleUserMapper;

  /**
   * 从数据库中检索展览会信息
   * 
   * @param inForm
   * @throws Exception
   */
  public EasyJson<Object> doSearchExpositionInfo(Pano0110Form inForm) throws Exception {

    // 从数据库检索展览会信息
    HashMap<String, Object> condition = Maps.newHashMap();

    condition.put("expositionId", FwStringUtils.getMatchParameter(inForm.expositionId));
    condition.put("expositionName", FwStringUtils.getMatchParameter(inForm.expositionName));
    condition.put("expositionStatus", inForm.expositionStatus);
    condition.put("expositionStartDate", LocalDate.parse(inForm.expositionStartDate));
    condition.put("expositionEndDate", LocalDate.parse(inForm.expositionEndDate));
    // 数据索引
    condition.put("offSet", (inForm.pageNumber - 1) * inForm.pageSize);
    // 每页的条数
    condition.put("pageSize", inForm.pageSize);

    int resultCount = pano0110AdminMapper.selectExpositionInfoCount(condition);
    checkCount(inForm, resultCount);
    // 判断有无从Pano0104传回的有效pageStartRowNo
    if (!ObjectUtils.isEmpty(inForm.pageStartRowNoFromPano0104)) {
      inForm.pageStartRowNo = Integer.parseInt(inForm.pageStartRowNoFromPano0104);
    }
    inForm.expositionInfo =
        pano0110AdminMapper.selectExpositionInfo(condition, inForm.pageStartRowNo);
    // 检查检索出的展览会中状态和时间显示的格式
    if (inForm.expositionInfo != null) {
      for (PanoExposition01Model panoExposition01Model : inForm.expositionInfo) {
        ExpositionStatus expositionStatus =
            FwCodeUtil.stringToEnum(ExpositionStatus.class, panoExposition01Model.status);
        panoExposition01Model.expositionStatusName =
            MessageUtils.getMessage(expositionStatus.getMessageId());
      }
    }

    // 重置0104传回的pageStartRowNo
    inForm.pageStartRowNoFromPano0104 = "";

    EasyJson<Object> easyJson = new EasyJson<Object>();
    easyJson.setRows(inForm.expositionInfo);
    easyJson.setTotal(resultCount);
    return easyJson;
  }

}
