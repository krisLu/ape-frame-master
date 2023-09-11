package com.jingdianjichi.demo;

import com.jingdianjichi.tool.validator.ListValue;
import com.jingdianjichi.tool.validator.ParamException;
import com.jingdianjichi.tool.validator.ValidatorUtil;
import com.jingdianjichi.user.DemoApplication;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.constraints.NotNull;

@SpringBootTest(classes = DemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@Slf4j
public class ValidatorUtilTest {

    @Test
    public void testValidator() throws Exception {
        try {
            Student student = new Student();
            ValidatorUtil.validate(student);
        } catch (ParamException e) {
            log.error("参数错误,msg:{},extra:{}", e.getMessage(), e.getExtra(), e);
        }
    }

    @Data
    class Student {

        @NotNull
        private String name;

        @ListValue(listValue = {"1", "2", "3"}, message = "tt 值只能是1、2、3")
        private String tt;

    }

}