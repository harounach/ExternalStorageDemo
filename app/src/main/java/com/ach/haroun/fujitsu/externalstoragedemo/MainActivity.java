package com.ach.haroun.fujitsu.externalstoragedemo;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    TextView txtResult;
    public static final String DIR_NAME = "ExternalStorageDir";
    public static final String TXT_FILE_NAME = "externalstoragefile.txt";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.et_id);
        txtResult = (TextView) findViewById(R.id.txt_result_id);
        txtResult.setVisibility(View.GONE);
    }
    public void writeExternalStorage(View view) {
        String state = "";
        String message = "";
        state = Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(state)){
            File root = Environment.getExternalStorageDirectory();
            File directory = new File(root.getAbsolutePath()+"/"+DIR_NAME);
            if(!directory.exists()){
                directory.mkdir();
            }
            File txtFile = new File(directory, TXT_FILE_NAME);
            message = editText.getText().toString();
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(txtFile);
                fileOutputStream.write(message.getBytes());
                fileOutputStream.close();
                editText.setText("");
                Toast.makeText(getApplicationContext(), "Message Saved", Toast.LENGTH_LONG).show();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else{
            Toast.makeText(getApplicationContext(), "SD Card Not Found", Toast.LENGTH_LONG).show();
        }

    }
    public void readExternalStorage(View view) {
        File root = Environment.getExternalStorageDirectory();
        File directory = new File(root.getAbsolutePath()+"/"+DIR_NAME);
        File txtFile = new File(directory, TXT_FILE_NAME);
        String message = "";
        try {
            FileInputStream fileInputStream = new FileInputStream(txtFile);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer =new StringBuffer();
            while((message=bufferedReader.readLine())!=null){
                stringBuffer.append(message+"\n");
            }
            txtResult.setText(stringBuffer.toString());
            txtResult.setVisibility(View.VISIBLE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
