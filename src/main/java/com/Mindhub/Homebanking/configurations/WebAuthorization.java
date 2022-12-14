package com.Mindhub.Homebanking.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@EnableWebSecurity

@Configuration

public class WebAuthorization extends WebSecurityConfigurerAdapter {

    @Override

    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers(HttpMethod.POST,"/api/login" , "/api/clients", "/api/transactions", "/api/clients/current/cards", "/api/clients/current/accounts", " /api/loans", "/api/loans/admin" ).permitAll()
                .antMatchers(HttpMethod.POST,"/api/logout").permitAll()
                .antMatchers(HttpMethod.PATCH, "/api/clients/current/cards/delete/{id}", "/api/clients/current/accounts/delete/{id}").permitAll()
                .antMatchers("/web/index.html", "/web/register.html","/style/style.css", "/script/login.js", "/script/register.js", "/assets/**").permitAll()
                .antMatchers("/api/clients/current", "/api/clients/current/accounts").hasAuthority("CLIENT")
                .antMatchers("/web/**", "/script/**", "/style/**", "/api/loans", "/api/accounts/{id}", "/api/accounts").hasAnyAuthority("CLIENT", "ADMIN")
                .antMatchers("/api/**", "/rest/**", "/h2-console" , "/admin/**").hasAuthority("ADMIN")
                .antMatchers("/**").permitAll();

//                .antMatchers("/api/clients", "/api/loans").permitAll()
//                .antMatchers("web/**", "js/**", "styles/**", "assets/**", "html/login.html", "html/register.html").permitAll()
//                .antMatchers("web/accounts.html","web/account.html","web/cards.html", "web/create-card.html", "web/transfers.html" ).permitAll()
//                .antMatchers("/rest/**", "/admin/**").hasAuthority("ADMIN");

        http.formLogin()

                .usernameParameter("email")

                .passwordParameter("password")

                .loginPage("/api/login");



        http.logout().logoutUrl("/api/logout");



        // turn off checking for CSRF tokens

        http.csrf().disable();



        //disabling frameOptions so h2-console can be accessed

        http.headers().frameOptions().disable();

        // if user is not authenticated, just send an authentication failure response

        http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        // if login is successful, just clear the flags asking for authentication

        http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));

        // if login fails, just send an authentication failure response

        http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        // if logout is successful, just send a success response

        http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());

    }

    private void clearAuthenticationAttributes(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        if (session != null) {

            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);

        }

    }

}



