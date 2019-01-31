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
package com.idealbank.module_main.mvp.model.api.service;

import com.idealbank.module_main.mvp.model.entity.BaseResponse;
import com.idealbank.module_main.mvp.model.entity.KnowledgeHierarchyData;
import com.idealbank.module_main.mvp.model.entity.ProjectClassifyData;
import com.idealbank.module_main.mvp.model.entity.User;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static com.idealbank.module_main.mvp.model.api.Api.GITHUB_DOMAIN_NAME;
import static com.idealbank.module_main.mvp.model.api.Api.wanandroid_DOMAIN_NAME;
import static me.jessyan.retrofiturlmanager.RetrofitUrlManager.DOMAIN_NAME_HEADER;

/**
 * ================================================
 * 展示 {@link Retrofit#create(Class)} 中需要传入的 ApiService 的使用方式
 * 存放关于 gank 的一些 API
 * <p>
 * Created by JessYan on 08/05/2016 12:05
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public interface MainService {

    /**
     * 如果不需要多个 BaseUrl, 继续使用初始化时传入 Retrofit 中的默认 BaseUrl, 就不要加上 DOMAIN_NAME_HEADER 这个 Header
     */
//    @Headers({DOMAIN_NAME_HEADER + wanandroid_DOMAIN_NAME})
    @GET("/tree/json")
    Observable<BaseResponse<List<ProjectClassifyData>>> getGirlList();


    @Headers({DOMAIN_NAME_HEADER + GITHUB_DOMAIN_NAME})
    @GET("/users")
    Observable<List<User>> getUsers(@Query("since") int lastIdQueried, @Query("per_page") int perPage);

}
