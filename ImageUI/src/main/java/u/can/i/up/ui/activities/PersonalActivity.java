package u.can.i.up.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import de.hdodenhof.circleimageview.CircleImageView;
import u.can.i.up.ui.R;
import u.can.i.up.ui.application.IApplication;
import u.can.i.up.ui.beans.User;
import u.can.i.up.ui.net.HttpLoginManager;
import u.can.i.up.ui.utils.IBitmapCache;

public class PersonalActivity extends AppCompatActivity {
    Button btnName;
    Button btnEmail;
    Button btnPhone;
    Button btnLoginOut;
    Button btnEdit;
    CircleImageView headIcon;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);

        initialise();
    }

    /**
     * Create, bind and set up the resources
     */
    private void initialise()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        getSupportActionBar().setTitle(R.string.personal_activity);
        initViews();

    }
    @Override
    protected void onResume(){
        super.onResume();
        initData();
    }
    private void initViews(){
        btnEmail=(Button)findViewById(R.id.useremail);
        btnPhone=(Button)findViewById(R.id.userphone);
        btnName=(Button)findViewById(R.id.username);
        btnLoginOut=(Button)findViewById(R.id.login_out);
        btnEdit=(Button)findViewById(R.id.btn_edit);
        headIcon=(CircleImageView)findViewById(R.id.imgicon);

    }
    private void initData(){
        IApplication iApplication=(IApplication)getApplication();
        if(iApplication.getIsLogin()){
            User user=iApplication.getUerinfo();
            if(TextUtils.isEmpty(user.getUserEmail())){
                btnEmail.setText("未认证");
            }else{
                btnEmail.setText(user.getUserEmail());
            }

            if(TextUtils.isEmpty(user.getUserName())){
                btnName.setText(user.getPhoneNumber());
            }else{
                btnName.setText(user.getUserName());
            }

            if(TextUtils.isEmpty(user.getPhoneNumber())){
                btnPhone.setText("未认证");
            }else{
                btnPhone.setText(user.getPhoneNumber());
            }
            if(!TextUtils.isEmpty(user.getPortrait())){
                String imguri=user.getPortrait();
                String[] uriArray=imguri.split("/");
                if(uriArray.length>1) {
                    String md5 = uriArray[uriArray.length - 1].replaceAll(".png", "");
                    IBitmapCache.BitmapAsync bitmapAsync=new IBitmapCache.BitmapAsync(headIcon);

                    bitmapAsync.execute(imguri, md5,"img");
                }
            }
            btnLoginOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HttpLoginManager httpLoginManager=HttpLoginManager.getHttpLoginManagerTInstance();
                    httpLoginManager.loginOut((IApplication)getApplication());
                    Intent intent=new Intent(PersonalActivity.this,MainActivity.class);
                    startActivity(intent);
                    PersonalActivity.this.finish();
                }
            });
        }else{
            Intent intent=new Intent(PersonalActivity.this,LoginActivity.class);
            startActivity(intent);
            PersonalActivity.this.finish();
        }
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PersonalActivity.this,UserEditActivity.class);
                startActivity(intent);
            }
        });
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_personal, menu);
//        return true;
//    }
//
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
            default:
                return super.onOptionsItemSelected(item);}
    }
}
