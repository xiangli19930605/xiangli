package me.jessyan.armscomponent.commonsdk.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.LogUtils;

import butterknife.BindView;
import me.jessyan.armscomponent.commonsdk.R;
import me.jessyan.armscomponent.commonsdk.widget.ActionBar;


/**
 * Describe：所有带actionBar的Activity基类
 * Created by 吴天强 on 2018/10/18.
 */

public abstract class ActionBarActivity<P extends BasePresenter> extends BaseActivity<P> {

//        @BindView(R.id.actionbar)
     ActionBar actionBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        actionBar= findViewById(R.id.actionbar);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
    }

    protected void setTitleText(String title) {
        LogUtils.warnInfo(title);
        if (actionBar != null) {
            actionBar.setCenterText(title);
        }
    }

    protected void setTitleText(int title) {
        if (actionBar != null) {
            actionBar.setCenterText(getString(title));
        }
    }

    @Override
    protected boolean isActionBar() {
        return true;
    }
}
