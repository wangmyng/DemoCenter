package com.wangmyng.common;


import android.support.v4.app.Fragment;
import android.util.Log;

/**
 * @author wangmyng
 * @date 2019/3/3
 */
public class BaseFragment extends Fragment {

    @Override
    public void onStart() {
        super.onStart();
        Log.d("currentClass", "onStart..." + getClass().getName());
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            Log.d("currentClass", "onHiddenChanged..." + getClass().getName());
        }
    }
}
