package com.demo.service;

import com.aliyun.tea.TeaException;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiChatCreateRequest;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.request.OapiImChatScenegroupCreateRequest;
import com.dingtalk.api.response.OapiChatCreateResponse;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.dingtalk.api.response.OapiImChatScenegroupCreateResponse;
import com.dingtalk.groupapp.robot.service.RobotOpenService;
import com.dingtalk.groupapp.robot.vos.CreateRobotGroupInstanceVO;
import com.taobao.api.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author shuyuan
 * @Date 2023/7/20 17:38
 * @Description
 **/

@Service
@Configuration
@PropertySource("classpath:application")
public class SubscribeService {
    @Value("${dingtalk.service.appKey}")
    private String appKey;

    @Value("${dingtalk.service.appSecret}")
    private String appSecret;

    @Value("${dingtalk.service.robotCode}")
    private String robotCode;

    @Value("${dingtalk.service.owner}")
    private String owner;

    @Value("${dingtalk.service.userIdList}")
    private String userIdList;

    @Value("${dingtalk.service.receiverUserIdList}")
    private List<String> receiverUserIdList;

    private String accessToken;

    public OapiImChatScenegroupCreateResponse createChat() throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/im/chat/scenegroup/create");
        OapiImChatScenegroupCreateRequest req = new OapiImChatScenegroupCreateRequest();
        req.setTitle("发钉演示群");
        req.setTemplateId("15ef7cbc-4065-445b-b2f1-8b748fdaa63e");
        req.setOwnerUserId(owner);
        req.setUserIds(userIdList);
        req.setSubadminIds(userIdList);
        req.setUuid("axcf23da");
        req.setIcon("@lALPDfmVclIBu7LNAeDNAeA");
        req.setMentionAllAuthority(0L);
        req.setShowHistoryType(0L);
        req.setValidationType(0L);
        req.setSearchable(0L);
        req.setChatBannedType(0L);
        req.setManagementType(0L);
        req.setOnlyAdminCanDing(0L);
        req.setAllMembersCanCreateMcsConf(1L);
        req.setAllMembersCanCreateCalendar(0L);
        req.setGroupEmailDisabled(0L);
        req.setOnlyAdminCanSetMsgTop(0L);
        req.setAddFriendForbidden(0L);
        req.setGroupLiveSwitch(1L);
        req.setMembersToAdminChat(0L);
        OapiImChatScenegroupCreateResponse rsp = client.execute(req, accessToken);
        return rsp;
    }

    public String subcribe() throws Exception {
        getTokenResponse();
        OapiImChatScenegroupCreateResponse oapiImChatScenegroupCreateResponse = createChat();
        String openConversationId = oapiImChatScenegroupCreateResponse.getResult().getOpenConversationId();
        return openConversationId;
    }

    private void getTokenResponse() throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/gettoken");
        OapiGettokenRequest request = new OapiGettokenRequest();
        request.setAppkey(appKey);
        request.setAppsecret(appSecret);
        request.setHttpMethod("GET");
        OapiGettokenResponse response = client.execute(request);
        accessToken = response.getAccessToken();
    }
}
