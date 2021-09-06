package cn.fan.constant;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @program: orange-music
 * @description:
 * @author: fanduanjin
 * @create: 2021-04-16 15:36
 */
@Document
public class ConfigConstant {
    /**
     * 基础url
     */
    public final static String baseUrl="https://u.y.qq.com/cgi-bin/musics.fcg?";

    /**
     * 签名固定字符串
     */
    public static final String encNonce = "CJBPACrRuNy7";
    /**
     * 签名前缀
     */
    public static final String signPrxfix = "zza";

    /**
     *
     */
    public static final char[] dir = "0234567890abcdefghijklmnopqrstuvwxyz".toCharArray();

    public static final String comm="{\"ct\":24,\"cv\":0}";

    public  static final  String maxProcess= String.valueOf(Runtime.getRuntime().availableProcessors()+1);
}
