package u.can.i.up.ui.customViews;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import u.can.i.up.ui.R;
import u.can.i.up.ui.application.IApplication;
import u.can.i.up.ui.beans.TMaterial;
import u.can.i.up.ui.utils.IBitmapCache;

public class ListViewAdapter extends BaseAdapter {
    private final Context mContext;

    private ArrayList<TMaterial> tMaterialArrayList;

    public ListViewAdapter(Context context) {
        mContext = context;
        tMaterialArrayList=((IApplication)((Activity)context).getApplication()).arrayListTMaterial;
    }

    @Override
    public int getCount() {
        return tMaterialArrayList.size();
    }

    @Override
    public TMaterial getItem(int position) {
        return tMaterialArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_library_type, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.mTextView = (TextView) convertView.findViewById(android.R.id.text1);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        String name = tMaterialArrayList.get(position).getTMaterialName();
        viewHolder.mTextView.setText(name);
        IBitmapCache.BitmapAsync bitmapAsync=new IBitmapCache.BitmapAsync(viewHolder.mTextView,mContext);

        bitmapAsync.execute(null,tMaterialArrayList.get(position).getTMaterialMd(),"left");
        return convertView;
    }

    private static class ViewHolder {
        public TextView mTextView;
    }
}