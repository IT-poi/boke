package com.cuit.boke;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@ComponentScan("com.cuit")
@RunWith(SpringRunner.class)
public class TestAlarm {

    @Test
    public void testInsert(){
    }

}