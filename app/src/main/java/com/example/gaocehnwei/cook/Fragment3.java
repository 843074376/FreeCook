package com.example.gaocehnwei.cook;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Alienware on 2016/12/13.
 */

public class Fragment3 extends Fragment {
    private View view ;
    private Button btn,btn_2;
    private TextView tv;
    private ListView rl;
    private EditText et;
    public String []ss;
    private String content;
    private boolean flag;
    public SimpleAdapter simpleAdapter;
    public List<Map<String, Object>> list = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment3, container, false);
        Log.d("state","fragment3 onCreateView");
        return view;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();
        btn = (Button)view.findViewById(R.id.search_bu);
        rl = (ListView) view.findViewById(R.id.result_list);
        et = (EditText)view.findViewById(R.id.search);
        tv = (TextView) view.findViewById(R.id.question);
        btn_2 = (Button)view.findViewById(R.id.jump);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl.setVisibility(view.VISIBLE);
                tv.setVisibility(view.GONE);
                btn_2.setVisibility(view.GONE);
                Map<Integer,String> map=searchFile(".txt");
                content = et.getText().toString();
                flag = false;
                list = new ArrayList<>();
                ss=new String[map.size()];
                Map<String, Object> temp = new LinkedHashMap<>();    //标题
                for(int i=0;i<map.size();i++)
                {
                    ss[i]=map.get(i);
                    Log.d("service",ss[i]);
                    if(ss[i].contains(content))
                    {
                        temp.put("name", ss[i]);
                        Log.d("service",content);
                        list.add(temp);
                        flag = true;
                        break;
                    }
                }
                if(!flag) {
                    rl.setVisibility(view.GONE);
                    tv.setVisibility(view.VISIBLE);
                    btn_2.setVisibility(view.VISIBLE);
                    tv.setText("库中没有找到相应书目,是否需要在web中进行搜索");
                }
                simpleAdapter=new SimpleAdapter(getActivity(),list,R.layout.search_result_item,new String[]{"name"},new int[]{R.id.book_name});
                rl.setAdapter(simpleAdapter);//调用适配器，更新界面
            }
        });

    }
    private Map<Integer,String> searchFile(String keyword) {
        String result = "";
        String f="/data/data/com.example.gaocehnwei.cook/";
        Log.d("size",f.length()+"");
        Map<Integer, String> temp = new LinkedHashMap<>();
        //File aa=new File("/");
        try{
            File aa=new File(f);
            int i=0;
            if(aa.isDirectory())
            {
                File[] files = aa.listFiles();
                for (File file : files) {
                    if (file.getName().indexOf(keyword) >= 0) {
                        result += file.getPath() + "\n";
                        String in=file.getPath().toString().substring(f.length(),file.getPath().toString().length());
                        Log.d("file",in);
                        temp.put(i,in);
                        i++;
                    }
                }
                if (result.equals("")){
                    result = "找不到文件!!";
                }
            }


        }
        catch (Exception e)
        {
            Log.d("tag",e.toString());
        }
        return temp;
    }
}