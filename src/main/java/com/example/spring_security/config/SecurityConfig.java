package com.example.spring_security.config;

import com.example.spring_security.service.UserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder(12);
    }
    @Bean
    public AuthenticationManager authenticationManager(PasswordEncoder passwordEncoder, UserDetailService userDetailService) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        authenticationProvider.setUserDetailsService(userDetailService);
        return new ProviderManager(authenticationProvider);
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .authorizeRequests()
                    .antMatchers("/register","/login/**").permitAll()
                    .antMatchers("/admin/**").hasAnyRole("ADMIN")
                    .antMatchers("/user/**").hasAnyRole("USER")
                    .antMatchers("/product/**").hasAnyRole("USER")
                    .anyRequest().authenticated()
                .and()
                .formLogin()
                    .loginPage("/login")
                    .defaultSuccessUrl("/")
                    .failureForwardUrl("/login?error=true")
                .and()
                .logout()
                .permitAll()
//                .logout()
//                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                .logoutSuccessUrl("login")
//                .deleteCookies("JSESSIONID")
//                .invalidateHttpSession(true)
//                .permitAll()
//                .and()
//                .authorizeRequests()
//                .antMatchers("register").permitAll()
//                .anyRequest().authenticated()
                .and()
                .exceptionHandling().accessDeniedPage("/access-denied");
//        http.addFilterBefore(new UsernamePasswordAuthenticationFilter(), BasicAuthenticationFilter.class);
        return http.build();
        //.antMatchers("/register") để cho phép mọi người truy cập trang đăng ký mà không cần xác thực.
        //.formLogin() để cấu hình trang đăng nhập, .loginPage("/login") để xác định trang đăng nhập tùy chỉnh,
        // và .defaultSuccessUrl("/") để xác định trang mặc định sau khi đăng nhập thành công.
        //.logout() để cấu hình trang đăng xuất và .
        // logoutRequestMatcher(new AntPathRequestMatcher("/logout")) để xác định URL cho đăng xuất.
    }
}
