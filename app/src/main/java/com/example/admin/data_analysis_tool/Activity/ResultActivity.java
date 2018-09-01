package com.example.admin.data_analysis_tool.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.example.admin.data_analysis_tool.R;

public class ResultActivity extends Activity {

    private TextView tv_result;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        tv_result = findViewById(R.id.tv_result);
        tv_result.setMovementMethod(new ScrollingMovementMethod());
        Intent intent = getIntent();
        String result = intent.getStringExtra("result");
        System.out.print(result+"\n");
        tv_result.setText(result);
    }
}
