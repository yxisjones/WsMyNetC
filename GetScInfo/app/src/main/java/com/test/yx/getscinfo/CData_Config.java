package com.test.yx.getscinfo;

import android.content.Context;
import java.io.Serializable;

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
public class CData_Config extends CData_ObjectUnit implements Serializable {
    public Context context = null;

    //// TODO: [变+] --> Serializable -80080000001000001L
    private static final long serialVersionUID = -80080000001000001L;
    public static int onlyMe = 0;

    public long sleepTimeSpan = 1000 * 60;      //1分钟
    //public long sleepTimeSpan = 1000 * 3;
    public long loginTime = -1;
    public long checkSpan = -1;      //检查间隔时间
    public long counter_checkSpan = -1;
    public long checkTime = -1;      //下次检查时间
    public int isCheckTime = -1;     //是否执行过该次检查
    public long uiReflushSpan = -1;  //界面刷新时间
    public long counter_uiRefluashSpan = -1;

    public int isServerSqlWorking = 0;  //当server的线程进行sql操作时 将值设为1 ,actiovity判断该值，避免双重操作。
    public int isNews = 0;                //当判断出有新数据时设置

    public  int uiIsReflushNow = -1; //ui执行刷新时，线程需要进入循环等待

    private boolean isThreading_Netting = false;    //网络检查过程中
    public boolean isThreading_Netting() {  return isThreading_Netting;    }
    public void setThreading_Netting(boolean threading_Netting) { isThreading_Netting = threading_Netting;    }

    public void setConfig(String ntype, long ndate, String nvaluechar, int nvalueint, long nvaluelong){

        if (ntype.equals(context.getString(R.string.CFG_checkSpan))){
            this.checkSpan = ndate;
        }else if (ntype.equals(context.getString(R.string.CFG_checkTime))){
            this.checkTime = ndate;
        }else if (ntype.equals(context.getString(R.string.CFG_uiReflushSpan))){
            this.uiReflushSpan = ndate;
        }
    }
}
