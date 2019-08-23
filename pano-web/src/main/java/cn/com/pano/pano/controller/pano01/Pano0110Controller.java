package cn.com.pano.pano.controller.pano01;

import java.text.MessageFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.com.pano.pano.common.PanoConstantsIF;
import cn.com.pano.pano.common.utils.PanoCommonUtil;
import cn.com.pano.pano.form.pano01.Pano0110Form;
import cn.com.pano.pano.service.pano01.Pano0110AdminSearchService;
import cn.com.pano.pano.service.pano01.Pano0110CreateStaticTourService;
import cn.com.pano.pano.service.pano01.Pano0110InitService;
import cn.com.pano.pano.service.pano01.Pano0110SearchService;
import cn.com.pano.panovr.service.panovr01.Pano0110CreateStaticVrService;
import cn.com.platform.util.EasyJson;
import cn.com.platform.util.FwFileUtils;
import cn.com.platform.web.BaseController;

/**
 * 展厅管理。
 * 
 * @author 唐友成
 * @date 2019-08-05
 * 
 */
@Controller
@RequestMapping("/" + PanoConstantsIF.URI_BASE_MEMBER + "/pano0110")
public class Pano0110Controller extends BaseController {
  @Autowired
  public Pano0110SearchService pano0110SearchService;
  @Autowired
  public Pano0110AdminSearchService pano0110AdminSearchService;
  @Autowired
  public Pano0110CreateStaticTourService pano0110CreateStaticTourService;
  @Autowired
  public Pano0110CreateStaticVrService pano0110CreateStaticVrService;
  @Autowired
  public Pano0110InitService pano0110InitService;

  @ModelAttribute
  public Pano0110Form setPano0110Form(@ModelAttribute Pano0110Form inForm) {
    return inForm;
  }

  /**
   * 初期显示处理。
   * 
   * @return
   */
  @RequestMapping("/")
  public String index(Pano0110Form inForm, HttpServletRequest request) throws Exception {
    pano0110InitService.doInit(inForm);

    // HttpSession httpSession = request.getSession();
    // httpSession.setAttribute("pano0110FormInforId", inForm.expositionId);
    // httpSession.setAttribute("pano0110FormInforName", inForm.expositionName);
    // httpSession.setAttribute("pano0110FormInforStatus", inForm.expositionStatus);
    // httpSession.setAttribute("pano0110FormInforStartDate", inForm.expositionStartDate);
    // httpSession.setAttribute("pano0110FormInforEndDate", inForm.expositionEndDate);

    return viewJsp("/pano/pano/pano01/pano0110");
  }

  /**
   * 检索处理。
   * 
   * @throws Exception 异常的场合
   * @return
   */
  @ResponseBody
  @RequestMapping("/doSearch")
  public EasyJson<Object> doSearch(Pano0110Form inForm, HttpServletRequest request)
      throws Exception {
    // pano0110InitService.doInit(inForm);

    // HttpSession httpSession = request.getSession();
    // httpSession.setAttribute("pano0110FormInforId", inForm.expositionId);
    // httpSession.setAttribute("pano0110FormInforName", inForm.expositionName);
    // httpSession.setAttribute("pano0110FormInforStatus", inForm.expositionStatus);
    // httpSession.setAttribute("pano0110FormInforStartDate", inForm.expositionStartDate);
    // httpSession.setAttribute("pano0110FormInforEndDate", inForm.expositionEndDate);
    // inForm.pageStartRowNo = 0;

    boolean isTenant = PanoCommonUtil.isLoginUserArchiveUser("tenant_manager");
    EasyJson<Object> returnData = new EasyJson<Object>();
    if (isTenant) {
      returnData = pano0110AdminSearchService.doSearchExpositionInfo(inForm);
    } else {
      returnData = pano0110SearchService.doSearchExpositionInfo(inForm);
    }
    return returnData;
  }


  /**
   * 展览发布处理。
   * 
   * @throws Exception 异常的场合
   * @return
   */
  @ResponseBody
  @RequestMapping("/doRelease")
  public EasyJson<Object> doRelease(Pano0110Form inForm) throws Exception {
    pano0110CreateStaticTourService.doCreateStaticTour(inForm);
    EasyJson<Object> easyJson = new EasyJson<Object>();
    easyJson.setMsg("发布成功！");
    return easyJson;
  }

  /**
   * vr展览发布处理。
   * 
   * @throws Exception 异常的场合
   * @return
   */
  @ResponseBody
  @RequestMapping("/doRelease_vr")
  public EasyJson<Object> doRelease_vr(Pano0110Form inForm) throws Exception {
    pano0110CreateStaticVrService.doCreateStaticVr(inForm);
    EasyJson<Object> easyJson = new EasyJson<Object>();
    easyJson.setMsg("发布成功！");
    return easyJson;
  }

  /**
   * 下载展览。
   * 
   * @throws Exception 异常的场合
   * @return
   */
  @ResponseBody
  @RequestMapping("/doDownload")
  public EasyJson<Object> doDownload(Pano0110Form inForm) throws Exception {
    String filePath = MessageFormat.format("statictour_app/{0}.zip", inForm.selectedExpositionId);
    // FileUtils.downloadFile(filePath, inForm.selectedExpositionId + ".zip");
    // TODO DOWNLOAD
    // FileUtils.downloadFile(filePath, inForm.selectedExpositionId + "_vr.zip");
    EasyJson<Object> easyJson = new EasyJson<Object>();
    easyJson.setMsg(filePath);
    return easyJson;
  }

  /**
   * 下载vr展览。
   * 
   * @throws Exception 异常的场合
   * @return
   */
  @ResponseBody
  @RequestMapping("/doDownload_vr")
  public EasyJson<Object> doDownload_vr(Pano0110Form inForm) throws Exception {
    String filePath =
        MessageFormat.format("statictour_app/{0}_vr.zip", inForm.selectedExpositionId);
    // TODO DOWNLOAD
    // FileUtils.downloadFile(filePath, inForm.selectedExpositionId + "_vr.zip");
    EasyJson<Object> easyJson = new EasyJson<Object>();
    easyJson.setMsg(filePath);
    return easyJson;
  }

  /**
   * 查询展览是否是VR展。
   * 
   * @param inForm Pano0110Form
   * @throws Exception 异常的场合
   * @return
   */
  @ResponseBody
  @RequestMapping("/doVrFlag")
  public EasyJson<Object> doVrFlag(Pano0110Form inForm) throws Exception {
    String result = pano0110SearchService.doVrFlag(inForm);
    EasyJson<Object> easyJson = new EasyJson<Object>();
    easyJson.setObj(result);
    return easyJson;
  }
}
