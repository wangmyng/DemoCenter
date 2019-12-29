package com.wangmyng.democenter.themes.webview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.wangmyng.democenter.R;
import com.wangmyng.common.arouter.ARouterPaths;

/**
 * @author wangmyng
 * @date 2019/3/3
 *
 */
@Route(path = ARouterPaths.WEBVIEW_ACTIVITY)
public class WebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_web_view);
    }


}
