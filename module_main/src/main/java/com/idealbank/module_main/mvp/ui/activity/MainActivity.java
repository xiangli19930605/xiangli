package com.idealbank.module_main.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.widget.FrameLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.idealbank.module_main.R2;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.idealbank.module_main.di.component.DaggerMainComponent;
import com.idealbank.module_main.mvp.contract.MainContract;
import com.idealbank.module_main.mvp.presenter.MainPresenter;

import com.idealbank.module_main.R;


import java.util.ArrayList;

import butterknife.BindView;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/21/2019 13:55
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {
    @BindView(R2.id.fragment_group)
    FrameLayout mFrameGroup;
    @BindView(R2.id.bottom_navigation_view)
    BottomNavigationView mBottomNavigationView;


    private ArrayList<BaseFragment> mFragments;
    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
//        DaggerMainComponent //如找不到该类,请编译一下项目
//                .builder()
//                .appComponent(appComponent)
//                .view(this)
//                .build()
//                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_main1; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
//        mFragments = new ArrayList<>();
//        if (savedInstanceState == null) {
//            mPresenter.setNightModeState(false);
//            initPager(false,0);
//        } else {
//            mBottomNavigationView.setSelectedItemId(R.id.tab_main_pager);
//            initPager(true, 1);
//        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }
}
