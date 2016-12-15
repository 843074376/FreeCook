package com.example.gaocehnwei.cook;

import android.content.DialogInterface;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static android.R.id.empty;

/**
 * Created by Alienware on 2016/12/13.
 */

public class Fragment1 extends Fragment {
    private View view ;
    public List<Map<String, Object>> data = new ArrayList<>();
    public myDb database;
    private ListView listView;
    private Button add;
    private TextView tv;
    private  Button play;
    final int requestCode=1;
    public String a=new String();
    public String b=new String();
    public String c=new String();
    public String url_before="/data/data/com.example.gaocehnwei.cook/";
    public SimpleAdapter simpleAdapter;
    public String []ss;
    public List<String> list = new ArrayList<String>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment1, container, false);
        Log.d("state","fragment1 onCreateView");
        return view;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d("state","fragment1 onCreate");
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        Log.d("state","fragment1 onStart()");
        super.onStart();
        tv = (TextView)view.findViewById(R.id.weather);
        add=(Button)view.findViewById(R.id.add);
        play =(Button)view.findViewById(R.id.play);
        database=new myDb(getActivity(),"ddbb.db",null,1);
        listView=(ListView)view.findViewById(R.id.listview);
        final Intent Intent1=getActivity().getIntent();
        Bundle mbundle=Intent1.getExtras();
        if(mbundle!=null)
        {
            String _id=mbundle.getString("_id");
            String url=mbundle.getString("url");
            String name=mbundle.getString("name");
            String page=mbundle.getString("page");
            database.updata(_id,name,url,page);
        }
        list_update(database,listView);
        //final Button search=(Button)findViewById(R.id.search);
        //final TextView file2=(TextView)findViewById(R.id.file);
        // spinner=(Spinner)findViewById(R.id.spinner) ;
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getActivity(),Main3Activity.class);
                startActivity(intent);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position>=1)
                {
                    Map<String, Object> map = (Map<String, Object>)listView.getItemAtPosition(position);
                    Intent intent_2 = new Intent("notification");
                    Intent intent_3 = new Intent("widget");
                    Bundle bundle_2=new Bundle();
                    bundle_2.putString("name",map.get("name").toString());
                    intent_2.putExtras(bundle_2);Log.d("service", "broadcast");
                    intent_3.putExtras(bundle_2);
                    try{
                        getActivity().sendBroadcast(intent_2);
                        getActivity().sendBroadcast(intent_3);
                        Log.d("err","siccess");
                    }catch(Exception e){
                        Log.d("err",e+"");
                    }


                    Bundle bundle=new Bundle();
                    bundle.putString("url_before",url_before);
                    bundle.putString("url",map.get("url").toString());
                    bundle.putString("_id",map.get("_id").toString());
                    bundle.putString("page",map.get("page").toString());
                    bundle.putString("name",map.get("name").toString());
                    Intent intent=new Intent(getActivity(),Main2Activity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    //getActivity().startActivityForResult(intent,requestCode);

                }
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if(position>=1)
                {
                    Map<String, Object> map = (Map<String, Object>)listView.getItemAtPosition(position);
                    Log.d("position",position+"");
                    LayoutInflater factory=LayoutInflater.from(getActivity());                     //设置dialog的view
                    View view1=factory.inflate(R.layout.dialog3,null);
                    AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                    builder.setView(view1);             //关联dialog和xml布局
                    final EditText dialog_name=(EditText)view1.findViewById(R.id.dialog_name);
                    //final TextView _id=(TextView) view1.findViewById(R.id._id);
                    final TextView url=(TextView)view1.findViewById(R.id.url);
                    final EditText page=(EditText)view1.findViewById(R.id.page);
                    final String Id=(String)map.get("_id");
                    dialog_name.setText((String)map.get("name"));
                    url.setText((String)map.get("url"));
                    page.setText((String)map.get("page"));
                    builder.setTitle("修改菜单").setMessage("登陆成功").setPositiveButton("update", //设置dialog事件
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    Log.d("tag","确认按钮被点击");
                                    a=new String();
                                    b=new String();
                                    c=new String();
                                    a=dialog_name.getText().toString();     //获取修改后的属性的值
                                    b=url.getText().toString();
                                    c=page.getText().toString();
                                    Log.d("tag",a);
                                    database.updata(Id,a,b,c);
                                    //=database.insert();           //更新database中的数据
                                    //Log.d("tagc",x+"");
                                    list_update(database,listView);     //遍历数据库更新listview显示。
                                }
                            }
                    ).setNegativeButton("delete",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(getActivity(), "取消按钮被点击", Toast.LENGTH_SHORT).show();
                                    database.delete(Id);
                                    list_update(database,listView);
                                }
                            }
                    ).create();
                    builder.show();
                    return true;
                }
                else
                    return true;
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<Integer,String>map=searchFile(".txt");
                list = new ArrayList<String>();
                ss=new String[map.size()];

                for(int i=0;i<map.size();i++)
                {
                    ss[i]=map.get(i);
                    list.add(map.get(i));
                    Log.d("map",map.get(i));
                }
                LayoutInflater factory=LayoutInflater.from(getActivity());                     //设置dialog的view
                View view1=factory.inflate(R.layout.dialog,null);
                final Spinner spinner=(Spinner)view1.findViewById(R.id.spinner);

                try{
                    Log.d("tag","x3");
                    spinner.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item, list));
                    Log.d("tag","x5");
                    spinner.setDropDownHorizontalOffset(40);
                    spinner.setDropDownVerticalOffset(100);

                }catch(Exception e)
                {
                    Log.d("tt",e+"");
                }
                AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                builder.setView(view1);             //关联dialog和xml布局
                final EditText dialog_name=(EditText)view1.findViewById(R.id.dialog_name);

                final EditText page=(EditText)view1.findViewById(R.id.page);
                b=new String();
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        b=spinner.getItemAtPosition(position).toString();
                        Log.d("it",b);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        b=spinner.getItemAtPosition(0).toString();
                    }
                });

                builder.setTitle("修改").setMessage("登陆成功").setPositiveButton("add", //设置dialog事件
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Log.d("tag","确认按钮被点击");
                                a=new String();
                                //b=new String();
                                c=new String();
                                a=dialog_name.getText().toString();     //获取修改后的属性的值
                                //b=url.getText().toString();
                                c=page.getText().toString();
                                if(b==null) b="no books";
                                Log.d("tag",a);
                                database.setstring(a,b,c);
                                long c=database.insert();           //更新database中的数据
                                Log.d("tagc",c+"");
                                list_update(database,listView);     //遍历数据库更新listview显示。
                            }
                        }
                ).setNegativeButton("cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getActivity(), "取消按钮被点击", Toast.LENGTH_SHORT).show();
                            }
                        }
                ).create();
                builder.show();


            }
        });



    }
    public void list_update(myDb database ,ListView listView){
        Cursor x=database.query();  //查询数据库的所有元素数据
        Log.d("count",x.getCount()+"");
        data=  new ArrayList<>();
        simpleAdapter=new SimpleAdapter(getActivity(),data,R.layout.recycle_item,new String[]{"_id","image","name","page","url"},new int[]{R.id._id,R.id.item_img,R.id.menu,R.id.page,R.id.url});
        Map<String, Object> temp = new LinkedHashMap<>();    //标题

        temp.put("image", R.mipmap.eat2);
        temp.put("name","菜名");
        temp.put("_id","_id");
        temp.put("page","进度");
        temp.put("url","路径");
        data.add(temp);   //对于每个item 的所有key-value用data的一个节点存储
        while (x.moveToNext())
        {
            temp = new LinkedHashMap<>();
            String name=x.getString(x.getColumnIndex( "name" ));
            String url=x.getString(x.getColumnIndex( "url" ));
            String page=x.getString(x.getColumnIndex( "page" ));
            String _id=x.getString(x.getColumnIndex( "_id" ));
            temp.put("image", R.mipmap.eat2);
            temp.put("name",name);
            temp.put("_id",_id);
            temp.put("page",page);
            temp.put("url",url);
            data.add(temp);   //对于每个item 的所有key-value用data的一个节点存储
        }
        x.close();
        listView.setAdapter(simpleAdapter);//调用适配器，更新界面
    }
    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent mIntent) {
        if(requestCode==1 && resultCode==1)
        {
            Bundle mbundle=mIntent.getExtras();
            String _id=mbundle.getString("_id");
            String url=mbundle.getString("url");
            String name=mbundle.getString("name");
            String page=mbundle.getString("page");
            database.updata(_id,name,url,page);
            list_update(database,listView);
        }
    }*/
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
