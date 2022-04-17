package com.daniel.cart.filter;

import com.alibaba.fastjson.JSONObject;
import com.daniel.cart.domain.result.Result;
import com.daniel.cart.domain.result.ResultCodeEnum;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 重写FormAuthenticationFilter，阻止Shiro重定向到默认的index.jsp，而是按照规定的格式返回json
 */
public class MyFormAuthenticationFilter extends FormAuthenticationFilter {
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletResponse resp = (HttpServletResponse) response;
        resp.setStatus(200);
        resp.setContentType("application/json; charset=utf-8");
        PrintWriter out = resp.getWriter();
        JSONObject jsonObject = new JSONObject();
        Result error = Result.error(ResultCodeEnum.LOGIN_ACL);
        jsonObject.put("success", error.getSuccess());
        jsonObject.put("code", error.getCode());
        jsonObject.put("message", error.getMessage());
        try {
            flushMsgStrToClient(response, jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        out.flush();
        out.close();
        return false;
    }

    public static void flushMsgStrToClient(ServletResponse response, Object object) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(JSONObject.toJSONString(object));
        response.getWriter().flush();
    }
}
