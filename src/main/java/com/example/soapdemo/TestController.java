package com.example.soapdemo;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.namespace.QName;
import java.util.HashMap;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Classname TestController
 * @Description TODO
 * @Version 1.0.0
 * @Date 2023/2/8 14:59
 * @Author by WuChaoWen
 */
@RestController
@RequestMapping("/soap")
public class TestController {

    @GetMapping("/doSoap")
    public Result<Object> doSoap(String url, String xml) {
        HashMap hashMap = XmlExtractor(xml);
        String methodName = hashMap.get("methodName").toString();
        String DWBH = hashMap.get("DWBH").toString();
        String KSQJ = hashMap.get("KSQJ").toString();
        String JSQJ = hashMap.get("JSQJ").toString();
        String KJND = hashMap.get("KJND").toString();
        Vector vector;
        String soapaction = "http://tempuri.org/";   //域名，这是在server定义的
        Service service = new Service();
        try {
            Call call = (Call) service.createCall();
            call.setTargetEndpointAddress(url);
            call.setOperationName(new QName(soapaction, methodName)); //设置要调用哪个方法
            call.addParameter(new QName(soapaction, "DWBH"), //设置要传递的参数
                    org.apache.axis.encoding.XMLType.XSD_STRING,
                    javax.xml.rpc.ParameterMode.IN);
            call.addParameter(new QName(soapaction, "KSQJ"), //设置要传递的参数
                    org.apache.axis.encoding.XMLType.XSD_STRING,
                    javax.xml.rpc.ParameterMode.IN);
            call.addParameter(new QName(soapaction, "JSQJ"), //设置要传递的参数
                    org.apache.axis.encoding.XMLType.XSD_STRING,
                    javax.xml.rpc.ParameterMode.IN);
            call.addParameter(new QName(soapaction, "KJND"), //设置要传递的参数
                    org.apache.axis.encoding.XMLType.XSD_STRING,
                    javax.xml.rpc.ParameterMode.IN);
            call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);//（标准的类型）
            call.setReturnType(new QName(soapaction, methodName), Vector.class); //要返回的数据类型（自定义类型）
            call.setUseSOAPAction(true);
            call.setSOAPActionURI(soapaction + methodName);
            vector = (Vector) call.invoke(new Object[]{DWBH, KSQJ, JSQJ, KJND});//调用方法并传递参数
            System.out.println(vector);
        } catch (Exception ex) {
            ex.printStackTrace();
            return Result.error(ex.getMessage());
        }
        return Result.success(vector);
    }

    public static HashMap XmlExtractor(String xml) {
        String methodName = "";
        String DWBH = "";
        String KSQJ = "";
        String JSQJ = "";
        String KJND = "";

        Pattern methodPattern = Pattern.compile("<tem:(.*?)>");
        Matcher methodMatcher = methodPattern.matcher(xml);
        if (methodMatcher.find()) {
            methodName = methodMatcher.group(1);
        }

        Pattern paramPattern = Pattern.compile("<tem:DWBH>(.*?)</tem:DWBH>.*" +
                "<tem:KSQJ>(.*?)</tem:KSQJ>.*<tem:JSQJ>(.*?)</tem:JSQJ>.*" +
                "<tem:KJND>(.*?)</tem:KJND>", Pattern.DOTALL);
        Matcher paramMatcher = paramPattern.matcher(xml);
        if (paramMatcher.find()) {
            DWBH = paramMatcher.group(1);
            KSQJ = paramMatcher.group(2);
            JSQJ = paramMatcher.group(3);
            KJND = paramMatcher.group(4);
        }
        HashMap map = new HashMap<>();
        map.put("methodName", methodName);
        map.put("DWBH", DWBH);
        map.put("KSQJ", KSQJ);
        map.put("JSQJ", JSQJ);
        map.put("KJND", KJND);
        return map;
    }


}
