package com.qzi.cms.common.util;

import com.baidu.aip.ocr.AipOcr;
import org.json.JSONObject;

import java.util.HashMap;

public class CardNoUtils {



    public static final String APP_ID = "22827607";
    public static final String API_KEY = "HnASKtqVfnqZrBLb0GTGOqu8";
    public static final String SECRET_KEY = "cGikhKZSfWZbkRciDb0qEqDnj6t0et1L";

    public static String getCardNo(String url){



        // 初始化一个AipOcr
        AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);



        // 调用接口
        String path = url;


        HashMap<String, String> options = new HashMap<String, String>();
        options.put("detect_direction", "true");
        options.put("detect_risk", "false");



        String idCardSide = "front";  //back为反面

        String image = url;
        JSONObject res = client.idcard(image, idCardSide, options);
        System.out.println(res.toString(2));




        return res.toString();

    }
}
