package com.test.yx.getscinfo;

import android.content.ContentValues;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/*
 *	CR(R) 2019, GetScInfo
 *	文件名:	
 *	摘  要: 
 *			
 *	     		
 *
 *	当前版本:	1.00
 *	作    者:	developer.yx (Administrator)
 *	完成日期:	2019-07-??
 *	备    注:	 
 *				
 */
/*	-----------------------------------------------------------------------------------------------	*/
public class CData_ObjectUnit {

    public static String getIOS8601Timestamp(long time,int type){
        /*获取当前系统时间*/
        //long time = System.currentTimeMillis();
        /*时间戳转换成IOS8601字符串*/
        Date date = new Date(time);
        TimeZone tz = TimeZone.getTimeZone("Asia/Beijing");

        String format = "yyyy年MM月dd日HH时mm分ss秒";
        if (type == 1) {
            format ="yyyy年MM月dd日";
        }

        SimpleDateFormat df = new SimpleDateFormat(format, Locale.getDefault());
        df.setTimeZone(tz);
        return df.format(date);
    }

    public static String getCurrentTimes(long time, int type){
        Date curDate = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        return format.format(curDate);
    }

    //基础类   #####################################################################################
    public class CDataBase{
        public long id;
        public long valNum1;        //配用值
        public long valNum2;        //配用值
    }

    //对象类   #####################################################################################
    public class CConfig extends CDataBase {
        public String typeName;     //类型名称
        public Date  changeDate;    //修改日期
        public String valChar;
    }

    public class CGroup extends CDataBase {
        public String title;
        public String url;
        public String memo;

        public CGroup(){}
        public CGroup(String ntitle,String nmemo, String nurl, int nValNum1){
            id = 0;
            title = ntitle.trim().toLowerCase();
            url = nurl.trim().toLowerCase();
            memo = nmemo.trim().toLowerCase();
            valNum1 = nValNum1;     //用于排序
            valNum2 = 0;
        }

        public ContentValues toContenValues()
        {
            ContentValues contentValues = new ContentValues();
            //contentValues.put("groupid", this.id);
            contentValues.put("cgrouptitle", this.title);
            contentValues.put("cmemo", this.memo);
            contentValues.put("cgroupurl", this.url);
            contentValues.put("cint1", this.valNum1);
            contentValues.put("cint2", this.valNum2);
            return contentValues;
        }

    }

    public class CItem extends CDataBase {
        public long groupId;
        public String  date;
        public String title;
        public String url;
        public int check;
    }
    //end 对象类 -----------------------------------------------------------------------------------
}
