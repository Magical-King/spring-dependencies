package org.springframework.safe.utils.cipher;

import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.engines.SM2Engine;
import org.bouncycastle.crypto.params.*;
import org.bouncycastle.crypto.signers.SM2Signer;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.math.ec.custom.gm.SM2P256V1Curve;
import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.security.spec.ECFieldFp;
import java.security.spec.ECParameterSpec;
import java.security.spec.EllipticCurve;

/**
 * CURVE 曲线sm2p256v1算法
 * RANDOM 随机数
 * SM2_ECC_P 素数p
 * SM2_ECC_A 素数a
 * SM2_ECC_B 素数b
 * SM2_ECC_N 生成元G的阶n
 * SM2_ECC_H 素数h
 * SM2_ECC_GX 生成元G是椭圆曲线的一个点，该点的横坐标x
 * SM2_ECC_GY G的纵坐标
 * G_POINT 此不可变类在仿射坐标中表示椭圆曲线 (EC) 上的点。其他坐标系可以扩展此类，以便在其他坐标中表示此点。
 * DOMAIN_PARAMS ECC参数
 * CURVE_LEN 曲线长度
 * JDK_CURVE ECC椭圆曲线算法-jdk自带
 * JDK_G_POINT ECC椭圆点位
 * JDK_EC_SPEC ECC参数规格
 * SM3_DIGEST_LENGTH SM3摘要长度
 * @author Sir.D
 */
public class Sm2Util extends SmBaseUtil {
    public static final SM2P256V1Curve CURVE = new SM2P256V1Curve();
    public static final SecureRandom RANDOM = new SecureRandom();
    public static final BigInteger SM2_ECC_P;
    public static final BigInteger SM2_ECC_A;
    public static final BigInteger SM2_ECC_B;
    public static final BigInteger SM2_ECC_N;
    public static final BigInteger SM2_ECC_H;
    public static final BigInteger SM2_ECC_GX;
    public static final BigInteger SM2_ECC_GY;
    public static final ECPoint G_POINT;
    public static final ECDomainParameters DOMAIN_PARAMS;
    public static final int CURVE_LEN;
    public static final EllipticCurve JDK_CURVE;
    public static final java.security.spec.ECPoint JDK_G_POINT;
    public static final ECParameterSpec JDK_EC_SPEC;
    public static final int SM3_DIGEST_LENGTH = 32;

    static {
        SM2_ECC_P = CURVE.getQ();
        SM2_ECC_A = CURVE.getA().toBigInteger();
        SM2_ECC_B = CURVE.getB().toBigInteger();
        SM2_ECC_N = CURVE.getOrder();
        SM2_ECC_H = CURVE.getCofactor();
        SM2_ECC_GX = new BigInteger("32C4AE2C1F1981195F9904466A39C9948FE30BBFF2660BE1715A4589334C74C7", 16);
        SM2_ECC_GY = new BigInteger("BC3736A2F4F6779C59BDCEE36B692153D0A9877CC62A474002DF32E52139F0A0", 16);

        G_POINT = CURVE.createPoint(SM2_ECC_GX, SM2_ECC_GY);
        DOMAIN_PARAMS = new ECDomainParameters(CURVE, G_POINT, SM2_ECC_N, SM2_ECC_H);
        CURVE_LEN = BceUtil.getCurveLength(DOMAIN_PARAMS);
        JDK_CURVE = new EllipticCurve(new ECFieldFp(SM2_ECC_P), SM2_ECC_A, SM2_ECC_B);
        JDK_G_POINT = new java.security.spec.ECPoint(G_POINT.getAffineXCoord().toBigInteger(), G_POINT.getAffineYCoord().toBigInteger());
        JDK_EC_SPEC = new ECParameterSpec(JDK_CURVE, JDK_G_POINT, SM2_ECC_N, SM2_ECC_H.intValue());
    }

    protected static AsymmetricCipherKeyPair ak() {
        return BceUtil.generateKeyPairParameter(DOMAIN_PARAMS, RANDOM);
    }


    protected static byte[] privateSigner(String var0, byte[] var1) throws CryptoException {
        BigInteger var2 = new BigInteger(var0, 16);
        ECPrivateKeyParameters var3 = new ECPrivateKeyParameters(var2, DOMAIN_PARAMS);
        return privateSigner(var3, var1);
    }

    protected static byte[] privateSigner(ECPrivateKeyParameters var0, byte[] var1) throws CryptoException {
        return privateSigner(var0, null, var1);
    }

    protected static byte[] privateSigner(ECPrivateKeyParameters var0, byte[] var1, byte[] var2) throws CryptoException {
        SM2Signer var3 = new SM2Signer();

        Object var4 = null;
        ParametersWithRandom var5 = new ParametersWithRandom(var0, RANDOM);
        if (var1 != null) {
            var4 = new ParametersWithID(var5, var1);
        } else {
            var4 = var5;
        }

        var3.init(true, (CipherParameters)var4);
        var3.update(var2, 0, var2.length);
        return var3.generateSignature();
    }

