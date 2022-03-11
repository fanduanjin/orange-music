package cn.fan.model.music;

/**
 * @author fanduanjin
 * @program orange-music
 * @Classname SingerType
 * @Description
 * @Date 2022/3/8 15:09
 * @Created by fanduanjin
 */

public enum SingerType {
    Singer(1),
    Team(2);

    private int code;

    private SingerType(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }
}
