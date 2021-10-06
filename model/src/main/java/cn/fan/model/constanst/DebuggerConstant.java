package cn.fan.model.constanst;

/**
 * @program: orange-music
 * @description: 带db 持久化mysql
 * 不带 则是 正常爬取数据
 * 带 coll 则  保存 mongodb
 * @author: fanduanjin
 * @create: 2021-04-16 23:15
 */
public class DebuggerConstant {


    public static final String queue_singer_list = "singer_list";
    public static final String queue_singer_detail = "singer_detail";
    public static final String queue_song_list = "song_list";
    public static final String queue_song_detail = "song_detail";
    public static final String queue_download_resource = "download_resource";
    public static final String queue_check_resource_modify="check_resource_modify";
    public static final String mq_message_join_charter="!@#";


}
