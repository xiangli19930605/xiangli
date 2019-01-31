package com.idealbank.module_one.di.module;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.idealbank.module_one.mvp.model.entity.GankItemBean;
import com.idealbank.module_one.mvp.ui.adapter.SmartRefreshAdapter;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.idealbank.module_one.mvp.contract.TestContract;
import com.idealbank.module_one.mvp.model.TestModel;

import java.util.ArrayList;
import java.util.List;


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
@Module
public abstract class TestModule {

    @Binds
    abstract TestContract.Model bindTestModel(TestModel model);
    @ActivityScope
    @Provides
    static RecyclerView.LayoutManager provideLayoutManager(TestContract.View view) {
        return new GridLayoutManager(view.getActivity(), 2);
    }
    @ActivityScope
    @Provides
    static List<GankItemBean> provideList() {
        return new ArrayList<>();
    }
    @ActivityScope
    @Provides
    static SmartRefreshAdapter provideSmartRefreshAdapter(List<GankItemBean> list) {
        return new SmartRefreshAdapter(list);
    }
}