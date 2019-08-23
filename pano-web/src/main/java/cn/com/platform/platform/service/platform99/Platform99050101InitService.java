package cn.com.platform.platform.service.platform99;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.com.platform.framework.common.CodeValueRecord;
import cn.com.platform.platform.form.platform99.Platform99050101Form;
import cn.com.platform.platform.mapper.common01.PlatformJobCron01Mapper;
import cn.com.platform.web.BaseService;

/**
 * 触发器设定初期化service
 * 
 * @author 王笃鹏
 * @date 2019-01-17
 */
@Service
public class Platform99050101InitService extends BaseService {

  @Autowired
  PlatformJobCron01Mapper platformJobCron01Mapper;

  public void doInit(Platform99050101Form inForm) {
    
    List<CodeValueRecord> weekList = new ArrayList<CodeValueRecord>();
    weekList.add(new CodeValueRecord(Objects.toString(Calendar.SUNDAY),"周日"));
    weekList.add(new CodeValueRecord(Objects.toString(Calendar.MONDAY),"周一"));
    weekList.add(new CodeValueRecord(Objects.toString(Calendar.TUESDAY),"周二"));
    weekList.add(new CodeValueRecord(Objects.toString(Calendar.WEDNESDAY),"周三"));
    weekList.add(new CodeValueRecord(Objects.toString(Calendar.THURSDAY),"周四"));
    weekList.add(new CodeValueRecord(Objects.toString(Calendar.FRIDAY),"周五"));
    weekList.add(new CodeValueRecord(Objects.toString(Calendar.SATURDAY),"周六"));
    inForm.weekList = weekList;
  }
}
