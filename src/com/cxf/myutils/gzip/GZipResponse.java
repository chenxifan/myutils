/*
 * 创建日期 2013-10-22
 */
package com.cxf.myutils.gzip;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * @author chenxf
 */
public class GZipResponse extends HttpServletResponseWrapper {
    private GZipStream stream;

    private PrintWriter writer;

    /**
     * @param arg0
     */
    public GZipResponse(HttpServletResponse response) throws IOException {
        super(response);
        stream = new GZipStream(response.getOutputStream());
    }

    /*
     * （非 Javadoc）
     * 
     * @see javax.servlet.ServletResponse#getOutputStream()
     */
    public ServletOutputStream getOutputStream() throws IOException {
        return stream;
    }

    /*
     * （非 Javadoc）
     * 
     * @see javax.servlet.ServletResponse#getWriter()
     */
    public PrintWriter getWriter() throws IOException {
        if (writer == null) {
            writer = new PrintWriter(new OutputStreamWriter(getOutputStream(), getCharacterEncoding()));
        }
        
        return writer;
    }
    
    public void flush() throws IOException {
        if (writer != null) {
            writer.flush();
        }
        stream.finish();
    }
}
