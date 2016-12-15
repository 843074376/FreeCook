package com.example.gaocehnwei.cook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Alienware on 2016/12/13.
 */

public class myDb extends SQLiteOpenHelper {
    public String Db_name;
    public String Tb_name="table1";
    public String  Name;
    public String  Url;
    public String  Page;
    //public SQLiteDatabase db;
    public String CREATE_TABLE= "CREATE TABLE table1 (_id INTEGER PRIMARY KEY autoincrement,name TEXT,url TEXT,page TEXT)";
    public myDb(Context context, String db_name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, db_name, factory, version);
        this.Db_name=db_name;
        this.Name="2";this.Url="2";this.Page="2";
        //db=getWritableDatabase();
        //db=getWritableDatabase();
    }
    public void setstring(String a,String b,String c)
    {
        this.Name=a;
        this.Url=b;
        this.Page=c;
    }
    public Cursor query(){
        try{
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor x = db.query( Tb_name ,
                    new String[] {  "_id","name" , "url" ,"page" },//select 的属性名字，where=null
                    null,  null,  null,  null,  null);
            //db.close();
            return x;   //返回一个指向元组的游标

        }catch (Exception e)
        {
            Log.d("error", e.getMessage());
            return  null;
        }
    }
    public void delete(String a)
    {
        try{
            SQLiteDatabase db=getWritableDatabase();
            String where="_id=?";
            db.delete(Tb_name,where,new String[] {a});//根据属性名的值组成的字符串数组作为where判断条件
            db.close();
        }catch(Exception e)
        {
            Log.d("error", e.getMessage());
        }
    }
    public   void updata(String _id,String a,String b,String c)
    {
        try{
            SQLiteDatabase db=getWritableDatabase();//获取对数据库的写权限
            ContentValues newValues =  new ContentValues();
            newValues.put("name", a);
            newValues.put("url", b);
            newValues.put("page", c);
            String where="_id=?";
            long i=db.update(Tb_name,newValues,where,new String[] {_id});//根据name的值查询到元组后用newValues更新
            Log.d("i",i+"");
            db.close();
        }catch(Exception e)
        {
            Log.d("error", e.getMessage());
        }
    }
    public long insert() {
        try{
            SQLiteDatabase db=getWritableDatabase();  //可写必可读，获取写权限
            ContentValues newValues =  new ContentValues();//key-value
            newValues.put("name", Name);
            newValues.put("url", Url);
            newValues.put("page", Page);
            if(Name.equals(""))
            {
                return 0;
            }
            else
            {
                long i=db.insert(Tb_name,  null, newValues);
                db.close();
                return i;
            }
        }catch(Exception e)
        {
            Log.d("error", e.getMessage());
            return  0;
        }
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        //this.db=db;
        // db=getWritableDatabase();
        Log.d("create", "success");
        try
        {
            db.execSQL(CREATE_TABLE);
        }catch (Exception w)
        {
            Log.d("error", w.getMessage());
        }

        Log.d("create", "defult");
    }
    @Override
    public String toString(){
        String result =  "";
        result+="name"+this.Name+",";
        result+="url"+this.Url+",";
        //result+="gift"+this.Gift+",";
        return result;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

