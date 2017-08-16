package exercise.pclap.retrofitinandroid.api_server;

import java.util.List;

import exercise.pclap.retrofitinandroid.retrofit_git.UserGit;
import exercise.pclap.retrofitinandroid.retrofit_reqres.DataUser;
import exercise.pclap.retrofitinandroid.retrofit_reqres.ListUser;
import exercise.pclap.retrofitinandroid.retrofit_reqres.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by PC Lap on 8/16/2017.
 */
public interface APIInterface {
    //api git
    @GET("users/list")
    Call<UserGit> getUserGit();
    @GET("users/list")
    Call<UserGit> getUserGit(@Query("sort") String desc);
    @GET("group/{id}/users")
    Call<List<UserGit>> getListUser(@Path("id") int groupId);
    @POST("users/new")
    Call<UserGit> createUser(@Body UserGit userGit);

    //api req
    @GET("/api/users")
    Call<ListUser> getListRe(@Query("page") String page);
    @GET("/api/users")
    Call<DataUser> getUser(@Query("id") Integer id);
    @POST("/api/users")
    Call<User> postUser(@Body User user);

}
