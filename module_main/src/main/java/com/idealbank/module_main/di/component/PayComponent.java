package com.idealbank.module_main.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.idealbank.module_main.di.module.PayModule;
import com.idealbank.module_main.mvp.contract.PayContract;

import com.jess.arms.di.scope.FragmentScope;
import com.idealbank.module_main.mvp.ui.fragment.PayFragment;


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
@FragmentScope
@Component(modules = PayModule.class, dependencies = AppComponent.class)
public interface PayComponent {
    void inject(PayFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        PayComponent.Builder view(PayContract.View view);

        PayComponent.Builder appComponent(AppComponent appComponent);

        PayComponent build();
    }
}