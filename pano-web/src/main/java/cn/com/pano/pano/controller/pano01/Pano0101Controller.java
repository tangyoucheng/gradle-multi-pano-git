package cn.com.pano.pano.controller.pano01;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.pano.form.pano01.Pano0101Form;
import cn.com.pano.pano.mapper.common.PanoExpositionMapper;
import cn.com.pano.pano.service.pano01.Pano0101EntryService;
import cn.com.pano.pano.service.pano01.Pano0101InitService;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseController;

/**
 * 
 * 展览登记。
 * 
 * @author ouyangzidu
 * 
 */
@Controller
@RequestMapping("/" + PanoConstantsIF.URI_BASE_MEMBER + "/pano0101")
public class Pano0101Controller extends BaseController {

  @Autowired
  public Pano0101EntryService pano0101EntryService;
  @Autowired
  public PanoExpositionMapper panoExpositionService;
  @Autowired
  public Pano0101InitService pano0101InitService;

  @ModelAttribute
  public Pano0101Form setPano0101Form(@ModelAttribute Pano0101Form inForm) {
    return inForm;
  }

  /**
   * 初期显示处理
   * 
   * @return
   * @throws Exception
   */
  @RequestMapping("/")
  public String index(Pano0101Form inForm) throws Exception {
    pano0101InitService.doInit(inForm);
    return viewJsp("/pano/pano/pano01/pano0101");
  }

  /**
   * 登录处理
   * 
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping(path = "/doEntry")
  public EasyJson<Object> doEntry(Pano0101Form inForm) throws Exception {
    return pano0101EntryService.doInsertExpositionInfo(inForm);
  }

  /**
   * 关联验证
   * 
   * @return
   * @throws SystemException
   */
//  public ActionMessages validateDoEntry() throws SystemException {
//    ActionMessages errors = new ActionMessages();
//    // 展览编号是否是数字和字母
//    if (CheckUtils.isEmpty(pano0101Form.expositionId)) {
//      errors.add("expositionId", new ActionMessage("errors.required", "展览编号"));
//    } else {
//      if (!CheckUtils.isAlphaNum(pano0101Form.expositionId)
//          || !CheckUtils.isHalfWidth(pano0101Form.expositionId)) {
//        errors.add("expositionId", new ActionMessage("framework.errors.E0003", "展览编号"));
//      }
//    }
//
//    // 展览名称是否为空
//    if (CheckUtils.isEmpty(pano0101Form.expositionName)) {
//      errors.add("expositionName", new ActionMessage("errors.required", "展览名称"));
//    }
//
//    if (!CheckUtils.isEmpty(pano0101Form.expositionId)) {
//      // 数据库中编号重复check
//      PanoExposition panoExposition = panoExpositionService.findById(pano0101Form.expositionId);
//      if (icWMExposition != null && pano0101Form.expositionId.equals(icWMExposition.expositionId)) {
//        errors.add("expositionId",
//            new ActionMessage("framework.errors.E0001", panoExposition.expositionId));
//      }
//    }
//
//    // 数据库中名称重复check
//    if (!CheckUtils.isEmpty(pano0101Form.expositionName)) {
//      HashMap<String, Object> condition = Maps.newHashMap();
//      conditions.put("expositionName", pano0101Form.expositionName);
//      List<PanoExposition> listResult = panoExpositionService.findByCondition(conditions);
//      if (listResult != null && listResult.size() > 0) {
//        errors.add("expositionName",
//            new ActionMessage("framework.errors.E0001", pano0101Form.expositionName));
//      }
//    }
//
//    // 开展时间是否晚于撤展时间检测
//    if (!CheckUtils.isEmpty(pano0101Form.expositionStartDate)
//        && !CheckUtils.isEmpty(pano0101Form.expositionEndDate)) {
//      Date dateStart = DateUtils.parse(pano0101Form.expositionStartDate,
//          FrameworkConstants.DATE_FORMAT_YYYY_YEAR_MM_MONTH_DD_DATE);
//      Date dateEnd = DateUtils.parse(pano0101Form.expositionEndDate,
//          FrameworkConstants.DATE_FORMAT_YYYY_YEAR_MM_MONTH_DD_DATE);
//      if (CheckUtils.isMoreThan(dateStart, dateEnd)) {
//        errors.add("expositionStartDate", new ActionMessage("framework.errors.E0002", "撤展时间"));
//      }
//    }
//
//    return errors;
//  }
}
