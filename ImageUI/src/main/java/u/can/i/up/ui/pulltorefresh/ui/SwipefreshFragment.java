package u.can.i.up.ui.pulltorefresh.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import u.can.i.up.ui.R;
import u.can.i.up.ui.activities.LibiraryDetailActivity;
import u.can.i.up.ui.application.IApplication;
import u.can.i.up.ui.beans.PearlBeans;
import u.can.i.up.ui.pulltorefresh.swiperefreshload.RefreshLayout;
import u.can.i.up.ui.utils.IBitmapCache;

public class SwipefreshFragment extends Fragment {

    private GridView gridView;
    private GridViewAdapter gridAdapter;
    private int type;

    private ArrayList<PearlBeans> pearlBeansArrayListSp =new ArrayList<>();

    private ArrayList<PearlBeans> pearlBeansArrayList =new ArrayList<>();

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }
    public static SwipefreshFragment newInstance(Bundle bundle)
    {
        SwipefreshFragment swipefreshFragment = new SwipefreshFragment();

        if (bundle != null)
        {
            swipefreshFragment.setArguments(bundle);
        }

        return swipefreshFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {

        View view = inflater.inflate(R.layout.fragment_refresh, container, false);
        gridView = (GridView) view.findViewById(R.id.refresh_gridView);
        type = 1;
        getPearlBeans();
        setGridView();

//        // 模拟一些数据
//        final List<String> datas = new ArrayList<String>();
//        for (int i = 0; i < 20; i++) {
//            datas.add("item - " + i);
//        }

//        // 构造适配器
//        final BaseAdapter adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, datas);
//        // 获取listview实例
//        ListView listView = (ListView) view.findViewById(R.id.listview);
//        listView.setAdapter(adapter);

        // 获取RefreshLayout实例
        final RefreshLayout myRefreshListView = (RefreshLayout)
                view.findViewById(R.id.swipe_layout);

        // 设置下拉刷新时的颜色值,颜色值需要定义在xml中
        myRefreshListView
                .setColorScheme(R.color.red,
                        R.color.black, R.color.blue,
                        R.color.green);
        // 设置下拉刷新监听器
        myRefreshListView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {

                Toast.makeText(getActivity(), "refresh", Toast.LENGTH_SHORT).show();

                myRefreshListView.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        // 更新数据
                        getPearlBeans();
                        gridAdapter.notifyDataSetChanged();
//                        datas.add(new Date().toGMTString());
//                        adapter.notifyDataSetChanged();
                        // 更新完后调用该方法结束刷新
                        myRefreshListView.setRefreshing(false);
                    }
                }, 1000);
            }
        });

        // 加载监听器
        myRefreshListView.setOnLoadListener(new RefreshLayout.OnLoadListener() {

            @Override
            public void onLoad() {

                Toast.makeText(getActivity(), "load", Toast.LENGTH_SHORT).show();

                myRefreshListView.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        getPearlBeans();
                        gridAdapter.notifyDataSetChanged();
//                        datas.add(new Date().toGMTString());
//                        adapter.notifyDataSetChanged();
                        // 加载完后调用该方法
                        myRefreshListView.setLoading(false);
                    }
                }, 1500);

            }
        });
        return view;
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


    private void setGridView(){
        gridAdapter = new GridViewAdapter(getActivity(),pearlBeansArrayListSp);
        gridView.setAdapter(gridAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                PearlBeans item = (PearlBeans) parent.getItemAtPosition(position);
                Toast.makeText(getView().getContext(),"123",Toast.LENGTH_SHORT);
//                //Create intent
//                Intent intent = new Intent(LibirarydisplayActivity.this, LibiraryDetailActivity.class);
//                intent.putExtra("pearl",pearlBeansArrayListSp.get(position));
//                //Start details activity
//                startActivity(intent);
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
