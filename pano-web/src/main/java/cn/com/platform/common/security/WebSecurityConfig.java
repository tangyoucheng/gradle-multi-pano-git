package cn.com.platform.common.security;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.firewall.HttpFirewall;
import cn.com.platform.common.CommonConstantsIF;
import cn.com.platform.web.context.SyncOnlineSessionFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // 开启security注解,属性设置后控制器层的方法前的@PreAuthorize("hasRole('admin')")
// 注解才能起效。
public class WebSecurityConfig {

    // 会员页面设定
    @Configuration
    @Order(1)
    public static class MemberConfigurerAdapter extends WebSecurityConfigurerAdapter {
      
      @Autowired
      private SessionRegistry sessionRegistry;
      @Autowired
      private HttpFirewall shMemberhttpFirewall;
      @Autowired
      private ShMemberDetailsAuthenticationProvider shMemberDetailsAuthenticationProvider;
      
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                .exceptionHandling().authenticationEntryPoint(new UnauthorizedEntryPoint())// 未登陆场合，访问了需要认证的资源时的异常处理
                .and()
                .sessionManagement().sessionFixation().none()//登录前后sessionID保持一致
                .and()
                .antMatcher("/" + CommonConstantsIF.URI_BASE_MEMBER + "/**") // 过滤请求范围
                .authorizeRequests()
//                    .antMatchers("/" + CommonConstantsIF.URI_BASE_MEMBER + "/?").permitAll()//登录页面
                    .antMatchers("/" + CommonConstantsIF.URI_BASE_MEMBER + "/**")
//                    .authenticated()
                    .hasRole("MEMBER") // 会员可方位的资源
                    .anyRequest().authenticated()
                .and()
                    .formLogin()
                    .loginProcessingUrl("/" + CommonConstantsIF.URI_BASE_MEMBER + "/login")//form表单提交时指定的
//                    .loginPage("/member/generalLogin")//登录页url
                    // 成功返回的处理
                    .successHandler(new AuthSuccessHandler())
                    // 失败返回的处理
                    .failureHandler(new AuthFailurelHandler()).permitAll()
//                    .failureUrl("/member/generalLogin")
//                    .defaultSuccessUrl("/member/")
                    .usernameParameter("loginId")//用户名的请求字段
                    .passwordParameter("password")//密码的请求字段
                .and()
                    .logout()// 退出系统处理设定
//                    .logoutUrl("/member/logout")// 退出系统处理的URL
//                    .logoutSuccessUrl("/")// 退出成功后跳转的URL
                    .deleteCookies("JSESSIONID")// 退出时删除缓存
                    .invalidateHttpSession(true)// 退出时会话无效化处理
                    .clearAuthentication(true)
                .and()
                    .csrf().disable()//关闭csrf 防止循环定向
                    .headers()
                        .cacheControl().disable()
                        .frameOptions().sameOrigin();// 配置Spring Security允许iframe frame加载同源的资源
            http.sessionManagement()
                .maximumSessions(1)// 以下这句就可以控制单个用户只能创建一个session，也就只能在服务器登录一次(-1无限制)
                .sessionRegistry(sessionRegistry)//在线用户使用的SessionRegistry
                .expiredUrl("/");//会话结束后跳转的URL
            http.exceptionHandling().accessDeniedHandler(new ShAccessDeniedHandler());// 已登陆场合，访问了无权访问的页面的场合，跳转到登录首页
            http.addFilterAfter(new SyncOnlineSessionFilter(), UsernamePasswordAuthenticationFilter.class);
        }

