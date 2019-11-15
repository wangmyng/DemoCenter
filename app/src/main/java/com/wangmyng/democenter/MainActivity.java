package com.wangmyng.democenter;

import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wangmyng.common.BaseActivity;
import com.wangmyng.common.arouter.ARouterUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


/**
 * @author wangming37
 * @date 2019/3/3
 */
public class MainActivity extends BaseActivity {

    private LinearLayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
    private MainListAdapter mListAdapter = new MainListAdapter();
    private List<MainListBean> mDataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mListAdapter);
    }

    @Override
    protected void initData() {
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
                    mDataList.add(bean);
                }
            }
        }

        //反射获取所有图片资源，填充首页
        Resources resources = getResources();
        String packageName = getPackageName();
        Field[] fields = R.mipmap.class.getDeclaredFields();
        for (Field field : fields) {
            if (field.getName().contains("ic") || field.getName().contains("expression")) {
                continue;
            }
            int id = resources.getIdentifier(field.getName(), "mipmap", packageName);
            MainListBean bean = new MainListBean("", "", id, id);
            mDataList.add(bean);
        }

        mListAdapter.setNewData(mDataList);

    }

    @Override
    protected void initListener() {
        mListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (!TextUtils.isEmpty(mDataList.get(position).getRoutePath())) {
                    ARouterUtil.navigation(mDataList.get(position).getRoutePath());
                }
            }
        });
    }

    private class MainListAdapter extends BaseQuickAdapter<MainListBean, BaseViewHolder> {

        MainListAdapter() {
            super(R.layout.item_main_list);
        }

        @Override
        protected void convert(BaseViewHolder helper, MainListBean item) {
            helper.setText(R.id.tv_title, item.getTitle());
            Glide.with(mContext).load(item.getBannerRes()).into((ImageView) helper.getView(R.id.iv_content));
        }
    }

}
