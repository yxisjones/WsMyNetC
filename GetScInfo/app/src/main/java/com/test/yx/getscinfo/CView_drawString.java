package com.test.yx.getscinfo;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;
import android.view.View;

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
public class CView_drawString extends View {

    public CView_drawString(Context context) {
        super(context);
    }

    public CView_drawString(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CView_drawString(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public static int intType = 0;
    //设置进度
    public void setTypeValue(int nValue) {
        intType = nValue;
        postInvalidate();//重绘
    }
    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);
        BlurMaskFilter bmf = null;
        Paint paint=new Paint();
        paint.setAntiAlias(true);          //抗锯齿
        paint.setColor(Color.BLACK);//画笔颜色
        paint.setStyle(Paint.Style.FILL);  //画笔风格
        paint.setTextSize(38);             //绘制文字大小，单位px
        paint.setStrokeWidth(3);           //画笔粗细

        int tmpHeight = 400;
        paint.setColor(Color.BLACK);//画笔颜色
        canvas.drawText("信息：", 100,tmpHeight , paint);tmpHeight+=50;
        canvas.drawText("     未知数据未验证.", 100, tmpHeight, paint);tmpHeight+=50;
        canvas.drawText("     请检查是否链接网络或者没有设置该APP网络权限.", 100, tmpHeight, paint);tmpHeight+=50;
        canvas.drawText("     错误信息：106", 100, tmpHeight, paint);tmpHeight+=50;
        canvas.drawText("     错误代码：未知", 100, tmpHeight, paint);tmpHeight+=50;

        if (intType == 1){
            bmf = new BlurMaskFilter(10f,BlurMaskFilter.Blur.SOLID);
            paint.setMaskFilter(bmf);
            canvas.drawText("测试-> 检索信息", 100, 70, paint);
        }
        else if (intType == 2){
            bmf = new BlurMaskFilter(10f,BlurMaskFilter.Blur.SOLID);
            paint.setMaskFilter(bmf);
            canvas.drawText("测试-> 信息数据 ", 100, 70, paint);
        }
        else if ( (intType & 1) ==1 ){
            bmf = new BlurMaskFilter(10f,BlurMaskFilter.Blur.SOLID);
            paint.setMaskFilter(bmf);
            canvas.drawText("测试-> 信息数据 ▉ ", 100, 70, paint);
        }else if ( (intType & 1) ==0 ){
            bmf = new BlurMaskFilter(10f,BlurMaskFilter.Blur.SOLID);
            paint.setMaskFilter(bmf);
            canvas.drawText("测试-> 信息数据 ▁", 100, 70, paint);
        }
        else {
            paint.setColor(Color.BLUE);//画笔颜色
            //bmf = new BlurMaskFilter(10f,BlurMaskFilter.Blur.NORMAL);
            //paint.setMaskFilter(bmf);
            canvas.drawText("信息：", 100, 400, paint);
        }

        /*
        bmf = new BlurMaskFilter(10f,BlurMaskFilter.Blur.NORMAL);
        paint.setMaskFilter(bmf);
        canvas.drawText("Test Out Name", 100, 100, paint);
        bmf = new BlurMaskFilter(10f,BlurMaskFilter.Blur.OUTER);
        paint.setMaskFilter(bmf);
        canvas.drawText("Test Out Name", 100, 200, paint);
        bmf = new BlurMaskFilter(10f,BlurMaskFilter.Blur.INNER);
        paint.setMaskFilter(bmf);
        canvas.drawText("Test Out Name", 100, 300, paint);
        bmf = new BlurMaskFilter(10f,BlurMaskFilter.Blur.SOLID);
        paint.setMaskFilter(bmf);
        canvas.drawText("Test Out Name", 100, 400, paint);
        //*/
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);     //关闭硬件加速
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int finalWidth = getMeasuredLength(widthMeasureSpec, true);
        int finalHeight = getMeasuredLength(heightMeasureSpec, false);
        setMeasuredDimension(finalWidth, finalHeight);
    }

    int DEFAULT_WIDTH = 220;
    int DEFAULT_HEIGHT = 720;
    private int getMeasuredLength(int length, boolean isWidth) {
        int specMode = MeasureSpec.getMode(length);
        int specSize = MeasureSpec.getSize(length);
        int size;
        int padding = isWidth ? getPaddingLeft() + getPaddingRight() : getPaddingTop() + getPaddingBottom();
        if (specMode == MeasureSpec.EXACTLY) {
            size = specSize;
        } else {
            size = isWidth ? padding + DEFAULT_WIDTH  : DEFAULT_HEIGHT + padding;//提供一个默认的值
            if (specMode == MeasureSpec.AT_MOST) {
                size = Math.min(size, specSize);
            }
        }
        return size;
    }
}
