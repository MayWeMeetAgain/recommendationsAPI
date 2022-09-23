package com.annieryannel.recommendationsapp.config;

import com.annieryannel.recommendationsapp.filters.CORSFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private CORSFilter corsFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(corsFilter, ChannelProcessingFilter.class);
        http
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/reviews/**").permitAll()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .antMatchers("/search").permitAll()
                .antMatchers(HttpMethod.GET, "/users/{username}").permitAll()
                .antMatchers(HttpMethod.POST, "/users").permitAll()
                .antMatchers(HttpMethod.PUT, "/reviews/{reviewId}").access("hasRole('ADMIN') or @reviewSecurity.isAuthor(authentication, #reviewId)")
                .antMatchers(HttpMethod.DELETE, "/reviews/{reviewId}").access("hasRole('ADMIN') or @reviewSecurity.isAuthor(authentication, #reviewId)")
                .anyRequest().authenticated()
                .and().httpBasic().and().cors()
                .and().csrf().disable();
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/css/**","/js/**","/fonts/**","/images/**", "*.bundle.*");
    }

}