package cn.com.pano.pano.controller.pano01;

import java.io.File;
import java.text.MessageFormat;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.pano.common.utils.FileServiceUtil;
import cn.com.pano.pano.form.pano01.Pano0103Form;
import cn.com.pano.pano.mapper.common.PanoExpositionMapper;
import cn.com.pano.pano.service.pano01.Pano0103InitService;
import cn.com.pano.pano.service.pano01.Pano0103UpdateService;
import cn.com.platform.util.EasyJson;
import cn.com.platform.util.FwFileUtils;
import cn.com.platform.web.BaseController;

/**
 * 展览编辑。
 * 
 * @author ouyangzidu
 * 
 */
@Controller
@RequestMapping("/" + PanoConstantsIF.URI_BASE_MEMBER + "/pano0103")
public class Pano0103Controller extends BaseController {

  @Autowired
  public Pano0103UpdateService pano0103UpdateService;
  @Autowired
  public Pano0103InitService pano0103InitService;
  @Autowired
  public PanoExpositionMapper panoExpositionService;

  @ModelAttribute
  public Pano0103Form setPano0103Form(@ModelAttribute Pano0103Form inForm) {
    return inForm;
  }

  /**
   * 初期显示处理。
   * 
   * @return
   */
  @RequestMapping("/")
  public String index(Pano0103Form inForm) throws Exception {
    pano0103InitService.doInit(inForm);
    return viewJsp("/pano/pano/pano01/pano0103");
  }

  /**
   * 展览信息更新处理。
   * 
   * @return
   */
  @ResponseBody
  @RequestMapping("/doUpdate")
  public EasyJson<Object> doUpdate(Pano0103Form inForm) throws Exception {
    return pano0103UpdateService.doUpdateExpositionInfo(inForm);
  }

  /**
   * 展览删除处理。
   * 
   * @return
   */
  @ResponseBody
  @RequestMapping("/doDelete")
  public EasyJson<Object> doDelete(Pano0103Form inForm) throws Exception {
    return pano0103UpdateService.doDeleteExpositionInfo(inForm);
  }

  /**
   * 取得临时文件处理。
   * 
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/doGetTempFile")
  public EasyJson<Object> doGetTempFile(Pano0103Form inForm) throws Exception {
    String _destPath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_TEMP_PRELOADFILE,
        inForm.expositionId);
    String result =
        FileServiceUtil.copyDirFromSessionStorageToAppServer(inForm.expositionId, _destPath);
    EasyJson<Object> easyJson = new EasyJson<Object>();
    easyJson.setObj(result);
    return easyJson;
  }

  /**
   * 删除临时文件处理。
   * 
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/doDeleteTempFile")
  public EasyJson<Object> doDeleteTempFile(Pano0103Form inForm) throws Exception {

    String filePath = MessageFormat.format(PanoConstantsIF.VAL_APP_SERVER_W_TEMP_PRELOADFILE,
        inForm.expositionId);
    FileUtils.deleteDirectory(new File(FwFileUtils.getAbsolutePath(filePath)));
    EasyJson<Object> easyJson = new EasyJson<Object>();
    return easyJson;
  }

  /**
   * 关联验证
   * 
   * @return
   */
  // public ActionMessages validateDoUpdate() throws Exception {
  // ActionMessages errors = new ActionMessages();
  //
  // // 开展时间是否晚于撤展时间检测
  // if (!CheckUtils.isEmpty(pano0103Form.expositionStartDate)
  // && !CheckUtils.isEmpty(pano0103Form.expositionEndDate)) {
  // Date dateStart = DateUtils.parse(pano0103Form.expositionStartDate,
  // FrameworkConstants.DATE_FORMAT_YYYY_YEAR_MM_MONTH_DD_DATE);
  // Date dateEnd = DateUtils.parse(pano0103Form.expositionEndDate,
  // FrameworkConstants.DATE_FORMAT_YYYY_YEAR_MM_MONTH_DD_DATE);
  // if (CheckUtils.isMoreThan(dateStart, dateEnd)) {
  // errors.add("expositionStartDate", new ActionMessage("framework.errors.E0002", "撤展时间"));
  // }
  // }
  //
  // // 数据库中展览名称重复check
  // if (!CheckUtils.isEmpty(pano0103Form.expositionName)) {
  // HashMap<String, Object> condition = Maps.newHashMap();
  // conditions.put("expositionName", pano0103Form.expositionName);
  // List<PanoExposition> listResult = panoExpositionService.findByCondition(conditions);
  // if (listResult != null && listResult.size() > 0
  // && !CheckUtils.isEqual(listResult.get(0).expositionId, pano0103Form.expositionId)) {
  // errors.add("expositionName",
  // new ActionMessage("framework.errors.E0001", pano0103Form.expositionName));
  // }
  // }
  // return errors;
  // }
}
