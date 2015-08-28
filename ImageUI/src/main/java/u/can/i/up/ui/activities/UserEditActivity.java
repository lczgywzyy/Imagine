package u.can.i.up.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

import u.can.i.up.ui.R;
import u.can.i.up.ui.application.IApplication;
import u.can.i.up.ui.application.IApplicationConfig;
import u.can.i.up.ui.beans.User;
import u.can.i.up.ui.utils.IBitmapCache;

public class UserEditActivity extends ActionBarActivity implements View.OnClickListener {

    private Button btnSubmit;

    private Button btnReset;

    private TextView txtPhone;

    private EditText edtName;

    private EditText edtEmail;


    private ImageView imgIcon;

    private User user;
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
                IBitmapCache.BitmapAsync bitmapAsync=new IBitmapCache.BitmapAsync(imgIcon);

                bitmapAsync.execute(imguri, md5,"img");
            }
        }
        imgIcon.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_submit:
                updateUserMsg();
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

            startPhotoZoom(data.getData());
        } else if (requestCode == 3)//得到裁剪后的图片
        {
            try
            {
                imgIcon.setImageBitmap(IBitmapCache.getBitMapCache().loadBitmapLocal("headTemp"));

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
    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 5);
        intent.putExtra("aspectY", 5);
        intent.putExtra("output", Uri.fromFile(new File(IApplicationConfig.DIRECTORY_SMATERIAL+File.separator+"headTemp.png")));
        intent.putExtra("outputFormat", "png");
        startActivityForResult(intent, 3);
    }

    private void updateUserMsg(){
        HashMap<String,String> hashMap=new HashMap<>();

        hashMap.put("tel",user.getPhoneNumber());
        hashMap.put("eString",user.geteString());
        hashMap.put("tString",user.gettString());
        hashMap.put("tokenString",user.getUserLoginToken());


    }
}