    protected static boolean publicVerify(byte[] var0, byte[] var1, byte[] var2) {
        ECPoint var3 = CURVE.decodePoint(var0);
        ECPublicKeyParameters var4 = new ECPublicKeyParameters(var3, DOMAIN_PARAMS);
        return publicVerify(var4, var1, var2);
    }

    protected static boolean publicVerify(ECPublicKeyParameters var0, byte[] var1, byte[] var2) {
        return publicVerify(var0, null, var1, var2);
    }

    protected static boolean publicVerify(ECPublicKeyParameters var0, byte[] var1, byte[] var2, byte[] var3) {
        SM2Signer var4 = new SM2Signer();
        Object var5 = null;
        if (var1 != null) {
            new ParametersWithID(var0,var1);
        } else {
            var5 = var0;
        }

        var4.init(false, (CipherParameters) var5);
        var4.update(var2, 0, var2.length);
        return var4.verifySignature(var3);
    }

    protected static byte[] publicEncrypt(ECPublicKeyParameters var0, byte[] var1) throws InvalidCipherTextException {
        SM2Engine var2 = new SM2Engine();
        ParametersWithRandom var3 = new ParametersWithRandom(var0, RANDOM);

        var2.init(true, var3);
        return var2.processBlock(var1, 0, var1.length);
    }

    protected static byte[] publicEncrypt(byte[] var0, byte[] var1) throws InvalidCipherTextException {
        ECPoint var2 = CURVE.decodePoint(var0);
        ECPublicKeyParameters var3 = new ECPublicKeyParameters(var2, DOMAIN_PARAMS);

        return publicEncrypt(var3, var1);
    }


    protected static byte[] privateDecrypt(ECPrivateKeyParameters var0, byte[] var1) throws InvalidCipherTextException {
        SM2Engine var2 = new SM2Engine();
        var2.init(false, var0);
        return var2.processBlock(var1, 0, var1.length);
    }

    protected static byte[] privateDecrypt(String var0, byte[] var1) throws InvalidCipherTextException {
        BigInteger var2 = new BigInteger(var0, 16);
        ECPrivateKeyParameters var3 = new ECPrivateKeyParameters(var2, DOMAIN_PARAMS);
        return privateDecrypt(var3, var1);
    }

    public static void main(String[] args) throws Exception {
        //第一种用法：公钥加密，私钥解密。---用于加解密
        //第二种用法：私钥签名，公钥验签。---用于签名
        String var0 =  "GZROBOT";

        AsymmetricCipherKeyPair var1 = ak();
        ECPrivateKeyParameters var2 = (ECPrivateKeyParameters) var1.getPrivate();
        ECPublicKeyParameters var3 = (ECPublicKeyParameters) var1.getPublic();

        System.out.println("===>Pri Hex: " + ByteUtils.toHexString(var2.getD().toByteArray()).toUpperCase());
        System.out.println("===>Pub Point Hex: " + ByteUtils.toHexString(var3.getQ().getEncoded(false)).toUpperCase());

        System.out.println("******************************随即生成公私钥（基于random）私钥签名公钥验签**************************************");

        byte[] var4 = privateSigner(var2, var0.getBytes());
        System.out.println("=====>SM2 sign uses the default USER_ID result: " + ByteUtils.toHexString(var4));
        boolean var5 = publicVerify(var3, var0.getBytes(), var4);
        System.out.println("=====>SM2 Public key verification result: " + var5);

        System.out.println("******************************随即生成公私钥（基于random）公钥加密私钥解密**************************************");

        byte[] var6 = publicEncrypt(var3, var0.getBytes());
        System.out.println("=====>SM2 public encrypt result: " + ByteUtils.toHexString(var6));
        byte[] var7 = privateDecrypt(var2, var6);
        System.out.println("=====>SM2 private encrypt result: " + new String(var7));

        System.out.println("***************************************给定公私钥，公钥加密私钥解密********************************************");

        // 设定公钥
        String var11 = "0428133A6069D3A3778EC3B1F4AA8BCF2CC64FC767487BC365C9E345F15820BE193CEF663BDBC96CA9C7514FADCEC64EB2192BB01A8AEE15F42A3B6615A9C9390A";
        byte[] var14 = publicEncrypt(ByteUtils.fromHexString(var11), var0.getBytes());
        System.out.println("=====>SM2 encrypt result: " + ByteUtils.toHexString(var14));

        // 设定私钥
        String var8 = "5D51C91B5238D88594A5293D7073B6465CCEE873299C2FF9940440507DC8E58A";
        byte[] var9 = privateDecrypt(var8, var14);
        System.out.println("=====>SM2 decrypt result: " + new String(var9));

        System.out.println("***************************************给定公私钥，私钥签名公钥验签********************************************");

        byte[] var10 = privateSigner(var8, var0.getBytes());
        System.out.println("=====>SM2 sign uses the default USER_ID result: " + ByteUtils.toHexString(var10));
        boolean var12 = publicVerify(ByteUtils.fromHexString(var11), var0.getBytes(), var10);
        System.out.println("=====>SM2 Public key verification result: " + var12);

    }

}
