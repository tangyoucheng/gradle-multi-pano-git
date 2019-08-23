package cn.com.platform.common.config;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import cn.com.platform.framework.common.StandardConstantsIF;

@Configuration
public class AppConfig {

  @Value("${spring.messages.basename}")
  String messagesBasename = null;
  @Value("${spring.messages.encoding}")
  String messagesEncoding = null;
  @Value("${spring.messages.cache-duration}")
  int messagesCacheSeconds;


  /**
   * MessageSource初始化。
   * 
   * @return
   */
  @Bean
  public ReloadableResourceBundleMessageSource messageSource() {
    ReloadableResourceBundleMessageSource messageSource =
        new ReloadableResourceBundleMessageSource();
    String[] baseName = messagesBasename.split(",");
    messageSource.setBasenames(baseName); // "classpath:/messages/message"
    messageSource.setDefaultEncoding(messagesEncoding);
    messageSource.setCacheSeconds(messagesCacheSeconds);
    messageSource.setUseCodeAsDefaultMessage(true);;
    return messageSource;
  }

  /**
   * 做成HttpFirewall实例。
   * 
   * @return HttpFirewall实例
   */
  @Bean
  public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
    DefaultHttpFirewall firewall = new DefaultHttpFirewall();
    firewall.setAllowUrlEncodedSlash(true);
    return firewall;
  }

  @Bean
  public SessionRegistry sessionRegistry() {
    return new SessionRegistryImpl();
  }

  /**
   * ObjectMapper时间相关设定。
   * 
   * @return ObjectMapper对象
   */
  @Bean
  public ObjectMapper objectMapper() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    objectMapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
    JavaTimeModule javaTimeModule = new JavaTimeModule();
    // 序列化格式设定
    javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(
        DateTimeFormatter.ofPattern(StandardConstantsIF.DATE_FORMART_YMDHMS_DASH)));
    javaTimeModule.addSerializer(LocalDate.class,
        new LocalDateSerializer(DateTimeFormatter.ISO_LOCAL_DATE));
    javaTimeModule.addSerializer(LocalTime.class,
        new LocalTimeSerializer(DateTimeFormatter.ISO_LOCAL_TIME));
    // 反序列化格式设定
    javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(
        DateTimeFormatter.ofPattern(StandardConstantsIF.DATE_FORMART_YMDHMS_DASH)));
    javaTimeModule.addDeserializer(LocalDate.class,
        new LocalDateDeserializer(DateTimeFormatter.ISO_LOCAL_DATE));
    javaTimeModule.addDeserializer(LocalTime.class,
        new LocalTimeDeserializer(DateTimeFormatter.ISO_LOCAL_TIME));
    objectMapper.registerModule(javaTimeModule).registerModule(new ParameterNamesModule());
    return objectMapper;
  }

  /**
   * HTTPS配置。
   * 
   * @return
   */
  // @Bean
  public ServletWebServerFactory servletContainer() {
    TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
      @Override
      protected void postProcessContext(Context context) {
        SecurityConstraint securityConstraint = new SecurityConstraint();
        securityConstraint.setUserConstraint("CONFIDENTIAL");
        SecurityCollection collection = new SecurityCollection();
        collection.addPattern("/*");
        securityConstraint.addCollection(collection);
        context.addConstraint(securityConstraint);
      }
    };
    tomcat.addAdditionalTomcatConnectors(createHttpConnector());
    return tomcat;
  }

  /**
   * 做成Connector。
   * 
   * @return Connector
   */
  private Connector createHttpConnector() {
    Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
    connector.setScheme("http");
    // Connector监听的http的端口号
    connector.setPort(8080);
    connector.setSecure(false);
    // 监听到http的端口号后转向到的https的端口号
    connector.setRedirectPort(8443);
    return connector;
  }
}
