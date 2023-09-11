package com.jingdianjichi.user.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @Author: ChickenWing
 * @Description: 人员变更event
 * @DateTime: 2023/1/8 22:53
 */
@Getter
public class PersonChangeEvent extends ApplicationEvent {

    private Person person;

    private String operateType;

    public PersonChangeEvent(Person person, String operateType) {
        super(person);
        this.person = person;
        this.operateType = operateType;
    }
}
