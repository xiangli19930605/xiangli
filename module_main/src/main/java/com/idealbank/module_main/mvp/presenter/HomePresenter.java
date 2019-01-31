package com.idealbank.module_main.mvp.presenter;

import android.app.Application;
import android.util.Log;

import com.google.gson.Gson;
import com.idealbank.module_main.mvp.model.entity.BaseResponse;
import com.idealbank.module_main.mvp.model.entity.ProjectClassifyData;
import com.idealbank.module_main.mvp.ui.adapter.HomeAdapter;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

import javax.inject.Inject;

import com.idealbank.module_main.mvp.contract.HomeContract;
import com.jess.arms.utils.RxLifecycleUtils;

import java.util.List;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/24/2019 16:17
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class HomePresenter extends BasePresenter<HomeContract.Model, HomeContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;
    @Inject
    List<ProjectClassifyData> mDatas;
    @Inject
    HomeAdapter mAdapter;
    private int lastPage = 1;
    private int preEndIndex;
    private boolean isFirst = true;
    @Inject
    public HomePresenter(HomeContract.Model model, HomeContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }


    public void requestGirls(final boolean pullToRefresh) {
        if (pullToRefresh) lastPage = 1;//下拉刷新默认只请求第一页
        boolean isEvictCache = pullToRefresh;//是否驱逐缓存,为ture即不使用缓存,每次下拉刷新即需要最新数据,则不使用缓存
        if (pullToRefresh && isFirst) {//默认在第一次下拉刷新时使用缓存
            isFirst = false;
            isEvictCache = false;
        }
        mModel.getGirlList(lastPage,isEvictCache)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(disposable -> {
                            if (pullToRefresh)
                                mRootView.showLoading();//显示下拉刷新的进度条
                            else
                                mRootView.startLoadMore();//显示上拉加载更多的进度条
                        }
                ).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    if (pullToRefresh)
                        mRootView.hideLoading();//隐藏下拉刷新的进度条
                    else
                        mRootView.endLoadMore();//隐藏上拉加载更多的进度条
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<ProjectClassifyData>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<ProjectClassifyData>> datas) {
                        if (datas.getData().size() == 0) {
                            mRootView.noLoadMore();
                            return;
                        }
                        lastPage = lastPage + 1;
                        if (pullToRefresh) mDatas.clear();//如果是下拉刷新则清空列表
                        preEndIndex = mDatas.size();//更新之前列表总长度,用于确定加载更多的起始位置
                        mDatas.addAll(datas.getData());
                        Log.e("datas.getData()",""+datas.getData().size());
                        if (pullToRefresh){
//                            mAdapter.setNewData(datas.getData());
                            mAdapter.notifyDataSetChanged();
 }
                        else {
//                            mAdapter.addData(datas.getData());
                            mAdapter.notifyItemRangeInserted(preEndIndex, datas.getData().size());
                        }
                    }
                });
    }

}
