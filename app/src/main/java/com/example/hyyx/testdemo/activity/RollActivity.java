package com.example.hyyx.testdemo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.hyyx.testdemo.R;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;

public class RollActivity extends AppCompatActivity {
    private RollPagerView mLoopViewPager;
    private TestLoopAdapter mLoopAdapter;
    int imgBanner[] = {R.mipmap.run01, R.mipmap.run02, R.mipmap.run03, R.mipmap.run04, R.mipmap.run05};
    int imgControl[] = {R.mipmap.start, R.mipmap.stop};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roll);
        initView();
    }

    private void initView() {
        mLoopViewPager = (RollPagerView) findViewById(R.id.loop_view_pager);
        mLoopViewPager.setImgsControl(imgControl);
        mLoopViewPager.setImgsLR(R.mipmap.anesthes_record_img_btn_leftpage2x, R.mipmap.anesthes_record_img_btn_rightpage2x);
        mLoopViewPager.setPlayDelay(500);
        mLoopViewPager.setAdapter(mLoopAdapter = new TestLoopAdapter(mLoopViewPager));
        mLoopAdapter.setImgs(imgBanner);

    }

    private class TestLoopAdapter extends LoopPagerAdapter {
        int[] imgs = new int[0];

        public void setImgs(int[] imgs) {
            this.imgs = imgs;
            notifyDataSetChanged();
        }


        public TestLoopAdapter(RollPagerView viewPager) {
            super(viewPager);
        }

        @Override
        public View getView(ViewGroup container, int position) {

            ImageView view = new ImageView(container.getContext());
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
            view.setImageResource(imgs[position]);

            return view;
        }

        @Override
        public int getRealCount() {
            return imgs.length;
        }

    }

}
