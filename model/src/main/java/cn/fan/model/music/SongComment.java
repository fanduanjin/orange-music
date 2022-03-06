package cn.fan.model.music;

import lombok.Data;

import java.util.Date;

/**
 * @author fanduanjin
 * @program orange-music
 * @Classname SongComment
 * @Description
 * @Date 2021/11/4 20:29
 * @Created by fanduanjin
 */
@Data
public class SongComment {

    /**
     * songPlatId 为空 则为 二级评论
     */
    private long songPlatId;

    private String rootCmId;
    private int rootCmIdIndex;
    private long SeqNo;
    private Date publicTime;
    private String content;
    private String uin;
    private int praiseNum;
    private String cmId;
    private int cmIdIndex;
    private int replyCnt;
    private int rankScore;
    //private int hotScore;
    //private int recScore;
    private String repliedCmId;
    private int repliedCmIdIndex;
}
