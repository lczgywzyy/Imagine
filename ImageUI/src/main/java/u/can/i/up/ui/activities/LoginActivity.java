package u.can.i.up.ui.activities;

import android.content.Intent;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import  android.os.Handler;

import java.lang.ref.WeakReference;
import java.util.HashMap;

import u.can.i.up.ui.R;
import u.can.i.up.ui.application.IApplication;
import u.can.i.up.ui.application.IApplicationConfig;
import u.can.i.up.ui.beans.ILoginBean;
import u.can.i.up.ui.net.HttpLoginManager;

public class LoginActivity extends ActionBarActivity implements View.OnClickListener {

    private EditText edtPhone;

    private EditText edtPsd;

    private Button btnLogin;

    private TextView txtFgPsd;

    private TextView txtRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity_t);
        initViews();
    }

    private void initViews(){
        edtPhone=(EditText)findViewById(R.id.edtphone);
        edtPsd=(EditText)findViewById(R.id.edtpsd);
        btnLogin=(Button)findViewById(R.id.btnlogin);
        txtFgPsd=(TextView)findViewById(R.id.txtfgpsd);
        txtRegister=(TextView)findViewById(R.id.txtreg);
        btnLogin.setOnClickListener(this);
        txtFgPsd.setOnClickListener(this);
        txtRegister.setOnClickListener(this);
    }

    private boolean verify(){
        if(TextUtils.isEmpty(edtPhone.getText().toString())){
            Toast.makeText(this,"请输入用户名",Toast.LENGTH_LONG).show();
            return false;
        }
        if(TextUtils.isEmpty(edtPsd.getText().toString())){
            Toast.makeText(this,"请输入密码",Toast.LENGTH_LONG).show();
            return false;
        }
        return true;

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btnlogin:
                //登录
                if(verify()){
                    loginUser();
                }
                break;
            case R.id.txtfgpsd:
                //忘记密码
                break;
            case R.id.txtreg:
                Intent intent=new Intent(this,RegisterActivity.class);
                startActivity(intent);
                break;
        }

    }

    private void loginUser(){
        HashMap<String,String> hashMap=new HashMap<>();

        hashMap.put("tel",edtPhone.getText().toString());
        hashMap.put("password", edtPsd.getText().toString());

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
                        Toast.makeText(getApplicationContext(), msgstr, Toast.LENGTH_LONG).show();
                        if (ILoginBean != null && Integer.parseInt(ILoginBean.getRetCode()) == IApplicationConfig.HTTP_CODE_SUCCESS) {

                            HttpLoginManager.setLoginStatus(ILoginBean.getData(),(IApplication)getApplication());

                            Intent intent = new Intent();
                            intent.setClass(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            LoginActivity.this.finish();
                        }
                        break;
                    case IApplicationConfig.HTTP_NET_ERROR:
                        //登录失败
                        Toast.makeText(getApplicationContext(),msgstr,Toast.LENGTH_LONG).show();
                        break;
                    case IApplicationConfig.HTTP_NET_TIMEOUT:
                        Toast.makeText(getApplicationContext(),msgstr,Toast.LENGTH_LONG).show();
                        break;
                }
            }
        });
        HttpLoginManager httpLoginManager=HttpLoginManager.getHttpLoginManagerTInstance();
        httpLoginManager.boundHandler(handlerWeakReference.get());
        httpLoginManager.boundParameter(hashMap);
        httpLoginManager.execute();
    }
}