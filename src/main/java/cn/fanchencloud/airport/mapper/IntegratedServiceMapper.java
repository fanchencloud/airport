package cn.fanchencloud.airport.mapper;

import cn.fanchencloud.airport.entity.IntegratedService;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by handsome programmer.
 * User: chen
 * Date: 19-1-23
 * Time: 下午6:14
 * Description: 综合信息服务数据持久化
 *
 * @author chen
 */
@Component
@Mapper
public interface IntegratedServiceMapper {

    /**
     * 将一条综合服务信息持久化到数据库
     *
     * @param integratedService 综合服务信息
     * @return 持久化结果
     */
    @Insert("insert into integratedService (flightInformationId, boardingTime, readyTime, closeTime, specialCase) VALUES" +
            " (#{flightInformationId}, #{boardingTime}, #{readyTime}, #{closeTime}, #{specialCase});")
    int addIntegratedService(IntegratedService integratedService);

    /**
     * 查询最近的数据
     *
     * @param currentDay 时间限制
     * @return 查询结果
     */
    @Select("select id, flightInformationId, boardingTime, readyTime, closeTime, specialCase, createTime from integratedService " +
            "where  createTime >= DATE_SUB(NOW(),INTERVAL #{currentDay} DAY);")
    List<IntegratedService> getCurrentRecord(int currentDay);

    /**
     * 通过记录id 查询记录详情
     *
     * @param id 记录id
     * @return 查询结果
     */
    @Select("select id, flightInformationId, boardingTime, readyTime, closeTime, specialCase, createTime from integratedService where id= #{id}")
    IntegratedService queryById(int id);

    /**
     * 将一条新的数据持久化到数据库中
     *
     * @param integratedService 新的记录
     * @return 持久化结果
     */
    @Update("update integratedService" +
            " set boardingTime = #{boardingTime}," +
            "    readyTime    = #{readyTime}," +
            "    closeTime    = #{closeTime}," +
            "    specialCase  = #{specialCase}" +
            "where id = #{id};")
    int update(IntegratedService integratedService);
}
