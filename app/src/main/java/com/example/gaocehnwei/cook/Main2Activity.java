package com.example.gaocehnwei.cook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class Main2Activity extends AppCompatActivity {
    private int resultCode = 1;
    private ScrollView scrollView;
    private Button back;
    public Bundle bundle=new Bundle();
    public int page;
    @Override
    protected  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        scrollView=(ScrollView)findViewById(R.id.scrollView);
        final TextView text=(TextView)findViewById(R.id.text);
        back=(Button)findViewById(R.id.back);
        Intent intent=getIntent();
        bundle=new Bundle();
        bundle=intent.getExtras();
        String url=bundle.getString("url_before")+bundle.getString("url");

        //final Button aa=(Button)findViewById(R.id.aa);

        Toast.makeText(Main2Activity.this, "url"+url, Toast.LENGTH_SHORT).show();
        try {
            // File urlFile = new File("/data/test2.txt");
            File urlFile = new File(url);
            InputStreamReader isr = new InputStreamReader(new FileInputStream(urlFile), "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            String str = "";
            String mimeTypeLine = null ;
            while ((mimeTypeLine = br.readLine()) != null) {
                str = str+mimeTypeLine+"\n";
            }
            text.setText(str);
            int c=10000;
            while(c-->0);

        } catch (Exception e) {
            e.printStackTrace();
        }
        page=Integer.parseInt(bundle.getString("page"));
        Toast.makeText(Main2Activity.this, "newpage"+page, Toast.LENGTH_SHORT).show();
        scrollView.post(new Runnable() {     //不能用scrollTo,因为界面没有初始化完成，点击按钮跳转到指定位置就可以用
            @Override                           //scrollView.post实际上会在activity加载完成后执行。
            public void run() {
                scrollView.scrollTo(0, page);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public  void onClick(View v) {
                Intent mIntent = new Intent(Main2Activity.this,MainActivity.class);


                int a=scrollView.getScrollY();
                bundle.remove("page");
                bundle.putString("page",a+"");


                mIntent.putExtras(bundle);
                Toast.makeText(Main2Activity.this, "newpage"+a, Toast.LENGTH_SHORT).show();
               startActivity(mIntent);
            }
        });


    }

}
