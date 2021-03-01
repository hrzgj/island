package com.verygood.island;

import com.verygood.island.util.ScheduledUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
class IslandApplicationTests {

    @Autowired
    ScheduledUtils scheduledUtils;

    @Test
    void contextLoads() {
        scheduledUtils.addTask(LocalDateTime.now(),new Thread());
    }

}