        @Override
        public void configure(WebSecurity web) throws Exception {
          // 解决静态资源被拦截的问题
          List<String> anonymousResouce = new ArrayList<>();
          anonymousResouce.add("/");
          anonymousResouce.add("/webjars/**");
          anonymousResouce.add("/framework/**");
          anonymousResouce.add("/**/*.css");
          anonymousResouce.add("/**/*.js");
          anonymousResouce.add("/**/favicon.ico");
          anonymousResouce.add("/**/*.jsp");

          anonymousResouce.add("/" + CommonConstantsIF.URI_BASE_MEMBER + "/platform01010201/");
          anonymousResouce.add("/" + CommonConstantsIF.URI_BASE_MEMBER + "/platform01010201/checkRegister");
//          anonymousResouce.add("/" + CommonConstantsIF.URI_BASE_MEMBER + "/platform01010202/");
//          anonymousResouce.add("/" + CommonConstantsIF.URI_BASE_MEMBER + "/platform01010202/checkGeneralLogin");
          anonymousResouce.add("/");
          anonymousResouce.add("/checkGeneralLogin");

          web.ignoring().antMatchers(anonymousResouce.toArray(new String[anonymousResouce.size()]))
             .and()
             .httpFirewall(shMemberhttpFirewall);
        }
        
        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
          // 设定认证方法
          auth.authenticationProvider(shMemberDetailsAuthenticationProvider);
        }
    }
    
    // 管理员页面设定
    @Configuration
    @Order(2)
    public static class AdminConfigurerAdapter extends WebSecurityConfigurerAdapter {

      @Autowired
      private SessionRegistry sessionRegistry;
      @Autowired
      private HttpFirewall shAdminhttpFirewall;
      @Autowired
      private ShAdminDetailsAuthenticationProvider shAdminDetailsAuthenticationProvider;
      
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                .exceptionHandling().authenticationEntryPoint(new UnauthorizedEntryPoint())// 未登陆场合，访问了需要认证的资源时的异常处理
                .and()
                .sessionManagement().sessionFixation().none()//登录前后sessionID保持一致
                .and()
                .antMatcher("/" + CommonConstantsIF.URI_BASE_ADMIN + "/**") // 过滤请求范围
                .authorizeRequests()
//                    .antMatchers("/admin/").permitAll() // 登陆未登陆都可以访问
                    .antMatchers("/" + CommonConstantsIF.URI_BASE_ADMIN + "/**")
//                    .authenticated()
                    .hasRole("ADMIN") // 管理员可访问的资源
                    .anyRequest().authenticated()
                .and()
                    .formLogin()
                    .loginProcessingUrl("/" + CommonConstantsIF.URI_BASE_ADMIN + "/login")
//                    .loginPage("/admin/generalLogin")
                    // 成功返回的处理
                    .successHandler(new AuthSuccessHandler())
                    // 失败返回的处理
                    .failureHandler(new AuthFailurelHandler()).permitAll()
//                    .failureUrl("/admin/")
//                    .defaultSuccessUrl("/admin/ps99/ps9901")
                    .usernameParameter("loginId")// 用户名的请求字段
                    .passwordParameter("password")// 密码的请求字段
                .and()
                    .logout()// 退出系统处理设定
//                    .logoutUrl("/**/logout")// 退出系统处理的URL
//                    .logoutSuccessUrl("/admin/")// 退出成功后跳转的URL
                    .deleteCookies("JSESSIONID")// 退出时删除缓存
                    .invalidateHttpSession(true)// 退出时会话无效化处理
                    .clearAuthentication(true)
                .and()
                    .csrf().disable()//关闭csrf 防止循环定向
                    .headers()
                        .cacheControl().disable()
                        .frameOptions().sameOrigin();// 配置Spring Security允许iframe frame加载同源的资源
            http.sessionManagement()
                .maximumSessions(1)// 以下这句就可以控制单个用户只能创建一个session，也就只能在服务器登录一次(-1无限制)
                .sessionRegistry(sessionRegistry)//在线用户使用的SessionRegistry
                .expiredUrl("/"+ CommonConstantsIF.URI_BASE_ADMIN + "/");//会话结束后跳转的URL
            http.exceptionHandling().accessDeniedHandler(new ShAccessDeniedHandler());// 已登陆场合，访问了无权访问的页面的场合，跳转到登录首页
            http.addFilterAfter(new SyncOnlineSessionFilter(), UsernamePasswordAuthenticationFilter.class);
        }

        @Override
        public void configure(WebSecurity web) throws Exception {
          // 解决静态资源被拦截的问题
          List<String> anonymousResouce = new ArrayList<>();

          anonymousResouce.add("/" + CommonConstantsIF.URI_BASE_ADMIN + "/");
          anonymousResouce.add("/" + CommonConstantsIF.URI_BASE_ADMIN + "/checkGeneralLogin");
          
          web.ignoring().antMatchers(anonymousResouce.toArray(new String[anonymousResouce.size()]))
             .and()
             .httpFirewall(shAdminhttpFirewall);
        }
        
        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
          // 设定认证方法
          auth.authenticationProvider(shAdminDetailsAuthenticationProvider);
        }
    }

}