package com.aisisea.oa.login.web.interceptor;

import com.aisisea.oa.commons.domain.ReturnObject;
import com.aisisea.oa.commons.utils.DefaultReturnObject;
import com.aisisea.oa.commons.utils.TokenUtilsFactory;
import com.aisisea.oa.commons.utils.token.TokenUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        if (token == null || token.equals("") || token.split("\\.").length != 3) {
            returnTokenError(response);
            return false;
        }

        TokenUtils tokenUtils = TokenUtilsFactory.getTokenUtils();
        try {
            boolean expire = tokenUtils.isExpireFromToken(token);
            if (expire)
                returnTokenError(response);
            return !expire;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void returnTokenError(HttpServletResponse response) throws Exception {
        ReturnObject tokenError = DefaultReturnObject.getTokenErrorReturnObject(null);
        String returnJSON = new ObjectMapper().writeValueAsString(tokenError);
        response.getWriter().write(returnJSON);
    }
}
