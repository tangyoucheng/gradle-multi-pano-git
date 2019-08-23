package cn.com.pano.pano.service.pano01;

import java.time.LocalDate;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.com.pano.pano.common.code.ExpositionStatus;
import cn.com.pano.pano.form.pano01.Pano0101Form;
import cn.com.pano.pano.mapper.common.PanoExpositionMapper;
import cn.com.pano.pano.model.common.PanoExposition;
import cn.com.platform.framework.code.FlagStatus;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseService;



/**
 * 展览登记。
 * 
 * @author 唐友成
 * @date 2019-08-05
 * 
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class Pano0101EntryService extends BaseService {
  @Autowired
  public PanoExpositionMapper panoExpositionMapper;

  /**
   * 数据登录处理。
   * 
   * @param inForm Pano0101Form
   * @return 处理结果
   * @throws Exception 异常的场合
   */
  public EasyJson<Object> doInsertExpositionInfo(Pano0101Form inForm) throws Exception {

    PanoExposition panoExpositionFromDb =
        panoExpositionMapper.selectByPrimaryKey(inForm.expositionId);
    if (ObjectUtils.isNotEmpty(panoExpositionFromDb)) {
      EasyJson<Object> easyJson = new EasyJson<Object>();
      easyJson.setSuccess(false);
      easyJson.setMsg("当前展览ID已存在！");
      return easyJson;
    }

    // 展览基本信息录入
    PanoExposition panoExposition = new PanoExposition();
    panoExposition.setExpositionId(inForm.expositionId);
    panoExposition.setExpositionName(inForm.expositionName);
    panoExposition.setNotes(inForm.expositionNotes);
    panoExposition.setStatusNotes(inForm.expositionStatusNotes);
    // 判断是否为展览选择了类型
    if (!ObjectUtils.isEmpty(inForm.expoTypeFlag)) {
      panoExposition.setExpositionType(inForm.expositionType);
    }
    // 判断是否为展览选择了状态
    panoExposition.setStatus(ExpositionStatus.PLANNING.toString());
    if (!ObjectUtils.isEmpty(inForm.expositionStatus)) {
      panoExposition.setStatus(inForm.expositionStatus);
    }
    // 判断是否为VR展览
    panoExposition.setVrFlag(FlagStatus.Disable.toString());
    if (!ObjectUtils.isEmpty(inForm.vrFlag)) {
      panoExposition.setVrFlag(inForm.vrFlag);
    }

    createAudit(panoExposition);

    if (!ObjectUtils.isEmpty(inForm.expositionStartDate)) {
      panoExposition.setExpositionStartDate(LocalDate.parse(inForm.expositionStartDate));
    }

    if (!ObjectUtils.isEmpty(inForm.expositionEndDate)) {
      panoExposition.setExpositionEndDate(LocalDate.parse(inForm.expositionEndDate));
    }
    panoExpositionMapper.insert(panoExposition);
    EasyJson<Object> easyJson = new EasyJson<Object>();
    easyJson.setMsg("数据登录成功！");
    return easyJson;
  }

}
