package com.wangmyng.common;

import android.app.Application;
import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * @author wangming37
 * @date 2019/3/3
 *
 * # Application 创建后需要在 manifest 的 Application 标签内用 name 属性指定
 * # 整个app只有一个实例，不需要new
 * # 类的介绍与使用场景 https://blog.csdn.net/carson_ho/article/details/78047771
 * # 不要在这里存储共享的数据，app进入后台被清理再重启时，是新的Application，系统恢复之前的界面去获取数据为空 https://blog.csdn.net/u013802387/article/details/78655503
 *
 */
public class CenterApplication extends Application {

    private static CenterApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        instance = this;
        ARouter.init(this);
    }

    public static Application getInstance() {
        return instance;
    }

    public static Context getContext() {
        return getInstance().getApplicationContext();
    }

}
