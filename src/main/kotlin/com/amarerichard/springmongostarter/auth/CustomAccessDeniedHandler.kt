package com.amarerichard.springmongostarter.auth

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class CustomAccessDeniedHandler : AccessDeniedHandler {

    override fun handle(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        accessDeniedException: org.springframework.security.access.AccessDeniedException?,
    ) {
        val data = hashMapOf<String, Any>()
        data["timestamp"] = LocalDateTime.now().toString()
        data["status"] = 403
        data["message"] = "Access Denied"

        response?.status = 403
        response?.contentType = "application/json"
        response?.writer?.println(data)
    }
}