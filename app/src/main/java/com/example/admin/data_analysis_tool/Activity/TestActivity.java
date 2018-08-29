package com.example.admin.data_analysis_tool.Activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.admin.data_analysis_tool.Apriori;
import com.example.admin.data_analysis_tool.Utils.ImagesUtil;
import com.example.admin.data_analysis_tool.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestActivity extends Activity implements View.OnClickListener{

    private TextView tv_content;
    private Button btn_test, btn_camera, btn_file;
    private Bitmap middleBitmap;
    String image_name = "";

    static List<List<String>> record = new ArrayList<List<String>>();// 数据记录表

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        tv_content = findViewById(R.id.tv_content);
        btn_test = findViewById(R.id.btn_test);
        btn_camera = findViewById(R.id.btn_camera);
        btn_file = findViewById(R.id.btn_file);
//        recognition.getAuth();
        btn_test.setOnClickListener(this);
        btn_camera.setOnClickListener(this);
        btn_file.setOnClickListener(this);
//            @Override
//            public void onClick(View v) {
//                Apriori apriori = new Apriori();
//                try {
//                    test(apriori);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Log.i("out", recognition.getAuth());
//                    }
//                }).start();

//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            recognition.requestPic(TestActivity.this.getAssets().getLocales() + "simple.txt", recognition.getAuth());
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }).start();
//            }
//        });
    }


    private void startCut(Uri uri){
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", true);
//        intent.putExtra("aspectX", 1);
//        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
//                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, 3);
    }


    /**
     * 通过uri获取bitmap
     * @param uri
     */
    private void setMiddleBitmap(Uri uri){
        String []  filePathColumns = {MediaStore.Images.Media.DATA};
        Cursor c = this.getContentResolver().query(uri, filePathColumns, null, null, null);
        c.moveToFirst();
        int columIndex = c.getColumnIndex(filePathColumns[0]);
        String imagePath = c.getString(columIndex);
        middleBitmap = ImagesUtil.reduceResolution(imagePath);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            ImagesUtil.recycleBitmap(middleBitmap);
            if(requestCode == 1 && data != null){
                //相册之后
                //调用系统裁剪
                Uri selectedImage = data.getData();
                setMiddleBitmap(selectedImage);
                startCut(selectedImage);
            }else if(requestCode == 2){
                //拍照之后
                //默认需要裁剪
                Uri uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), image_name));
                startCut(uri);
            }else if(requestCode == 3){
                //裁剪之后
                Bundle bundle = data.getExtras();
                if(bundle != null){
                    Bitmap bitmap = bundle.getParcelable("data");
//                    startRecognize(bitmap);
                }
            }
        }
        if(resultCode == Activity.RESULT_CANCELED && requestCode == 3){
            //选择不裁剪
            if(middleBitmap != null && !middleBitmap.isRecycled()){
//                startRecognize(middleBitmap);
                middleBitmap = null;
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_test:
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 1);
                break;
            case R.id.btn_camera:
                Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                image_name = "imageency" + System.currentTimeMillis() +".jpg";
                intent1.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(), image_name)));
                startActivityForResult(intent1, 2);
                break;
            case  R.id.btn_file:

                break;
        }
    }
}
