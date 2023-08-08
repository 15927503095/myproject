package com.example.writetext.controller;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.example.writetext.entity.MeasApiResultEntity;
import com.example.writetext.entity.MeasBean;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/write")
public class WriteToTextController {

    HttpClient httpClient = new DefaultHttpClient();

    @GetMapping("/text")
    public void test() {
        MeasApiResultEntity measApiResultEntity = invokeMeasApiAndParse();
        System.out.println(measApiResultEntity);
    }


    private static String PARAMS = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
            "\t<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
            "\t<soap:Body>\n" +
            "\t\t<GetMeasConfig xmlns=\"http://tempuri.org/\" />\n" +
            "\t</soap:Body>\n" +
            "</soap:Envelope>";

    private static StringEntity entity = new StringEntity(PARAMS, "UTF-8");

    public MeasApiResultEntity invokeMeasApiAndParse() {

        String measUrl = "http://10.110.20.170:8080/ReadMeasuresConfig.asmx/GetMeasConfig";
        System.err.println("开始调用api:" + measUrl);
        HttpPost httpPost = new HttpPost(measUrl);
        long start = System.currentTimeMillis();
        try {
            httpPost.setHeader("Content-Type", "text/xml");
            httpPost.setEntity(entity);
            HttpResponse response = httpClient.execute(httpPost);
            System.err.println("调用结束,耗时:" + (System.currentTimeMillis() - start));
            HttpEntity entity = response.getEntity();
            if (null != entity) {
                InputStream in = entity.getContent();// 将返回的内容流入输入流内
//                String str = new String(ByteStreams.toByteArray(in));
//                System.err.println("API result:" + str);


                // 创建一个Document解析工厂
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                // 将输入流解析为Document
                Document document = builder.parse(in);// 用输入流实例化Document

                Element rootElement = document.getDocumentElement();
                NodeList codeNode = rootElement.getElementsByTagName("GetMeasConfigResult");
                String text = codeNode.item(0).getTextContent();
                System.err.println("API RESULT Length:" + text.length());
                return parseJson2MeasApiResult(text);

            }
            throw new Exception("return entity is null.");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private MeasApiResultEntity parseJson2MeasApiResult(String text) throws IOException {
        File writeFile=new File("C:\\iodemo\\File\\");
        FileWriter writer1 = new FileWriter(new File(writeFile.getPath()));
        writer1.write(text);
        writer1.flush();

        Set<String> set = new HashSet<>();
        JSONObject jsonObject = JSON.parseObject(text);
        MeasApiResultEntity result = new MeasApiResultEntity();
        result.setSuccess(jsonObject.getBooleanValue("Success"));
        result.setStatusCode(jsonObject.getIntValue("StatusCode"));
        result.setErrorMessage(jsonObject.getString("ErrorMessage"));
        result.setTotalCounts(jsonObject.getIntValue("TotalCounts"));

        List<MeasBean> measBeans = new ArrayList<>();
        JSONArray ja = jsonObject.getJSONArray("ResultList");

        for (int i = 0; i < ja.size(); i++) {
            JSONObject jo = ja.getJSONObject(i);
            MeasBean measBean = new MeasBean();
            measBean.setChannelName(jo.getString("ChannelName"));
            measBean.setDeviceName(jo.getString("DeviceName"));
            measBean.setMeasId(jo.getIntValue("MeasId"));
            measBean.setMeasName(jo.getString("MeasName"));
            measBean.setCetDeviceId(jo.getIntValue("DeviceId"));
            measBean.setChannelId(jo.getIntValue("ChannelID"));
            String line = measBean.getChannelName() + "\t" + measBean.getDeviceName() + "\t" + measBean.getCetDeviceId();
            set.add(line);
            measBeans.add(measBean);
        }
        result.setResultList(measBeans);


        FileWriter writer = new FileWriter(new File(writeFile.getPath()));
        set.stream().forEach(line -> {
            try {
                writer.write(line + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        writer.flush();
        return result;
    }
}
