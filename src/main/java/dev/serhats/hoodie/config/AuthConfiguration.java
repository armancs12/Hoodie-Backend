package dev.serhats.hoodie.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AuthConfiguration {
    @Configuration
    @RequiredArgsConstructor
    public static class UserServiceConfiguration {
        private final UserDetailsService userDetailsService;

        public UserDetailsService getUserDetailsService() {
            return userDetailsService;
        }
    }

    @Configuration
    public static class PasswordEncoderConfiguration {
        @Bean
        public PasswordEncoder getPasswordEncoder() {
            return new BCryptPasswordEncoder();
        }
    }

    @Configuration
    @Order(999998)
    @RequiredArgsConstructor
    public static class AuthenticationManagerConfiguration extends WebSecurityConfigurerAdapter {
        private final UserServiceConfiguration userServiceConfiguration;
        private final PasswordEncoderConfiguration passwordEncoderConfiguration;

        @Autowired
        void configureAuthenticationManager(AuthenticationManagerBuilder builder) throws Exception {
            builder.userDetailsService(userServiceConfiguration.getUserDetailsService())
                    .passwordEncoder(passwordEncoderConfiguration.getPasswordEncoder());
        }
    }

    @Configuration
    @Order(999999)
    public static class AuthenticationManagerBeanConfiguration extends WebSecurityConfigurerAdapter {
        @Bean(BeanIds.AUTHENTICATION_MANAGER)
        @Override
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }
    }
}
