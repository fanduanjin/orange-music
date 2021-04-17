package cn.fan.util;

import cn.fan.constant.ConfigConstant;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Random;

/**
 * @program: orange-music
 * @description:
 * @author: fanduanjin
 * @create: 2021-04-16 18:18
 */
public class QqEncrypt {


    /**
     * @param encParams 需要加密的参数，这是一段请求体数据，为json字符串格式，例如下面的格式，可以抓包获取
     *                  {"comm":{"ct":24,"cv":0},"vip":{"module":"userInfo…baseinfo_v2","param":{"vec_uin":["3011429848"]}}}
     * @return 加密的方式为固定字串 zza加上一个10-16位的随机字符串再加上 固定字串 CJBPACrRuNy7加上请求数据拼接的 MD5值
     */
    public static String getSign(String encParams) {
        return ConfigConstant.signPrxfix + uuidGenerate() + convertToMd5(ConfigConstant.encNonce + encParams);
    }

    private static String uuidGenerate() {
        int minLen = 10;
        int maxLen = 16;
        Random ran = new Random(System.currentTimeMillis());
        int ranLen = ran.nextInt(maxLen - minLen) + minLen;
        StringBuilder sb = new StringBuilder(ranLen);
        for (int i = 0; i < ranLen; i++) {
            sb.append(ConfigConstant.dir[ran.nextInt(ConfigConstant.dir.length)]);
        }
        return sb.toString();
    }

    public static String convertToMd5(String plainText) {
        byte[] secretBytes = null;
        try {
            secretBytes = MessageDigest.getInstance("md5").digest(plainText.getBytes("utf-8"));
        } catch (Exception e) {
            throw new RuntimeException("没有这个md5算法！");
        }
        String md5code = new BigInteger(1, secretBytes).toString(16);
        for (int i = 0; i < 32 - md5code.length(); i++) {
            md5code = "0" + md5code;
        }
        return md5code;
    }
}

