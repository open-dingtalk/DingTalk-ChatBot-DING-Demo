package com.demo.controller;

import com.demo.service.DingSimulateService;
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

    @Autowired
    private DingSimulateService dingSimulateService;

    @RequestMapping(value = "simulate")
    public void simulate() throws Exception {
        dingSimulateService.dingSimulate();
    }
}
