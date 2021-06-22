package org.springframework.safe.utils;

import java.util.*;

/**
 * 配置文件
 * @author Sir.D
 */
public class PropertiesFileUtil {
    private static Map<String, PropertiesFileUtil> configMap = new HashMap<String, PropertiesFileUtil>();
    private Date loadTime = null;
    private ResourceBundle resourceBundle = null;

    private static final String NAME = "config";
    private static final Integer TIME_OUT = 60000;

    private PropertiesFileUtil(String var1) {
        this.loadTime = new Date();
        this.resourceBundle = ResourceBundle.getBundle(var1);
    }

    public static synchronized PropertiesFileUtil getInstance() {
        return getInstance(NAME);
    }

    public static synchronized PropertiesFileUtil getInstance(String var0) {
        PropertiesFileUtil var1 = (PropertiesFileUtil)configMap.get(var0);
        if (var1 == null) {
            var1 = new PropertiesFileUtil(var0);
            configMap.put(var0, var1);
        }

        if ((new Date()).getTime() - var1.getLoadTime().getTime() > (long)TIME_OUT) {
            var1 = new PropertiesFileUtil(var0);
            configMap.put(var0, var1);
        }

        return var1;
    }

    public String get(String var1) {
        try {
            String var2 = this.resourceBundle.getString(var1);
            return var2;
        } catch (MissingResourceException var3) {
            return "";
        }
    }

    public Integer getInt(String var1) {
        try {
            String var2 = this.resourceBundle.getString(var1);
            return Integer.parseInt(var2);
        } catch (MissingResourceException var3) {
            return null;
        }
    }

    public boolean getBool(String var1) {
        try {
            String var2 = this.resourceBundle.getString(var1);
            return "true".equals(var2);
        } catch (MissingResourceException var3) {
            return false;
        }
    }

    public Date getLoadTime() {
        return this.loadTime;
    }

    public static void main(String[] args) {
        PropertiesFileUtil.getInstance();
    }

}
