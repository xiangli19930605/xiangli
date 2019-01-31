package com.idealbank.module_main.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.idealbank.module_main.mvp.ui.activity.Main2Activity;
import com.jess.arms.di.component.AppComponent;

import com.idealbank.module_main.di.module.MainModule;
import com.idealbank.module_main.mvp.contract.MainContract;

import com.jess.arms.di.scope.ActivityScope;
import com.idealbank.module_main.mvp.ui.activity.MainActivity;


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
@ActivityScope
@Component(modules = MainModule.class, dependencies = AppComponent.class)
public interface MainComponent {
    void inject(Main2Activity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        MainComponent.Builder view(MainContract.View view);

        MainComponent.Builder appComponent(AppComponent appComponent);

        MainComponent build();
    }
}