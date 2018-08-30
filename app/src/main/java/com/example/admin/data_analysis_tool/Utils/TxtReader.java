package com.example.admin.data_analysis_tool.Utils;

import android.net.Uri;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
//本程序用于读取矩阵型的记录数据，并转换为List<List<String>>格式数据
public class TxtReader {

    public List<List<String>> getRecord(String filepath) {
        List<List<String>> record = new ArrayList<List<String>>();

        try {
            String encoding = "UTF-8"; // 字符编码(可解决中文乱码问题 )
            InputStream stream = new FileInputStream(new File(filepath));
            InputStreamReader read = new InputStreamReader(stream, encoding);
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTXT = null;
            while ((lineTXT = bufferedReader.readLine()) != null) {//读一行文件
                String[] lineString = lineTXT.split(" ");
                List<String> lineList = new ArrayList<String>();
                for (int i = 0; i < lineString.length; i++) {
                        lineList.add(lineString[i]);
                }
                record.add(lineList);
            }
            read.close();
        } catch (Exception e) {
            System.out.println("读取文件内容操作出错");
            e.printStackTrace();
        }
        return record;
    }
}
