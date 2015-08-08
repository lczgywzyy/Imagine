package u.can.i.up.ui.fragments;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import u.can.i.up.ui.R;
import u.can.i.up.ui.application.IApplication;
import u.can.i.up.ui.beans.Pearl;
import u.can.i.up.ui.customViews.ViewPagerAdapter;
import u.can.i.up.ui.utils.BitmapCache;
import u.can.i.up.ui.utils.IBitmapCache;
import u.can.i.up.ui.utils.ImageViewImpl_collocate;


public class Fragment1 extends Fragment implements AdapterView.OnItemClickListener{


	public static final int NumsPearl=14;

	private ArrayList<Pearl> arrayListTPearls;

	private ArrayList<Pearl> arrayListPearls;

	private ArrayList<GridView> arrayListGridView;

	private ViewPager viewPager;

    private ViewPagerAdapter viewPagerAdapter;


	private int pageCounts;

	private View view;



	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		view=getActivity().getLayoutInflater().inflate(R.layout.fragment_viewpager, null);
        setArrayList();
		return view;
	}

	private void initViews(){
		viewPager=(ViewPager)view.findViewById(R.id.viewpager);
        viewPagerAdapter=new ViewPagerAdapter((List)arrayListGridView);
        viewPager.setAdapter(viewPagerAdapter);
	}

	private void setArrayList(){
		arrayListPearls=((IApplication)getActivity().getApplication()).arrayListPearl;

        arrayListGridView=new ArrayList<>();

        arrayListTPearls=new ArrayList<>();

		Iterator<Pearl> iterator=arrayListPearls.iterator();

        String tag=getTag();

		while(iterator.hasNext()){

            Pearl pearl=iterator.next();


            if(tag!=null&&tag.equals(pearl.gettMaterialName())) {
                arrayListTPearls.add(pearl);
            }
		}

		initPageCounts();

        initGridViews();

        initViews();

	}

	private void initPageCounts(){

        double countd=((double)arrayListTPearls.size())/((double)NumsPearl);
        int counti=arrayListTPearls.size()/NumsPearl;
        pageCounts=countd==counti?counti:counti+1;
       // pageCounts= arrayListTPearls.size()/NumsPearl==(int)(arrayListTPearls.size()/NumsPearl)?(int)(arrayListTPearls.size()/NumsPearl):(int)(arrayListTPearls.size()/NumsPearl)+1;


	}

	private void initGridViews(){

        for(int i=0;i<pageCounts;i++){
            GridView gridView=new GridView(getActivity());

            gridView.setOnItemClickListener(this);
            gridView.setNumColumns(NumsPearl/2);
            gridView.setBackgroundColor(Color.TRANSPARENT);
            gridView.setHorizontalSpacing(1);
            gridView.setVerticalSpacing(1);
            gridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
            gridView.setCacheColorHint(0);
            gridView.setPadding(5, 0, 5, 0);
            gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
            gridView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT));
            gridView.setGravity(Gravity.CENTER);
            int begin=i*NumsPearl;

            int end=0;
            if(i==pageCounts-1){
                //last
                end=arrayListTPearls.size();
            }else{
                //not last
                end=begin+NumsPearl;
            }

            GridViewAdapter gridViewAdapter=initAdapter(begin,end);

            gridView.setAdapter(gridViewAdapter);

            arrayListGridView.add(gridView);

        }


	}

    private GridViewAdapter initAdapter(int begin,int end){

        int beginP=begin;
        int endP=end;
        List<Pearl> pearlArrayList=(List)arrayListTPearls.subList(beginP,endP);

        return new GridViewAdapter(getActivity(),pearlArrayList);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ImageViewImpl_collocate imageViewImpl_collocate = BitmapCache.getImageViewImpl_collocate();
        imageViewImpl_collocate.setBmpMotion(IBitmapCache.getBitMapCache().getBitmap(((Pearl) parent.getAdapter().getItem(position)).getPath(), ((Pearl) parent.getAdapter().getItem(position)).getMD5()));

    }

    class GridViewAdapter extends  BaseAdapter{



        private Context context;

        private List<Pearl> pearlArrayList;

        private LayoutInflater inflate;

        public GridViewAdapter(Context context,List<Pearl> pearlArrayList){

            this.context=context;
            this.pearlArrayList=pearlArrayList;
            this.inflate=LayoutInflater.from(context);
        }
        public int getCount() {
            return pearlArrayList.size();
        }
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder=null;
            if(convertView == null) {
                viewHolder=new ViewHolder();
                convertView=inflate.inflate(R.layout.item_material_selected, null);
                viewHolder.iv_face=(ImageView)convertView.findViewById(R.id.gridImage);
                convertView.setTag(viewHolder);
            } else {
                viewHolder=(ViewHolder)convertView.getTag();
            }
            viewHolder.iv_face.setImageBitmap(IBitmapCache.getBitMapCache().getBitmap(pearlArrayList.get(position).getPath(),pearlArrayList.get(position).getMD5()));

            viewHolder.iv_face.setTag(pearlArrayList.get(position));


            return convertView;
        }
        public Pearl getItem(int position) {
            return pearlArrayList.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        class ViewHolder {

            public ImageView iv_face;
        }
    }

}
