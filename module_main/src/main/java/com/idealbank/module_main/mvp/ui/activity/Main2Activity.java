package com.idealbank.module_main.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.widget.FrameLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.idealbank.module_main.R;
import com.idealbank.module_main.R2;
import com.idealbank.module_main.di.component.DaggerMainComponent;
import com.idealbank.module_main.mvp.contract.MainContract;
import com.idealbank.module_main.mvp.presenter.MainPresenter;
import com.idealbank.module_main.mvp.ui.fragment.HomeFragment;
import com.idealbank.module_main.mvp.ui.fragment.MineFragment;
import com.idealbank.module_main.mvp.ui.fragment.PayFragment;
import com.idealbank.module_main.mvp.ui.fragment.ProjectFragment;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;
import me.jessyan.armscomponent.commonsdk.utils.FragmentUtils;

@Route(path = RouterHub.MAIN_MAINACTIVITY)
public class Main2Activity extends BaseActivity<MainPresenter> implements MainContract.View {
    @BindView(R2.id.main_frame)
    FrameLayout mMainFrame;
    @BindView(R2.id.bottomBar)
    BottomBar mBottomBar;
    private List<Integer> mTitles;
    private List<Fragment> mFragments;
    private int mReplace = 0;
    private OnTabSelectListener mOnTabSelectListener = tabId -> {

        if (tabId == R.id.tab_home) {
            mReplace = 0;
        } else if (tabId == R.id.tab_pay) {
            mReplace = 1;
        } else if (tabId == R.id.tab_project) {
            mReplace = 2;
        }else if (tabId == R.id.tab_mine) {
            mReplace = 3;
        }
        FragmentUtils.hideAllShowFragment(mFragments.get(mReplace));
    };

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

        DaggerMainComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_main2;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        HomeFragment homeFragment;
        MineFragment mineFragment;
        PayFragment payFragment;
        ProjectFragment projectFragment;
        if (savedInstanceState == null) {
            homeFragment = HomeFragment.newInstance();
            payFragment = PayFragment.newInstance();
            mineFragment = MineFragment.newInstance();
            projectFragment = ProjectFragment.newInstance();
        } else {
            mReplace = savedInstanceState.getInt("2");
            FragmentManager fm = getSupportFragmentManager();
            homeFragment = (HomeFragment) FragmentUtils.findFragment(fm, HomeFragment.class);
            payFragment = (PayFragment) FragmentUtils.findFragment(fm, PayFragment.class);
            mineFragment = (MineFragment) FragmentUtils.findFragment(fm, MineFragment.class);
            projectFragment = (ProjectFragment) FragmentUtils.findFragment(fm, ProjectFragment.class);

        }
        if (mFragments == null) {
            mFragments = new ArrayList<>();
            mFragments.add(payFragment);
            mFragments.add(homeFragment);
            mFragments.add(projectFragment);
            mFragments.add(mineFragment);
        }
        FragmentUtils.addFragments(getSupportFragmentManager(), mFragments, R.id.main_frame, 0);
        mBottomBar.setOnTabSelectListener(mOnTabSelectListener);

    }


    @Override
    public void showMessage(@NonNull String message) {

    }
}
