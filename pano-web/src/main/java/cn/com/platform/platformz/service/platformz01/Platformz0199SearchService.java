package cn.com.platform.platformz.service.platformz01;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.com.platform.platform.mapper.common01.PlatformDepartment01Mapper;
import cn.com.platform.platform.model.common01.PlatformDepartment01Model;
import cn.com.platform.framework.code.FlagStatus;
import cn.com.platform.platformz.form.platformz01.Platformz0199Form;
import cn.com.platform.util.EasyJson;
import cn.com.platform.web.BaseService;

/**
 * 社区管理查询service
 * 
 * @author 代仁宗
 * @date 2019-06-17
 */
@Service
public class Platformz0199SearchService extends BaseService {
  /** 自定义SQL */
  @Autowired
  PlatformDepartment01Mapper platformzDepartment01Mapper;

  public EasyJson<PlatformDepartment01Model> doSearch(Platformz0199Form inForm) {

    // SQL文的参数
    HashMap<String, Object> parameter = new HashMap<String, Object>();
    // 社区名
    parameter.put("departmentName", inForm.getDepartmentName());
    // 删除标识
    parameter.put("deleteFlag", FlagStatus.Disable.toString());
    // 排序
    parameter.put("orderByClause", getOrderInfo(inForm));
    // 数据索引
    parameter.put("offSet", (inForm.pageNumber - 1) * inForm.pageSize);
    // 每页的条数
    parameter.put("pageSize", inForm.pageSize);

    // 总件数 selectCompanyCount为xml的ID名
    inForm.recordCount = platformzDepartment01Mapper.selectDepartmentCount(parameter);
    if (inForm.recordCount > 0) {
      // 每页数据
      List<PlatformDepartment01Model> resultInfo = new ArrayList<PlatformDepartment01Model>();
      resultInfo = platformzDepartment01Mapper.selectDepartmentInfo(parameter);
      
      
      inForm.departmentList = new ArrayList<PlatformDepartment01Model>();
      // 生成科目树状结构
      int count1 = 1;
      for (PlatformDepartment01Model subjectOne : resultInfo) {
        if (subjectOne.getDepartmentHierarchy().equals("1")) {
          int count2 = 1;
          // 一级科目
          subjectOne.id = Objects.toString(count1);
          subjectOne.parentId = null;
          inForm.departmentList.add(subjectOne);
          for (PlatformDepartment01Model subjectTwo : resultInfo) {
            if (subjectTwo.getParentDepartmentId() != null
                && subjectTwo.getParentDepartmentId().equals(subjectOne.getDepartmentId())) {
              int count3 = 1;
              // 二级科目
              subjectTwo.id = Objects.toString(count1) + (count2);
              subjectTwo.parentId = Objects.toString(count1);
              inForm.departmentList.add(subjectTwo);
              for (PlatformDepartment01Model subjectThree : resultInfo) {
                if (subjectThree.getParentDepartmentId() != null
                    && subjectThree.getParentDepartmentId().equals(subjectTwo.getDepartmentId())) {
                  // 三级科目
                  subjectThree.id = Objects.toString(count1) + count2 + count3;
                  subjectThree.parentId = Objects.toString(count1) + count2;
                  inForm.departmentList.add(subjectThree);
                  count3++;
                }
              }
              count2++;
            }
          }
          count1++;
        }
      }
      
      
    }

    EasyJson<PlatformDepartment01Model> easyJson = new EasyJson<PlatformDepartment01Model>();
    easyJson.setRows(inForm.departmentList);
//    easyJson.setTotal(inForm.recordCount);
    return easyJson;
  }

  /**
   * 取得排序信息。
   * 
   * @param inForm
   * @return 排序信息
   */
  private String getOrderInfo(Platformz0199Form inForm) {
    Map<Object, Object> orderkeyInfo = new HashMap<>();
    orderkeyInfo.put("departmentId", "department_id");
    // 中文排序
    orderkeyInfo.put("departmentName", "CONVERT( department_name USING gbk ) COLLATE gbk_chinese_ci");
    if (orderkeyInfo.get(inForm.sortName) == null) {
      return null;
    }
    return orderkeyInfo.get(inForm.sortName) + " " + inForm.sortOrder;
  }
}
