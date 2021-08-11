package org.budget.budgetserver.jwt

import org.budget.budgetserver.service.token.JwtTokenService
import io.jsonwebtoken.lang.Strings.hasText
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.GenericFilterBean
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest


@Component
class JwtFilter : GenericFilterBean() {
    @Autowired
    private lateinit var jwtTokenService: JwtTokenService

    @Autowired
    private lateinit var userDetailsService: CustomUserDetailsService

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(servletRequest: ServletRequest?, servletResponse: ServletResponse?, filterChain: FilterChain) {

        val token = getTokenFromRequest(servletRequest as HttpServletRequest)
        if (token != null && jwtTokenService.validateToken(token)) {
            val userLogin = jwtTokenService.getLoginFromToken(token)
            val userDetails = userDetailsService.loadUserByUsername(userLogin)
            val auth = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
            SecurityContextHolder.getContext().authentication = auth
        }
        filterChain.doFilter(servletRequest, servletResponse)
    }

    private fun getTokenFromRequest(request: HttpServletRequest): String? {
        val bearer = request.getHeader(AUTHORIZATION)
        return if (hasText(bearer) && bearer.startsWith("Bearer "))
            bearer.substring(7)
        else null
    }

    companion object {
        const val AUTHORIZATION = "Authorization"
    }
}