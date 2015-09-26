package u.can.i.up.ui.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.avast.android.dialogs.fragment.SimpleDialogFragment;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.SendMessageToWX;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.mm.sdk.openapi.WXImageObject;
import com.tencent.mm.sdk.openapi.WXMediaMessage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import me.iwf.photopicker.event.OnItemCheckListener;
import u.can.i.up.ui.R;
import u.can.i.up.ui.application.IApplication;
import u.can.i.up.ui.application.IApplicationConfig;
import u.can.i.up.ui.beans.PearlBeans;
import u.can.i.up.ui.beans.TMaterial;
import u.can.i.up.ui.dbs.PSQLiteOpenHelper;
import u.can.i.up.ui.utils.BitmapCache;
import u.can.i.up.ui.utils.IBitmapCache;
import u.can.i.up.utils.image.ImageUtils;
import u.can.i.up.utils.image.MD5Utils;
import u.can.i.up.utils.image.ShareUtils;

/**
 * @author dongfeng
 * @data 2015.09.26
 * @sumary 抠图保存界面：抠取完成后，将素材分类保存至素材库
 */

public class CutoutSaveActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageButton imageButton_back;

    private Bitmap tempbitmap;

    private Button btn_save, btn_backmain;

    private EditText editText_name, editText_material, editText_size;

    private Spinner spinner_type;
    private ArrayAdapter arrayAdapter;
    private int positionSelect=-1;
    private ImageView imageview;

    private PearlBeans pearlBeans;
    private ArrayList<TMaterial> tMaterials;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cutoutsave);
        initView();
    }

    private void initView(){
        imageview = (ImageView) findViewById(R.id.cutoutsave_image);
        imageButton_back = (ImageButton)findViewById(R.id.cutoutsave_back);
        btn_backmain = (Button)findViewById(R.id.cutoutsave_backmain);
        btn_save = (Button)findViewById(R.id.cutoutsave_save);

        editText_name = (EditText)findViewById(R.id.cutoutsave_name);
        editText_material = (EditText)findViewById(R.id.cutoutsave_material);
        editText_size = (EditText)findViewById(R.id.cutoutsave_size);
        spinner_type = (Spinner)findViewById(R.id.cutoutsave_type);
        //需要修改，使用系统的TMaterial中的TMaterialName来装载arrayAdapter
        arrayAdapter = ArrayAdapter.createFromResource(this, R.array.cutoutsave_type, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_type.setAdapter(arrayAdapter);
        spinner_type.setVisibility(View.VISIBLE);
        //显示抠取结果
        tempbitmap = BitmapCache.getBitmapcache();
        imageview.setImageBitmap(tempbitmap);

        imageButton_back.setOnClickListener(this);
        btn_backmain.setOnClickListener(this);
        btn_save.setOnClickListener(this);
        spinner_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                positionSelect = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cutoutsave_back:
                finish();
                break;
            case R.id.cutoutsave_backmain:
                Intent intent= new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            case R.id.cutoutsave_save:
            {
                if (positionSelect != -1 ){
                    createPearBeans();
                    try {
                        savePearlBeans(tempbitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else {
                    SimpleDialogFragment.createBuilder(getBaseContext(), getSupportFragmentManager())
                            .setTitle("注意").setMessage("请选择素材分类")
                            .setNegativeButtonText("确认")
                            .show();
                }


                break;
            }
            default:
                break;
        }
    }


    private void createPearBeans(){
        tMaterials= ((IApplication)getApplication()).arrayListTMaterial;
        pearlBeans = new PearlBeans();
        pearlBeans.setSize(editText_size.getText().toString());
        pearlBeans.setCategory(tMaterials.get(positionSelect).getTMaterialId());
        pearlBeans.setName(editText_name.getText().toString());
        pearlBeans.setType(2);

        pearlBeans.setWeight("");
        pearlBeans.setDescription("");
        pearlBeans.setMaterial(editText_material.getText().toString());
        pearlBeans.setAperture("");
        pearlBeans.setPrice("1000");
    }


    private void savePearlBeans(Bitmap bitmap) throws IOException {

        Bitmap bitmapStore=Bitmap.createScaledBitmap(bitmap, 120,120, true);
        byte[] bytes= IBitmapCache.Bitmap2Bytes(bitmapStore);
        String md5= MD5Utils.getMD5String(bytes);
        bitmapStore.recycle();
        FileOutputStream os = this.openFileOutput(md5, Context.MODE_PRIVATE);
        os.write(bytes);
        os.close();
        pearlBeans.setMD5(md5);
        pearlBeans.setPath("/static/img/png/" + md5);
        PSQLiteOpenHelper psqLiteOpenHelper=new PSQLiteOpenHelper(this);
        psqLiteOpenHelper.addPearl(pearlBeans);
        ((IApplication)getApplication()).arrayListPearlBeans.add(pearlBeans);
        Toast.makeText(this,"素材获取成功",Toast.LENGTH_LONG).show();
    }
}
