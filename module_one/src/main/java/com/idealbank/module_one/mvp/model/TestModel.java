package com.idealbank.module_one.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.idealbank.module_one.mvp.model.api.service.OneService;
import com.idealbank.module_one.mvp.model.entity.GankBaseResponse;
import com.idealbank.module_one.mvp.model.entity.GankItemBean;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.idealbank.module_one.mvp.contract.TestContract;

import java.util.List;

import io.reactivex.Observable;


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
@ActivityScope
 public class TestModel extends BaseModel implements TestContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public TestModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<GankBaseResponse<List<GankItemBean>>> getGirlList(int num, int page) {
        return mRepositoryManager
                .obtainRetrofitService(OneService.class)
                .getGirlList(num, page);
    }
}