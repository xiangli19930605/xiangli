package com.idealbank.module_main.di.module;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.idealbank.module_main.mvp.model.entity.User;
import com.idealbank.module_main.mvp.ui.adapter.PayAdapter;
import com.jess.arms.di.scope.FragmentScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.idealbank.module_main.mvp.contract.PayContract;
import com.idealbank.module_main.mvp.model.PayModel;

import java.util.ArrayList;
import java.util.List;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/29/2019 10:27
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class PayModule {

    @Binds
    abstract PayContract.Model bindPayModel(PayModel model);
    @FragmentScope
    @Provides
    static RecyclerView.LayoutManager provideLayoutManager(PayContract.View view) {
        return new GridLayoutManager(view.getActivity(), 2);
    }

    @FragmentScope
    @Provides
    static List<User> provideUserList() {
        return new ArrayList<>();
    }

    @FragmentScope
    @Provides
    static PayAdapter provideUserAdapter(List<User> list){
        return new PayAdapter(list);
    }
}