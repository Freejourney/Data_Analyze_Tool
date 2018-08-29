package com.example.admin.data_analysis_tool;

import org.json.JSONArray;
import org.json.JSONObject;

public class Jsonparsetest {


public static void main(String[] args) {

    String s = "{\"error\":0,\"status\":\"success\",\"results\":[{\"currentCity\":\"青岛\",\"index\":[{\"title\":\"穿衣\",\"zs\":\"较冷\",\"tipt\":\"穿衣指数\",\"des\":\"建议着厚外套加毛衣等服装。年老体弱者宜着大衣、呢外套加羊毛衫。\"},{\"title\":\"紫外线强度\",\"zs\":\"最弱\",\"tipt\":\"紫外线强度指数\",\"des\":\"属弱紫外线辐射天气，无需特别防护。若长期在户外，建议涂擦SPF在8-12之间的防晒护肤品。\"}],}]}";
    String s2 = "{\"result\":{\"result_data\":\"http://bj.bcebos.com/v1/aip-web/form_ocr/358922FF0CC94C2F846920404537B838.xls?authorization=bce-auth-v1%2Ff86a2044998643b5abc89b59158bad6d%2F2018-08-27T11%3A54%3A57Z%2F86400%2F%2F40d86d7bbe3a1751d9552bbb256a7eb480f310faec3654f2783e737e49c85819\",\"ret_msg\":\"已完成\",\"request_id\":\"11326044_437279\",\"percent\":100,\"ret_code\":3},\"log_id\":153537091208610}";

    JSONObject s2JsonObject = new JSONObject(s2);
    System.out.println(s2JsonObject.toString());
    System.out.println("result : " + s2JsonObject.get("result"));
    System.out.println("log_id : " + s2JsonObject.get("log_id"));
    JSONObject s2jsonparse = (JSONObject) s2JsonObject.get("result");
    System.out.println("result_data : " + s2jsonparse.getString("result_data"));
    System.out.println("ret_msg : "+ s2jsonparse.getString("ret_msg"));
    System.out.println("ret_request_id : "+ s2jsonparse.getString("request_id"));
    System.out.println("ret_percent : "+ s2jsonparse.getInt("percent"));
    System.out.println("ret_code : "+ s2jsonparse.getInt("ret_code"));
    
    JSONObject jsonObject = new JSONObject(s);

    //提取出error为 0
    int error = jsonObject.getInt("error");
    System.out.println("error:" + error);

    //提取出status为 success
    String status = jsonObject.getString("status");
    System.out.println("status:" + status);    

    //注意：results中的内容带有中括号[]，所以要转化为JSONArray类型的对象
    JSONArray result = jsonObject.getJSONArray("results");

    for (int i = 0; i < result.length(); i++) {
        //提取出currentCity为 青岛
        String currentCity = result.getJSONObject(i).getString("currentCity");
        System.out.println("currentCity:" + currentCity);

        //注意：index中的内容带有中括号[]，所以要转化为JSONArray类型的对象
        JSONArray index = result.getJSONObject(i).getJSONArray("index");

        for (int j = 0; j < index.length(); j++) {
            String title = index.getJSONObject(j).getString("title");
            System.out.println("title:" + title);
            String zs = index.getJSONObject(j).getString("zs");
            System.out.println("zs:" + zs);
            String tipt = index.getJSONObject(j).getString("tipt");
            System.out.println("tipt:" + tipt);
            String des = index.getJSONObject(j).getString("des");
            System.out.println("des:" + des);

        }
    }



}



}