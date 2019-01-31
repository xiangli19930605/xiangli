package me.jessyan.armscomponent.commonsdk.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.ViewGroup;

import com.jess.arms.mvp.BasePresenter;

import me.jessyan.armscomponent.commonsdk.R;

/**
 * Describe：所有需要Mvp开发的Activity的基类
 * Created by 吴天强 on 2018/10/15.
 */
@SuppressWarnings("unchecked")
public abstract class BaseMvpActivity<P extends BasePresenter> extends BaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isActionBar()) {
            setContentView(R.layout.activity_base);
            ((ViewGroup) findViewById(R.id.fl_content)).addView(getLayoutInflater().inflate(initView(savedInstanceState), null));
        } else {
            setContentView(initView(savedInstanceState));
        }
    }


    /**
            * 是否需要ActionBar
     * TODO 暂时用此方法 后续优化
     */
    protected boolean isActionBar() {
        return true;
    }
}
