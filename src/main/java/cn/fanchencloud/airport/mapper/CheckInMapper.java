package cn.fanchencloud.airport.mapper;

import cn.fanchencloud.airport.entity.CheckIn;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by handsome programmer.
 * User: chen
 * Date: 19-1-22
 * Time: 下午1:07
 * Description:
 *
 * @author chen
 */
@Component
@Mapper
public interface CheckInMapper {
    /**
     * 添加一条值机记录到数据库中
     *
     * @param checkIn 值机信息
     * @return 添加记录
     */
    @Insert("insert into checkIn (flightInformationId,realNumber,luggageNumber,specialCase) " +
            "values (#{flightInformationId},#{realNumber},#{luggageNumber},#{specialCase})")
    int addRecord(CheckIn checkIn);

    /**
     * 删除一条值机信息记录
     *
     * @param id 记录id
     * @return 删除结果
     */
    @Delete("delete from checkIn where id = #{id}")
    int deleteRecord(int id);

    /**
     * 更新一条记录
     *
     * @param checkIn 值机信息记录
     * @return 更新记录结果
     */
    @Update("update checkIn set realNumber = #{realNumber}," +
            "luggageNumber  = #{luggageNumber},specialCase = #{specialCase} where id = #{id}")
    int updateRecord(CheckIn checkIn);

    /**
     * 查询两天内的数据记录
     *
     * @return 查询结果
     */
    @Select("select id, flightInformationId, realNumber, luggageNumber, specialCase, createTime from checkIn where createTime >= DATE_SUB(NOW(),INTERVAL 2 DAY);")
    List<CheckIn> getCheckInByTimeInTwoDays();

    /**
     * 查询最近的值机信息记录
     *
     * @param currentDays 时间限制
     * @return 查询结果
     */
    @Select("select id, flightInformationId, realNumber, luggageNumber, specialCase, createTime from checkIn where createTime >= DATE_SUB(NOW(),INTERVAL #{currentDays} DAY);")
    List<CheckIn> getCurrentRecords(int currentDays);

    /**
     * 根据id获取值机信息记录
     *
     * @param id id
     * @return 查询结果
     */
    @Select("select id, flightInformationId, realNumber, luggageNumber, specialCase, createTime from checkIn where id = #{id} ")
    CheckIn getRecordById(int id);

    /**
     * 根据航班记录id查询值机信息记录
     *
     * @param ids 航班记录id列表
     * @return 查询结果
     */
    @Select({"<script> ",
            "select id, flightInformationId, realNumber, luggageNumber, specialCase, createTime from checkin where flightInformationId in ",
            "<foreach collection='ids' item='item' index='index' open='(' close=')' separator=','>",
            "(#{item})",
            "</foreach>",
            "</script> "
    })
    @MapKey("flightInformationId")
    Map<Integer, CheckIn> getRecordByIdList(@Param("ids") List<Integer> ids);
}

