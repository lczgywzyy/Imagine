package u.can.i.up.ui.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.PhotoSinglePickerActivity;
import me.iwf.photopicker.utils.PhotoPickerIntent;
import me.iwf.photopicker.utils.PhotoSinglePickerIntent;
import u.can.i.up.ui.R;
import u.can.i.up.ui.utils.BitmapCache;
import u.can.i.up.ui.utils.ImageViewImpl_PearlBuild;

/**
 * @author dongfeng
 * @data 2015.07.15
 * @sumary 串珠组装界面：选择素珠，颗数，形状，搭配出一串珠子
 */
public class PearlBuildActivity extends Activity  implements View.OnClickListener {

    private EditText ballnum;
    private Button preview;
    private ImageButton add_pearl;
    private ImageButton setover;
    private ImageButton closeBtm;
    public final static int REQUEST_CODE = 1;
    public ImageViewImpl_PearlBuild pearlBuild;
    private ArrayList<String> selectedPhotos = new ArrayList<>();
    //传递到下个界面的东西
    private String suzhu_path;
    private int suzhu_num;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pearlbuild);
        initView();
    }

    /**
     * 初始化组件
     */
    private void initView() {
        //init view
        ballnum = (EditText)findViewById(R.id.ballnumb);
        preview = (Button)findViewById(R.id.pearlbuildpreview);
        add_pearl = (ImageButton)findViewById(R.id.pearlbuild_add);
        pearlBuild = (ImageViewImpl_PearlBuild)findViewById(R.id.ImageViewImpl_PearlBuild);
        setover = (ImageButton)findViewById(R.id.pearlbuild_continue_btn);
        closeBtm = (ImageButton)findViewById(R.id.pearlbuild_close_btn);

        this.setRegion(ballnum);


        String photoPath = getIntent().getStringExtra("photoUri");
        pearlBuild.setBmpSuzhu(BitmapFactory.decodeFile(photoPath));
        suzhu_path = photoPath;

        preview.setOnClickListener(this);
        add_pearl.setOnClickListener(this);
        setover.setOnClickListener(this);
        closeBtm.setOnClickListener(this);
    }

    @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        List<String> photos = null;
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            if (data != null) {
                photos = data.getStringArrayListExtra(PhotoSinglePickerActivity.KEY_SELECTED_PHOTOS);
            }
            selectedPhotos.clear();

            if (photos != null) {

                selectedPhotos.addAll(photos);
            }
            //加载temp
            pearlBuild.setBmpSuzhu(BitmapFactory.decodeFile(selectedPhotos.get(0)));
            suzhu_path = selectedPhotos.get(0);
            String tag = "path: " + selectedPhotos.get(0);
            Log.e("path", tag);
        }
    }

    @Override
    public void onClick(View v) {
        //s为颗数输入字符，用于判定输入
        String s = ballnum.getText().toString();
        switch (v.getId()) {
            case R.id.pearlbuild_add:
            {
//                PhotoPickerIntent intent = new PhotoPickerIntent(PearlBuildActivity.this);
                PhotoSinglePickerIntent intent = new PhotoSinglePickerIntent(PearlBuildActivity.this);
                intent.setPhotoCount(1);
                intent.setShowCamera(false);
                intent.setShowGif(false);
                startActivityForResult(intent, REQUEST_CODE);
                break;
            }
            case R.id.pearlbuildpreview: {
                if (s != null && !s.equals("")){
                    suzhu_num = Integer.parseInt(s);
                    if (suzhu_num < 10 ) {
                        Toast.makeText(getBaseContext(), "颗数不能低于10", Toast.LENGTH_SHORT).show();
                    ballnum.setHintTextColor(Color.RED);
                    ballnum.setHint(R.string.suzhu_num_hint);

                    }else {
                        pearlBuild.updateImage(suzhu_num);
                    }
                } else {
                    new AlertDialog.Builder(this)
                            .setTitle("素珠确认")
                            .setMessage("请输入素珠个数")
                            .setPositiveButton("确定", null)
                            .show();
                }
//                suzhu_num = Integer.parseInt(ballnum.getText().toString());
////                String a = ballnum.getText().toString();
////                int inputNum = Integer.parseInt(a);
//                ballnum.setText(null);
//                if (suzhu_num >= 10 && suzhu_num <= 120) {
//                    ballnum.setHintTextColor(Color.BLACK);
//                    ballnum.setHint(R.string.suzhu_num_hint);
//                    pearlBuild.updateImage(suzhu_num);
//
//                } else {
//                    int withs = ballnum.getWidth();
//                    ballnum.setHintTextColor(Color.RED);
//                    ballnum.setHint(R.string.suzhu_num_hint);
//                    ballnum.setWidth(withs);
//                }
                break;
            }
            case R.id.pearlbuild_continue_btn:
            {
//                suzhu_num = Integer.parseInt(ballnum.getText().toString());
//                ballnum.setText(null);
                if (s != null && !s.equals("")){
                    suzhu_num = Integer.parseInt(s);
                    if (suzhu_num < 10 ) {
                        Toast.makeText(getBaseContext(), "颗数不能低于10", Toast.LENGTH_SHORT).show();
                        ballnum.setHintTextColor(Color.RED);
                        ballnum.setHint(R.string.suzhu_num_hint);

                    }else {
                        Intent i = new Intent(PearlBuildActivity.this, PearlBuildCollocateActivity.class);
                        i.putExtra("suzhu_path", suzhu_path);
                        i.putExtra("suzhu_num", suzhu_num);
                        startActivity(i);
                    }
                }else {
                    new AlertDialog.Builder(this)
                            .setTitle("素珠确认")
                            .setMessage("请输入素珠个数")
                            .setPositiveButton("确定", null)
                            .show();
                }
                break;
            }

            default:
        }

    }


    //设置颗数范围
    private int MIN_MARK = 10;
    private int MAX_MARK = 120;
    //private void setRegion(EditText et)
    private void setRegion( final EditText et)
    {
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (start > 1)
                {
                    if (MIN_MARK != -1 && MAX_MARK != -1)
                    {
                        int num = Integer.parseInt(s.toString());
                        if (num > MAX_MARK)
                        {
                            s = String.valueOf(MAX_MARK);
                            et.setText(s);
                        }
                        else if(num < MIN_MARK)
                            s = String.valueOf(MIN_MARK);
                        return;
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s)
            {
                if (s != null && !s.equals(""))
                {
                    if (MIN_MARK != -1 && MAX_MARK != -1)
                    {
                        int markVal = 0;
                        try
                        {
                            markVal = Integer.parseInt(s.toString());
                        }
                        catch (NumberFormatException e)
                        {
                            markVal = 0;
                        }
//                        if (markVal < MIN_MARK)
//                        {
//                            Toast.makeText(getBaseContext(), "颗数不能低于10", Toast.LENGTH_SHORT).show();
//                            et.setText(String.valueOf(MIN_MARK));
//                        }
                        if (markVal > MAX_MARK)
                        {
                            Toast.makeText(getBaseContext(), "颗数不能超过120", Toast.LENGTH_SHORT).show();
                            et.setText(String.valueOf(MAX_MARK));
                        }
                        return;
                    }
                }
            }
        });
    }


}
