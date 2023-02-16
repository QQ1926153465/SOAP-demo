package com.example.soapdemo;

import cn.hutool.core.util.XmlUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;

public class TestSoap {

    public static void main(String[] args) {
        String url = "http://58.51.104.25:9091/cwbase/service/customized/KMYEService.asmx?op=GetKmyeInfo";
        String body = "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:tem=\"http://tempuri.org/\">\n" +
                "    <soap:Header/>\n" +
                "    <soap:Body>\n" +
                "        <tem:GetKmyeInfo>\n" +
                "            <tem:param>\n" +
                "                <tem:DWBH>HBCTSTYYGS10</tem:DWBH>\n" +
                "                <tem:KSQJ>02</tem:KSQJ>\n" +
                "                <tem:JSQJ>02</tem:JSQJ>\n" +
                "                <tem:KJND>2022</tem:KJND>\n" +
                "            </tem:param>\n" +
                "        </tem:GetKmyeInfo>\n" +
                "    </soap:Body>\n" +
                "</soap:Envelope>";

        // 设置SOAPAction
        HttpRequest request = HttpRequest.post(url)
                .header("Content-Type", "text/xml;charset=UTF-8")
                .header("SOAPAction", "http://tempuri.org/GetKmyeInfo")
                .body(body);

        // 发送请求
        HttpResponse response = request.execute();

        // 处理响应
        if (response.isOk()) {
            String responseBody = response.body();
            System.out.println(XmlUtil.format(responseBody));
        } else {
            System.out.println("请求失败，响应状态码：" + response.getStatus());
        }
    }


}
