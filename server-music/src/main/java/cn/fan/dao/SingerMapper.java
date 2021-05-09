package cn.fan.dao;

import cn.fan.model.music.Singer;
import cn.fan.model.music.SingerExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SingerMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table singer
     *
     * @mbg.generated Sun May 09 14:30:45 CST 2021
     */
    long countByExample(SingerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table singer
     *
     * @mbg.generated Sun May 09 14:30:45 CST 2021
     */
    int deleteByExample(SingerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table singer
     *
     * @mbg.generated Sun May 09 14:30:45 CST 2021
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table singer
     *
     * @mbg.generated Sun May 09 14:30:45 CST 2021
     */
    int insert(Singer record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table singer
     *
     * @mbg.generated Sun May 09 14:30:45 CST 2021
     */
    int insertSelective(Singer record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table singer
     *
     * @mbg.generated Sun May 09 14:30:45 CST 2021
     */
    List<Singer> selectByExampleWithBLOBs(SingerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table singer
     *
     * @mbg.generated Sun May 09 14:30:45 CST 2021
     */
    List<Singer> selectByExample(SingerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table singer
     *
     * @mbg.generated Sun May 09 14:30:45 CST 2021
     */
    Singer selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table singer
     *
     * @mbg.generated Sun May 09 14:30:45 CST 2021
     */
    int updateByExampleSelective(@Param("record") Singer record, @Param("example") SingerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table singer
     *
     * @mbg.generated Sun May 09 14:30:45 CST 2021
     */
    int updateByExampleWithBLOBs(@Param("record") Singer record, @Param("example") SingerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table singer
     *
     * @mbg.generated Sun May 09 14:30:45 CST 2021
     */
    int updateByExample(@Param("record") Singer record, @Param("example") SingerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table singer
     *
     * @mbg.generated Sun May 09 14:30:45 CST 2021
     */
    int updateByPrimaryKeySelective(Singer record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table singer
     *
     * @mbg.generated Sun May 09 14:30:45 CST 2021
     */
    int updateByPrimaryKeyWithBLOBs(Singer record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table singer
     *
     * @mbg.generated Sun May 09 14:30:45 CST 2021
     */
    int updateByPrimaryKey(Singer record);
}