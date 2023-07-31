package com.demo.service;

import com.aliyun.dingtalkim_1_0.models.SendRobotInteractiveCardHeaders;
import com.aliyun.dingtalkim_1_0.models.SendRobotInteractiveCardRequest;
import com.aliyun.dingtalkrobot_1_0.models.OrgGroupSendResponse;
import com.aliyun.tea.TeaException;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.models.RuntimeOptions;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.taobao.api.ApiException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

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

    @Value("${dingtalk.service.owner}")
    private String owner;

    @Value("${dingtalk.service.userIdList}")
    private List<String> userIdList;

    @Value("${dingtalk.service.receiverUserIdList}")
    private List<String> receiverUserIdList;

    private String accessToken;

    private void sendChatMessage(String openConversationId) throws Exception {
        com.aliyun.dingtalkim_1_0.Client client = DingSimulateService.createRobotClient();
        SendRobotInteractiveCardHeaders sendRobotInteractiveCardHeaders = new SendRobotInteractiveCardHeaders();
        sendRobotInteractiveCardHeaders.xAcsDingtalkAccessToken = accessToken;
        SendRobotInteractiveCardRequest.SendRobotInteractiveCardRequestSendOptions sendOptions = new SendRobotInteractiveCardRequest.SendRobotInteractiveCardRequestSendOptions()
                .setAtAll(true);
        SendRobotInteractiveCardRequest sendRobotInteractiveCardRequest = new SendRobotInteractiveCardRequest()
                .setCardTemplateId("StandardCard")
                .setOpenConversationId(openConversationId)
                .setCardBizId("card0001")
                .setRobotCode("SD9h63f8BUCRLa216904571118581280")
                .setCardData("{   \"config\": {     \"autoLayout\": true,     \"enableForward\": true   },   \"header\": {     \"title\": {       \"type\": \"text\",       \"text\": \"告警通知\"     },     \"logo\": \"@lALPDfJ6V_FPDmvNAfTNAfQ\"   },   \"contents\": [     {       \"type\": \"text\",       \"text\": \"设备温度过高，请及时处理！！\",       \"id\": \"text_1690462183037\"     },     {       \"type\": \"divider\",       \"id\": \"divider_1690462183037\"     },     {       \"type\": \"markdown\",       \"text\": \"**设备名称：5号设备**\\n**值班人：张三**\\n<font color=common_red1_color>设备温度已达到83度！[查看详情](https://dingtalk.com)\\n[火][火]紧急紧急[火][火]\",       \"id\": \"markdown_1690462357830\"     },     {       \"type\": \"action\",       \"actions\": [         {           \"type\": \"button\",           \"label\": {             \"type\": \"text\",             \"text\": \"去处理\",             \"id\": \"text_1690462183038\"           },           \"actionType\": \"openLink\",           \"url\": {             \"all\": \"https://www.dingtalk.com\"           },           \"status\": \"primary\",           \"id\": \"button_1646816888247\"         },         {           \"type\": \"button\",           \"label\": {             \"type\": \"text\",             \"text\": \"已处理\",             \"id\": \"text_1690462183067\"           },           \"actionType\": \"request\",           \"status\": \"primary\",           \"id\": \"button_1646816888257\"         }       ],       \"id\": \"action_1690462183038\"     }   ] }")
                .setSendOptions(sendOptions)
                .setPullStrategy(false);
        try {
            client.sendRobotInteractiveCardWithOptions(sendRobotInteractiveCardRequest, sendRobotInteractiveCardHeaders, new RuntimeOptions());
        } catch (TeaException err) {
            if (!com.aliyun.teautil.Common.empty(err.code) && !com.aliyun.teautil.Common.empty(err.message)) {
                // err 中含有 code 和 message 属性，可帮助开发定位问题
                System.out.println(err.message);
            }

        } catch (Exception _err) {
            TeaException err = new TeaException(_err.getMessage(), _err);
            if (!com.aliyun.teautil.Common.empty(err.code) && !com.aliyun.teautil.Common.empty(err.message)) {
                // err 中含有 code 和 message 属性，可帮助开发定位问题
                System.out.println(err.message);
            }

        }
    }

    private void robotSendDing() throws Exception {
        com.aliyun.dingtalkrobot_1_0.Client client = DingSimulateService.createClient();
        com.aliyun.dingtalkrobot_1_0.models.RobotSendDingHeaders robotSendDingHeaders = new com.aliyun.dingtalkrobot_1_0.models.RobotSendDingHeaders();
        robotSendDingHeaders.xAcsDingtalkAccessToken = accessToken;
        com.aliyun.dingtalkrobot_1_0.models.RobotSendDingRequest robotSendDingRequest = new com.aliyun.dingtalkrobot_1_0.models.RobotSendDingRequest()
                .setRobotCode(robotCode)
                .setReceiverUserIdList(receiverUserIdList)
                .setContent("生产一部：设备温度达到80度")
                .setRemindType(1);
        try {
            client.robotSendDingWithOptions(robotSendDingRequest, robotSendDingHeaders, new com.aliyun.teautil.models.RuntimeOptions());
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

    public static com.aliyun.dingtalkim_1_0.Client createRobotClient() throws Exception {
        Config config = new Config();
        config.protocol = "https";
        config.regionId = "central";
        return new com.aliyun.dingtalkim_1_0.Client(config);
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

    public void dingSimulate(String cid) throws Exception {
        accessToken = getTokenResponse().getAccessToken();
        sendChatMessage(cid);
        try {
            Thread.sleep(10000); // 10000毫秒 = 10秒
            // 发送Ding消息
            robotSendDing();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
