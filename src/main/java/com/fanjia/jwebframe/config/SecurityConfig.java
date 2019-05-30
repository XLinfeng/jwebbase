package com.fanjia.jwebframe.config;

import com.fanjia.jwebframe.admin.service.impl.UserService;
import com.fanjia.jwebframe.components.CustomAccessDecisionManager;
import com.fanjia.jwebframe.components.CustomFilterinvocationSecurityMetadataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

/**
 * 安全控制配置项
 */
@SpringBootConfiguration
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    UserService userService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/static/**").permitAll()
                .and()
                .formLogin().loginPage("/login").permitAll()
                .loginProcessingUrl("/login/process").permitAll().usernameParameter("username").passwordParameter("password")
                .and()
                .logout().logoutUrl("/logout").permitAll().clearAuthentication(true).invalidateHttpSession(true).logoutSuccessUrl("/login")
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O object) {
                        object.setSecurityMetadataSource(cfisms());
                        object.setAccessDecisionManager(cadm());
                        return object;
                    }
                });
        /*http.authorizeRequests()
                .antMatchers("/static/**","/test").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login").loginProcessingUrl("/login/process").usernameParameter("username").passwordParameter("password").permitAll()
                .and()
                .logout().logoutUrl("/logout").clearAuthentication(true).invalidateHttpSession(true).logoutSuccessUrl("/login").permitAll();*/

        http.csrf().disable();
    }

    /**
     * 指定密码加密码方式
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public CustomFilterinvocationSecurityMetadataSource cfisms() {
        return new CustomFilterinvocationSecurityMetadataSource();
    }

    @Bean
    public CustomAccessDecisionManager cadm() {
        return new CustomAccessDecisionManager();
    }
}
