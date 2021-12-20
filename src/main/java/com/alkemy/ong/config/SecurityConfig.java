package com.alkemy.ong.config;

import com.alkemy.ong.services.UserDetailsServices;
import com.alkemy.ong.util.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsServices userDetailsService;

    @Autowired
    private JwtFilter jwtFilter;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider());
                //.passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers("/auth/**",
                        "/register",
                        "/login",
                        "/v3/api-docs/swagger-config",
                        "/api/swagger-ui/**",
                        "/v3/api-docs",
                        "/api/docs",
                        "/api/docs/**",
                        "/contacts")
               .permitAll()
                .antMatchers(HttpMethod.GET,
                        "/categories/{id}",
                        "/categories",
                        "/s3/images",
                        "/organization/public",
                        "/organization/public/{id}",
                        "/contacts",
                        "/users").hasAnyAuthority("ADMIN","USER")
                .antMatchers(HttpMethod.GET,
                        "/members",
                        "/comments").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.POST,
                        "/testimonials",
                        "/members",
                        "/categories",
                        "/s3/images",
                        "/organization/public",
                        "/contacts").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.PUT,
                        "/testimonials",
                        "/members/{id}",
                        "/categories/{id}").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.PUT,
                        "comments/{id}").hasAnyAuthority("ADMIN","USER")
                .antMatchers(HttpMethod.PATCH,
                        "/organization/public/{id}",
                        "/users/{id}").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE,
                        "/testimonials/{id}",
                        "/members/{id}",
                        "/categories/{id}",
                        "/users/{id}").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE,
                        "/comments/{id}").hasAnyAuthority("ADMIN","USER")
                .anyRequest().authenticated().and().sessionManagement();

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

}
