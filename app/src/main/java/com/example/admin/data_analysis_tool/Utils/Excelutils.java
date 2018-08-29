package com.example.admin.data_analysis_tool.Utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.util.Log;

import com.example.admin.data_analysis_tool.Student;

import java.util.ArrayList;

import jxl.Sheet;
import jxl.Workbook;

import static android.content.ContentValues.TAG;

public class Excelutils {

    public ArrayList<Student> getXlsData(String xlsName, int index, Context context) {
        ArrayList<Student> countryList = new ArrayList<Student>();
        AssetManager assetManager = context.getAssets();

        try {
            Workbook workbook = Workbook.getWorkbook(assetManager.open(xlsName));
            Sheet sheet = workbook.getSheet(index);

            int sheetNum = workbook.getNumberOfSheets();
            int sheetRows = sheet.getRows();
            int sheetColumns = sheet.getColumns();

            Log.d(TAG, "the num of sheets is " + sheetNum);
            Log.d(TAG, "the name of sheet is  " + sheet.getName());
            Log.d(TAG, "total rows is 行=" + sheetRows);
            Log.d(TAG, "total cols is 列=" + sheetColumns);

            for (int i = 0; i < sheetRows; i++) {
                for (int j = 0; j < sheetColumns; j++) {
                    String temp = sheet.getCell(j, i).getContents();
                    try {
                        int grade = Integer.parseInt(temp);
                        String rank = "";
                        if (grade >= 90) {
                            rank = "A";
                        } else if (grade >= 85) {
                            rank = "B";
                        } else if (grade >= 80) {
                            rank = "C";
                        } else if (grade >= 75) {
                            rank = "D";
                        } else if (grade >= 70) {
                            rank = "E";
                        } else if (grade >= 65) {
                            rank = "F";
                        } else if (grade >= 60) {
                            rank = "G";
                        } else {
                            rank = "H";
                        }
                        System.out.print(i + rank + " ");
                    } catch (Exception e) {}
                }
            }

            workbook.close();

        } catch (Exception e) {
            Log.e(TAG, "read error=" + e, e);
        }

        return countryList;
    }


    //在异步方法中 调用
//    private class ExcelDataLoader extends AsyncTask<String, Void, ArrayList<Student>> {
//
//        @Override
//        protected void onPreExecute() {
//            progressDialog.setMessage("加载中,请稍后......");
//            progressDialog.setCanceledOnTouchOutside(false);
//            progressDialog.show();
//        }
//
//        @Override
//        protected ArrayList<Student> doInBackground(String... params) {
//            return getXlsData(params[0], 0);
//        }
//
//        @Override
//        protected void onPostExecute(ArrayList<Student> countryModels) {
//            if (progressDialog.isShowing()) {
//                progressDialog.dismiss();
//            }
//
//            if(countryModels != null && countryModels.size()>0){
//                //存在数据
//                sortByName(countryModels);
//                setupData(countryModels);
//            }else {
//                //加载失败
//
//
//            }
//
//        }
//    }
}
