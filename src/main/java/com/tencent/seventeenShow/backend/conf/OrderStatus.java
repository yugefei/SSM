package com.tencent.seventeenShow.backend.conf;

/**
 * Created by Edward on 2016/9/19.
 */
public enum OrderStatus {
    submitted, //已提交
    assigned, //已派单
    workerSent,//已派工
    finished, //已完成
    commented, //已评价
    canceled //已取消
}