package u.can.i.up.ui.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import u.can.i.up.ui.R;
import u.can.i.up.ui.activities.LibirarydisplayActivity;
import u.can.i.up.ui.customViews.ListViewAdapter;

/**
 * @author dongfeng
 * @date 2015.07.30
 * @sumary 基础素材页面
 */
public class BaseMaterialFragment extends Fragment {
    @SuppressLint("InflateParams")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_listview, container, false);

        ListView list = (ListView) root.findViewById(android.R.id.list);
        ListViewAdapter listAdapter = new ListViewAdapter(getActivity());
        list.setAdapter(listAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(view.getContext(),LibirarydisplayActivity.class);
                intent.putExtra("position",i);

                startActivity(intent);
            }

        });
        return root;
    }
}
