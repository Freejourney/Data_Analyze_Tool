package com.example.admin.data_analysis_tool;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TestActivity extends Activity {

    private TextView tv_content;
    private Button btn_test;
    Recognition recognition = new Recognition();

    static List<List<String>> record = new ArrayList<List<String>>();// 数据记录表

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        tv_content = findViewById(R.id.tv_content);
        btn_test = findViewById(R.id.btn_test);
//        recognition.getAuth();
        btn_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Apriori apriori = new Apriori();
                try {
                    test(apriori);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("out", recognition.getAuth());
                    }
                }).start();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            recognition.requestPic(TestActivity.this.getAssets().getLocales() + "simple.txt", recognition.getAuth());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
    }

    public void test(Apriori apriori) throws IOException {
        // TODO Auto-generated method stub
        apriori.record = apriori.getRecord(TestActivity.this.getAssets().open("simple.txt"));// 获取原始数据记录
        List<List<String>> cItemset = apriori.findFirstCandidate();// 获取第一次的备选集
        List<List<String>> lItemset = apriori.getSupportedItemset(cItemset);// 获取备选集cItemset满足支持的集合

        while (apriori.endTag != true) {// 只要能继续挖掘
            List<List<String>> ckItemset = apriori.getNextCandidate(lItemset);// 获取第下一次的备选集
            List<List<String>> lkItemset = apriori.getSupportedItemset(ckItemset);// 获取备选集cItemset满足支持的集合
            apriori.getConfidencedItemset(lkItemset, lItemset, apriori.dkCountMap, apriori.dCountMap);// 获取备选集cItemset满足置信度的集合
            if (apriori.confItemset.size() != 0)// 满足置信度的集合不为空
                apriori.printConfItemset(apriori.confItemset);// 打印满足置信度的集合
            apriori.confItemset.clear();// 清空置信度的集合
            cItemset = ckItemset;// 保存数据，为下次循环迭代准备
            lItemset = lkItemset;
            apriori.dCountMap.clear();
            apriori.dCountMap.putAll(apriori.dkCountMap);
            ;
        }

    }

}
