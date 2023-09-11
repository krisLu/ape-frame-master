package com.jingdianjichi.user.delayQueue;

import lombok.Data;

import java.util.Date;

/**
 * @Author: ChickenWing
 * @Description: 群发任务
 * @DateTime: 2023/1/8 23:21
 */
@Data
public class MassMailTask {

    private Long taskId;

    private Date startTime;

}
