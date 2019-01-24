package com.wangmyng.common.arouter;


import com.alibaba.android.arouter.launcher.ARouter;

/**
 * @author wangming37
 * @date 2019/3/3
 *
 */
public class ARouterUtil {

    public static void navigation(String routePath) {
        ARouter.getInstance().build(routePath).navigation();
    }
}
