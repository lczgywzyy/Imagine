package u.can.i.up.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.SendMessageToWX;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.mm.sdk.openapi.WXImageObject;
import com.tencent.mm.sdk.openapi.WXMediaMessage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import u.can.i.up.ui.R;
import u.can.i.up.ui.application.IApplicationConfig;
import u.can.i.up.ui.utils.BitmapCache;
import u.can.i.up.utils.image.ImageUtils;
import u.can.i.up.utils.image.ShareUtils;

/**
 * @author dongfeng
 * @data 2015.06.13
 * @sumary 分享界面：搭配完成后，分享图片
 */

public class ShareActivity extends Activity implements View.OnClickListener{


    private ImageButton imgWXShare,imgWXFShare,imgWeiboShare,imgQQShare;

    private static final int THUMB_SIZE = 150;

    private Bitmap tempbitmap;

    private IWXAPI api;

    private Button savetoalbum;
    private TextView savetotext;
    private ImageButton share_close_btn;
    private Button back_main;
    private ImageView share_image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        initView();
    }

    private void initView(){
        share_image = (ImageView) findViewById(R.id.share_image);
        share_close_btn = (ImageButton)findViewById(R.id.cutout_1_close_btn);
        back_main = (Button)findViewById(R.id.back_main);
        savetoalbum = (Button)findViewById(R.id.save_to_album_btn);
        savetotext = (TextView)findViewById(R.id.save_to_album_text);

        tempbitmap = BitmapCache.getBitmapcache();
        share_image.setImageBitmap(tempbitmap);

        savetoalbum.setOnClickListener(this);
        back_main.setOnClickListener(this);
        share_close_btn.setOnClickListener(this);
        //分享功能
        api = WXAPIFactory.createWXAPI(this, ShareUtils.getMetaDataValue("WXAPPID", this));
        imgWXShare=(ImageButton)findViewById(R.id.img_wx);
        imgWXFShare=(ImageButton)findViewById(R.id.img_wxf);
        imgQQShare=(ImageButton)findViewById(R.id.img_qq);
        imgWeiboShare=(ImageButton)findViewById(R.id.img_weibo);
        imgWXFShare.setOnClickListener(this);
        imgWXShare.setOnClickListener(this);
        imgQQShare.setOnClickListener(this);
        imgWeiboShare.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_wx:
                shareWX(SendMessageToWX.Req.WXSceneSession);
                break;
            case R.id.img_wxf:
                shareWX(SendMessageToWX.Req.WXSceneTimeline);
                break;
            case R.id.img_qq:
                break;
            case R.id.img_weibo:
                break;
            case R.id.cutout_1_close_btn:
                finish();
                break;
            case R.id.back_main:
                Intent intent= new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            case R.id.save_to_album_btn:
            {
                String image_path;
                long time=System.currentTimeMillis();
                image_path = IApplicationConfig.DIRECTORY_IMAGE_COLLOCATE + File.separator + time + ".jpg";
                savetoalbum.setClickable(false);
                //保存到sd卡
                saveBitmap(image_path, tempbitmap);
                //保存到我的相册
                MyAlbumActivity.imageList.add(image_path);
                savetotext.setText("已保存至我的相册");
                Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT);
                break;
            }
            default:
                break;
        }
    }

    private void shareWX(int flag){
        WXImageObject imgObj = new WXImageObject(tempbitmap);

        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;

        int height=tempbitmap.getHeight();
        int width=tempbitmap.getWidth();
        int x=THUMB_SIZE*height/width;

        Bitmap thumbBmp = resizeImage(tempbitmap);
        msg.thumbData = ImageUtils.bmpToByteArray(thumbBmp, true);  // 设置缩略图

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("img");
        req.message = msg;
        req.scene =  flag;
        api.sendReq(req);
    }

    private void shareWXF(){

    }
    private void shareQQ(){

    }

    private void shareWeiBo(){

    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }
    public static Bitmap resizeImage(Bitmap bitmap)
    {
        Bitmap BitmapOrg = bitmap;
        int width = BitmapOrg.getWidth();
        int height = BitmapOrg.getHeight();
        float scale_factor=1f;


        if (THUMB_SIZE> width &&  THUMB_SIZE > height) {
            // scale_factor = bitmap_width_pre / canvas_width_pre > bitmap_height_pre / canvas_height_pre ? bitmap_width_pre / canvas_width_pre : bitmap_height_pre / canvas_height_pre;

        } else {
            //背景位图矩阵缩放
            scale_factor = width /THUMB_SIZE >height / THUMB_SIZE ? width /THUMB_SIZE : height / THUMB_SIZE;

        }

        float bitmapScaleWidth=((float)width)/scale_factor;

        float bitmapScaleHeight=((float)height)/scale_factor;

        Bitmap bitmapCompress= Bitmap.createScaledBitmap(bitmap,  (int)bitmapScaleWidth,(int)bitmapScaleHeight, true);
        return bitmapCompress;
    }

    /** 保存方法 */
    public void saveBitmap(String path, Bitmap tem){
        String file_path = IApplicationConfig.DIRECTORY_IMAGE_COLLOCATE;
        File dir = new File(file_path);
        if(!dir.exists())
            dir.mkdirs();
        File file = new File(path);
        try {
            FileOutputStream fOut = new FileOutputStream(file);
            tem.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
            fOut.flush();
            fOut.close();
        }catch (IOException e) {
            e.printStackTrace();
        }

    }

}
