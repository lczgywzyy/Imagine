package u.can.i.up.ui.activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import u.can.i.up.ui.R;
import u.can.i.up.ui.customViews.ListViewAdapter;


public class LibiraryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initActionBar();
    }

    @SuppressWarnings("deprecation")
    private void initActionBar() {

        if (getSupportActionBar() != null) {
            ActionBar actionBar = getSupportActionBar();
//            actionBar.setHomeButtonEnabled(true);
//            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
//            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            actionBar.setTitle("素材中心");

//            actionBar.setCustomView(R.layout.abs_layout);
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
            actionBar.addTab(actionBar.newTab()
                    .setText("基础素材")
                    .setTabListener(new ActionBar.TabListener() {
                        @Override
                        public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
                            fragmentTransaction.replace(android.R.id.content, new ListViewFragment());
                        }

                        @Override
                        public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
                        }

                        @Override
                        public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
                        }
                    }));
            actionBar.addTab(actionBar.newTab()
                    .setText("在线图库")
                    .setTabListener(new ActionBar.TabListener() {
                        @Override
                        public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
                            fragmentTransaction.replace(android.R.id.content, new ListViewFragment());
                        }

                        @Override
                        public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
                        }

                        @Override
                        public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
                        }
                    }));
            actionBar.addTab(actionBar.newTab()
                    .setText("我的收藏")
                    .setTabListener(new ActionBar.TabListener() {
                        @Override
                        public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
                            fragmentTransaction.replace(android.R.id.content, new ListViewFragment());
                        }

                        @Override
                        public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
                        }

                        @Override
                        public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
                        }
                    }));
            actionBar.addTab(actionBar.newTab()
                    .setText("素材制作")
                    .setTabListener(new ActionBar.TabListener() {
                        @Override
                        public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
                            fragmentTransaction.replace(android.R.id.content, new ListViewFragment());
                        }

                        @Override
                        public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
                        }

                        @Override
                        public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
                        }
                    }));
//            ImageButton libirary_btn = (ImageButton)findViewById(R.id.libirarybackbtn);
//            libirary_btn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    onBackPressed();
//                }
//            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
               this.finish();
            case R.id.about:
                TextView content = (TextView) getLayoutInflater().inflate(R.layout.about_view, null);
                content.setMovementMethod(LinkMovementMethod.getInstance());
                content.setText(Html.fromHtml(getString(R.string.about_body)));
                new AlertDialog.Builder(this)
                        .setTitle(R.string.about)
                        .setView(content)
                        .setInverseBackgroundForced(true)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create().show();
            default:
                return super.onOptionsItemSelected(item);
        }

//        if (item.getItemId() == R.id.about) {
//            TextView content = (TextView) getLayoutInflater().inflate(R.layout.about_view, null);
//            content.setMovementMethod(LinkMovementMethod.getInstance());
//            content.setText(Html.fromHtml(getString(R.string.about_body)));
//            new AlertDialog.Builder(this)
//                    .setTitle(R.string.about)
//                    .setView(content)
//                    .setInverseBackgroundForced(true)
//                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    }).create().show();
//        }
//        return super.onOptionsItemSelected(item);
    }

    public static class ListViewFragment extends Fragment {

        @SuppressLint("InflateParams")
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View root = inflater.inflate(R.layout.fragment_listview, container, false);

            ListView list = (ListView) root.findViewById(android.R.id.list);
            ListViewAdapter listAdapter = new ListViewAdapter(getActivity(),
                    getResources().getStringArray(R.array.countries));
            list.setAdapter(listAdapter);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    startActivity(new Intent(view.getContext(), LibirarydisplayActivity.class));
                }
            });
//            FloatingActionButton fab = (FloatingActionButton) root.findViewById(R.id.fab);
//            fab.attachToListView(list, new ScrollDirectionListener() {
//                @Override
//                public void onScrollDown() {
//                    Log.d("ListViewFragment", "onScrollDown()");
//                }
//
//                @Override
//                public void onScrollUp() {
//                    Log.d("ListViewFragment", "onScrollUp()");
//                }
//            }, new AbsListView.OnScrollListener() {
//                @Override
//                public void onScrollStateChanged(AbsListView view, int scrollState) {
//                    Log.d("ListViewFragment", "onScrollStateChanged()");
//                }
//
//                @Override
//                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                    Log.d("ListViewFragment", "onScroll()");
//                }
//            });

            return root;
        }
    }

//    public static class RecyclerViewFragment extends Fragment {
//        @Override
//        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//            View root = inflater.inflate(R.layout.fragment_recyclerview, container, false);
//
//            RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.recycler_view);
//            recyclerView.setHasFixedSize(true);
//            recyclerView.setItemAnimator(new DefaultItemAnimator());
//            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//            recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
//
//            RecyclerViewAdapter adapter = new RecyclerViewAdapter(getActivity(), getResources()
//                    .getStringArray(R.array.countries));
//            recyclerView.setAdapter(adapter);
//
//            FloatingActionButton fab = (FloatingActionButton) root.findViewById(R.id.fab);
//            fab.attachToRecyclerView(recyclerView);
//
//            return root;
//        }
//    }
//
//    public static class ScrollViewFragment extends Fragment {
//        @Override
//        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//            View root = inflater.inflate(R.layout.fragment_scrollview, container, false);
//
//            ObservableScrollView scrollView = (ObservableScrollView) root.findViewById(R.id.scroll_view);
//            LinearLayout list = (LinearLayout) root.findViewById(R.id.list);
//
//            String[] countries = getResources().getStringArray(R.array.countries);
//            for (String country : countries) {
//                TextView textView = (TextView) inflater.inflate(R.layout.list_item, container, false);
//                String[] values = country.split(",");
//                String countryName = values[0];
//                int flagResId = getResources().getIdentifier(values[1], "drawable", getActivity().getPackageName());
//                textView.setText(countryName);
//                textView.setCompoundDrawablesWithIntrinsicBounds(flagResId, 0, 0, 0);
//
//                list.addView(textView);
//            }
//
////            FloatingActionButton fab = (FloatingActionButton) root.findViewById(R.id.fab);
////            fab.attachToScrollView(scrollView);
//
//            return root;
//        }
//    }
}