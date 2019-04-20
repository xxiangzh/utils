//package com.xzh.utils;
//
//import com.alibaba.fastjson.JSONObject;
//import com.mmtvip.gatewayservice.config.KeyConfig;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.cloud.context.config.annotation.RefreshScope;
//import org.springframework.stereotype.Component;
//import sun.misc.BASE64Decoder;
//
//import javax.annotation.Resource;
//import javax.crypto.Cipher;
//import java.nio.charset.StandardCharsets;
//import java.security.KeyFactory;
//import java.security.PrivateKey;
//import java.security.PublicKey;
//import java.security.Signature;
//import java.security.spec.PKCS8EncodedKeySpec;
//import java.security.spec.X509EncodedKeySpec;
//import java.util.ArrayList;
//import java.util.Base64;
//import java.util.List;
//
///**
// * RSA
// *
// * @author 向振华
// * @date 2019/01/08 13:35
// */
//@Slf4j
//@RefreshScope
//@Component
//public class RSAUtils {
//
//    private String publicKey;
//
//    private String privateKey;
//
//    /**
//     * 验签
//     *
//     * @param json
//     * @return
//     */
//    public boolean verify(JSONObject json){
//        String sign = json.getString("sign");
//        if (StringUtils.isBlank(sign)){
//            return false;
//        }
//        return verify(buildSignData(json), sign);
//    }
//
//    /**
//     * 加签
//     *
//     * @param json
//     * @return
//     */
//    public String sign(JSONObject json) {
//        return sign(buildSignData(json));
//    }
//
//    /**
//     * 加密
//     *
//     * @param json
//     * @return
//     */
//    public String encodeData(JSONObject json){
//        return encodeData(buildSignData(json));
//    }
//
//    /**
//     * 解密
//     *
//     * @param json
//     * @return
//     */
//    public String decodeData(JSONObject json){
//        return encodeData(buildSignData(json));
//    }
//
//    /**
//     * 验签
//     *
//     * @param data
//     * @param sign
//     * @return
//     */
//    public boolean verify(String data, String sign){
//        try {
//            PublicKey publicKey = getPublicKey();
//            Signature signature = Signature.getInstance("Sha1WithRSA");
//            signature.initVerify(publicKey);
//            signature.update(data.getBytes(StandardCharsets.UTF_8));
//            byte[] signby = getBytesBASE64(sign);
//            return signature.verify(signby);
//        }catch (Exception e){
//            log.error("verify-Exception", e);
//            return false;
//        }
//    }
//
//    /**
//     * 加签
//     *
//     * @param data
//     * @return
//     */
//    public String sign(String data) {
//        try {
//            PrivateKey privateKey = getPrivateKey();
//            Signature signature = Signature.getInstance("Sha1WithRSA");
//            signature.initSign(privateKey);
//            signature.update(data.getBytes(StandardCharsets.UTF_8));
//            byte[] signed = signature.sign();
//            return Base64.getEncoder().encodeToString(signed);
//        }catch (Exception e){
//            log.error("sign-Exception", e);
//            return null;
//        }
//    }
//
//    /**
//     * 加密数据
//     *
//     * @param originData
//     * @return
//     */
//    public String encodeData(String originData){
//        try {
//            Cipher cipher = Cipher.getInstance("RSA");
//            cipher.init(Cipher.ENCRYPT_MODE,getPublicKey());
//            byte[] bytesEncrypt = cipher.doFinal(originData.getBytes());
//            byte[] bytesEncryptBase64 = Base64.getEncoder().encode(bytesEncrypt);
//            return new String(bytesEncryptBase64);
//        } catch (Exception e) {
//            log.error("encodeData-Exception", e);
//            return null;
//        }
//    }
//
//    /**
//     * 解密数据
//     *
//     * @param encodeData
//     * @return
//     */
//    public String decodeData(String encodeData){
//        try {
//            byte[] bytesEncrypt = getBytesBASE64(encodeData);
//            Cipher cipher = Cipher.getInstance("RSA");
//            cipher.init(Cipher.DECRYPT_MODE,getPrivateKey());
//            byte[] bytesDecrypt = cipher.doFinal(bytesEncrypt);
//            return new String(bytesDecrypt);
//        } catch (Exception e) {
//            log.error("decodeData-Exception", e);
//            return null;
//        }
//    }
//
//    /**
//     * 获取公钥
//     *
//     * @return
//     */
//    private PublicKey getPublicKey(){
//        byte[] bytesPublic = getBytesBASE64(publicKey);
//        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(bytesPublic);
//        try {
//            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//            return keyFactory.generatePublic(keySpec);
//        } catch (Exception e) {
//            log.error("getPublicKey-Exception", e);
//            return null;
//        }
//    }
//
//    /**
//     * 获取私钥
//     *
//     * @return
//     */
//    private PrivateKey getPrivateKey(){
//        byte[] bytesPrivate = getBytesBASE64(privateKey);
//        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(bytesPrivate);
//        try {
//            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//            return keyFactory.generatePrivate(keySpec);
//        } catch (Exception e) {
//            log.error("getPrivateKey-Exception", e);
//            return null;
//        }
//    }
//
//    /**
//     * BASE64 编码的字符串 str 进行解码
//     *
//     * @param str
//     * @return
//     */
//    private byte[] getBytesBASE64(String str) {
//        if (str == null) {
//            return null;
//        }
//        BASE64Decoder decoder = new BASE64Decoder();
//        try {
//            return decoder.decodeBuffer(str);
//        } catch (Exception e) {
//            return null;
//        }
//    }
//
//    /**
//     * 生成待签名串
//     *
//     * @param jsonObject
//     * @return
//     */
//    private String buildSignData(JSONObject jsonObject) {
//        StringBuilder content = new StringBuilder();
//        // 按照key做首字母升序排列
//        List<String> keys = new ArrayList<String>(jsonObject.keySet());
//        keys.sort(String.CASE_INSENSITIVE_ORDER);
//        for (int i = 0; i < keys.size(); i++) {
//            String key = (String) keys.get(i);
//            if ("sign".equals(key)) {
//                continue;
//            }
//            String value = jsonObject.getString(key);
//            if (StringUtils.isBlank(value)) {
//                continue;
//            }
//            content.append(i == 0 ? "" : "&").append(key).append("=").append(value);
//        }
//        String signSrc = content.toString();
//        if (signSrc.startsWith("&")) {
//            signSrc = signSrc.replaceFirst("&", "");
//        }
//        return signSrc;
//    }
//}
