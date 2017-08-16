package exercise.pclap.retrofitinandroid.retrofit_reqres;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import exercise.pclap.retrofitinandroid.R;
import exercise.pclap.retrofitinandroid.api_server.APIClient;
import exercise.pclap.retrofitinandroid.api_server.APIInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by PC Lap on 8/16/2017.
 */
public class MainApi extends AppCompatActivity {
    private static final String TAG = MainApi.class.getSimpleName();
    private APIInterface mApiClient;
    private TextView tvShowListUserPage, tvUserData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reqres_in);
        findViewByIds();
        initComponents();
        setEvents();
    }

    private void setEvents() {
    }

    private void findViewByIds() {
        tvShowListUserPage = (TextView) findViewById(R.id.query_list_user_in_page);
        tvUserData = (TextView) findViewById(R.id.tv_user);

    }
    private void initComponents() {
        mApiClient = APIClient.getClientReq().create(APIInterface.class);

        mApiClient.getListRe("1").enqueue(new Callback<ListUser>() {
            @Override
            public void onResponse(Call<ListUser> call, Response<ListUser> response) {
                if(response.isSuccessful()){
                    Log.d(TAG, "Code List User: " + response.code());
                    ListUser listUserInPage = response.body();
                    String page = listUserInPage.getPage();
                    int personPage = listUserInPage.getPerPage();
                    int total = listUserInPage.getTotal();
                    int totalPage = listUserInPage.getTotalPages();

                    Log.d(TAG, "Information: " + "page: " + page + "___" + "Person Page: " + personPage + "___" + "Total: " + total + "___Total Page: " + totalPage);

                    tvShowListUserPage.setText("page: " + page + "___" + "Person Page: " + personPage + "___" + "Total: " + total + "___Total Page: " + totalPage);
                }
            }

            @Override
            public void onFailure(Call<ListUser> call, Throwable t) {
                methodError(call, t);
            }
        });

        //query user
        mApiClient.getUser(1).enqueue(new Callback<DataUser>() {
            @Override
            public void onResponse(Call<DataUser> call, Response<DataUser> response) {
                if(response.isSuccessful()){
                    DataUser date = response.body();
//                    Integer id = date.getId();
                    String firstName = date.getFirstName();
                    String lastName = date.getLastName();
                    String avatar = date.getAvatar();
                    tvUserData.setText("FirstName: " + firstName + "_lastName: " + lastName + "_Avatar: " + avatar);

                }
            }

            @Override
            public void onFailure(Call<DataUser> call, Throwable t) {
                methodError(call, t);
            }
        });
        User user = new User("1", "a", "b");
        //post user
        mApiClient.postUser(user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    User user = response.body();
                    String id = user.getId();
                    String name = user.getName();
                    String job = user.getJob();

                    Log.d(TAG, "Id: " + id + " Name: " + name + " Job: "+ job);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                methodError(call, t);
            }
        });

    }

    //information error
    private void methodError(Call<? extends Object> call, Throwable t){
        Log.d(TAG, "Error Information: " + t.getMessage());
        call.cancel();
    }
}
