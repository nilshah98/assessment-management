package com.accolite.assessmentmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@SpringBootApplication
@RestController
public class AssessmentManagementApplication extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http.cors().and()
                .authorizeRequests(a -> a
                        .antMatchers("/", "/error", "/webjars/**","/api/login").permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling(e -> e
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                )
                .oauth2Login();
        // @formatter:on
    }

    @GetMapping("/api/login")
    public ModelAndView login() {
        return new ModelAndView("redirect:/oauth2/authorization/google");
    }

    @GetMapping("/api/profile")
    public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) {
        return principal.getAttributes();
    }

    @GetMapping("/hello")
    public String greet(){
        return "Hello World";
    }

    public static void main(String[] args) {
        SpringApplication.run(AssessmentManagementApplication.class, args);
    }
}
