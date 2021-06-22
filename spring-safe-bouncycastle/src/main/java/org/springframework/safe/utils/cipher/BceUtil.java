package org.springframework.safe.utils.cipher;

import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.generators.ECKeyPairGenerator;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECKeyGenerationParameters;

import java.security.SecureRandom;

/**
 * ECKeyPairGenerator 实例化密钥对生成器
 * ECKeyGenerationParameters 设置密钥对生成器的相关参数，包括曲线类型和随机数生成器
 *
 * @author Sir.D
 */
public class BceUtil {

    public static int getCurveLength(ECDomainParameters var0) {
        return (var0.getCurve().getFieldSize() + 7) / 8;
    }

    public static AsymmetricCipherKeyPair generateKeyPairParameter(ECDomainParameters var0, SecureRandom var1) {
        ECKeyGenerationParameters var2 = new ECKeyGenerationParameters(var0, var1);
        ECKeyPairGenerator var3 = new ECKeyPairGenerator();
        var3.init(var2);
        return var3.generateKeyPair();
    }
}
