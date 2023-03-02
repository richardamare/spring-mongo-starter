package com.amarerichard.springmongostarter.config

import com.amarerichard.springmongostarter.auth.CustomAccessDeniedHandler
import com.amarerichard.springmongostarter.auth.JwtAuthFilter
import com.amarerichard.springmongostarter.model.response.ErrorResponse
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig constructor(
    private val jwtAuthFilter: JwtAuthFilter,
    private val authenticationProvider: AuthenticationProvider,
    private val customAccessDeniedHandler: CustomAccessDeniedHandler,
) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf()
            .disable()
            .authorizeHttpRequests()
            .requestMatchers("/api/v1/auth/**")
            .permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .exceptionHandling()
            .authenticationEntryPoint { _, response, _ ->
                val data = ErrorResponse(401, "Unauthorized")
                response.status = 401
                response.contentType = "application/json"
                response.writer.println(data.toJson())
            }
            .accessDeniedHandler(customAccessDeniedHandler)
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter::class.java)
        return http.build()
    }
}