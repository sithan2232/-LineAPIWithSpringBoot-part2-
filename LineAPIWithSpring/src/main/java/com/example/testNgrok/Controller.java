package com.example.testNgrok;

import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.json.JSONObject;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;

@RestController
public class Controller {
    private static final String channelAccessToken = "{Ygj7sNVylHAkfEdncf7RkC2lxR+qDN49KYhpKuaWwaJOsT1WIoTUDKqYNf9wuddb5HsTd+KuVue9Yp09+ViSqClgU2tDpiOsVf32C44BnKMfHMsoeFd9jdvZYTcE8pn9JEm7FDl4yS34G/3Je/IwQgdB04t89/1O/w1cDnyilFU=}";
    @GetMapping(path = "/hello")
    public String getHelloWorld(){
        return "Hello World.";
    }

    @PostMapping(path = "/Webhook")
    public void getHelloWorld2(@RequestBody String object) throws JSONException{
        JSONObject obj = new JSONObject(object);
        JSONArray events=obj.getJSONArray("events");
        JSONObject eventsNum=events.getJSONObject(0);
        JSONObject message=eventsNum.getJSONObject("message");
        String text=message.getString("text");
        JSONObject source=eventsNum.getJSONObject("source");
        String userId=source.getString("userId");

//        ส่งไลน์กลับด้วยคําพูดเดิม
        RestTemplate restTemplate=new RestTemplate();
        HttpHeaders headers=new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("Authorization","Bearer "+channelAccessToken);

        JSONObject body=new JSONObject();
        JSONArray messageArray=new JSONArray();
        JSONObject messageObject=new JSONObject();
        messageObject.put("type","text");
        messageObject.put("text",text);
        messageArray.put(messageObject);
        body.put("to",userId);
        body.put("messages",messageArray);
        System.out.println();
        System.out.println(body);
        System.out.println();
        HttpEntity<String> requestEntity=new HttpEntity<String>(body.toString(),headers);
        restTemplate.postForObject("https://api.line.me/v2/bot/message/push", requestEntity, String.class);

        System.out.println();
        System.out.println(userId);
        System.out.println(text);
        System.out.println(object);
        System.out.println();
    }

}
