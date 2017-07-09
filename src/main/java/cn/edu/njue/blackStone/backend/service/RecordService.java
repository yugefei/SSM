package cn.edu.njue.blackStone.backend.service;

import cn.edu.njue.blackStone.backend.controller.form.RecordForm;
import cn.edu.njue.blackStone.backend.model.Record;

import java.util.List;

/**
 * Created by Edward on 2017/2/15 015.
 */
public interface RecordService {
    /**
     *
     * @param userId 用户id
     * @param count 数量
     * @param startId 起始记录id
     * @return 用户提交的鸟种记录,时间倒序
     */
    List<Record> userRecordList(Long userId,Long count,Long startId);
//
//    /**
//     *
//     * @param speciesId 物种id
//     * @param count 记录数
//     * @param startId 起始记录id 为0则从头开始
//     * @return 鸟种记录,时间倒序
//     */
//    List<Record> speciesRecordList(Long speciesId,Long count,Long startId);

    /**
     * 添加记录方法
     * @param record 记录
     * @return 是否添加成功
     */
    boolean addRecord(RecordForm record);

    /**
     * 返回记录详情
     * @param recordId 记录id
     */
    Record recordById(Long recordId);

    /**
     * 删除记录
     * @param recordId 记录id
     * @param userId 用户id
     * @return 如此用户有此记录,则删除并返回true 否则一律false
     */
    boolean deleteRecord(Long recordId, Long userId);

    /**
     * 编辑记录
     * @param record 所有信息需全
     * @return true成功
     */
    boolean editRecord(RecordForm record);
}
