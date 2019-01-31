package com.idealbank.module_main.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.idealbank.module_main.mvp.model.api.cache.CommonCache;
import com.idealbank.module_main.mvp.model.api.service.MainService;
import com.idealbank.module_main.mvp.model.entity.BaseResponse;
import com.idealbank.module_main.mvp.model.entity.KnowledgeHierarchyData;
import com.idealbank.module_main.mvp.model.entity.ProjectClassifyData;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.FragmentScope;

import javax.inject.Inject;

import com.idealbank.module_main.mvp.contract.HomeContract;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictDynamicKey;


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
public class HomeModel extends BaseModel implements HomeContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public HomeModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<List<ProjectClassifyData>>> getGirlList(int lastPage,boolean update) {
        return
                Observable.just(mRepositoryManager
                        .obtainRetrofitService(MainService.class)
                        .getGirlList())
                        .flatMap(new Function<Observable<BaseResponse<List<ProjectClassifyData>>>, ObservableSource<BaseResponse<List<ProjectClassifyData>>>>() {
                    @Override
                    public ObservableSource<BaseResponse<List<ProjectClassifyData>>> apply(Observable<BaseResponse<List<ProjectClassifyData>>> baseResponseObservable) throws Exception {
                        return mRepositoryManager.obtainCacheService(CommonCache.class).getGirlListCache(baseResponseObservable
                                , new DynamicKey(lastPage)
                                , new EvictDynamicKey(update))
                                .map(listReply -> listReply.getData());
                    }
                });
    }

}