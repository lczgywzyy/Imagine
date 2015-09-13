package u.can.i.up.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.HashMap;

import u.can.i.up.ui.R;
import u.can.i.up.ui.application.IApplication;
import u.can.i.up.ui.application.IApplicationConfig;
import u.can.i.up.ui.beans.IHttpNormalBean;
import u.can.i.up.ui.beans.User;
import u.can.i.up.ui.net.HttpManager;
import u.can.i.up.ui.net.HttpNormalManager;
import u.can.i.up.ui.utils.IBitmapCache;

public class UserEditActivity extends ActionBarActivity implements View.OnClickListener {

    private Button btnSubmit;

    private Button btnReset;

    private TextView txtPhone;

    private EditText edtName;

    private EditText edtEmail;


    private ImageView imgIcon;

    private User user;

    private boolean isPortraitChange;

    enum UType {PORTRAIT,NAME,EMAIL,TEL}

    private UType uptype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_edit);

        initViews();
        initData();
    }


    private void initViews(){

        btnSubmit=(Button)findViewById(R.id.btn_submit);

        btnReset=(Button)findViewById(R.id.btn_reset);

        txtPhone=(TextView)findViewById(R.id.txtphone);

        edtEmail=(EditText)findViewById(R.id.edtemail);

        edtName=(EditText)findViewById(R.id.edtname);

        imgIcon=(ImageView)findViewById(R.id.imgicon);

        btnSubmit.setOnClickListener(this);

        btnReset.setOnClickListener(this);

        imgIcon.setOnClickListener(this);


    }

    private void initData(){
        user=((IApplication)getApplication()).getUerinfo();
        if(!TextUtils.isEmpty(user.getUserName())){
            edtName.setText(user.getUserName());
        }
        if(!TextUtils.isEmpty(user.getUserEmail())){
            edtEmail.setText(user.getUserEmail());
        }

        if(!TextUtils.isEmpty(user.getPhoneNumber())){
            txtPhone.setText(user.getPhoneNumber());
        }
        //设置用户头像
        if(!TextUtils.isEmpty(user.getPortrait())){
            String imguri=user.getPortrait();
            String[] uriArray=imguri.split("/");
            if(uriArray.length>1) {
                String md5 = uriArray[uriArray.length - 1].replaceAll(".png", "");
                IBitmapCache.BitmapAsync bitmapAsync=new IBitmapCache.BitmapAsync(imgIcon,UserEditActivity.this.getApplicationContext());

                bitmapAsync.execute(imguri, md5,"img");
            }
        }
        imgIcon.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_submit:
                if(isChanged(UType.PORTRAIT)) {
                    uptype=UType.PORTRAIT;
                }else if(isChanged(UType.NAME)){
                    uptype=UType.NAME;
                }else if(isChanged(UType.EMAIL)){
                    uptype=UType.EMAIL;
                }
                update();
                break;
            case R.id.btn_reset:
                initData();
                break;
            case R.id.imgicon:
                choosePicture();
                break;

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 0)
            return;

        if (requestCode == 2)//调用系统裁剪
        {
            try {
                startPhotoZoom(data.getData());
            }catch (Exception e){

            }
        } else if (requestCode == 3)//得到裁剪后的图片
        {
            try
            {
                Bitmap bmap = data.getParcelableExtra("data");

                imgIcon.setImageBitmap(bmap);
                FileOutputStream os = this.openFileOutput("headTemp",
                        Context.MODE_PRIVATE);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] bytes=baos.toByteArray();
                os.write(bytes);
                os.close();
                isPortraitChange=true;

            } catch (Exception e)
            {
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void choosePicture(){
        Intent intent = new Intent();
        intent.setType("image/*");
                /* 使用Intent.ACTION_GET_CONTENT这个Action */
        intent.setAction(Intent.ACTION_GET_CONTENT);
                /* 取得相片后返回本画面 */
        startActivityForResult(intent, 2);


    }
    public void startPhotoZoom(Uri uri) throws IOException {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 5);
        intent.putExtra("aspectY", 5);
        intent.putExtra("return-data", true);
        intent.putExtra("outputFormat", "png");
        startActivityForResult(intent, 3);
    }

    private HashMap<String,String> updateUserMsgBase(){
        HashMap<String,String> hashMap=new HashMap<>();

        hashMap.put("tel", user.getPhoneNumber());
        hashMap.put("eString", user.geteString());
        hashMap.put("tString", user.gettString());
        hashMap.put("tokenString", user.getUserLoginToken());

        return hashMap;
    }

    private boolean isChanged(UType uptype){
       if(uptype== UType.EMAIL){
            if(!TextUtils.isEmpty(edtEmail.getText().toString())&&!(edtEmail.getText().toString().equals(user.getUserEmail()))){
                return true;
            }
       }else if(uptype== UType.PORTRAIT){
            return isPortraitChange;
       }else if(uptype== UType.NAME){
           if(!TextUtils.isEmpty(edtName.getText().toString())&&!(edtName.getText().toString().equals(user.getUserName()))){
               return true;
           }
       }
        return false;

    }
    Handler handler=new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            switch (msg.what) {
                case IApplicationConfig.HTTP_NET_SUCCESS:
                    IHttpNormalBean IHttpNormalBean = (IHttpNormalBean) bundle.getSerializable(IApplicationConfig.HTTP_BEAN);
                    if (IHttpNormalBean != null && Integer.parseInt(IHttpNormalBean.getRetCode()) == IApplicationConfig.HTTP_CODE_SUCCESS) {
                        if(uptype== UType.PORTRAIT) {
                            ((IApplication) getApplication()).getUerinfo().setPortrait(IHttpNormalBean.getData());
                           uptype=UType.NAME;
                            update();
                        }else if(uptype== UType.NAME){
                            ((IApplication) getApplication()).getUerinfo().setUserName(IHttpNormalBean.getData());
                            uptype=UType.EMAIL;
                            update();
                        }else if(uptype== UType.EMAIL){
                            ((IApplication) getApplication()).getUerinfo().setUserEmail(IHttpNormalBean.getData());
                            uptype=UType.PORTRAIT;
                            Toast.makeText(UserEditActivity.this,"更新成功",Toast.LENGTH_LONG).show();
                        }
                    }
                    break;
                case IApplicationConfig.HTTP_NET_ERROR:
                    break;
                case IApplicationConfig.HTTP_NET_TIMEOUT:


            }

        }
    };

    private  void update(){
            if(isChanged(uptype)) {
                WeakReference<Handler> weakReference = new WeakReference<Handler>(new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        Bundle bundle = msg.getData();

                        switch (msg.what) {
                            case IApplicationConfig.HTTP_NET_SUCCESS:

                                IHttpNormalBean IHttpNormalBean = (IHttpNormalBean) bundle.getSerializable(IApplicationConfig.HTTP_BEAN);
                                if (IHttpNormalBean != null && Integer.parseInt(IHttpNormalBean.getRetCode()) == IApplicationConfig.HTTP_CODE_SUCCESS) {
                                    //获取csc验证码
                                    //开启更改头像线程
                                    HashMap<String, String> hashMap = updateUserMsgBase();
                                    hashMap.put("csc", IHttpNormalBean.getData());
                                    HttpNormalManager httpNormalManager = HttpNormalManager.getHttpChecksumManagerInstance();
                                    if(uptype== UType.PORTRAIT) {
                                        hashMap.put("suffix", "png");
                                        HashMap<String, SoftReference<Bitmap>> hashMapImg = new HashMap<>();
                                        hashMapImg.put("image_file", new SoftReference<>(IBitmapCache.getBitMapCache(UserEditActivity.this.getApplicationContext()).loadBitmapLocal("headTemp")));
                                        httpNormalManager.boundImage(hashMapImg);
                                        httpNormalManager.boundUrl(IApplicationConfig.HTTP_URL_PORTRAIT);
                                    }else if(uptype== UType.EMAIL){
                                        hashMap.put("target","EMAIL");
                                        hashMap.put("data1", edtEmail.getText().toString());
                                        httpNormalManager.boundUrl(IApplicationConfig.HTTP_URL_PARAMETERS);

                                    }else if(uptype== UType.NAME){
                                        hashMap.put("target","USERNAME");
                                        hashMap.put("data1", edtName.getText().toString());
                                        httpNormalManager.boundUrl(IApplicationConfig.HTTP_URL_PARAMETERS);
                                    }

                                    httpNormalManager.boundType(HttpManager.HttpType.POST);
                                    httpNormalManager.boundParameter(hashMap);
                                    httpNormalManager.boundHandler(handler);
                                    httpNormalManager.execute();
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

                HttpNormalManager httpNormalManager = HttpNormalManager.getHttpChecksumManagerInstance();
                httpNormalManager.boundParameter(updateUserMsgBase());
                httpNormalManager.boundHandler(weakReference.get());
                httpNormalManager.boundUrl(IApplicationConfig.HTTP_URL_CHECKSUM);
                httpNormalManager.boundType(HttpManager.HttpType.POST);
                httpNormalManager.execute();
            }else{
                if(uptype==UType.EMAIL){
                    Toast.makeText(UserEditActivity.this,"更新成功",Toast.LENGTH_LONG).show();
                }
            }
    }

}
