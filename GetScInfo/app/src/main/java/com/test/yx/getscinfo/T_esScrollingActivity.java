package com.test.yx.getscinfo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import org.mozilla.javascript.Function;
import org.mozilla.javascript.NativeJavaObject;
import org.mozilla.javascript.NativeObject;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;


public class T_esScrollingActivity extends AppCompatActivity {
    //// TODO: [变+] --> 建立全局引用对象
    AEsApplication _application = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _application = (AEsApplication)this.getApplicationContext();
        _application.get_dataConfig().context = this.getApplicationContext();

        if(_application.get_dataConfig().onlyMe ==1) {
        //if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();// return;
            Intent intent=new Intent("com.test.yx.getscinfo.MainActivity");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setClass(T_esScrollingActivity.this, MainActivity.class);
            startActivity(intent);
        }
        setContentView(R.layout.activity_t_es_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        _application.get_dataConfig().onlyMe =1;
        // 避免从桌面启动程序后，会重新实例化入口类的activity
        if (!this.isTaskRoot()) { // 当前类不是该Task的根部，那么之前启动
            Intent intent = getIntent();
            if (intent != null) {
                String action = intent.getAction();
                if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && Intent.ACTION_MAIN.equals(action)) { // ??????????????????
                    finish(); // finish掉该类，直接打开该Task中现存的Activity
                    return;
                }
            }
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*跳转下一页面
                Intent intent=new Intent("com.test.yx.getscinfo.MainActivity");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setClass(T_esScrollingActivity.this, MainActivity.class);
                startActivity(intent);
                //*/
                ///*
                String tmpStr = String.format("-- %1$s --",mCurrentProgress);
                Snackbar.make(view, tmpStr, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                //*/
            }
        });

        mViewDrawString = (CView_drawString)findViewById(R.id.viewString);
        mButton = (FloatingActionButton)findViewById(R.id.fab);
        new Thread(new ProgressRunable()).start();

        //加载rhino
        testRhino();
    }

    @Override
    public void onBackPressed() {
        //moveTaskToBack(true);
        //后退键，返回桌面，不销毁窗体
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }

    private int mTotalProgress = 21;
    private int mCurrentProgress = 0;
    CView_drawString mViewDrawString ;
    FloatingActionButton mButton ;
    class ProgressRunable implements Runnable {
        @Override
        public void run() {

            while (true) {
                try {
                    if (mCurrentProgress > mTotalProgress) mCurrentProgress = 0;
                    mCurrentProgress += 1;
                    mViewDrawString.setTypeValue(mCurrentProgress);
                    //mButton.setVisibility(mCurrentProgress != 1? View.INVISIBLE:View.VISIBLE);
                    //mButton.setEnabled(mCurrentProgress != 1? true: false);
                    Thread.sleep(500);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /** Java执行js的方法 */
    private static final String JAVA_CALL_JS_FUNCTION = "function Test(nint){ return  nint==2?'这是2':'这是几？'; }";

    /** js调用Java中的方法 */
    private static final String JS_CALL_JAVA_FUNCTION = //
            "var ScriptAPI = java.lang.Class.forName(\"" + T_esScrollingActivity.class.getName() + "\", true, javaLoader);" + //
                    "var methodRead = ScriptAPI.getMethod(\"jsCallJava\", [java.lang.String]);" + //
                    "function jsCallJava(url) {return methodRead.invoke(null, url);}" + //
                    "function Test(){ return jsCallJava(); }";

    private  void  testRhino(){
        String tmpStr = runScript(JAVA_CALL_JS_FUNCTION,"Test", new Object[]{"2"});
        tmpStr = "";
    }

    public String runScript(String js, String functionName, Object[] functionParams) {
        //org.mozilla.javascript.
        org.mozilla.javascript.Context rhino = org.mozilla.javascript.Context.enter();
        rhino.setOptimizationLevel(-1);

        try{
            // 初始化Scriptable
            Scriptable scope = rhino.initSafeStandardObjects();

            ScriptableObject.putProperty(scope, "javaContext", org.mozilla.javascript.Context.javaToJS(T_esScrollingActivity.this, scope));
            ScriptableObject.putProperty(scope, "javaLoader", org.mozilla.javascript.Context.javaToJS(T_esScrollingActivity.class.getClassLoader(), scope));
            // 这一句是必须的，否则  scope.get("isPrime", scope)返回不了Function,而是返回UniqueTag
            rhino.evaluateString(scope,js,null,1,null);
            // 得到自定义函数
            Function function =(Function)scope.get(functionName,scope);

            // 调用自定义函数，第三个参数一般应该是传调用对象，但我发现对函数来说，传null也可以得到同样的结果
            // 第四个参数是函数的参数，以对象数据的形式传递
            Object result = function.call(rhino, scope, scope, functionParams);

            if(result instanceof String){
                return (String)result;
            }else if(result instanceof NativeJavaObject) {
                return (String)((NativeJavaObject)result).getDefaultValue(String.class);
            }else if(result instanceof NativeObject){
                return (String)((NativeObject)result).getDefaultValue(String.class);
            }
            return result.toString();
        }catch (Exception e){
            e.printStackTrace();
            return "-1";
        }finally {
            org.mozilla.javascript.Context.exit();
            //return "";
        }
    }
}
