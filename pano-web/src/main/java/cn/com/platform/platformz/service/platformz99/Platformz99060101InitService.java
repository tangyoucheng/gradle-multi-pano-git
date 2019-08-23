package cn.com.platform.platformz.service.platformz99;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.com.platform.platform.mapper.common01.PlatformJobRepeat01Mapper;
import cn.com.platform.platformz.form.platformz99.Platformz99060101Form;
import cn.com.platform.web.BaseService;

/**
 * 触发器设定初期化service
 * 
 * @author 王笃鹏
 * @date 2019-01-17
 */
@Service
public class Platformz99060101InitService extends BaseService {

  @Autowired
  PlatformJobRepeat01Mapper platformJobRepeat01Mapper;

  public void doInit(Platformz99060101Form inForm) {
    
  }
}
