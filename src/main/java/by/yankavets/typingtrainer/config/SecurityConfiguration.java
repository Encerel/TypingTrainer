package by.yankavets.typingtrainer.config;

import by.yankavets.typingtrainer.security.BasicAuthEntryPoint;
import by.yankavets.typingtrainer.security.filter.JwtTokenGeneratorFilter;
import by.yankavets.typingtrainer.security.filter.JwtTokenValidatorFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
public class SecurityConfiguration {



    private final JwtTokenValidatorFilter jwtTokenValidatorFilter;
    private final JwtTokenGeneratorFilter jwtTokenGeneratorFilter;


    @Autowired
    public SecurityConfiguration(JwtTokenValidatorFilter jwtTokenValidatorFilter,
                                 JwtTokenGeneratorFilter jwtTokenGeneratorFilter) {
        this.jwtTokenValidatorFilter = jwtTokenValidatorFilter;
        this.jwtTokenGeneratorFilter = jwtTokenGeneratorFilter;
    }


    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }



    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .cors()
                .and()
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/auth/**", "/api/users").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtTokenValidatorFilter, BasicAuthenticationFilter.class)
                .addFilterAfter(jwtTokenGeneratorFilter,  BasicAuthenticationFilter.class)
                .httpBasic();

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder () {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://127.0.0.1:3000", "http://localhost:3000, http://26.199.222.75:3000"));
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(List.of("*"));
        configuration.setAllowedHeaders(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }



}
