package com.demo.service;

import com.aliyun.dingtalkrobot_1_0.models.OrgGroupSendResponse;
import com.aliyun.dingtalkrobot_1_0.models.RobotSendDingResponse;
import com.aliyun.tea.TeaException;
import com.aliyun.teautil.models.RuntimeOptions;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.request.OapiV2UserGetbymobileRequest;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.dingtalk.api.response.OapiV2UserGetbymobileResponse;
import com.taobao.api.ApiException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @author shuyuan
 * @Date 2023/7/20 17:39
 * @Description
 **/
@Service
@Configuration
@PropertySource("classpath:application")
public class DingSimulateService {
    @Value("${dingtalk.service.appKey}")
    private String appKey;

    @Value("${dingtalk.service.appSecret}")
    private String appSecret;

    @Value("${dingtalk.service.robotCode}")
    private String robotCode;

    @Value("${dingtalk.service.mobile}")
    private String mobile;

    private String accessToken;

    private String getUserIdByMobile() throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/v2/user/getbymobile");
        OapiV2UserGetbymobileRequest req = new OapiV2UserGetbymobileRequest();
        req.setMobile(mobile);
        OapiV2UserGetbymobileResponse rsp = client.execute(req, accessToken);
        System.out.println(rsp.getBody());
        return rsp.getResult().getUserid();
    }
    private void robotSendDing(String userId) throws Exception {

        com.aliyun.dingtalkrobot_1_0.Client client = DingSimulateService.createClient();
        com.aliyun.dingtalkrobot_1_0.models.RobotSendDingHeaders robotSendDingHeaders = new com.aliyun.dingtalkrobot_1_0.models.RobotSendDingHeaders();
        robotSendDingHeaders.xAcsDingtalkAccessToken = accessToken;
        com.aliyun.dingtalkrobot_1_0.models.RobotSendDingRequest robotSendDingRequest = new com.aliyun.dingtalkrobot_1_0.models.RobotSendDingRequest()
                .setRobotCode(robotCode)
                .setReceiverUserIdList(Collections.singletonList(userId))
                .setContent("生产一部：设备温度达到80度")
                .setRemindType(1);
        try {
            RobotSendDingResponse robotSendDingResponse = client.robotSendDingWithOptions(robotSendDingRequest, robotSendDingHeaders, new RuntimeOptions());

        } catch (TeaException err) {
            if (!com.aliyun.teautil.Common.empty(err.code) && !com.aliyun.teautil.Common.empty(err.message)) {
                // err 中含有 code 和 message 属性，可帮助开发定位问题
            }

        } catch (Exception _err) {
            TeaException err = new TeaException(_err.getMessage(), _err);
            if (!com.aliyun.teautil.Common.empty(err.code) && !com.aliyun.teautil.Common.empty(err.message)) {
                // err 中含有 code 和 message 属性，可帮助开发定位问题
            }

        }
    }

    public static com.aliyun.dingtalkrobot_1_0.Client createClient() throws Exception {
        com.aliyun.teaopenapi.models.Config config = new com.aliyun.teaopenapi.models.Config();
        config.protocol = "https";
        config.regionId = "central";
        return new com.aliyun.dingtalkrobot_1_0.Client(config);
    }

    private OapiGettokenResponse getTokenResponse() throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/gettoken");
        OapiGettokenRequest request = new OapiGettokenRequest();
        request.setAppkey(appKey);
        request.setAppsecret(appSecret);
        request.setHttpMethod("GET");
        OapiGettokenResponse response = client.execute(request);
        return response;
    }

    public void dingSimulate() throws Exception {
        accessToken = getTokenResponse().getAccessToken();
        String userId = getUserIdByMobile();
        robotSendDing(userId);
    }
}
