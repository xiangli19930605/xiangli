/*
 * Copyright 2017 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.idealbank.module_one.mvp.ui.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.idealbank.module_one.R;
import com.idealbank.module_one.mvp.model.entity.GankItemBean;
import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.ArmsUtils;

import java.util.List;

import me.jessyan.armscomponent.commonsdk.imgaEngine.config.CommonImageConfigImpl;


/**
 * ================================================
 * 展示 {@link DefaultAdapter} 的用法
 * <p>
 * Created by JessYan on 09/04/2016 12:57
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class SmartRefreshAdapter extends BaseQuickAdapter<GankItemBean, BaseViewHolder> {
    private AppComponent mAppComponent;
    private ImageLoader mImageLoader;//用于加载图片的管理类,默认使用 Glide,使用策略模式,可替换框架
    public SmartRefreshAdapter(@Nullable List data) {
        super(R.layout.gank_recycle_list, data);

    }


    @Override
    protected void convert(BaseViewHolder helper, GankItemBean data) {
//        GlideApp.with(mContext).load(item.getBgPicture()).into((ImageView) helper.getView(R.id.iv_item_smart_refresh));

//        helper.setText(R.id.tv_item_smart_refresh, item.getName());
        //可以在任何可以拿到 Context 的地方,拿到 AppComponent,从而得到用 Dagger 管理的单例对象
        mAppComponent = ArmsUtils.obtainAppComponentFromContext(helper.itemView.getContext());
        mImageLoader = mAppComponent.imageLoader();
        ImageView mAvatar = (ImageView) helper.getView(R.id.iv_avatar);
        if (!TextUtils.isEmpty(data.getUrl())) {
            mImageLoader.loadImage(helper.itemView.getContext(),
                    CommonImageConfigImpl
                            .builder()
                            .url(data.getUrl())
                            .imageView(mAvatar)
                            .build());


//            Glide.with(PoliceApplication.getContext())
//                    .load(AppConstant.URL_ENTERPRISE + item.getPhoto())
//                    .placeholder(R.drawable.ic_image_loading)
//                    .error(R.drawable.ic_default_adimage)
//                    .into(img);
        } else {
//            mAvatar.setImageResource(R.mipmap.gank_ic_logo);
        }
    }
}