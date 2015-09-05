package u.can.i.up.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import u.can.i.up.ui.R;
import u.can.i.up.ui.application.IApplication;
import u.can.i.up.ui.beans.PearlBeans;
import u.can.i.up.ui.customViews.ImageItem;
import u.can.i.up.ui.utils.IBitmapCache;
import u.can.i.up.utils.image.Pearl;

/**
 * @author dongfeng
 * @data 2015.06.13
 * @sumary 本地素材界面：列表显示素材概要
 */

public class LibirarydisplayActivity extends AppCompatActivity {
    private GridView gridView;
    private GridViewAdapter gridAdapter;

    private ArrayList<PearlBeans> pearlBeansArrayListSp =new ArrayList<>();

    private ArrayList<PearlBeans> pearlBeansArrayList =new ArrayList<>();

    private int type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_libirary_display);
        setBar();
        getType();
        getPearlBeans();
        setGridView();
    }

    private void setBar(){
        final Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.library_display);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void getType(){
        int position=getIntent().getIntExtra("position",0);

        type=((IApplication)getApplication()).arrayListTMaterial.get(position).getTMaterialId();

    }
    private void getPearlBeans(){

        pearlBeansArrayList=((IApplication)getApplication()).arrayListPearlBeans;
        Iterator<PearlBeans> iterator=pearlBeansArrayList.iterator();

        while(iterator.hasNext()){
            PearlBeans pearlBeans=iterator.next();
            if(pearlBeans.getCategory()==type){
                pearlBeansArrayListSp.add(pearlBeans);
            }
        }
    }

    private void setGridView(){
        gridView = (GridView) findViewById(R.id.gridView_library);
        gridAdapter = new GridViewAdapter(this,pearlBeansArrayListSp);
        gridView.setAdapter(gridAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                PearlBeans item = (PearlBeans) parent.getItemAtPosition(position);

                //Create intent
                Intent intent = new Intent(LibirarydisplayActivity.this, LibiraryDetailActivity.class);
                intent.putExtra("pearl",pearlBeansArrayListSp.get(position));
                //Start details activity
                startActivity(intent);
            }
        });
    }

    class GridViewAdapter extends BaseAdapter {



        private Context context;

        private List<PearlBeans> pearlBeansArrayList;

        private LayoutInflater inflate;

        public GridViewAdapter(Context context,List<PearlBeans> pearlBeansArrayList){

            this.context=context;
            this.pearlBeansArrayList = pearlBeansArrayList;
            this.inflate=LayoutInflater.from(context);
        }
        public int getCount() {
            return pearlBeansArrayList.size();
        }
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder=null;
            if(convertView == null) {
                viewHolder=new ViewHolder();
                convertView=inflate.inflate(R.layout.grid_item_layout, null);
                viewHolder.iv_face=(ImageView)convertView.findViewById(R.id.grid_image);
                viewHolder.title=(TextView)convertView.findViewById(R.id.grid_text);

                IBitmapCache.BitmapAsync bitmapAsync=new IBitmapCache.BitmapAsync(viewHolder.iv_face);

                bitmapAsync.execute(pearlBeansArrayListSp.get(position).getPath(), pearlBeansArrayListSp.get(position).getMD5(),"img");

                viewHolder.title.setText(pearlBeansArrayListSp.get(position).getName());

                convertView.setTag(viewHolder);

            } else {
                viewHolder=(ViewHolder)convertView.getTag();
            }



            return convertView;
        }
        public PearlBeans getItem(int position) {
            return pearlBeansArrayList.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        class ViewHolder {

            public ImageView iv_face;

            public TextView title;
        }
    }
}
