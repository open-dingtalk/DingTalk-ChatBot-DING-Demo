package com.demo.controller;

import com.demo.service.DingSimulateService;
import com.demo.service.SubscribeService;
import com.dingtalk.api.response.OapiChatCreateResponse;
import com.taobao.api.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shuyuan
 * @Date 2023/7/20 17:17
 * @Description
 **/
@RestController
@RequestMapping(value = "dingTest")
public class DingTestApiController {
    private String openConversationId;

    @Autowired
    private  SubscribeService subscribeService;

    @Autowired
    private DingSimulateService dingSimulateService;

    @RequestMapping(value = "subscribe")
    public void subscribe() throws Exception {
        openConversationId = subscribeService.subcribe();
    }

    @RequestMapping(value = "simulate")
    public void simulate() throws Exception {
        dingSimulateService.dingSimulate(openConversationId);
    }
}
