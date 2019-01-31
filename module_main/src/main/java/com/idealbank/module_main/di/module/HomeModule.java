package com.idealbank.module_main.di.module;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.idealbank.module_main.mvp.model.entity.ProjectClassifyData;
import com.idealbank.module_main.mvp.ui.adapter.HomeAdapter;
import com.jess.arms.di.scope.FragmentScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.idealbank.module_main.mvp.contract.HomeContract;
import com.idealbank.module_main.mvp.model.HomeModel;

import java.util.ArrayList;
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
@Module
public abstract class HomeModule {

    @Binds
    abstract HomeContract.Model bindHomeModel(HomeModel model);

    @FragmentScope
    @Provides
    static RecyclerView.LayoutManager provideLayoutManager(HomeContract.View view) {
        return new LinearLayoutManager(view.getActivity());
    }

    @FragmentScope
    @Provides
    static List<ProjectClassifyData> provideList() {
        return new ArrayList<>();
    }

    @FragmentScope
    @Provides
    static HomeAdapter provideSmartRefreshAdapter(List<ProjectClassifyData> list) {
        return new HomeAdapter(list);
    }
}