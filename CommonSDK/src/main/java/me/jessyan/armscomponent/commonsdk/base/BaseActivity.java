package me.jessyan.armscomponent.commonsdk.base;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.WindowManager;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.jess.arms.base.delegate.IActivity;
import com.jess.arms.integration.cache.Cache;
import com.jess.arms.integration.cache.CacheType;
import com.jess.arms.integration.lifecycle.ActivityLifecycleable;
import com.jess.arms.mvp.IPresenter;
import com.jess.arms.utils.ArmsUtils;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.Arrays;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;
import me.jessyan.armscomponent.commonsdk.R;
import me.jessyan.armscomponent.commonsdk.utils.EventBusUtils;
import me.jessyan.armscomponent.commonsdk.widget.ActionBar;

import static com.jess.arms.utils.ThirdViewUtil.convertAutoView;

/**
 * Describe：所有Activity的基类
 * Created by 吴天强 on 2018/10/15.
 */

public abstract class BaseActivity <P extends IPresenter>extends FragmentActivity implements IActivity, ActivityLifecycleable {
    protected final String TAG = this.getClass().getSimpleName();
    private final BehaviorSubject<ActivityEvent> mLifecycleSubject = BehaviorSubject.create();
    private Cache<String, Object> mCache;
    private Unbinder unbinder;
    @Inject
    @Nullable
    protected P mPresenter;//如果当前页面逻辑简单, Presenter 可以为 null.
    @NonNull
    @Override
    public synchronized Cache<String, Object> provideCache() {
        if (mCache == null) {
            mCache = ArmsUtils.obtainAppComponentFromContext(this).cacheFactory().build(CacheType.ACTIVITY_CACHE);
        }
        return mCache;
    }
    private ViewStub emptyView;
    protected Context mContext;
    protected ImmersionBar mImmersionBar;
//    protected LoadingDialog loadingDialog;

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        View view = convertAutoView(name, context, attrs);
        return view == null ? super.onCreateView(name, context, attrs) : view;
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        int layoutResID = initView(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        if (isActionBar()) {
            setContentView(R.layout.activity_base);
            ((ViewGroup) findViewById(R.id.fl_content)).addView(getLayoutInflater().inflate( layoutResID, null));
        } else {
            setContentView( layoutResID);
        }
        //初始化ButterKnife
        unbinder = ButterKnife.bind(this);

        //沉浸式状态栏
        initImmersionBar(R.color.blue);

        //加入Activity管理器
//        BaseApplication.getApplication().getActivityManage().addActivity(this);
        if (regEvent()) {
            EventBusUtils.register(this);
        }
//        loadingDialog = new LoadingDialog(mContext);
        initData(savedInstanceState);
    }


    /**
     * 沉浸栏颜色
     */
    protected void initImmersionBar(int color) {
        mImmersionBar = ImmersionBar.with(this);
        if (color != 0) {
            mImmersionBar.statusBarColor(color);
        }
        mImmersionBar.init();
    }
    @Override
    public boolean useFragment() {
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
        if (regEvent()) {
            EventBusUtils.unregister(this);
        }
        //必须调用该方法，防止内存泄漏
        if (mImmersionBar != null) {
            mImmersionBar.destroy();
        }
        //将Activity从管理器移除
//        BaseApplication.getApplication().getActivityManage().removeActivity(this);
    }
    @NonNull
    @Override
    public final Subject<ActivityEvent> provideLifecycleSubject() {
        return mLifecycleSubject;
    }
    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        initView();
    }

    //***************************************空页面方法*************************************
    protected void showEmptyView(String text) {
        showEmptyOrErrorView(text, R.drawable.bg_no_data);
    }

    protected void showEmptyView() {
        showEmptyView(getString(R.string.no_data));
    }

    protected void showErrorView(String text) {
        showEmptyOrErrorView(text, R.drawable.bg_no_net);
    }

    protected void showErrorView() {
        showErrorView(getString(R.string.error_data));
    }

    public void showEmptyOrErrorView(String text, int img) {

        if (emptyView == null) {
            emptyView = findViewById(R.id.vs_empty);
        }
        emptyView.setVisibility(View.VISIBLE);
        findViewById(R.id.iv_empty).setBackgroundResource(img);
        ((TextView) findViewById(R.id.tv_empty)).setText(text);
        findViewById(R.id.ll_empty).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPageClick();
            }
        });
    }

    protected void hideEmptyView() {
        if (emptyView != null) {
            emptyView.setVisibility(View.GONE);
        }
    }

    /**
     * 空页面被点击
     */
    protected void onPageClick() {

    }

    //***************************************空页面方法*********************************


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        Logger.e("permissions:" + Arrays.toString(permissions) + " grantResults:" + Arrays.toString(grantResults));

        //如果有未授权权限则跳转设置页面
        if (!requestPermissionsResult(grantResults)) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.parse("package:" + getPackageName()));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }


    }

    /**
     * 判断授权结果
     */
    private boolean requestPermissionsResult(int[] grantResults) {
        for (int code : grantResults) {
            if (code == -1) {
                return false;
            }
        }
        return true;
    }


    /**
     * 子类接受事件 重写该方法
     */
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onEventBus(Event event) {
//    }


    /**
     * 是否需要ActionBar
     * TODO 暂时用此方法 后续优化
     */
    protected boolean isActionBar() {
        return false;
    }

    /**
     * 需要接收事件 重写该方法 并返回true
     */
    protected boolean regEvent() {
        return false;
    }

//    protected abstract int getLayoutId();
//
    protected abstract void initView();

}
