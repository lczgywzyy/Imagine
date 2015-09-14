    package u.can.i.up.ui.customViews;

    import android.content.Context;
    import android.support.v4.app.Fragment;
    import android.support.v4.app.FragmentManager;
    import android.support.v4.app.FragmentPagerAdapter;

    import u.can.i.up.ui.fragments.BaseMaterialFragment;
    import u.can.i.up.ui.fragments.CutoutFragment;


    /**
     * @author fyales
     */
    public class TabViewPagerAdapter extends FragmentPagerAdapter {

        private final int PAGE_COUNT = 4;


        private String mTabTitle[] = new String[]{"基础素材","在线图库","我的收藏","我的制作"};
        private Context mContext;

        public TabViewPagerAdapter(FragmentManager fm, Context context) {
            super(fm);
            this.mContext = context;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment tmpfragment = null;
            switch (position){
                case 0:
                {
                    tmpfragment =  new BaseMaterialFragment();
                    break;
                }
                case 1:
                {
                    tmpfragment =  new BaseMaterialFragment();
                    break;
                }
                case 2:
                {
                    tmpfragment =  new BaseMaterialFragment();
                    break;
                }
                default:
                    break;
                case 3:
                {
                    tmpfragment =  new BaseMaterialFragment();
                    break;
                }
            }
            return tmpfragment;

        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return  mTabTitle[position];
        }


    }
