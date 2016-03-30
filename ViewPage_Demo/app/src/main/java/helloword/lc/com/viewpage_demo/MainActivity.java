package helloword.lc.com.viewpage_demo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import helloword.lc.com.viewpage_demo.domain.DatasInfo;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private static final String TAG = "MainActivity";
    private ViewPager mVpViewPager;
    private LinearLayout mLlContainer;
    private int[] photo = new int[]{R.mipmap.icon_1, R.mipmap.icon_2, R.mipmap.icon_3, R.mipmap.icon_4, R.mipmap.icon_5};
    private String[] names = new String[]{"1", "2", "3", "4", "5"};
    private List<DatasInfo> mDatas = null;
    private TextView mTvDesc;
    private int middle = 0;

    private boolean auto_flag = true;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            mVpViewPager.setCurrentItem(mVpViewPager.getCurrentItem() + 1);
            if (auto_flag) {
                handler.sendEmptyMessageDelayed(0, 2000);
            }
        }
    };

    @Override
    protected void onDestroy() {
        auto_flag = false;
        super.onDestroy();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mVpViewPager = (ViewPager) findViewById(R.id.main_vp_viewpager);
        mLlContainer = (LinearLayout) findViewById(R.id.paint_container);
        mTvDesc = (TextView) findViewById(R.id.main_tv_desc);
        mDatas = new ArrayList<>();
        //初始化数据
        setDatas();
        mVpViewPager.setAdapter(new MyPagerAdapter());
        mVpViewPager.addOnPageChangeListener(this);

        middle = Integer.MAX_VALUE / 2 - (Integer.MAX_VALUE / 2) % mDatas.size();
        mVpViewPager.setCurrentItem(middle);
        //自动轮播
        handler.sendEmptyMessageDelayed(0, 2000);
    }

    private void setDatas() {
        for (int x = 0; x < photo.length; x++) {
            DatasInfo info = new DatasInfo();
            ImageView image = new ImageView(this);
            image.setImageResource(photo[x]);
            image.setScaleType(ImageView.ScaleType.FIT_XY);

            info.setIcon(image);
            info.setDesc(names[x]);
            mDatas.add(info);
            //设置有几个圆点
            View view = new View(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(30, 30);
            params.setMargins(8, 8, 8, 8);
            view.setLayoutParams(params);
            if (x == 0) {
                view.setBackgroundResource(R.drawable.paint_selector);
                mTvDesc.setText(mDatas.get(x).getDesc());
            } else {
                view.setBackgroundResource(R.drawable.paint_normal);
            } //
            mLlContainer.addView(view);
        }
    }

    //检测画面改变,位置,百分比
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


    }

    //检测画面改变
    public void onPageSelected(int position) {
        position = position % mDatas.size();
        //其他的设置成白色
        for (int x = 0; x < mDatas.size(); x++) {
            View view = mLlContainer.getChildAt(x);
            if (position == x) {
                view.setBackgroundResource(R.drawable.paint_selector);
                mTvDesc.setText(mDatas.get(position).getDesc());
            } else {
                view.setBackgroundResource(R.drawable.paint_normal);
            }
        }

    }

    //检测闲置,固定,移动
    public void onPageScrollStateChanged(int state) {

    }

    private class MyPagerAdapter extends PagerAdapter {


        public int getCount() {
            if (mDatas != null) {
                return Integer.MAX_VALUE;
            }
            return 0;
        }

        //初始化代码时候调用
        public Object instantiateItem(ViewGroup container, int position) {

            position = position % mDatas.size();
            ImageView view = mDatas.get(position).getIcon();
            container.addView(view);

            return view;
        }

        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        //销毁一个调用
        public void destroyItem(ViewGroup container, int position, Object object) {
            position = position % mDatas.size();
            container.removeView(mDatas.get(position).getIcon());
        }
    }
}
