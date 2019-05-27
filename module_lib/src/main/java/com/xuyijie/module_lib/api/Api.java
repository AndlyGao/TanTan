package com.xuyijie.module_lib.api;

import com.xuyijie.module_lib.base.BaseGson;
import com.xuyijie.module_lib.entity.UserStateBean;
import com.xuyijie.module_lib.entity.UserStateListBean;
import com.xuyijie.module_lib.gson.HomeTitleGson;
import com.xuyijie.module_lib.gson.UserGson;
import com.xuyijie.module_lib.gson.UserPostGson;
import com.xuyijie.module_lib.gson.UserTopicGson;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface Api {
    @GET("/Mango/public/index.php/index/Home/queryHomeActiveName")
    Observable<BaseGson<HomeTitleGson>> queryHomeActiveName();

    @GET("/Mango/public/index.php/index/Post/queryUserPostByPage")
    Observable<BaseGson<UserPostGson>> queryUserPostByPage(@Query("page")String page, @Query("userId")String userId);

    @GET("/Mango/public/index.php/index/Post/queryUserPostTopic")
    Observable<BaseGson<UserTopicGson>> queryUserPostTopic(@Query("userId")String userId);

    @GET("/Mango/public/index.php/index/User/queryHotUserList")
    Observable<BaseGson<UserGson>> queryHotUserList(@Query("latitude")String latitude,@Query("longitude") String longitude);

    /*获取好友在线信息*/
    @GET("/v1/users/{username}/userstat")
    Call<UserStateBean> isFriendState(
            @Path("username") String username);

    /*获取好友批量在线状态*/

    @POST("/v1/users/userstat")
    Call<UserStateListBean> isFriendsStateList(@Query("") String[] list);
}
