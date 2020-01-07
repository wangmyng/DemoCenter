package com.wangmyng.democenter.samples.widgets;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.wangmyng.democenter.R;

import java.lang.ref.WeakReference;


/**
 * @author wangmyng
 * date 2020/1/6
 *
 * 右上角按钮弹窗，全屏背景变暗
 *
 * demo:
 *
 * TopRightListPopupWindow mPopupWindow;
 * if (mPopupWindow == null) {
 *       mPopupWindow = new TopRightListPopupWindow(this);
 *       mPopupWindow.setBackgroundAlpha(0.7f)
 *              .setDataList(items)
 *              .setWindowWidth(SizeUtils.dp2px(180))
 *              .setOnItemClickListener(new AdapterView.OnItemClickListener() {
 *                         @Override
 *                         public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
 *                             ToastUtils.showShortToast(items[position]);
 *                         }
 *                     });
 *  }
 *  mPopupWindow.showAsDropDown(findViewById(R.id.right_icon), -SizeUtils.dp2px(18), SizeUtils.dp2px(5), Gravity.END);
 *
 */
public class TopRightListPopupWindow extends PopupWindow {
    private boolean hasInit;
    private WeakReference<Activity> activityReference;
    private Activity activity;
    private float alpha = 0.7f;
    private AdapterView.OnItemClickListener listener;
    private int width = 480;
    private String[] dataList;
    private int itemStyle;

    public TopRightListPopupWindow(Activity activity) {
        super(activity);
        activityReference = new WeakReference<>(activity);
        activity = activityReference.get();
    }

    private void initView() {
        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_list, null);
        setContentView(view);
        setWidth(width);
        setTouchable(true);
        setFocusable(true);
        setBackgroundDrawable(new BitmapDrawable());
        setOutsideTouchable(true);
        setOnDismissListener(new PopupWindow.OnDismissListener() {
            // 在dismiss中恢复透明度
            public void onDismiss() {
                WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
                lp.alpha = 1f;
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                activity.getWindow().setAttributes(lp);
            }
        });
        ListView listView = view.findViewById(R.id.list_view);
        listView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return dataList.length;
            }

            @Override
            public Object getItem(int position) {
                return dataList[position];
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = LayoutInflater.from(activity).inflate(R.layout.item_list_dialog, parent, false);
                }
                ((TextView) convertView.findViewById(R.id.tv_item)).setText(dataList[position]);
                return convertView;
            }
        });
        listView.setOnItemClickListener(listener);
        hasInit = true;
    }

    @Override
    public void showAsDropDown(View anchor, int xOff, int yOff, int gravity) {
        if(!hasInit) initView();
        WindowManager.LayoutParams lp = activity.getWindow()
                .getAttributes();
        lp.alpha = alpha;
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        activity.getWindow().setAttributes(lp);
        update();
        super.showAsDropDown(anchor, xOff, yOff, gravity);

    }

    public TopRightListPopupWindow setWindowWidth(int width) {
        this.width = width;
        return this;
    }

    public TopRightListPopupWindow setBackgroundAlpha(float alpha) {
        this.alpha = alpha;
        return this;
    }

    public TopRightListPopupWindow setItemStyle(int style) {
        this.itemStyle = style;
        return this;
    }
    
    public TopRightListPopupWindow setDataList(String[] list){
        this.dataList = list;
        return this;
    }

    public TopRightListPopupWindow setOnItemClickListener(AdapterView.OnItemClickListener listener){
        this.listener = listener;
        return this;
    }

    public float getAlpha() {
        return alpha;
    }

    public AdapterView.OnItemClickListener getListener() {
        return listener;
    }

    @Override
    public int getWidth() {
        return width;
    }

    public String[] getDataList() {
        return dataList;
    }
}
