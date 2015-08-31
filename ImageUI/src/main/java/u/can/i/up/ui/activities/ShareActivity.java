package u.can.i.up.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.SendMessageToWX;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.mm.sdk.openapi.WXImageObject;
import com.tencent.mm.sdk.openapi.WXMediaMessage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import u.can.i.up.ui.R;
import u.can.i.up.ui.utils.BitmapCache;
import u.can.i.up.ui.utils.ImageUtils;
import u.can.i.up.ui.utils.ShareUtils;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        init();
    }

    private void init(){
        final ImageView share_image = (ImageView) findViewById(R.id.share_image);
        ImageButton cutout_1_close_btn = (ImageButton)findViewById(R.id.cutout_1_close_btn);
        Button back_main = (Button)findViewById(R.id.back_main);

        tempbitmap = BitmapCache.getBitmapcache();
        share_image.setImageBitmap(tempbitmap);

        back_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ShareActivity.this, MainActivity.class));
            }
        });
        cutout_1_close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        api = WXAPIFactory.createWXAPI(this, ShareUtils.getMetaDataValue("WXAPPID", this));
        initViews();

    }

    private void initViews(){
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
        int newWidth = THUMB_SIZE;
        int newHeight = THUMB_SIZE*height/width;

        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // if you want to rotate the Bitmap
        // matrix.postRotate(45);
        Bitmap resizedBitmap = Bitmap.createBitmap(BitmapOrg, 0, 0, width,
                height, matrix, true);
        return resizedBitmap;
    }

}
