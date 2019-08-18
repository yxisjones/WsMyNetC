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
 *	�ļ���:	
 *	ժ  Ҫ: 
 *			
 *	     		
 *
 *	��ǰ�汾:	1.00
 *	��    ��:	developer.yx (Administrator)
 *	�������:	2019-07-??
 *	��    ע:	 
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
    //���ý���
    public void setTypeValue(int nValue) {
        intType = nValue;
        postInvalidate();//�ػ�
    }
    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);
        BlurMaskFilter bmf = null;
        Paint paint=new Paint();
        paint.setAntiAlias(true);          //�����
        paint.setColor(Color.BLACK);//������ɫ
        paint.setStyle(Paint.Style.FILL);  //���ʷ��
        paint.setTextSize(38);             //�������ִ�С����λpx
        paint.setStrokeWidth(3);           //���ʴ�ϸ

        int tmpHeight = 400;
        paint.setColor(Color.BLACK);//������ɫ
        canvas.drawText("��Ϣ��", 100,tmpHeight , paint);tmpHeight+=50;
        canvas.drawText("     δ֪����δ��֤.", 100, tmpHeight, paint);tmpHeight+=50;
        canvas.drawText("     �����Ƿ������������û�����ø�APP����Ȩ��.", 100, tmpHeight, paint);tmpHeight+=50;
        canvas.drawText("     ������Ϣ��106", 100, tmpHeight, paint);tmpHeight+=50;
        canvas.drawText("     ������룺δ֪", 100, tmpHeight, paint);tmpHeight+=50;

        if (intType == 1){
            bmf = new BlurMaskFilter(10f,BlurMaskFilter.Blur.SOLID);
            paint.setMaskFilter(bmf);
            canvas.drawText("����-> ������Ϣ", 100, 70, paint);
        }
        else if (intType == 2){
            bmf = new BlurMaskFilter(10f,BlurMaskFilter.Blur.SOLID);
            paint.setMaskFilter(bmf);
            canvas.drawText("����-> ��Ϣ���� ", 100, 70, paint);
        }
        else if ( (intType & 1) ==1 ){
            bmf = new BlurMaskFilter(10f,BlurMaskFilter.Blur.SOLID);
            paint.setMaskFilter(bmf);
            canvas.drawText("����-> ��Ϣ���� �� ", 100, 70, paint);
        }else if ( (intType & 1) ==0 ){
            bmf = new BlurMaskFilter(10f,BlurMaskFilter.Blur.SOLID);
            paint.setMaskFilter(bmf);
            canvas.drawText("����-> ��Ϣ���� �x", 100, 70, paint);
        }
        else {
            paint.setColor(Color.BLUE);//������ɫ
            //bmf = new BlurMaskFilter(10f,BlurMaskFilter.Blur.NORMAL);
            //paint.setMaskFilter(bmf);
            canvas.drawText("��Ϣ��", 100, 400, paint);
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
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);     //�ر�Ӳ������
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
            size = isWidth ? padding + DEFAULT_WIDTH  : DEFAULT_HEIGHT + padding;//�ṩһ��Ĭ�ϵ�ֵ
            if (specMode == MeasureSpec.AT_MOST) {
                size = Math.min(size, specSize);
            }
        }
        return size;
    }
}
