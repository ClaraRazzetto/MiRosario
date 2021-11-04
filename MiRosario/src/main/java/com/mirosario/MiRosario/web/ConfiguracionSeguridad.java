package com.mirosario.MiRosario.web;

import com.mirosario.MiRosario.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ConfiguracionSeguridad extends WebSecurityConfigurerAdapter {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(usuarioServicio)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.headers().frameOptions().sameOrigin().and()
                .authorizeRequests()
                .antMatchers("/css/ /img/")
                .permitAll()
                .and().formLogin()
                    .loginPage("/login")
                    .loginProcessingUrl("/logincheck")
                    .usernameParameter("nombreUsuario")
                    .passwordParameter("password")
                    //configurar la vista de usuario loggeado con exito dependiendo del rol.
                    .defaultSuccessUrl("/inicio")
                    .failureUrl("/login?error=error")
                    .permitAll()
                .and().logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/inicio")
                    .permitAll()
                    .and().csrf().disable();

    }
}

