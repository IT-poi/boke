package com.cuit.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class GwCorsFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    // nothing
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader("Allow","GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS, PATCH");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", req.getHeader("Access-Control-Request-Headers"));
        response.setHeader("Access-Control-Allow-Credentials","true");
        response.setHeader("Vary","Origin");
       if(req.getMethod().equals("OPTIONS")){
           response.setStatus(200);
       } else {
           chain.doFilter(request, response);
       }
    }

    @Override
    public void destroy() {
        // nothing
    }


    public static void main(String[] args){
        String s = "asfaslfhaslfasljvluf;adasdasfqwqcgqeqwertyuop[fdshl;sjfwqru;cjasfouaioflasjfqoifha";
        char[] chars = s.toCharArray();
        String maxString = "";
        for (int i = 0; i < chars.length; i++) {
            char a = chars[i];
            StringBuilder sb = new StringBuilder("");
            sb.append(a);
            for (int j = i + 1; j < chars.length; j++) {
                char b = chars[j];
                if (a == b) {
                    sb.append(b);
                    maxString = maxString.length() < sb.length() ? sb.toString() : maxString;
                    break;
                } else if (sb.toString().contains(String.valueOf(b))) {
                    break;
                }
                sb.append(b);
            }
            if (maxString.length() > chars.length - i) {
                break;
            }
        }
        System.out.println(maxString);
    }
}