package com.example.admin.data_analysis_tool.Utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import jxl.Sheet;
import jxl.Workbook;

import static android.content.ContentValues.TAG;

public class Excelutils {

    private String line_data = "";
    private File file = null;
    private String filename = "";

    public String getXlsData(String xlsName, int index, Context context) {

        try {
            Workbook workbook = Workbook.getWorkbook(new File(Environment.getExternalStorageDirectory()+"/"+xlsName));
            Sheet sheet = workbook.getSheet(index);
            filename = Environment.getExternalStorageDirectory() + "/" + "analysisdata" + System.currentTimeMillis() +".txt";
            file = new File(filename);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter out = new BufferedWriter(fileWriter);

            int sheetNum = workbook.getNumberOfSheets();
            int sheetRows = sheet.getRows();
            int sheetColumns = sheet.getColumns();

            Log.d(TAG, "the num of sheets is " + sheetNum);
            Log.d(TAG, "the name of sheet is  " + sheet.getName());
            Log.d(TAG, "total rows is 行=" + sheetRows);
            Log.d(TAG, "total cols is 列=" + sheetColumns);

            for (int i = 0; i < sheetRows; i++) {
                line_data = "";
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
                        line_data += j + rank + " ";
                        System.out.print(j + rank + " ");
                    } catch (Exception e) {}
                }
                if (line_data.length() != 0) {
                    line_data += "\n";
                    out.write(line_data);
                }
            }
            out.close();
            workbook.close();
        } catch (Exception e) {
            Log.e(TAG, "read error=" + e, e);
        }

        return filename;
    }

}
