package org.springframework.safe.utils.wrapper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.common.properties.GzrobotProperties;
import org.springframework.common.util.fastjson.JsonUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.safe.utils.HttpUtil;
import org.springframework.stereotype.Component;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * @author Sir.D
 */
@Slf4j
public class Sm2RequestWrapper extends HttpServletRequestWrapper {

    private byte[] body = new byte[0];
    private Map<String, String[]> parameterMap;

    public Sm2RequestWrapper(HttpServletRequest request, boolean flag) throws Exception {
        super(request);
        String contentType = request.getContentType();
        if (null != contentType && contentType.toUpperCase().contains(HttpUtil.MULTIPART_FORM_DATA)) {
            return;
        }

        String var0;
        if (flag) {
            var0 = HttpUtil.decrypt(request);
        } else {
            var0 = HttpUtil.json(request);
        }
        if (!StringUtils.isBlank(var0)) {
            System.out.println("解密后内容为==================>" + var0);
            try {
                var0 = URLDecoder.decode(var0, "UTF-8");
            } catch (Exception e) {

            }
            this.body = var0.getBytes();

            if (null == contentType) {
                contentType = HttpUtil.XXX_FORM_RULENCODED;
            }

            if (contentType.toUpperCase().contains(HttpUtil.APPLICATION_JSON)) {
                this.parameterMap = new HashMap();

                try {
                    JSONObject object = JSON.parseObject(var0);
                    object.forEach((k, v) -> {
                        this.parameterMap.put(k, new String[]{String.valueOf(v)});
                    });
                } catch (Exception var10) {
                }
            } else if (contentType.toUpperCase().contains(HttpUtil.XXX_FORM_RULENCODED)) {
                String[] params = var0.split("&");
                this.parameterMap = new HashMap();
                String[] var5 = params;
                int var6 = params.length;

                for(int var7 = 0; var7 < var6; ++var7) {
                    String param = var5[var7];
                    String[] value = param.split("=");
                    if (value.length == 2) {
                        this.parameterMap.put(value[0], new String[]{value[1]});
                    } else {
                        this.parameterMap.put(value[0], new String[]{""});
                    }
                }
            }
        }
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream stream = new ByteArrayInputStream(body);

        return new ServletInputStream() {
            @Override
            public int read() throws IOException {
                return stream.read();
            }

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {
            }
        };
    }

    /**
     * 获取所有参数名
     * @return 返回所有参数名
     */
    @Override
    public Enumeration<String> getParameterNames() {
        if (null == parameterMap) {
           return super.getParameterNames();
        }

        Vector<String> vector = new Vector<String>(parameterMap.keySet());
        return vector.elements();
    }

    /**
     * 获取指定参数名的值，如果有重复的参数名，则返回第一个的值 接收一般变量 ，如text类型
     * @param name
     *            指定参数名
     * @return 指定参数名的值
     */
    @Override
    public String getParameter(String name) {
        if (null == parameterMap) {
            return super.getParameter(name);
        }

        String[] results = parameterMap.get(name);
        if (results == null || results.length <= 0) {
            return null;

        } else {
            return results[0];
        }
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        if (null == parameterMap) {
            return super.getParameterMap();
        }
        return parameterMap;
    }

    @Override
    public String[] getParameterValues(String name) {
        if (null == parameterMap) {
            return super.getParameterValues(name);
        }
        return null == parameterMap.get(name) ? new String[]{""} : parameterMap.get(name);
    }
}
