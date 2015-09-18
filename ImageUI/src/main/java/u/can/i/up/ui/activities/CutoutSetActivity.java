package u.can.i.up.ui.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.avast.android.dialogs.fragment.SimpleDialogFragment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.utils.PhotoPickerIntent;
import u.can.i.up.ui.R;
import u.can.i.up.ui.application.IApplication;
import u.can.i.up.ui.beans.PearlBeans;
import u.can.i.up.ui.beans.TMaterial;
import u.can.i.up.ui.utils.IBitmapCache;


/**
 * @author dongfeng
 * @data 2015.06.13
 * @sumary 素材制作选择界面：用户选择素材，进入抠图界面
 */
public class CutoutSetActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    private Button testcutout;

    private GridView gridView;

    private ArrayList<TMaterial> tMaterials;

    public final static int REQUEST_CODE = 1;
    private int positionSelect=-1;

    private PearlBeans pearlBeans;

    private EditText edtName;
    private EditText edtSize;
    private EditText edtMaterial;

    private ArrayList<String> selectedPhotos = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragmemt_cutout);
        initView();
    }

    private void initView(){
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.material_build);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        testcutout = (Button)findViewById(R.id.testcutout);
        testcutout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (positionSelect != -1 ){
                    PhotoPickerIntent intent = new PhotoPickerIntent(CutoutSetActivity.this);
                    intent.setPhotoCount(1);
                    intent.setShowCamera(true);
                    startActivityForResult(intent, REQUEST_CODE);
                }else {
                    SimpleDialogFragment.createBuilder(getBaseContext(), getSupportFragmentManager())
                            .setTitle("注意").setMessage("请选择素材分类")
                            .setNegativeButtonText("确认")
                            .show();
                }

            }
        });
        gridView=(GridView)findViewById(R.id.gridView);
        edtName=(EditText)findViewById(R.id.p_name);
        edtSize=(EditText)findViewById(R.id.p_size);
        setGridView();

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<String> photos = null;
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            if (data != null) {
                photos = data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);
            }
            selectedPhotos.clear();

            if (photos != null) {

                selectedPhotos.addAll(photos);
            }
//            Uri imageUri = Uri.fromFile(new File(selectedPhotos.get(0)));
            String path = selectedPhotos.get(0);
            pearlBeans=new PearlBeans();
            pearlBeans.setSize(edtSize.getText().toString());
            pearlBeans.setCategory(tMaterials.get(positionSelect).getTMaterialId());
            pearlBeans.setName(edtName.getText().toString());
            pearlBeans.setType(2);

            pearlBeans.setWeight("");
            pearlBeans.setDescription("");
            pearlBeans.setMaterial("玉石");
            pearlBeans.setAperture("");


            Intent newdata = new Intent(CutoutSetActivity.this, CutoutActivity.class);
            newdata.putExtra("photo_path", path);
            newdata.putExtra("pearl_beans",pearlBeans);
            startActivity(newdata);
        }
    }



    private void setGridView(){
        tMaterials= ((IApplication)getApplication()).arrayListTMaterial;
        gridView.setNumColumns(5);
        gridView.setBackgroundColor(Color.TRANSPARENT);
        gridView.setHorizontalSpacing(1);
        gridView.setVerticalSpacing(1);
        gridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
        gridView.setCacheColorHint(0);
        gridView.setPadding(5, 0, 5, 0);
        gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        gridView.setGravity(Gravity.CENTER);
        gridView.setChoiceMode(GridView.CHOICE_MODE_SINGLE);
        GridViewAdapters gridViewAdapter=new GridViewAdapters(this,tMaterials);
        gridView.setAdapter(gridViewAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                positionSelect=position;
            }
        });

    }


    class GridViewAdapters extends  BaseAdapter{



        private Context context;

        private List<TMaterial> tmaterialArrayList;


        public GridViewAdapters(Context context,List<TMaterial> pearlBeansArrayList){

            this.context=context;
            this.tmaterialArrayList = pearlBeansArrayList;
        }
        public int getCount() {
            return tmaterialArrayList.size();
        }
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_library_type, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.mTextView = (TextView) convertView.findViewById(android.R.id.text1);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            String name = tmaterialArrayList.get(position).getTMaterialName();
            viewHolder.mTextView.setText(name);
            IBitmapCache.BitmapAsync bitmapAsync=new IBitmapCache.BitmapAsync(viewHolder.mTextView,CutoutSetActivity.this);

            bitmapAsync.execute(null, tmaterialArrayList.get(position).getTMaterialMd(),"bottom");
            return convertView;
        }
        public TMaterial getItem(int position) {
            return tmaterialArrayList.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        class ViewHolder {
            private TextView mTextView;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
