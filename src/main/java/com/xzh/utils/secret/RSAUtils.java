package com.xzh.utils.secret;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * RSA
 *
 * @author 向振华
 * @date 2019/09/09 10:35
 */
@Slf4j
public class RSAUtils {

    private String publicKey;

    private String privateKey;

    /**
     * 验签
     *
     * @param data
     * @param sign
     * @return
     */
    public boolean verify(String data, String sign){
        if (StringUtils.isBlank(data) || StringUtils.isBlank(sign)){
            return false;
        }
        try {
            PublicKey publicKey = getPublicKey();
            Signature signature = Signature.getInstance("Sha1WithRSA");
            signature.initVerify(publicKey);
            signature.update(data.getBytes(StandardCharsets.UTF_8));
            byte[] signby = getBytesBASE64(sign);
            return signature.verify(signby);
        }catch (Exception e){
            log.error("verify-Exception", e);
            return false;
        }
    }

    /**
     * 加签
     *
     * @param data
     * @return
     */
    public String sign(String data) {
        if (StringUtils.isBlank(data)){
            return null;
        }
        try {
            PrivateKey privateKey = getPrivateKey();
            Signature signature = Signature.getInstance("Sha1WithRSA");
            signature.initSign(privateKey);
            signature.update(data.getBytes(StandardCharsets.UTF_8));
            byte[] signed = signature.sign();
            return Base64.getEncoder().encodeToString(signed);
        }catch (Exception e){
            log.error("sign-Exception", e);
            return null;
        }
    }

    /**
     * 加密数据
     *
     * @param originData
     * @return
     */
    public String encodeData(String originData){
        if (StringUtils.isBlank(originData)){
            return null;
        }
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE,getPublicKey());
            byte[] bytes = originData.getBytes(StandardCharsets.UTF_8);
            byte[] encode = null;
            for (int i = 0; i < bytes.length; i += 117) {
                byte[] doFinal  = cipher.doFinal(ArrayUtils.subarray(bytes, i,i + 117));
                encode = ArrayUtils.addAll(encode, doFinal);
            }
            BASE64Encoder encoder = new BASE64Encoder();
            return encoder.encode(encode);
        } catch (Exception e) {
            log.error("encodeData-Exception", e);
            return null;
        }
    }

    /**
     * 解密数据
     *
     * @param encodeData
     * @return
     */
    public String decodeData(String encodeData){
        if (StringUtils.isBlank(encodeData)){
            return null;
        }
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE,getPrivateKey());
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] bytes = decoder.decodeBuffer(encodeData);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            for (int i = 0; i < bytes.length; i += 128) {
                byte[] doFinal = cipher.doFinal(ArrayUtils.subarray(bytes, i, i + 128));
                out.write(doFinal, 0, doFinal.length);  
            }
            byte[] decryptedData = out.toByteArray();  
            out.close();
            return new String(decryptedData, StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error("decodeData-Exception", e);
            return null;
        }
    }

    /**
     * 获取公钥
     *
     * @return
     */
    private PublicKey getPublicKey(){
        byte[] bytesPublic = getBytesBASE64(publicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(bytesPublic);
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePublic(keySpec);
        } catch (Exception e) {
            log.error("getPublicKey-Exception", e);
            return null;
        }
    }

    /**
     * 获取私钥
     *
     * @return
     */
    private PrivateKey getPrivateKey(){
        byte[] bytesPrivate = getBytesBASE64(publicKey);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(bytesPrivate);
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePrivate(keySpec);
        } catch (Exception e) {
            log.error("getPrivateKey-Exception", e);
            return null;
        }
    }

    /**
     * BASE64 编码的字符串 str 进行解码
     *
     * @param str
     * @return
     */
    private byte[] getBytesBASE64(String str) {
        if (str == null) {
            return null;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            return decoder.decodeBuffer(str);
        } catch (Exception e) {
            return null;
        }
    }
}
