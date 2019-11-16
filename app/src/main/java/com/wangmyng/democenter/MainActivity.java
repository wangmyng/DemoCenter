package com.wangmyng.democenter;

import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.wangmyng.common.BaseActivity;
import com.wangmyng.common.arouter.ARouterUtil;
import com.wangmyng.common.network.CallBackUtil;
import com.wangmyng.common.network.HttpUrls;
import com.wangmyng.common.network.OkhttpUtil;
import com.wangmyng.common.utils.SizeUtils;
import com.wangmyng.democenter.bean.MainListBean;
import com.wangmyng.democenter.bean.PexelsResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;


/**
 * @author wangming37
 * @date 2019/3/3
 */
public class MainActivity extends BaseActivity {

    private SampleListAdapter mSampleListAdapter = new SampleListAdapter();
    private List<MainListBean> mSampleBeanList = new ArrayList<>();
    private StaggeredGridLayoutManager mSamplesLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);

    private PexelListAdapter mPexelListAdapter = new PexelListAdapter();
    private List<PexelsResponse.PhotosBean> mPexelBeanList = new ArrayList<>();
    private StaggeredGridLayoutManager mPexelsLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initView() {
        RecyclerView rvSamples = findViewById(R.id.rv_samples);
        RecyclerView rvPexels = findViewById(R.id.rv_pexels);
        rvSamples.setAdapter(mSampleListAdapter);
        rvSamples.setLayoutManager(mSamplesLayoutManager);
        rvPexels.setAdapter(mPexelListAdapter);
        rvPexels.setLayoutManager(mPexelsLayoutManager);
    }

    @Override
    protected void initData() {
        getSamples();
        getPexels();
    }

    /**
     * 获取AndroidManifest中配置的本地数据，包括图片、Arouter路径、页面主题
     */
    private void getSamples() {
        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageInfo != null) {
            ActivityInfo[] activityInfos = packageInfo.activities;
            for (ActivityInfo info : activityInfos) {
                if (info.icon != 0) {
                    String routePath = info.name.substring(packageInfo.packageName.length());
                    //replaceAll(regex, str) 方法未能使用转义符
                    for (int i = 0; i < routePath.length(); i++) {
                        if (routePath.contains(".")) {
                            routePath = routePath.replace(".", "/");
                        } else {
                            break;
                        }
                    }
                    String simpleName = info.name.substring(info.name.lastIndexOf(".") + 1);
                    MainListBean bean = new MainListBean(routePath, simpleName, info.icon, info.banner);
                    mSampleBeanList.add(bean);
                }
            }
        }
        mSampleListAdapter.setNewData(mSampleBeanList);
    }

    /**
     * 获取Pexels来源的摄影作品
     */
    private void getPexels() {
        Map<String, String> params = new HashMap<>();
        params.put("per_page", "60");
        params.put("page", "1");
        Map<String, String> header = new HashMap<>();
        header.put("Authorization", "563492ad6f91700001000001340cbf6bad2846819301256f79e3fcde");
        OkhttpUtil.okHttpGet(HttpUrls.PEXELS_CURATED, params, header, new CallBackUtil.CallBackString() {
            @Override
            public void onFailure(Call call, Exception e) {
                Toast.makeText(mStaticContext, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                PexelsResponse entity = new Gson().fromJson(response, PexelsResponse.class);
                if (entity != null) {
                    mPexelBeanList.addAll(entity.getPhotos());
                    mPexelListAdapter.setNewData(mPexelBeanList);
                    ImageView iv = new ImageView(mActivity);
                    mPexelListAdapter.addHeaderView(iv);
                    iv.setPadding(SizeUtils.dp2px(12), SizeUtils.dp2px(24), SizeUtils.dp2px(12), SizeUtils.dp2px(12));
                    Glide.with(mActivity).load(HttpUrls.PEXELS_LOGO).into(iv);
                }
            }
        });
    }

    @Override
    protected void initListener() {
        mSampleListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ARouterUtil.navigation(mSampleBeanList.get(position).getRoutePath());
            }
        });
    }

    private class SampleListAdapter extends BaseQuickAdapter<MainListBean, BaseViewHolder> {

        SampleListAdapter() {
            super(R.layout.item_main_list);
        }

        @Override
        protected void convert(BaseViewHolder helper, MainListBean bean) {
            helper.setText(R.id.tv_title, "MY: " + bean.getTitle());
            Glide.with(mContext).load(bean.getBannerRes()).into((ImageView) helper.getView(R.id.iv_content));
        }
    }

    private class PexelListAdapter extends BaseQuickAdapter<PexelsResponse.PhotosBean, BaseViewHolder> {

        PexelListAdapter() {
            super(R.layout.item_main_list);
        }

        @Override
        protected void convert(BaseViewHolder helper, PexelsResponse.PhotosBean bean) {
            helper.setText(R.id.tv_title, "by: " + bean.getPhotographer());
            Glide.with(mContext).load(bean.getSrc().getLarge()).into((ImageView) helper.getView(R.id.iv_content));
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mSamplesLayoutManager.setSpanCount(2);
            mPexelsLayoutManager.setSpanCount(3);
        } else {
            mSamplesLayoutManager.setSpanCount(1);
            mPexelsLayoutManager.setSpanCount(2);
        }
    }
}
