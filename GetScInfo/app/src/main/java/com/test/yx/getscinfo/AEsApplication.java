package com.test.yx.getscinfo;

import android.app.Application;

/*
 *	CR(R) 2019, GetScInfo
 *	文件名:	
 *	摘  要: 
 *
 *	当前版本:	1.00
 *	作    者:	developer.yx (Administrator)
 *	完成日期:	2019-07-??
 *	备    注:	 
 *				
 */
/*	-----------------------------------------------------------------------------------------------	*/
public class AEsApplication extends Application {
    //<application android:name=".AEsApplication">
    //设置全局变量
    private static  CData_Config _dataConfig = null;      //记录config项
    // >>end 全局变量

    //设置读取方式
    public static  CData_Config get_dataConfig(){ return _dataConfig; }
    // >>end 读取方式

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        _dataConfig = new CData_Config();
    }
}
