//package com.wsh.framework.config;
//
//
//import com.wsh.framework.security.filter.JwtAuthenticationTokenFilter;
//import com.wsh.framework.security.filter.WxJwtAuthenticationTokenFilter;
//import com.wsh.framework.security.handle.AuthenticationEntryPointImpl;
//import com.wsh.framework.security.handle.LogoutSuccessHandlerImpl;
//import com.wsh.framework.web.service.UserDetailsServiceImpl;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.annotation.Order;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.security.web.authentication.logout.LogoutFilter;
//import org.springframework.web.filter.CorsFilter;
//
//@Configuration
//@EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true, securedEnabled = true)
//@EnableWebSecurity
//@ConditionalOnClass(WebSecurityConfigurerAdapter.class)
//@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
//public class CustomSpringBootWebSecurityConfiguration {
//    /**
//     * 后台接口安全策略. 默认配置
//     */
//    @Configuration
//    @Order(1)
//    static class AdminConfigurerAdapter extends WebSecurityConfigurerAdapter {
//
//
//        /**
//         * 认证失败处理类
//         */
//        @Autowired
//        private AuthenticationEntryPointImpl unauthorizedHandler;
//
//        /**
//         * 退出处理类
//         */
//        @Autowired
//        private LogoutSuccessHandlerImpl logoutSuccessHandler;
//
//        /**
//         * token认证过滤器
//         */
//        @Autowired
//        private JwtAuthenticationTokenFilter authenticationTokenFilter;
//
//        @Autowired
//        private UserDetailsServiceImpl userDetailsService;
//        /**
//         * 跨域过滤器
//         */
//        @Autowired
//        private CorsFilter corsFilter;
//
//        /**
//         * 解决 无法直接注入 AuthenticationManager
//         *
//         * @return
//         * @throws Exception
//         */
////        @Bean
//        @Bean
//        @Override
//        public AuthenticationManager authenticationManagerBean() throws Exception
//        {
//            return super.authenticationManagerBean();
//        }
//
//        @Override
//        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//            DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
//            //用户详情服务个性化
//            daoAuthenticationProvider.setUserDetailsService(userDetailsService);
//            // 也可以设计特定的密码策略
//            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//            daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder);
//            auth.authenticationProvider(daoAuthenticationProvider);
//        }
//
//        @Override
//        public void configure(WebSecurity web) {
//            try {
//                super.configure(web);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        @Override
//        protected void configure(HttpSecurity http) throws Exception {
//            // 根据需求自行定制
//            http.antMatcher("/system/**")
//                    .csrf().disable()
//                    // 认证失败处理类
//                     .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
//                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
//                    .authorizeRequests()
//                   // 对于登录login 验证码captchaImage 允许匿名访问
//                    .antMatchers("/login", "/captchaImage").anonymous()
//                     .antMatchers(
//                        HttpMethod.GET,
//                        "/*.html",
//                        "/**/*.html",
//                        "/**/*.css",
//                        "/**/*.js"
//                     ).permitAll()
//                    .antMatchers("/merchant/**").anonymous()
//                .antMatchers("/profile/**").anonymous()
//                .antMatchers("/common/download**").anonymous()
//                .antMatchers("/common/download/resource**").anonymous()
//                .antMatchers("/swagger-ui.html").anonymous()
//                .antMatchers("/swagger-resources/**").anonymous()
//                .antMatchers("/webjars/**").anonymous()
//                .antMatchers("/*/api-docs").anonymous()
//                .antMatchers("/druid/**").anonymous()
//                // 除上面外的所有请求全部需要鉴权认证
//                .anyRequest().authenticated()
//                .and()
//                .headers().frameOptions().disable();
//            http.logout().logoutUrl("/logout").logoutSuccessHandler(logoutSuccessHandler);
//        // 添加JWT filter
//            http.addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
////        httpSecurity.addFilterBefore(wxAuthenticationProcessingFilter,UsernamePasswordAuthenticationFilter.class);
//        // 添加CORS filter
////        httpSecurity.addFilterBefore(corsFilter, WxAuthenticationProcessingFilter.class);
//            http.addFilterBefore(corsFilter, JwtAuthenticationTokenFilter.class);
//            http.addFilterBefore(corsFilter, LogoutFilter.class);
//
//
//
//        }
//    }
//
//
//    /**
//     * app接口安全策略. 没有{@link Order}注解优先级比上面低
//     */
//    @Configuration
//    static class AppConfigurerAdapter extends WebSecurityConfigurerAdapter {
//
//        @Autowired
//        private UserDetailsServiceImpl wxUserDetailsService;
//
//        /**
//         * 认证失败处理类
//         */
//        @Autowired
//        private AuthenticationEntryPointImpl unauthorizedHandler;
//
//        /**
//         * 退出处理类
//         */
//        @Autowired
//        private LogoutSuccessHandlerImpl logoutSuccessHandler;
//
//        /**
//         * token认证过滤器
//         */
//        @Autowired
//        private WxJwtAuthenticationTokenFilter authenticationTokenFilter;
//
//        /**
//         * 跨域过滤器
//         */
//        @Autowired
//        private CorsFilter corsFilter;
//
//
//
////        @Bean
////        @Override
////        public AuthenticationManager authenticationManagerBean() throws Exception
////        {
////            return new MyAuthenticationManager();
////        }
//
//        @Override
//        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//            DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
//            //用户详情服务个性化
//            daoAuthenticationProvider.setUserDetailsService(wxUserDetailsService);
//            // 也可以设计特定的密码策略
//            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//            daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder);
//            auth.authenticationProvider(daoAuthenticationProvider);
//        }
//
//        @Override
//        public void configure(WebSecurity web) {
//            try {
//                super.configure(web);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        @Override
//        protected void configure(HttpSecurity http) throws Exception {
//            // 根据需求自行定制
//            http.antMatcher("/miniapp/**")
//                    .csrf().disable()
//                    // 认证失败处理类
//                    .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
//                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
//                    .authorizeRequests()
//                    // 对于登录login 验证码captchaImage 允许匿名访问
//                    .antMatchers("/miniapp/wxLogin", "/captchaImage").anonymous()
//                    .antMatchers(
//                            HttpMethod.GET,
//                            "/*.html",
//                            "/**/*.html",
//                            "/**/*.css",
//                            "/**/*.js"
//                    ).permitAll()
//                    .antMatchers("/merchant/**").anonymous()
//                    .antMatchers("/profile/**").anonymous()
//                    .antMatchers("/common/download**").anonymous()
//                    .antMatchers("/common/download/resource**").anonymous()
//                    .antMatchers("/swagger-ui.html").anonymous()
//                    .antMatchers("/swagger-resources/**").anonymous()
//                    .antMatchers("/webjars/**").anonymous()
//                    .antMatchers("/*/api-docs").anonymous()
//                    .antMatchers("/druid/**").anonymous()
//                    // 除上面外的所有请求全部需要鉴权认证
//                    .anyRequest().authenticated()
//                    .and()
//                    .headers().frameOptions().disable();
//            http.logout().logoutUrl("/logout").logoutSuccessHandler(logoutSuccessHandler);
//            // 添加JWT filter
//            http.addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
////        httpSecurity.addFilterBefore(wxAuthenticationProcessingFilter,UsernamePasswordAuthenticationFilter.class);
//            // 添加CORS filter
////        httpSecurity.addFilterBefore(corsFilter, WxAuthenticationProcessingFilter.class);
//            http.addFilterBefore(corsFilter, WxJwtAuthenticationTokenFilter.class);
//
//
//        }
//    }
//}
