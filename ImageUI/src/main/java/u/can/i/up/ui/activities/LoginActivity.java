package u.can.i.up.ui.activities;

import android.content.Intent;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import  android.os.Handler;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import u.can.i.up.ui.R;
import u.can.i.up.ui.application.IApplication;
import u.can.i.up.ui.application.IApplicationConfig;
import u.can.i.up.ui.beans.ILoginBean;
import u.can.i.up.ui.beans.IPearlBeans;
import u.can.i.up.ui.beans.PearlBeans;
import u.can.i.up.ui.beans.User;
import u.can.i.up.ui.net.HttpLoginManager;
import u.can.i.up.ui.net.HttpSMaterialUpdateManager;

public class LoginActivity extends ActionBarActivity implements View.OnClickListener {

    private EditText edtPhone;

    private EditText edtPsd;

    private Button btnLogin;

    private Button txtFgPsd;

    private Button txtRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_new);
        handlerWeakReference=new SoftReference<Handler>(new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                Bundle bundle=msg.getData();

                String msgstr=bundle.getString(IApplicationConfig.MESSAGE);

                switch (msg.what) {
                    case IApplicationConfig.HTTP_NET_SUCCESS:
                        //登录成功

                        try {
                            ILoginBean ILoginBean = (ILoginBean) bundle.getSerializable(IApplicationConfig.HTTP_BEAN);
                            Toast.makeText(getApplicationContext(), msgstr, Toast.LENGTH_LONG).show();
                            if (ILoginBean != null && Integer.parseInt(ILoginBean.getRetCode()) == IApplicationConfig.HTTP_CODE_SUCCESS) {

                                HttpLoginManager.setLoginStatus(ILoginBean.getData(), (IApplication) getApplication());
                                getMaterial();
                            }else{
                                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(i);
                                LoginActivity.this.finish();
                            }
                        }catch(Exception e){
                            ArrayList<PearlBeans> pearlBeansList=(ArrayList) (((IPearlBeans)bundle.getSerializable(IApplicationConfig.HTTP_BEAN)).getData());
                            refreshLocalSMaterial(pearlBeansList);
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
        initViews();
    }

    private void initViews(){
        edtPhone=(EditText)findViewById(R.id.edtphone);
        edtPsd=(EditText)findViewById(R.id.edtpsd);
        btnLogin=(Button)findViewById(R.id.btnlogin);
        txtFgPsd=(Button)findViewById(R.id.txtfgpsd);
        txtRegister=(Button)findViewById(R.id.txtreg);
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


   private SoftReference<Handler> handlerWeakReference;

    private void loginUser(){
        HashMap<String,String> hashMap=new HashMap<>();

        hashMap.put("tel",edtPhone.getText().toString());
        hashMap.put("password", edtPsd.getText().toString());


        HttpLoginManager httpLoginManager=HttpLoginManager.getHttpLoginManagerTInstance();
        Handler handler=handlerWeakReference.get();
        httpLoginManager.boundHandler(handlerWeakReference.get());
        httpLoginManager.boundParameter(hashMap);
        httpLoginManager.execute();
    }
    private void getMaterial(){

        HttpSMaterialUpdateManager httpSMaterialUpdateManager=HttpSMaterialUpdateManager.getSMaterialUpdateHttpInstance();

        HashMap<String,String> hashMap=new HashMap<>();

        User user=((IApplication)getApplication()).getUerinfo();

        hashMap.put("tel", user.getPhoneNumber());
        hashMap.put("eString", user.geteString());
        hashMap.put("tString", user.gettString());
        hashMap.put("tokenString", user.getUserLoginToken());
        hashMap.put("type","1");
        hashMap.put("category","0");
        httpSMaterialUpdateManager.boundHandler(handlerWeakReference.get());
        httpSMaterialUpdateManager.boundParameter(hashMap);
        httpSMaterialUpdateManager.boundUrl(IApplicationConfig.HTTP_URL_SMATRIAL_UPDATE);
        httpSMaterialUpdateManager.execute();


    }

    private void refreshLocalSMaterial(ArrayList<PearlBeans> pearlBeansArrayList){

        Iterator<PearlBeans> iterator=pearlBeansArrayList.iterator();
        while (iterator.hasNext()){
            PearlBeans pearlBeans=iterator.next();
            if(!((IApplication) getApplication()).arrayListPearlBeans.contains(pearlBeans)) {
                pearlBeans.setIsSynchronized(true);
                ((IApplication) getApplication()).psqLiteOpenHelper.addPearl(pearlBeans);
                ((IApplication) getApplication()).arrayListPearlBeans.add(pearlBeans);
            }

        }


    }
        @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
            default:
                return super.onOptionsItemSelected(item);
        }
        }

}
