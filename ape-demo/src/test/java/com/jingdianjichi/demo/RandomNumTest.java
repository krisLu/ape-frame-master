package com.jingdianjichi.demo;

import com.jingdianjichi.user.DemoApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Random;

/**
 * 随机数测试类
 *
 * @author: ChickenWing
 * @date: 2023/1/22
 */
@SpringBootTest(classes = DemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@Slf4j
public class RandomNumTest {

    @Test
    public void getRandomNum() throws Exception {
        Random random = new Random();
        int totalNum = 107;
        int moneyPrize = random.nextInt(totalNum) + 1;
        log.info("恭喜序号【{}】小伙伴获得100元现金红包！", moneyPrize);
        int starPrize = random.nextInt(totalNum) + 1;
        log.info("恭喜序号【{}】小伙伴获得星球免费门票！", starPrize);
        int starPrizeTwo = random.nextInt(totalNum) + 1;
        log.info("恭喜序号【{}】小伙伴获得星球免费门票！", starPrizeTwo);
    }

}
