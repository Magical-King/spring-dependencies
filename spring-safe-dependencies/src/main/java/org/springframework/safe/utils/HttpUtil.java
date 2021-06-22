package org.springframework.safe.utils;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.springframework.safe.utils.Sm2Util;
import org.springframework.safe.utils.message.Message;
import org.springframework.safe.utils.message.Status;
import org.springframework.util.StreamUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author Sir.D
 */
public class HttpUtil {
    public static final String APPLICATION_JSON = "APPLICATION/JSON";
    public static final String XXX_FORM_RULENCODED = "APPLICATION/X-WWW-FORM-URLENCODED";
    public static final String MULTIPART_FORM_DATA = "MULTIPART/FORM-DATA";

    /**
     * 返回json
     * @param response
     * @param status
     * @param message
     * @throws IOException
     */
    public static void response(HttpServletResponse response, Status status, String message) throws IOException {
        response.setCharacterEncoding("UTF-8");
//        response.setHeader("Content-type", "text/html; charset=utf-8");
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(JSON.toJSONString(new Message(status, message)));
    }

    public static String decrypt(HttpServletRequest var0) throws IOException, InvalidCipherTextException {
        String var2 = StreamUtils.copyToString(var0.getInputStream(), StandardCharsets.UTF_8);
        if (null == var2) {
            var2 = get(var0);
        }
        return StringUtils.isBlank(var2) ? "" : Sm2Util.decrypt(var2, "6F86C601E532D54FB49BB116FE3FC9931CAE04E0BE555D3C7EDDAD9F1CEFAA3F");
    }

    public static String json(HttpServletRequest var0) throws IOException {
        String var2 = StreamUtils.copyToString(var0.getInputStream(), StandardCharsets.UTF_8);
        if (null == var2) {
            var2 = get(var0);
        }

        return StringUtils.isBlank(var2) ? "" : var2;
    }

    public static String get(HttpServletRequest var0) {
        String var1 = var0.getQueryString();
        if (StringUtils.isNotBlank(var1)) {
            return var0.getQueryString();
        }

        return null;
    }

}
