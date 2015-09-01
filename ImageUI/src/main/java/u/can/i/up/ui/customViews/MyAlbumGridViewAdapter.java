package u.can.i.up.ui.customViews;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import u.can.i.up.ui.R;


public class MyAlbumGridViewAdapter extends BaseAdapter {

        private Context context;

        private ArrayList<Bitmap> imageList = new ArrayList<Bitmap>();

        private LayoutInflater inflate;

//		LinearLayout.LayoutParams params;

        public MyAlbumGridViewAdapter(Context context,ArrayList<Bitmap> ImageList){

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
                convertView.setTag(itemViewTag);

            } else {
                itemViewTag = (ItemViewTag) convertView.getTag();
            }
            // set icon
            itemViewTag.iv_face.setImageBitmap(imageList.get(position));
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
