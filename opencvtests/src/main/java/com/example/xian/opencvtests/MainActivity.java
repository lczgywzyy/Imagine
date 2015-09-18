package com.example.xian.opencvtests;


        import android.app.Activity;
        import android.graphics.Bitmap;
        import android.graphics.drawable.BitmapDrawable;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.ImageView;

public class MainActivity extends Activity {
    /** Called when the activity is first created. */
    ImageView imgView;
    Button btnNDK, btnRestore;
    public static native int[] ImgFun(int[] buf, int w, int h);
    static {
        System.loadLibrary("opencv_java3");
        System.loadLibrary("ImgFun");
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_manipulations_surface_view);

        this.setTitle("使用NDK转换灰度图");
        btnRestore = (Button) this.findViewById(R.id.btnRestore);
        //btnRestore.setText(ImgFun());
        btnRestore.setOnClickListener(new ClickEvent());
        btnNDK = (Button) this.findViewById(R.id.btnNDK);
        btnNDK.setOnClickListener(new ClickEvent());
        imgView = (ImageView) this.findViewById(R.id.imageView);
        Bitmap img = ((BitmapDrawable) getResources().getDrawable(
                R.mipmap.ic_launcher)).getBitmap();
        imgView.setImageBitmap(img);
    }

    class ClickEvent implements View.OnClickListener {
        public void onClick(View v) {
            //btnRestore.setText(ImgFun());
            if (v == btnNDK) {
                long current = System.currentTimeMillis();
                Bitmap img1 = ((BitmapDrawable) getResources().getDrawable(
                        R.mipmap.ic_launcher)).getBitmap();
                int w = img1.getWidth(), h = img1.getHeight();
                int[] pix = new int[w * h];
                img1.getPixels(pix, 0, w, 0, 0, w, h);
                int[] resultInt = ImgFun(pix, w, h);
                Bitmap resultImg = Bitmap.createBitmap(w, h, Bitmap.Config.RGB_565);
                resultImg.setPixels(resultInt, 0, w, 0, 0, w, h);
                long performance = System.currentTimeMillis() - current;
                imgView.setImageBitmap(resultImg);
            } else if (v == btnRestore) {
                Bitmap img2 = ((BitmapDrawable) getResources().getDrawable(
                        R.mipmap.ic_launcher)).getBitmap();
                imgView.setImageBitmap(img2);
            }
        }
    }


}