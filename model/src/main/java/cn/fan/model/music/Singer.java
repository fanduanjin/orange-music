package cn.fan.model.music;

import java.io.Serializable;
import java.util.Date;

public class Singer implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column singer.id
     *
     * @mbg.generated Sun May 09 19:12:54 CST 2021
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column singer.plat_id
     *
     * @mbg.generated Sun May 09 19:12:54 CST 2021
     */
    private Integer platId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column singer.mid
     *
     * @mbg.generated Sun May 09 19:12:54 CST 2021
     */
    private String mid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column singer.name
     *
     * @mbg.generated Sun May 09 19:12:54 CST 2021
     */
    private String name;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column singer.type
     *
     * @mbg.generated Sun May 09 19:12:54 CST 2021
     */
    private Integer type;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column singer.area
     *
     * @mbg.generated Sun May 09 19:12:54 CST 2021
     */
    private Integer area;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column singer.genre
     *
     * @mbg.generated Sun May 09 19:12:54 CST 2021
     */
    private Integer genre;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column singer.foreign_name
     *
     * @mbg.generated Sun May 09 19:12:54 CST 2021
     */
    private String foreignName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column singer.birthday
     *
     * @mbg.generated Sun May 09 19:12:54 CST 2021
     */
    private Date birthday;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column singer.pic
     *
     * @mbg.generated Sun May 09 19:12:54 CST 2021
     */
    private String pic;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column singer.pic_resource_id
     *
     * @mbg.generated Sun May 09 19:12:54 CST 2021
     */
    private Long picResourceId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column singer.desc
     *
     * @mbg.generated Sun May 09 19:12:54 CST 2021
     */
    private String desc;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column singer.wiki
     *
     * @mbg.generated Sun May 09 19:12:54 CST 2021
     */
    private String wiki;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table singer
     *
     * @mbg.generated Sun May 09 19:12:54 CST 2021
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column singer.id
     *
     * @return the value of singer.id
     *
     * @mbg.generated Sun May 09 19:12:54 CST 2021
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column singer.id
     *
     * @param id the value for singer.id
     *
     * @mbg.generated Sun May 09 19:12:54 CST 2021
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column singer.plat_id
     *
     * @return the value of singer.plat_id
     *
     * @mbg.generated Sun May 09 19:12:54 CST 2021
     */
    public Integer getPlatId() {
        return platId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column singer.plat_id
     *
     * @param platId the value for singer.plat_id
     *
     * @mbg.generated Sun May 09 19:12:54 CST 2021
     */
    public void setPlatId(Integer platId) {
        this.platId = platId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column singer.mid
     *
     * @return the value of singer.mid
     *
     * @mbg.generated Sun May 09 19:12:54 CST 2021
     */
    public String getMid() {
        return mid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column singer.mid
     *
     * @param mid the value for singer.mid
     *
     * @mbg.generated Sun May 09 19:12:54 CST 2021
     */
    public void setMid(String mid) {
        this.mid = mid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column singer.name
     *
     * @return the value of singer.name
     *
     * @mbg.generated Sun May 09 19:12:54 CST 2021
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column singer.name
     *
     * @param name the value for singer.name
     *
     * @mbg.generated Sun May 09 19:12:54 CST 2021
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column singer.type
     *
     * @return the value of singer.type
     *
     * @mbg.generated Sun May 09 19:12:54 CST 2021
     */
    public Integer getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column singer.type
     *
     * @param type the value for singer.type
     *
     * @mbg.generated Sun May 09 19:12:54 CST 2021
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column singer.area
     *
     * @return the value of singer.area
     *
     * @mbg.generated Sun May 09 19:12:54 CST 2021
     */
    public Integer getArea() {
        return area;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column singer.area
     *
     * @param area the value for singer.area
     *
     * @mbg.generated Sun May 09 19:12:54 CST 2021
     */
    public void setArea(Integer area) {
        this.area = area;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column singer.genre
     *
     * @return the value of singer.genre
     *
     * @mbg.generated Sun May 09 19:12:54 CST 2021
     */
    public Integer getGenre() {
        return genre;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column singer.genre
     *
     * @param genre the value for singer.genre
     *
     * @mbg.generated Sun May 09 19:12:54 CST 2021
     */
    public void setGenre(Integer genre) {
        this.genre = genre;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column singer.foreign_name
     *
     * @return the value of singer.foreign_name
     *
     * @mbg.generated Sun May 09 19:12:54 CST 2021
     */
    public String getForeignName() {
        return foreignName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column singer.foreign_name
     *
     * @param foreignName the value for singer.foreign_name
     *
     * @mbg.generated Sun May 09 19:12:54 CST 2021
     */
    public void setForeignName(String foreignName) {
        this.foreignName = foreignName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column singer.birthday
     *
     * @return the value of singer.birthday
     *
     * @mbg.generated Sun May 09 19:12:54 CST 2021
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column singer.birthday
     *
     * @param birthday the value for singer.birthday
     *
     * @mbg.generated Sun May 09 19:12:54 CST 2021
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column singer.pic
     *
     * @return the value of singer.pic
     *
     * @mbg.generated Sun May 09 19:12:54 CST 2021
     */
    public String getPic() {
        return pic;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column singer.pic
     *
     * @param pic the value for singer.pic
     *
     * @mbg.generated Sun May 09 19:12:54 CST 2021
     */
    public void setPic(String pic) {
        this.pic = pic;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column singer.pic_resource_id
     *
     * @return the value of singer.pic_resource_id
     *
     * @mbg.generated Sun May 09 19:12:54 CST 2021
     */
    public Long getPicResourceId() {
        return picResourceId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column singer.pic_resource_id
     *
     * @param picResourceId the value for singer.pic_resource_id
     *
     * @mbg.generated Sun May 09 19:12:54 CST 2021
     */
    public void setPicResourceId(Long picResourceId) {
        this.picResourceId = picResourceId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column singer.desc
     *
     * @return the value of singer.desc
     *
     * @mbg.generated Sun May 09 19:12:54 CST 2021
     */
    public String getDesc() {
        return desc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column singer.desc
     *
     * @param desc the value for singer.desc
     *
     * @mbg.generated Sun May 09 19:12:54 CST 2021
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column singer.wiki
     *
     * @return the value of singer.wiki
     *
     * @mbg.generated Sun May 09 19:12:54 CST 2021
     */
    public String getWiki() {
        return wiki;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column singer.wiki
     *
     * @param wiki the value for singer.wiki
     *
     * @mbg.generated Sun May 09 19:12:54 CST 2021
     */
    public void setWiki(String wiki) {
        this.wiki = wiki;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table singer
     *
     * @mbg.generated Sun May 09 19:12:54 CST 2021
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", platId=").append(platId);
        sb.append(", mid=").append(mid);
        sb.append(", name=").append(name);
        sb.append(", type=").append(type);
        sb.append(", area=").append(area);
        sb.append(", genre=").append(genre);
        sb.append(", foreignName=").append(foreignName);
        sb.append(", birthday=").append(birthday);
        sb.append(", pic=").append(pic);
        sb.append(", picResourceId=").append(picResourceId);
        sb.append(", desc=").append(desc);
        sb.append(", wiki=").append(wiki);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}