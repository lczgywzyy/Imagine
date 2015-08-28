package u.can.i.up.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import u.can.i.up.ui.R;
import u.can.i.up.ui.application.IApplication;
import u.can.i.up.ui.beans.PearlBeans;
import u.can.i.up.ui.mypullrefresh.OnLoadListener;
import u.can.i.up.ui.mypullrefresh.OnRefreshListener;
import u.can.i.up.ui.mypullrefresh.RefreshGridView;
import u.can.i.up.ui.utils.IBitmapCache;

public class MyPullRefreshFragment extends Fragment {


    private GridViewAdapter gridAdapter;
    private int type;

    private ArrayList<PearlBeans> pearlBeansArrayListSp =new ArrayList<>();

    private ArrayList<PearlBeans> pearlBeansArrayList =new ArrayList<>();

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }
    public static MyPullRefreshFragment newInstance(Bundle bundle)
    {
        MyPullRefreshFragment myPullRefreshFragment = new MyPullRefreshFragment();

        if (bundle != null)
        {
            myPullRefreshFragment.setArguments(bundle);
        }

        return myPullRefreshFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {

        final RefreshGridView gv = new RefreshGridView(getActivity());
        //准备数据
        type = 1;
        getPearlBeans();
        //设置适配器
        gridAdapter = new GridViewAdapter(getActivity(),pearlBeansArrayListSp);
        gv.setAdapter(gridAdapter);

        gv.setOnRefreshListener(new OnRefreshListener() {

            @Override
            public void onRefresh() {
                Toast.makeText(getActivity().getApplicationContext(), "refreshing", Toast.LENGTH_SHORT)
                        .show();

                gv.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        // 更新数据
//                        getPearlBeans();
//                        gridAdapter.notifyDataSetChanged();
                        gv.refreshComplete();
                    }
                }, 1500);
            }
        });

        // 不设置的话到底部不会自动加载
        gv.setOnLoadListener(new OnLoadListener() {

            @Override
            public void onLoadMore() {
                Toast.makeText(getActivity().getApplicationContext(), "loading", Toast.LENGTH_SHORT)
                        .show();

                gv.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        getPearlBeans();
                        gridAdapter.notifyDataSetChanged();
//                        datas.add(new Date().toGMTString());
//                        adapter.notifyDataSetChanged();
                        // 加载完后调用该方法
                        gv.loadCompelte();
                    }
                }, 1500);
            }
        });
        return gv;
    }
    private void getPearlBeans(){
        pearlBeansArrayList=((IApplication)getActivity().getApplication()).arrayListPearlBeans;
        Iterator<PearlBeans> iterator=pearlBeansArrayList.iterator();
        while(iterator.hasNext()){
            PearlBeans pearlBeans=iterator.next();
            if(pearlBeans.getCategory()==type){
                pearlBeansArrayListSp.add(pearlBeans);
            }
        }
    }


//    private void setGridView(){
//
//
//        gridView.setAdapter(gridAdapter);
//        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
//                PearlBeans item = (PearlBeans) parent.getItemAtPosition(position);
//                Toast.makeText(getView().getContext(),"123",Toast.LENGTH_SHORT);
////                //Create intent
////                Intent intent = new Intent(LibirarydisplayActivity.this, LibiraryDetailActivity.class);
////                intent.putExtra("pearl",pearlBeansArrayListSp.get(position));
////                //Start details activity
////                startActivity(intent);
//            }
//        });
//    }

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
