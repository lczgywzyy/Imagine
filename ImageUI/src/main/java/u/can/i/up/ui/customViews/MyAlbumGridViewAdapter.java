package u.can.i.up.ui.customViews;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

import in.srain.cube.util.LocalDisplay;
import u.can.i.up.ui.R;


public class MyAlbumGridViewAdapter extends BaseAdapter {
//        private static final int sGirdImageSize = (LocalDisplay.SCREEN_WIDTH_PIXELS - LocalDisplay.dp2px(12 + 12 + 10)) / 2;
        private Context context;

        private ArrayList<String> imageList = new ArrayList<String>();

        private LayoutInflater inflate;

//		LinearLayout.LayoutParams params;

        public MyAlbumGridViewAdapter(Context context,ArrayList<String> ImageList){

            this.context=context;
            this.imageList = ImageList;
            this.inflate=LayoutInflater.from(context);
//			params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//			params.gravity = Gravity.CENTER;

        }

        public int getCount() {
            return imageList.size();
        }

        public Object getItem(int position) {
            return imageList.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ItemViewTag itemViewTag;
            if(convertView == null) {
                convertView=inflate.inflate(R.layout.grid_myalbum_item_layout, null);
                itemViewTag = new ItemViewTag((ImageView)convertView.findViewById(R.id.myalbum_grid_image));

//                LinearLayout.LayoutParams lyp = new LinearLayout.LayoutParams(sGirdImageSize, sGirdImageSize);
//                itemViewTag.iv_face.setLayoutParams(lyp);
//                itemViewTag.iv_face.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                convertView.setTag(itemViewTag);

            } else {
                itemViewTag = (ItemViewTag) convertView.getTag();
            }
            // set icon

            itemViewTag.iv_face.setImageBitmap(BitmapFactory.decodeFile(imageList.get(position)));
//			itemViewTag.iv_face.setLayoutParams(params);
            return convertView;
        }


        class ItemViewTag {

            protected ImageView iv_face;

            public ItemViewTag(ImageView icon)
            {
                this.iv_face = icon;
            }
        }

}
