package com.example.admin.data_analysis_tool.Activity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.admin.data_analysis_tool.Apriori;
import com.example.admin.data_analysis_tool.Utils.ImagesUtil;
import com.example.admin.data_analysis_tool.R;
import com.example.admin.data_analysis_tool.baidu_table_rec;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class OptionActivity extends Activity implements View.OnClickListener{

    private Button btn_test, btn_camera, btn_file;
    private baidu_table_rec btr = new baidu_table_rec();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_test);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();

        btn_test = findViewById(R.id.btn_test);
        btn_camera = findViewById(R.id.btn_camera);
        btn_file = findViewById(R.id.btn_file);
        btn_test.setOnClickListener(this);
        btn_camera.setOnClickListener(this);
        btn_file.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            try {
                Uri uri = data.getData();
                ContentResolver cr = this.getContentResolver();
                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                final InputStream stream = new ByteArrayInputStream(baos.toByteArray());

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            startRecognize(stream);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_test:
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
                break;
            case R.id.btn_camera:

                break;
            case  R.id.btn_file:

                break;
        }
    }

    public void startRecognize(InputStream stream) throws IOException {
        System.out.println("*********************百度识别**********************");
        String result_r = btr.main(input2byte(stream), OptionActivity.this);
        System.out.println("*********************RESULT**********************");
        System.out.println(result_r);
    }

    public byte[] input2byte(InputStream inStream)
            throws IOException {
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        byte[] buff = new byte[100];
        int rc = 0;
        while ((rc = inStream.read(buff, 0, 100)) > 0) {
            swapStream.write(buff, 0, rc);
        }
        byte[] in2b = swapStream.toByteArray();
        return in2b;
    }
}
