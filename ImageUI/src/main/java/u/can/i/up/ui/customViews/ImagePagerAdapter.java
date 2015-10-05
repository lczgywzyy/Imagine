/**
 * ImagePagerAdapter.java
 * ImageChooser
 * 
 * Created by likebamboo on 2014-4-22
 * Copyright (c) 1998-2014 http://likebamboo.github.io/ All rights reserved.
 */

package u.can.i.up.ui.customViews;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;


import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import u.can.i.up.ui.R;
import u.can.i.up.ui.application.IApplicationConfig;
import u.can.i.up.ui.utils.FileUtil;
import uk.co.senab.photoview.PhotoView;


/**
 * 查看大图的ViewPager适配�? * 
 * @author likebamboo
 */
public class ImagePagerAdapter extends PagerAdapter {
    /**
     * 数据�?     */
    private List<String> mDatas = new ArrayList<String>();

    /**
     * UIL的ImageLoader
     */
    private ImageLoader mImageLoader = ImageLoader.getInstance();

    /**
     * 显示参数
     */
    private DisplayImageOptions mOptions = null;


    public ImagePagerAdapter(ArrayList<String> dataList) {
        mDatas = dataList;
        mOptions = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.pic_thumb)
                .showImageForEmptyUri(R.drawable.pic_thumb).showImageOnFail(R.drawable.pic_thumb)
                .cacheInMemory(true).cacheOnDisc(true).build();
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {
        PhotoView photoView = new PhotoView(container.getContext());
        photoView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));
        String imgPath = (String)getItem(position);
        mImageLoader.displayImage(FileUtil.getFormatFilePath(IApplicationConfig.DIRECTORY_IMAGE_COLLOCATE+ File.separator+imgPath+".jpg"), (ImageView)photoView,
                mOptions, null);
        // Now just add PhotoView to ViewPager and return it
        container.addView(photoView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        return photoView;
    }

    public Object getItem(int position) {
        if (position < mDatas.size()) {
            return mDatas.get(position);
        } else {
            return null;
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

}
