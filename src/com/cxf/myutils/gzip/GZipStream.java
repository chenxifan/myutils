/*
 * 创建日期 2013-10-22
 */
package com.cxf.myutils.gzip;

import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

import javax.servlet.ServletOutputStream;

/**
 * @author chenxf
 */
public class GZipStream extends ServletOutputStream {

    private GZIPOutputStream zipStream;

    /**
     *  
     */
    public GZipStream(OutputStream out) throws IOException {
        zipStream = new GZIPOutputStream(out);
    }

    /*
     * （非 Javadoc）
     * 
     * @see java.io.OutputStream#close()
     */
    public void close() throws IOException {
        zipStream.close();
    }

    public void finish() throws IOException {
        zipStream.finish();
    }
    
    /*
     * （非 Javadoc）
     * 
     * @see java.io.OutputStream#flush()
     */
    public void flush() throws IOException {
        zipStream.flush();
    }

    /*
     * （非 Javadoc）
     * 
     * @see java.io.OutputStream#write(byte[], int, int)
     */
    public void write(byte[] b, int off, int len) throws IOException {
        zipStream.write(b, off, len);
    }

    /*
     * （非 Javadoc）
     * 
     * @see java.io.OutputStream#write(byte[])
     */
    public void write(byte[] b) throws IOException {
        zipStream.write(b);
    }

    /*
     * （非 Javadoc）
     * 
     * @see java.io.OutputStream#write(int)
     */
    public void write(int b) throws IOException {
        zipStream.write(b);
    }

}
