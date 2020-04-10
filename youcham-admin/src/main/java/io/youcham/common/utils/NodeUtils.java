package io.youcham.common.utils;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import net.sf.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * 短信发送查询工具类
 *
 * @author XiaWenFeng
 * @email 2410824038@qq.com
 * @description
 * @date 2019-10-25 15:35
 */
public class NodeUtils {

    /**
     * 短信单发
     *
     * @param phone        手机号
     * @param templateCode 模板code
     * @param map          需要传递的参数
     * @return void
     * @author XiaWenFeng
     * @email 2410824038@qq.com
     * @date 2019-10-25 15:50
     */
    public void SendSms(String phone, String templateCode, Map<String, String> map) {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", PropertyUtils.getPropertyValueByKeyToPathConfig("access_key_id"), PropertyUtils.getPropertyValueByKeyToPathConfig("access_key_secret"));
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");//SendSms为发送短信接口
        request.putQueryParameter("RegionId", "cn-hangzhou");//服务器地区
        request.putQueryParameter("PhoneNumbers", phone);//需要发送的手机号
        request.putQueryParameter("SignName", "九江三同直航");//短信签名名称
        request.putQueryParameter("TemplateCode", templateCode);//短信模板
        request.putQueryParameter("TemplateParam", JSONObject.fromObject(map).toString());//短信模板变量对应的实际值，JSON格式。
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

    /**
     * 短信群发
     *
     * @param phone        手机号集合
     * @param templateCode 模板code
     * @param map          需要传递的参数
     * @return void
     * @author XiaWenFeng
     * @email 2410824038@qq.com
     * @date 2019-10-25 15:50
     */
    public void SendBatchSms(List<String> phone, String templateCode, Map<String, String> map) {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", PropertyUtils.getPropertyValueByKeyToPathConfig("access_key_id"), PropertyUtils.getPropertyValueByKeyToPathConfig("access_key_secret"));
        IAcsClient client = new DefaultAcsClient(profile);

        String phones = "";

        String SignNameJsons = "";
        for (String str : phone) {
            phones += str + ",";
            SignNameJsons += "九江三同直航" + ",";
        }
        phones = "[" + phones.substring(0, phones.length() - 1) + "]";
        SignNameJsons = "[" + SignNameJsons.substring(0, SignNameJsons.length() - 1) + "]";


        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendBatchSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumberJson", phones);
        request.putQueryParameter("SignNameJson", SignNameJsons);
        request.putQueryParameter("TemplateCode", templateCode);
        request.putQueryParameter("TemplateParamJson", JSONObject.fromObject(map).toString());
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

}
