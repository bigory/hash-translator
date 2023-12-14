package tt.authorization.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .httpBasic()
                .authenticationEntryPoint((request, response, authException) -> {
                    log.error("Authentication exception {}", authException.getMessage());
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("Message exception: invalid credentials");
                })
                .and()
                .csrf().disable()
                .sessionManagement().disable()
                .authorizeRequests().antMatchers("/api/admin/**").hasAuthority("ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .logout((logout) -> {
                    logout.logoutUrl("/api/logout");
                    logout.deleteCookies("JSESSIONID");
                    logout.logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
                });
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
