package org.springframework.safe.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author Sir.D
 */
public class FileUtil {

    public static void writeStringToFile(String var0, String var1, String var2) throws IOException {
        File var3 = new File(var1);
        if (!var3.exists()) {
            var3.mkdirs();
        }

        File var4 = new File(var1 + var2);
        var4.delete();
        var4.createNewFile();

        FileWriter var5 = new FileWriter(var4, true);
        var5.write(var0);
        var5.flush();
        var5.close();
    }

}
