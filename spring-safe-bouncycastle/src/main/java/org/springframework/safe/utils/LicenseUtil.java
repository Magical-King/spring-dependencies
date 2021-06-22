package org.springframework.safe.utils;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.safe.license.Hardware;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 许可证工具类
 * ENCODEDLICENSE 编码许可
 * MACHINECODE_KEY MAC代码
 * ISNOTIMELIMIT_KEY 许可证限制是否不限制
 * LICENSELIMIT 许可证限制
 * @author Sir.D
 */
@Slf4j
@NoArgsConstructor
public class LicenseUtil {
    public static String LICENSEID_KEY = "LICENSEID";
    public static String LICENSENAME_KEY = "LICENSENAME";
    public static String ISNOTIMELIMIT_KEY = "ISNOTIMELIMIT";
    public static String LICENSELIMIT_KEY = "LICENSELIMIT";
    public static String MACHINECODE_KEY = "MACHINECODE";
    public static String ENCODEDLICENSE_KEY = "ENCODEDLICENSE";

    public static String SEMICOLON = ";";
    public static String CLASSES = "classes/";
    public static String PRIVATE_KEY = "00DA940504B229E52EF260552330148A418643A536F83D1AD6971F428952C769AC";

    public static String machineCode(boolean isDocker) throws Exception {
        Map var0 = Hardware.info(isDocker);
        if (var0 == null) {
            log.info("===> 未获取到机器码(MachineCode)!");
            return null;
        } else {
            String var1 = Md5Util.md5Encode(var0.toString().replace(" ", "")).toUpperCase();
            log.info("===> 当前服务器的机器码(MachineCode)为：{}", var1);
            return var1;
        }
    }

    public static boolean authLicense(boolean isDocker) {
        return lic("lic", isDocker);
    }

    public static boolean authLicense() {
        return lic("lic", false);
    }

    private static boolean lic(String var0, boolean isDocker) {
        try {
            InputStream var1 = LicenseUtil.class.getClassLoader().getResourceAsStream("lic/license.lic");
            if (null == var1) {
                log.error("==========> 证书不存在，请联系系统管理员! <==========");
            } else {
                if (lic(var1, isDocker)) {
                    return true;
                }
            }
        } catch (Exception var) {
            var.printStackTrace();
            return false;
        }

        return false;
    }

    private static boolean lic(InputStream var3, boolean isDocker) {
        boolean var1 = false;
        try {
            Properties var2 = new Properties();
//            InputStream var3 = new FileInputStream(var0);
            var2.load(new InputStreamReader(var3));
            String var4 = var2.getProperty(LICENSEID_KEY);
            String var5 = var2.getProperty(LICENSENAME_KEY);
            String var6 = var2.getProperty(ISNOTIMELIMIT_KEY);
            String var7 = var2.getProperty(LICENSELIMIT_KEY);
            String var8 = var2.getProperty(MACHINECODE_KEY);
            String var9 = var2.getProperty(ENCODEDLICENSE_KEY);


            String var10 = Sm2Util.decrypt(var9, PRIVATE_KEY);
            String[] var11 = var10.split("\n");
            Map<String,String> var12 = new HashMap(16);
            for (String var13 : var11) {
                String[] var14 = var13.split("=");
                if (var14.length == 2) {
                    var12.put(var14[0], var14[1]);
                }
            }

            String var15 = machineCode(isDocker);
            if (null == var15) {
                return false;
            } else if (!var4.equalsIgnoreCase(var12.get(LICENSEID_KEY))) {
                log.error("=================> 证书无效,请联系系统管理员申请证书(A)！<=================");
                return false;
            } else if (!var5.equalsIgnoreCase(var12.get(LICENSENAME_KEY))) {
                log.error("=================> 证书无效,请联系系统管理员申请证书(B)！<=================");
                return false;
            } else if (!var6.equalsIgnoreCase(var12.get(ISNOTIMELIMIT_KEY))) {
                log.error("=================> 证书无效,请联系系统管理员申请证书(C)！<=================");
                return false;
            } else if (!var7.equalsIgnoreCase(var12.get(LICENSELIMIT_KEY))) {
                log.error("=================> 证书无效,请联系系统管理员申请证书(D)！<=================");
                return false;
            } else if (!var8.equalsIgnoreCase(var12.get(MACHINECODE_KEY))) {
                log.error("=================> 证书无效,请联系系统管理员申请证书(E)！<=================");
                return false;
            } else if (!var8.equalsIgnoreCase(var15 + SEMICOLON)) {
                log.error("=================> 证书无效,请联系系统管理员申请证书(F)！<=================");
                return false;
            } else {
                if (!(Boolean.TRUE.toString() + SEMICOLON).equalsIgnoreCase(var6) && DateUtil.str2Date(var7.substring(0, var7.length() - 1)).after(new Date())) {
                    var1 = true;
                }

                if (var1) {
                    log.info("=================> 证书有效！<=================");
                } else {
                    log.error("=================> 证书无效,请联系系统管理员申请证书！<=================");
                }

                return var1;
            }

        } catch (Exception var) {
            var.printStackTrace();
            return false;
        }
    }

}
