package u.can.i.up.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.os.Handler;

import java.lang.ref.WeakReference;
import java.util.HashMap;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

import u.can.i.up.ui.R;
import u.can.i.up.ui.application.IApplication;
import u.can.i.up.ui.application.IApplicationConfig;
import u.can.i.up.ui.beans.ILoginBean;
import u.can.i.up.ui.beans.IRegisterBean;
import u.can.i.up.ui.net.HttpLoginManager;
import u.can.i.up.ui.net.HttpRegisterManager;

public class RegisterActivity extends ActionBarActivity implements View.OnClickListener {

    private EditText edtPhone;

    private EditText edtPcd;

    private EditText edtUserName;

    private EditText edtPsd;

    private EditText edtPsdEn;

    private Button btnGetPcd;

    private Button btnRegister;

    private String SMSAPPKEY;

    private String SMSAPPSECRET;

    private ImageButton register_back;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_new);
        initViews();
        getAppKeys();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
        return true;
    }

    private void initViews(){
        edtPhone=(EditText)findViewById(R.id.edt_phone);
        edtPcd=(EditText)findViewById(R.id.edt_sms_pcd);
        edtUserName=(EditText)findViewById(R.id.edt_username);
        edtPsd=(EditText)findViewById(R.id.edt_psd);
        edtPsdEn=(EditText)findViewById(R.id.edt_psd_en);
        btnGetPcd=(Button)findViewById(R.id.btn_getsmspcd);
        btnRegister=(Button)findViewById(R.id.btn_register);
        register_back = (ImageButton)findViewById(R.id.btn_register_back);
        btnGetPcd.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        register_back.setOnClickListener(this);

    }

    private void initSDK(){
        SMSSDK.initSDK(this, SMSAPPKEY, SMSAPPSECRET);
        EventHandler eh=new EventHandler(){

            @Override
            public void afterEvent(int event, int result, Object data) {

                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                handler.sendMessage(msg);
            }

        };
        SMSSDK.registerEventHandler(eh);
    }

    private void getAppKeys(){
        SMSAPPKEY=getMetaDataValue("smsAppkey",this);
        SMSAPPSECRET=getMetaDataValue("smsAppSecret",this);
        initSDK();
    }
    private String getMetaDataValue(String name,Context context) {

        Object value = null;

        PackageManager packageManager = context.getPackageManager();

        ApplicationInfo applicationInfo;

        try {

            applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), 128);

            if (applicationInfo != null && applicationInfo.metaData != null) {

                value = applicationInfo.metaData.get(name);

            }

        } catch (PackageManager.NameNotFoundException e) {

            throw new RuntimeException(

                    "Could not read the name in the manifest file.", e);

        }

        if (value == null) {

            throw new RuntimeException("The name '" + name

                    + "' is not defined in the manifest file's meta data.");

        }

        return value.toString();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_getsmspcd:
                if(!TextUtils.isEmpty(edtPhone.getText().toString())){
                    SMSSDK.getVerificationCode("86",edtPhone.getText().toString());
                    btnGetPcd.setClickable(false);
                    btnGetPcd.setText("再次获取");
                }else {
                   Toast.makeText(getApplicationContext(),"电话不能为空",Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.btn_register:
                if(vertifyInput()) {
                    registerUsers();
                }
                break;
            case R.id.btn_register_back:
                RegisterActivity.this.finish();
                break;
            default:

        }
    }
    private void registerUsers(){
        HashMap<String,String> hashMapParameter=new HashMap<>();

        hashMapParameter.put("tel",edtPhone.getText().toString());

        hashMapParameter.put("cs",edtPcd.getText().toString());

        hashMapParameter.put("uname",edtUserName.getText().toString());

        hashMapParameter.put("pw", edtPsd.getText().toString());

    /*    HttpManager<IRegisterBean> httpRegister=new HttpManager<IRegisterBean>("http://45.55.12.70/AppRegister", HttpManager.HttpType.POST,hashMapParameter,IRegisterBean.class){
            @Override
            protected void onPostExecute(IHttpStatus s) {
                super.onPostExecute(s);
                //验证是否注册成功
                IRegisterBean reStatus=(IRegisterBean)s.getHttpObj();
                int rect=Integer.parseInt(reStatus.getRetCode().replace("\n",""));
                if(rect== IApplicationConfig.HTTP_REGISTER_CODE_SUCCESS){
                    //注册成功,执行登录入口
                    Toast.makeText(getApplicationContext(),"注册成功",Toast.LENGTH_LONG).show();
                    //跳转至登录界面
                    Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);

                    RegisterActivity.this.startActivity(intent);

                }else if(rect==IApplicationConfig.HTTP_REGISTER_CODE_EX_ERROR){
                    //手机号已被注册
                    Toast.makeText(getApplicationContext(),"该手机号码已经被注册",Toast.LENGTH_LONG).show();
                }else if(rect== IApplicationConfig.HTTP_REGISTER_CODE_CP_ERROR){
                    //短信验证码错误
                    Toast.makeText(getApplicationContext(),"短信验证码输入错误,请重新输入",Toast.LENGTH_LONG).show();
                }
            }
        };
        httpRegister.execute();*/

        HttpRegisterManager httpRegisterManager=HttpRegisterManager.getRegisterHttpInstance();

        httpRegisterManager.boundParameter(hashMapParameter);

        WeakReference<Handler> handlerWeakReference=new WeakReference<Handler>(new Handler(){
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle bundle=msg.getData();
                String messagestr=bundle.getString(IApplicationConfig.MESSAGE);
                Toast.makeText(getApplicationContext(),messagestr,Toast.LENGTH_LONG).show();
                switch (msg.what){
                    case IApplicationConfig.HTTP_NET_SUCCESS:

                        IRegisterBean IRegisterBean =(IRegisterBean)bundle.getSerializable(IApplicationConfig.HTTP_BEAN);
                        //注册成功,调用登录接口
                        login();
                        break;
                    case IApplicationConfig.HTTP_NET_ERROR:
                        //网络未连接
                        break;
                    case IApplicationConfig.HTTP_NET_TIMEOUT:
                        //网络连接超时、服务器错误、字符解析失败
                        break;
                }

            }
        });
        httpRegisterManager.boundHandler(handlerWeakReference.get());
        httpRegisterManager.execute();

    }

    private void login(){
        HashMap<String,String> hashMapParameter=new HashMap<>();

        hashMapParameter.put("tel",edtPhone.getText().toString());


        hashMapParameter.put("password", edtPsd.getText().toString());
        WeakReference<Handler> handlerWeakReference=new WeakReference<Handler>(new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                Bundle bundle=msg.getData();

                String msgstr=bundle.getString(IApplicationConfig.MESSAGE);

                switch (msg.what) {
                    case IApplicationConfig.HTTP_NET_SUCCESS:
                        //登录成功

                        ILoginBean ILoginBean = (ILoginBean) bundle.getSerializable(IApplicationConfig.HTTP_BEAN);
                        if (ILoginBean != null && Integer.parseInt(ILoginBean.getRetCode()) == IApplicationConfig.HTTP_CODE_SUCCESS) {

                            HttpLoginManager.setLoginStatus(ILoginBean.getData(), (IApplication) getApplication());

                            Intent intent = new Intent();
                            intent.setClass(RegisterActivity.this, MainActivity.class);
                            startActivity(intent);
                            RegisterActivity.this.finish();
                        }
                        break;
                    case IApplicationConfig.HTTP_NET_ERROR:
                        //登录失败
                        break;
                    case IApplicationConfig.HTTP_NET_TIMEOUT:
                        break;
                }
            }
        });
        HttpLoginManager httpLoginManager=HttpLoginManager.getHttpLoginManagerTInstance();
        httpLoginManager.boundHandler(handlerWeakReference.get());
        httpLoginManager.boundParameter(hashMapParameter);
        httpLoginManager.execute();
    }
    private boolean vertifyInput(){

        if(TextUtils.isEmpty(edtPhone.getText().toString())){
            Toast.makeText(this,"用户手机号码不能为空",Toast.LENGTH_LONG).show();
            return false;
        }
        if(TextUtils.isEmpty(edtPcd.getText().toString())){
            Toast.makeText(this,"用户短信验证码不能为空",Toast.LENGTH_LONG).show();
            return false;
        }
        if(TextUtils.isEmpty(edtUserName.getText().toString())){
            Toast.makeText(this,"用户名不能为空",Toast.LENGTH_LONG).show();
            return false;
        }
        if(TextUtils.isEmpty(edtPsd.getText().toString())){
            Toast.makeText(this,"用户密码不能为空",Toast.LENGTH_LONG).show();
            return false;
        }
        if(TextUtils.isEmpty(edtPsdEn.getText().toString())){
            Toast.makeText(this,"请确认用户密码",Toast.LENGTH_LONG).show();
            return false;
        }
        if(!TextUtils.equals(edtPsd.getText().toString(),edtPsdEn.getText().toString())){
            Toast.makeText(this,"两次输入密码不一致,请重新确认",Toast.LENGTH_LONG).show();
            return false;
        }

        return  true;
    }

    Handler handler=new Handler()
        {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            int event = msg.arg1;
            int result = msg.arg2;
            Object data = msg.obj;
            if (result == SMSSDK.RESULT_COMPLETE) {
                //短信注册成功后，返回MainActivity,然后提示新好友
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {//提交验证码成功
                    Toast.makeText(getApplicationContext(), "提交验证码成功", Toast.LENGTH_SHORT).show();
                    //textView2.setText("提交验证码成功");
                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                    Toast.makeText(getApplicationContext(), "验证码已经发送", Toast.LENGTH_SHORT).show();
                    btnGetPcd.setText("已发送");
                    this.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            btnGetPcd.setText("重新获取");
                            btnGetPcd.setClickable(true);
                        }
                    }, 60000);

                }else if (event ==SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){//返回支持发送验证码的国家列表
                    Toast.makeText(getApplicationContext(), "获取国家列表成功", Toast.LENGTH_SHORT).show();
                    //countryTextView.setText(data.toString());

                }else{
                    Toast.makeText(getApplicationContext(), "验证码发送失败，请重新获取", Toast.LENGTH_SHORT).show();
                    btnGetPcd.setText("重新获取验证码");
                    btnGetPcd.setClickable(true);
                }
            }

        }

    };

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        SMSSDK.unregisterAllEventHandler();
    }

//        @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                // app icon in action bar clicked; goto parent activity.
//                this.finish();
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
}
