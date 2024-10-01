package com.cy.swapiinterfaces;

import com.sw.swapiclientsdk.client.SwApiClient;
import com.sw.swapiclientsdk.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class SwApiInterfacesApplicationTests {

    @Resource
    private SwApiClient swApiClient;

    @Test
    void contextLoads() {
        String result = swApiClient.getNameByGet("张杰");

        User user = new User();
        user.setName("邓紫棋");
        String result1 = swApiClient.getNameByRestful(user);
        System.out.println(result);
        System.out.println(result1);
    }

}
