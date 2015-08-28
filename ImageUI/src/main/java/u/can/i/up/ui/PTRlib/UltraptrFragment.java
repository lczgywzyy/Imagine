package u.can.i.up.ui.PTRlib;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.header.MaterialHeader;
import u.can.i.up.ui.PTRlib.mvc.MVCHelper;
import u.can.i.up.ui.PTRlib.mvc.data.Book;
import u.can.i.up.ui.PTRlib.mvc.data.BooksAdapter;
import u.can.i.up.ui.PTRlib.mvc.data.BooksDataSource;
import u.can.i.up.ui.PTRlib.mvc.data.ImagesDataSource;
import u.can.i.up.ui.PTRlib.mvc.helper.MVCUltraHelper;
import u.can.i.up.ui.PTRlib.mvc.viewhandler.GridViewHandler;
import u.can.i.up.ui.R;

public class UltraptrFragment extends Fragment {

    private MVCHelper<List<Book>> listViewHelper;
    public static UltraptrFragment newInstance(Bundle bundle)
    {
        UltraptrFragment ultraptrFragment = new UltraptrFragment();

        if (bundle != null)
        {
            ultraptrFragment.setArguments(bundle);
        }

        return ultraptrFragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void onDestroy() {
        super.onDestroy();
        // 释放资源
        listViewHelper.destory();
    }


    /**
     * 根据dip值转化成px值
     *
     * @param context
     * @param dip
     * @return
     */
    public static int dipToPix(Context context, int dip) {
        int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, context.getResources().getDisplayMetrics());
        return size;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_ultraptr, container, false);
        // 设置LoadView的factory，用于创建使用者自定义的加载失败，加载中，加载更多等布局,写法参照DeFaultLoadViewFactory
        // ListViewHelper.setLoadViewFactory(new LoadViewFactory());
		/*
		 * 配置PtrClassicFrameLayout的刷新样式
		 */
        PtrClassicFrameLayout mPtrFrameLayout = (PtrClassicFrameLayout) view.findViewById(R.id.rotate_header_list_view_frame);
        final MaterialHeader header = new MaterialHeader(getActivity());
        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        header.setPadding(0, dipToPix(getActivity(), 15), 0, dipToPix(getActivity(), 10));
        header.setPtrFrameLayout(mPtrFrameLayout);
        mPtrFrameLayout.setLoadingMinTime(800);
        mPtrFrameLayout.setDurationToCloseHeader(800);
        mPtrFrameLayout.setHeaderView(header);
        mPtrFrameLayout.addPtrUIHandler(header);

        listViewHelper = new MVCUltraHelper<List<Book>>(mPtrFrameLayout);
        // 设置数据源
        listViewHelper.setDataSource(new ImagesDataSource());
        // 设置适配器
//        listViewHelper.setAdapter(new BooksAdapter(getActivity()), new GridViewHandler());
        listViewHelper.setAdapter(new BooksAdapter(view.getContext()),new GridViewHandler());

        // 加载数据
        listViewHelper.refresh();
        return view;
    }

}
