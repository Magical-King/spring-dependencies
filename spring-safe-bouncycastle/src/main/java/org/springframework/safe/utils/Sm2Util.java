package org.springframework.safe.utils;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;

import java.nio.charset.StandardCharsets;

/**
 * @author Sir.D
 */
@Slf4j
@NoArgsConstructor
public class Sm2Util extends org.springframework.safe.utils.cipher.Sm2Util {
    public static AsymmetricCipherKeyPair KEYPAIR;
    public static String PRIVATEKEY_HEX_JS;
    public static String PUBLICKEY_HEX_JS;

    static {
        try {
            KEYPAIR = ak();
            ECPrivateKeyParameters var0 = (ECPrivateKeyParameters)KEYPAIR.getPrivate();
            ECPublicKeyParameters var1 = (ECPublicKeyParameters)KEYPAIR.getPublic();
            PRIVATEKEY_HEX_JS = ByteUtils.toHexString(var0.getD().toByteArray()).toUpperCase();
            log.info("=====> 私钥(HEX) : {}", PRIVATEKEY_HEX_JS );
            PUBLICKEY_HEX_JS = ByteUtils.toHexString(var1.getQ().getEncoded(false)).toUpperCase();
            log.info("=====> 公钥(HEX) : {}", PUBLICKEY_HEX_JS);
        } catch (Exception var) {
            log.error(var.getMessage());
        }
    }

    public static String encrypt(byte[] var0, byte[] var1) {
        try {
            if (var0 ==null || var0.length == 0) {
                return null;
            }

            byte[] var2;
            if (var1 !=null && var1.length != 0) {
                var2 = publicEncrypt(var1, var0);
            } else {
                ECPublicKeyParameters var3 = (ECPublicKeyParameters)KEYPAIR.getPublic();
                var2 = publicEncrypt(var3, var0);
            }

            return ByteUtils.toHexString(var2).toUpperCase();
        } catch (Exception var) {
            log.error("===> 加密失败！原因: {}", var.getMessage());
            return null;
        }

    }

    public static String encrypt(String var0, String var1) {
        return encrypt(var0.getBytes(), ByteUtils.fromHexString(var1));
    }

    public static String encrypt(String var0) {
        return encrypt(var0.getBytes(), null);
    }

    public static String decrypt(byte[] var0, String var1) throws InvalidCipherTextException {
        if (var0 ==null || var0.length == 0) {
            return null;
        }

        byte[] var2;
        if (StringUtils.isNotBlank(var1)) {
            var2 = privateDecrypt(var1, var0);
        } else {
            ECPrivateKeyParameters var3 = (ECPrivateKeyParameters)KEYPAIR.getPrivate();
            var2 = privateDecrypt(var3, var0);
        }

        return new String(var2, StandardCharsets.UTF_8);
    }

    public static String decrypt(String var0, String var1) throws InvalidCipherTextException {
        return decrypt(ByteUtils.fromHexString(var0), var1);
    }

    public static String decrypt(String var0) throws InvalidCipherTextException {
        return decrypt(ByteUtils.fromHexString(var0), null);
    }

    public static byte[] sign(byte[] var0, String var1) {
        try {
            if (var0 ==null || var0.length == 0) {
                return null;
            }

            byte[] var2;
            if (StringUtils.isNotBlank(var1)) {
                var2 = privateSigner(var1, var0);
            } else {
                ECPrivateKeyParameters var3 = (ECPrivateKeyParameters)KEYPAIR.getPrivate();
                var2 = privateSigner(var3, var0);
            }

            return var2;
        } catch (Exception var) {
            var.printStackTrace();
            log.error("===> 验签失败！原因: {}", var.getMessage());
            return null;
        }
    }

    public static String sign(String var0, String var1) {
        return ByteUtils.toHexString(sign(var0.getBytes(), var1));
    }

    public static String sign(String var0) {
        return ByteUtils.toHexString(sign(var0.getBytes(), null));
    }

    public static boolean verify(byte[] var0, byte[] var1, byte[] var2) {
        if (var0 != null && var0.length > 0) {
            return publicVerify(var0, var1, var2);
        } else {
            ECPublicKeyParameters var3 = (ECPublicKeyParameters)KEYPAIR.getPublic();
            return publicVerify(var3, var1, var2);
        }
    }

    public static boolean verify(String var0, String var1, String var2) {
        return verify(ByteUtils.fromHexString(var0), var1.getBytes(), ByteUtils.fromHexString(var2));
    }

    public static boolean verify(String var0, String var1) {
        return verify(null, var0.getBytes(), ByteUtils.fromHexString(var1));
    }

    public static void main(String[] args) {
        String var = "LICENSEID=5c2cac41-a46a-4e1a-a1a8-2833c66862dd;LICENSENAME=Gzrobot使用证书;ISNOTIMELIMIT=false;LICENSELIMIT=2020-08-02;MACHINECODE=2ea25a758720241a0bd0b86dd9ed855b;ENCODEDLICENSE=04E7361DAA3C2F93B450EA21120A707094B5FA835BFFB73B3C760FBB6A18A3C63D1332CFD4FF81C78B626A674F4CB6E35A3E4BCD3D707AD2B382CC4AA856571851C3BEB16416618EAE36FE84244F1352A3876F8F0DAE454095461A20214662B44250E1CDD2549D902B427C5C19E3EE6AD1667AD4CDC3A022DD94BBCF46D67EADB4D4AEF6FF71CD182940A2A7A2FE438B32429A6FF147006462736096B28E401D1EB222E3241986BAE8C241C61246BE233333DE460F9546F3842E2FCBE467E07B4895D939E691C7DD32948EDF46C676C246B02EF54F733F2D4E0987030463C5AEEB9A188BBB0DD38BD058716CDEC557DA94780BA4CF29EA1D25FF0009B8409DDF5A101F991181D435AA9A0C564C93FEDF43B4C89036A3F47C\n";
        String pri = "6F86C601E532D54FB49BB116FE3FC9931CAE04E0BE555D3C7EDDAD9F1CEFAA3F";
        String pub = "04395DCF9C903B298A76495952611992986E45AD08BB9BF03B2415CF24D3F30D1797FA31147B903E3DBA2929AC150BBCB551B2FB7519753D00AC801F3B277EFD1F";

        String var10 = "{\n" +
                "    \"orgId\": \"1\",\n" +
                "    \"orgName\": \"啊爱思大多\"\n" +
                "}";
        String var1 = encrypt(var10,pub);
        System.out.println(var1);
//        String var2 = decrypt(var1,pri);
//        System.out.println(var2);

//        String var3 = sign(var);
//        System.out.println(var3);
//        boolean var4 = verify(var, var3);
//        System.out.println(var4);
    }

}
