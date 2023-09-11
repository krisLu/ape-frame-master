package com.jingdianjichi.user.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: ChickenWing
 * @Description: 人员event发布service
 * @DateTime: 2023/1/8 23:02
 */
@Service
@Slf4j
public class PersonEventService {

    @Resource
    private ApplicationEventPublisher applicationEventPublisher;

    public void creatPerson(Person person) {
        applicationEventPublisher.publishEvent(new PersonChangeEvent(person, "create"));
    }

}
