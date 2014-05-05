/*
 * 创建日期 2013-10-22
 */
package com.cxf.myutils.gzip;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author chenxf
 */
public class GZipFilter implements Filter {

    /* （非 Javadoc）
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    public void init(FilterConfig arg0) throws ServletException {

    }

    /* （非 Javadoc）
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
     */
    public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain chain) throws IOException,
            ServletException {
        HttpServletRequest req = (HttpServletRequest)arg0;
        HttpServletResponse res = (HttpServletResponse)arg1;
        
        if (isGZipEncoding(req)) {
            GZipResponse zipResponse = new GZipResponse(res);
            res.setHeader("Content-Encoding", "gzip");
            chain.doFilter(arg0, zipResponse);
            zipResponse.flush();
        } else {
            chain.doFilter(arg0, arg1);
        }
    }

    /**
     * 功能：判断浏览器是否支持GZIP
     * @param req
     * @return
     */
    private static boolean isGZipEncoding(HttpServletRequest req) {
        boolean flag = false;
        String encoding = req.getHeader("Accept-Encoding");
        if (encoding.indexOf("gzip") != -1) {
            flag = true;
        }
        
        return flag;
    }

    /* （非 Javadoc）
     * @see javax.servlet.Filter#destroy()
     */
    public void destroy() {

    }

}
