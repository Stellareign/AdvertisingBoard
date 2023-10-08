package ru.skypro.homework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import ru.skypro.homework.dto.Role;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private static final String[] AUTH_WHITELIST = {
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/v3/api-docs",
            "/webjars/**",
            "/login",
            "/register"
    };

    @Bean
    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails user =
                User.builder()
                        .username("user@gmail.com")
                        .password("password")
                        .passwordEncoder(passwordEncoder::encode)
                        .roles(Role.USER.name())
                        .build();

        UserDetails admin =
                User.builder()
                        .username("admin@gmail.com")
                        .password("password")
                        .passwordEncoder(passwordEncoder::encode)
                        .roles(Role.ADMIN.name())
                        .build();

        UserDetails pupkin =
                User.builder()
                        .username("pupkin@poy.ru")
                        .password("qwerty123")
                        .passwordEncoder(passwordEncoder::encode)
                        .roles(Role.USER.name())
                        .build();
        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .authorizeHttpRequests(
                        authorization ->
                                authorization
                                        .mvcMatchers(AUTH_WHITELIST)
                                        .permitAll()
                                        .mvcMatchers("/ads/**", "/users/**")
                                        .authenticated())
                .cors()
                .and()
                .httpBasic(withDefaults());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //***********************************************************************************************


//    private void customizeRequest(AuthorizeHttpRequestsConfigurer<HttpSecurity>
//                                          .AuthorizationManagerRequestMatcherRegistry registry) {
//        try {
//            registry.requestMatchers(new AntPathRequestMatcher("/admin/**"))
//                    .hasAnyRole("ADMIN")
//                    // Определяем правило авторизации для запросов
//                    // к URL, которые начинаются с "/admin/",
//                    // позволяя доступ только пользователям с ролью "ADMIN".
//
//                    .requestMatchers(new AntPathRequestMatcher("/**"))
//                    .hasAnyRole("USER")
//                    // Определяем правило авторизации для остальных запросов,
//                    // позволяя доступ только пользователям с ролью "USER".
//
//                    .and()
//                    .formLogin().permitAll()
//                    // Позволяем всем пользователям доступ к форме входа.
//
//                    .and()
//                    .logout().logoutUrl("/logout");
//            // Настраиваем механизм выхода из системы
//            // с заданием URL "/logout".
//
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    // Создаем бин userDetailsManager.
//    // Он использует JdbcUserDetailsManager для работы с базой данных.
//    @Bean
//    @Primary
//    public UserDetailsManager userDetailsManager(DataSource dataSource,
//                                                 AuthenticationManager authenticationManager) {
//
//        // Инициализируем JdbcUserDetailsManager с dataSource
//        // и authenticationManager для работы с базой данных
//        JdbcUserDetailsManager jdbcUserDetailsManager =
//                new JdbcUserDetailsManager(dataSource);
//
//        jdbcUserDetailsManager.setAuthenticationManager(authenticationManager);
//        return jdbcUserDetailsManager;
//    }
//
//    // Создаем бин authenticationManager и получаем его
//    // из AuthenticationConfiguration.
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
//            throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }
}
