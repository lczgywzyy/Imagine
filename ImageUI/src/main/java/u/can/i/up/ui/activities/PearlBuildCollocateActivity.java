package u.can.i.up.ui.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import java.util.ArrayList;

import u.can.i.up.ui.R;
import u.can.i.up.ui.application.IApplication;
import u.can.i.up.ui.beans.PearlBeans;
import u.can.i.up.ui.beans.TMaterial;
import u.can.i.up.ui.fragments.CollocateTabFragment;
import u.can.i.up.ui.fragments.PearlBuildCollocateTabFragment;
import u.can.i.up.ui.utils.BitmapCache;
import u.can.i.up.ui.utils.IBitmapCache;
import u.can.i.up.ui.utils.ImageViewImpl_PearlBuild;


/**
 * @author dongfeng
 * @data 2015.08.30
 * @sumary 自由组珠搭配界面：素珠生成完毕，往素珠中选素材，加入组珠中
 */

public class PearlBuildCollocateActivity extends FragmentActivity implements View.OnClickListener {

    private final String TAG = this.getClass().getName();
    /**
     * FragmentTabhost
     */
    private FragmentTabHost mTabHost;

    /**
     * 布局填充器
     *
     */
    private LayoutInflater mLayoutInflater;

    private ArrayList<PearlBeans> pearlBeansArrayList =new ArrayList<>();

    private ArrayList<TMaterial> tMaterialArrayList = new ArrayList<>();

    private ImageViewImpl_PearlBuild pearlbuild_collocate;

    private ImageButton setover;

    private ImageButton closeBtm;

    //上个个界面传递过来的东西
    private String suzhu_path;
    private int suzhu_num;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pearlbuild_image_collocate);
        //赋值
        suzhu_path = getIntent().getStringExtra("suzhu_path");
        suzhu_num = getIntent().getIntExtra("suzhu_num", 0);
//        suzhu_num = Integer.parseInt(getIntent().getStringExtra("suzhu_num"));
        initView();
        initTabView();
        pearlbuild_collocate.setBmpSuzhu(BitmapFactory.decodeFile(suzhu_path));
        pearlbuild_collocate.updateImage(suzhu_num);
    }

    /**
     * 初始化组件
     */
    private void initView() {
        pearlBeansArrayList =((IApplication)getApplication()).arrayListPearlBeans;
        tMaterialArrayList=((IApplication)getApplication()).arrayListTMaterial;
        //init view
        pearlbuild_collocate = (ImageViewImpl_PearlBuild) findViewById(R.id.ImageViewImpl_PearlBuild_allocate);
        pearlbuild_collocate.setBmpBack(BitmapFactory.decodeFile(getIntent().getStringExtra("bmp")));
        setover = (ImageButton)findViewById(R.id.pearlbuild_collocate_continue_btn);
        closeBtm = (ImageButton)findViewById(R.id.pearlbuild_collocate_close_btn);
        setover.setOnClickListener(this);
        closeBtm.setOnClickListener(this);
        //给出值
        BitmapCache.setImageViewImpl_PearlBuild(pearlbuild_collocate);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.pearlbuild_collocate_continue_btn:
                Bitmap mypic = pearlbuild_collocate.saveBitmapAll();
//                ShareActivity.exportImageByFinger(mypic);

                BitmapCache.setBitmapcache(mypic);
                startActivity(new Intent(PearlBuildCollocateActivity.this, ImageCollocateActivity.class));
                break;
            case R.id.pearlbuild_collocate_close_btn:

                break;
            default:

        }
    }

    /**
     * 初始化组件
     */
    private void initTabView() {
        mLayoutInflater = LayoutInflater.from(this);
        // 找到TabHost
        mTabHost = (FragmentTabHost) findViewById(R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        // 得到fragment的个数
        int count = tMaterialArrayList.size();
        for (int i = 0; i < count; i++) {
            // 给每个Tab按钮设置图标、文字和内容
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(tMaterialArrayList.get(i).getTMaterialName())
                    .setIndicator(getTabItemView(i));
            // 将Tab按钮添加进Tab选项卡中
            Bundle bundle=new Bundle();
            mTabHost.addTab(tabSpec, PearlBuildCollocateTabFragment.class, bundle);
            // 设置Tab按钮的背景
            mTabHost.getTabWidget().getChildAt(i)
                    .setBackgroundResource(R.drawable.selector_tab_background);
        }
    }

    /**
     *
     * 给每个Tab按钮设置图标和文字
     */
    private View getTabItemView(int index) {
        View view = mLayoutInflater.inflate(R.layout.item_navigator_material_selected, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
        imageView.setImageBitmap(IBitmapCache.getBitMapCache(this.getApplicationContext()).getBitmap(null,tMaterialArrayList.get(index).getTMaterialMd()));
        TextView textView = (TextView) view.findViewById(R.id.textview);
        textView.setText(tMaterialArrayList.get(index).getTMaterialName());

        return view;
    }

}
