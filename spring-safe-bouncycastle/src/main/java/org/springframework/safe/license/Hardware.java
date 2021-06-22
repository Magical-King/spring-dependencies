package org.springframework.safe.license;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.safe.utils.HexUtil;
import org.springframework.safe.utils.PropertiesFileUtil;
import org.springframework.safe.utils.enums.SystemOS;

import java.io.*;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 硬件基本信息
 * CPU_ID cpu编码
 * MAIN_BOARD_NUMBER 主板编码
 * DISK_NUMBER 磁盘编码
 * MAC_ID 物理地址
 * @author Sir.D
 */
@Slf4j
@NoArgsConstructor
public class Hardware {
    private static final String UUID = "UUID";
    private static final String CPU_ID = "CPU_ID";
    private static final String MAIN_BOARD_ID = "MAIN_BOARD_ID";
    private static final String DISK_ID = "DISK_ID";
    private static final String MAC_ID = "MAC_ID";
    private static LinkedHashMap var = null;


    /**
     * 命令执行
     * @param var10 需执行的命令
     * @param var11 未获取结果时提醒的语句
     * @return
     */
    public static String exec(String var10, String var11){
        String var0 = "";

        try {
            File var1 = File.createTempFile("realhowto", ".vbs");
            var1.deleteOnExit();

            FileWriter var2 = new FileWriter(var1);
            var2.write(var10);
            var2.close();

            String var4 = var1.getPath().replace("%20", " ");
            Process var5 = Runtime.getRuntime().exec("cscript //NoLogo " + var4);

            BufferedReader var6;
            String var7;
            for(var6 = new BufferedReader(new InputStreamReader(var5.getInputStream())); (var7 = var6.readLine()) != null; var0 = var0 + var7) {}
            var6.close();
            var1.delete();
        } catch (Exception var8) {
            var8.fillInStackTrace();
        }

        if (var0.trim().length() < 1 || var0 == null) {
            var0 = var11;
        }

        return var0.trim();
    }

    public static String cpu() {
        String var0 = "Set objWMIService = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")\nSet colItems = objWMIService.ExecQuery _ \n   (\"Select * from Win32_Processor\") \nFor Each objItem in colItems \n    Wscript.Echo objItem.ProcessorId \n    exit for  ' do the first cpu only! \nNext \n";
        String var1 = "无CPU_ID被读取";

        return exec(var0, var1);
    }

    public static String mb() {
        String var0 = "Set objWMIService = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")\nSet colItems = objWMIService.ExecQuery _ \n   (\"Select * from Win32_BaseBoard\") \nFor Each objItem in colItems \n    Wscript.Echo objItem.SerialNumber \n    exit for  ' do the first cpu only! \nNext \n";
        String var1 = "无MAIN_BOARD_ID被读取";

        return exec(var0, var1);
    }

    public static String disk() {
        String var0 = "Set objFSO = CreateObject(\"Scripting.FileSystemObject\")\nSet colDrives = objFSO.Drives\nSet objDrive = colDrives.item(\"c\")\n" + "Wscript.Echo objDrive.SerialNumber";
        String var1 = "无DISK_ID被读取";

        return exec(var0, var1);
    }

    public static String run(String var0) {
        try {
            log.info("===> got cmd job : " + var0);
            Runtime var1 = Runtime.getRuntime();
            Process var2 = var1.exec(var0);
            InputStream var3 = var2.getInputStream();
            new BufferedReader(new InputStreamReader(var3));
            StringBuffer var5 = new StringBuffer();
            byte[] var6 = new byte[8192];

            int var7;
            while((var7 = var3.read(var6)) != -1) {
                var5.append(new String(var6, 0, var7));
            }

            var3.close();
            var2.destroy();
            return var5.toString();
        } catch (Exception var8) {
            var8.printStackTrace();
            return null;
        }
    }

    public static String job(String var0, String var1, String var2) {
        String var3 = run(var0);
        String[] var4 = var3.split("\n");
        String[] var8 = var4;
        int var7 = var4.length;

        for(int var6 = 0; var6 < var7; ++var6) {
            String var5 = var8[var6];
            var5 = var5.trim();
            if (var5.indexOf(var1) != -1) {
                var5.replace(" ", "");
                String[] var9 = var5.split(var2);
                return var9[1];
            }
        }

        return null;
    }

    public static String mac() {
        try {
            String var0 = PropertiesFileUtil.getInstance().get("MAIN_IP");
            NetworkInterface var1 = NetworkInterface.getByInetAddress(InetAddress.getByName(var0));
            if (var1 != null) {
                return HexUtil.byte2HexString(var1.getHardwareAddress());
            } else {
                log.error("==========> 未获取到mac地址!");
            }
        } catch (Exception var) {
            var.printStackTrace();
        }

        return null;
    }

    public static String os(){
        return System.getProperty("os.name").toUpperCase();
    }

    public static Map<String, String> info() {
        return info(false);
    }


    public static Map<String, String> info(boolean isDocker) {
        if (var != null && var.size() > 0) {
            return var;
        }

        try {
            String var0 = os();
            var = new LinkedHashMap();
            var.put("OS.NAME", var0.toUpperCase().replace(" ", "").replace("-",""));

            if (isDocker) {
                log.info("==========> for docker");
                String var6 = run("cat /sys/class/dmi/id/product_uuid");
                var.put(UUID, var6.toUpperCase().replace(" ", "").replace("-","").replace("\n",""));
            } else {
                String var2;
                String var3;
                String var4;
                String var5;
                if (SystemOS.LINUX.code().equals(var0)) {
                    log.info("==========> for linux");

                    var2 = job("sudo /usr/sbin/dmidecode -t processor | grep 'ID'", "ID", ":");
                    var3 = job("sudo -S /usr/sbin/dmidecode |grep 'Serial Number'", "Serial Number", ":");
                    var4 = job("sudo  -S /sbin/fdisk -l", "Disk identifier", ":");
                    if (var4 == null) {
                        var4 = job("sudo  -S /sbin/fdisk -l", "磁盘标识符", "：");
                    }
                } else if (SystemOS.MACOS.code().equals(var0)) {
                    var2 = job("system_profiler SPHardwareDataType | grep ID", "ID", ":");
                    var3 = job("system_profiler SPHardwareDataType | grep Serial", "Serial Number", ":");
                    var4 = job("system_profiler SPStorageDataType | grep UUID", "UUID", ":");
                } else {
                    log.info("==========> for windows");
                    var2 = cpu();
                    var3 = mb();
                    var4 = disk();
                }

                var5 = mac();
                if (var5 == null || var2 == null || var3 == null || var4 == null) {
                    log.error("==========> 未获取到服务器信息！");
                    var.clear();
                    return null;
                }
                var.put(MAC_ID, var5.toUpperCase().replace(" ", "").replace("-",""));
                var.put(CPU_ID, var2.toUpperCase().replace(" ", "").replace("-",""));
                var.put(MAIN_BOARD_ID, var3.toUpperCase().replace(" ", "").replace("-",""));
                var.put(DISK_ID, var4.toUpperCase().replace(" ", "").replace("-",""));
            }

            log.info("==========> 服务器信息：{}", var);
        } catch (Exception var6) {
            var.clear();
            var6.printStackTrace();
        }

        return var;
    }

    public static void main(String[] args) {
        Hardware.info(true);
    }
}
