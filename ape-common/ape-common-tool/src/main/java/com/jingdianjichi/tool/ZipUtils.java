package com.jingdianjichi.tool;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

@Slf4j
public class ZipUtils {

    private static final String DEFAULT_ENCODING = "UTF-8";

    /**
     * 使用gzip进行压缩
     */
    public static String gzip(String primStr) {
        if (primStr == null || primStr.length() == 0) {
            return primStr;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        String result = null;
        GZIPOutputStream gzip = null;
        try {
            gzip = new GZIPOutputStream(out);
            gzip.write(primStr.getBytes(DEFAULT_ENCODING));
            gzip.finish();
            result = new String(new Base64().encode(out.toByteArray()), DEFAULT_ENCODING);
        } catch (IOException e) {
            log.error("ZipUtils.gzip.error", e);
            throw new RuntimeException(e);
        } finally {
            if (gzip != null) {
                try {
                    gzip.close();
                } catch (IOException e) {
                    log.error("ZipUtils.gzip.close.error", e);
                }
            }
        }
        return result;

    }

    /**
     * 使用gzip进行解压缩
     */
    public static String unGzip(String compressedStr) {
        if (compressedStr == null) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = null;
        GZIPInputStream gzipIn = null;
        byte[] compressed;
        String decompressed = null;
        try {
            compressed = new Base64().decode(compressedStr.getBytes(DEFAULT_ENCODING));
            in = new ByteArrayInputStream(compressed);
            gzipIn = new GZIPInputStream(in);
            byte[] buffer = new byte[1024];
            int offset;
            while ((offset = gzipIn.read(buffer)) != -1) {
                out.write(buffer, 0, offset);
            }
            decompressed = out.toString(DEFAULT_ENCODING);
        } catch (IOException e) {
            log.error("ZipUtils.unGzip.error", e);
        } finally {
            if (gzipIn != null) {
                try {
                    gzipIn.close();
                } catch (IOException e) {
                    log.error("ZipUtils.unGzip.close.error", e);
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    log.error("ZipUtils.unGzip.in.close.error", e);
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    log.error("ZipUtils.unGzip.out.close.error", e);
                }
            }
        }
        return decompressed;
    }

}
