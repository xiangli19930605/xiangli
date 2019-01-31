package com.idealbank.module_one.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseViewHolder;
import com.idealbank.module_one.R2;
import com.idealbank.module_one.mvp.model.entity.GankItemBean;
import com.idealbank.module_one.mvp.ui.adapter.SmartRefreshAdapter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.cache.Cache;
import com.jess.arms.utils.ArmsUtils;

import com.idealbank.module_one.di.component.DaggerTestComponent;
import com.idealbank.module_one.mvp.contract.TestContract   ;
import com.idealbank.module_one.mvp.presenter.TestPresenter;

import com.idealbank.module_one.R;
import com.paginate.Paginate;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.trello.rxlifecycle2.android.ActivityEvent;


import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.subjects.Subject;
import me.jessyan.armscomponent.commonsdk.base.ActionBarActivity;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;
import me.jessyan.armscomponent.commonsdk.widget.ActionBar;
import timber.log.Timber;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/16/2019 13:41
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Route(path = RouterHub.ONE_TESTACTIVITY)
public class TestActivity extends ActionBarActivity<TestPresenter> implements TestContract.View {

    @BindView(R2.id.srl_smart_refresh)
    SmartRefreshLayout srl_smart_refresh;
    @BindView(R2.id.rv_smart_refresh)
    RecyclerView mRecyclerView;
    @Inject
    SmartRefreshAdapter mAdapter;
    @Inject
    RecyclerView.LayoutManager mLayoutManager;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerTestComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public boolean useEventBus() {
        return false;
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_test; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initRecyclerView();
        mRecyclerView.setAdapter(mAdapter);
    }

    /**
     * 初始化RecyclerView
     */
    private void initRecyclerView() {
        ArmsUtils.configRecyclerView(mRecyclerView, mLayoutManager);
        srl_smart_refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                    mPresenter.requestGirls(true);
            }
        });
        srl_smart_refresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull final RefreshLayout refreshLayout) {
                mPresenter.requestGirls(false);
            }
        });
        srl_smart_refresh.autoRefresh();
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
    @Override
    public void showLoading() {
        Timber.tag(TAG).w("showLoading");
    }

    @Override
    public void hideLoading() {
        Timber.tag(TAG).w("hideLoading");
        srl_smart_refresh.finishRefresh();


    }

    /**
     * 开始加载更多
     */
    @Override
    public void startLoadMore() {
    }

    /**
     * 结束加载更多
     */
    @Override
    public void endLoadMore() {
        srl_smart_refresh.finishLoadMore();
        showEmptyView("2222");
    }

    @Override
    public void noLoadMore() {
        srl_smart_refresh.finishLoadMoreWithNoMoreData();

    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    protected void initView() {
        setTitleText("福利");
    }
}

