package com.example.admin.data_analysis_tool;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;

import com.example.admin.data_analysis_tool.Activity.ResultActivity;
import com.example.admin.data_analysis_tool.Utils.AuthService;
import com.example.admin.data_analysis_tool.Utils.Base64Util;
import com.example.admin.data_analysis_tool.Utils.Excelutils;
import com.example.admin.data_analysis_tool.Utils.HttpUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class baidu_table_rec {

    public static Context context;
	private String request_Url = "https://aip.baidubce.com/rest/2.0/solution/v1/form_ocr/request";
	private String AccessToken = "";
	private String filename = "";
	private String filepath = "";
	private Apriori apriori = new Apriori();
	private String result_r = "";
	private String sup = "";
	private String con = "";

	private void RequestPost(String url, String accessToken, byte[] stream) {
		try {
			String imgStr = Base64Util.encode(stream);
            final String params = URLEncoder.encode("image", "UTF-8") + "=" + URLEncoder.encode(imgStr, "UTF-8");
            String resultjson = HttpUtil.post(url, accessToken, params);
            System.out.println(resultjson);
            try{
            	Thread.sleep(30000);
            	}catch(Exception e){
            	System.exit(0);//退出程序
            	}
            JSONObject jsonObject = new JSONObject(resultjson);
            JSONArray resultarray = jsonObject.getJSONArray("result");
            String request_id = resultarray.getJSONObject(0).getString("request_id");
            String result_type = "json";
            String result_url = "https://aip.baidubce.com/rest/2.0/solution/v1/form_ocr/get_request_result";
            Map param = new HashMap<String, String>();
            param.put("request_id", request_id);
            param.put("result_type", result_type);
            String body = URLEncoder.encode("request_id", "UTF-8") + "=" + URLEncoder.encode(request_id, "UTF-8");
            final String result = HttpUtil.post(result_url, accessToken, body);
            JSONObject jsonRecResult = new JSONObject(result);
            JSONObject recdata = (JSONObject) jsonRecResult.get("result");
            final String result_data = recdata.getString("result_data");
            System.out.println("Return xls file url : " + recdata.getString("result_data"));

            filename = "analysisdata" + System.currentTimeMillis() +".xls";
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        downLoadFromUrl(result_data, filename, String.valueOf(Environment.getExternalStorageDirectory()));
                        Excelutils excelutils = new Excelutils();
                        filepath = excelutils.getXlsData(filename,0, context);

                        System.out.println("*********************APRIORI**********************");
                        result_r = apriori.main(filepath,context, sup, con);
                        Intent intent = new Intent(baidu_table_rec.context, ResultActivity.class);
                        intent.putExtra("result", result_r);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
//                        System.out.println(result_r);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }).start();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
            e.printStackTrace();
        }
    }
	


    /**
     * 
     * @param urlStr
     * @param fileName
     * @param savePath
     * @throws IOException
     */
    public void downLoadFromUrl(String urlStr, String fileName, String savePath) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        // 设置超时间为3秒
        conn.setConnectTimeout(3 * 1000);
        // 防止屏蔽程序抓取而返回403错误
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        // 得到输入流
        InputStream inputStream = conn.getInputStream();
        // 获取自己数组
        byte[] getData = readInputStream(inputStream);
        // 文件保存位置
        File saveDir = new File(savePath);
        if (!saveDir.exists()) {
            saveDir.mkdir();
        }
        File file = new File(saveDir + File.separator + fileName);

        FileOutputStream fos = new FileOutputStream(file);
        fos.write(getData);
        if (fos != null) {
            fos.close();
        }
        if (inputStream != null) {
            inputStream.close();
        }

        System.out.println("info:" + url + " download success");

    }

    /**
     * 从输入流中获取字节数组
     * 
     * @param inputStream
     * @return
     * @throws IOException
     */
    public byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }

	public String main(byte[] stream, Context context, String sup, String con) {
        this.sup = sup;
        this.con = con;
        this.context = context;
		baidu_table_rec btr = new baidu_table_rec();
		AuthService authService = new AuthService();
		btr.AccessToken = authService.getAuth();
		btr.RequestPost(btr.request_Url, btr.AccessToken, stream);
//		System.out.println("Access Token : "+authService.getAuth());
        return result_r;
	}
}
