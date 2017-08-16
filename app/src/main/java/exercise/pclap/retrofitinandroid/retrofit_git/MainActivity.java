package exercise.pclap.retrofitinandroid.retrofit_git;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import exercise.pclap.retrofitinandroid.R;
import exercise.pclap.retrofitinandroid.api_server.APIClient;
import exercise.pclap.retrofitinandroid.api_server.APIInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private APIInterface apiInterface;
    private TextView tvQuery, tvQueryPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewByIds();
        initComponents();
    }

    private void initComponents() {
        apiInterface = APIClient.getClient().create(APIInterface.class);
        apiInterface.getUserGit().enqueue(new Callback<UserGit>() {
            @Override
            public void onResponse(Call<UserGit> call, Response<UserGit> response) {

                if(response.isSuccessful()){
                    UserGit userGit = response.body();
                    String login = userGit.getLogin();
                    int id = userGit.getId();
                    String avatarUrl = userGit.getAvatarUrl();
                    String htmlURl = userGit.getHtmlUrl();
                    String url = userGit.getUrl();
                    tvQuery.setText("Id: " + id + "\nLogin: " + login + "\nAvatarUrl: " + avatarUrl + "\nHTMLUrl: " + htmlURl + "\nUrl: " + url);
                }else {
                    showMs("Khong the ket noi!!!");
                }
            }
            @Override
            public void onFailure(Call<UserGit> call, Throwable t) {
                Log.d(TAG, "Error: " + t.getMessage());
                call.cancel();
            }
        });

        /*
        Query sort
         */
        apiInterface.getUserGit("desc").enqueue(new Callback<UserGit>() {
            @Override
            public void onResponse(Call<UserGit> call, Response<UserGit> response) {
                if(response.isSuccessful()){
                    Log.d(TAG, "Code Res: " + response.code());
                    UserGit userGit = response.body();
                    String login = userGit.getLogin();
                    int id = userGit.getId();
                    String avatarUrl = userGit.getAvatarUrl();
                    String htmlURl = userGit.getHtmlUrl();
                    String url = userGit.getUrl();
                    tvQuery.setText("Id: " + id + "\nLogin: " + login + "\nAvatarUrl: " + avatarUrl + "\nHTMLUrl: " + htmlURl + "\nUrl: " + url);

                }
            }

            @Override
            public void onFailure(Call<UserGit> call, Throwable t) {
                Log.d(TAG, "Error: " + t.getMessage());
            }
        });
        /*
            Query User Path
         */
        apiInterface.getListUser(1).enqueue(new Callback<List<UserGit>>() {
            @Override
            public void onResponse(Call<List<UserGit>> call, Response<List<UserGit>> response) {
                if(response.isSuccessful()){
                    Log.d(TAG, "Code Query List: " + response.code());
                    List<UserGit> listUer = response.body();
                    tvQueryPath.setText(listUer.size() + "");
                }
            }

            @Override
            public void onFailure(Call<List<UserGit>> call, Throwable t) {
                Log.d(TAG, "Error: " + t.getMessage());
                call.cancel();
            }
        });

        /* Create User Post*/
        final UserGit userGit = new UserGit();
        apiInterface.createUser(userGit).enqueue(new Callback<UserGit>() {
            @Override
            public void onResponse(Call<UserGit> call, Response<UserGit> response) {
                if(response.isSuccessful()){
                    Log.d(TAG, "Code Query List: " + response.code());
                    UserGit userResponse = response.body();
                    Log.d(TAG, "Information: " + "Id: " + userResponse.getId() + "___AvatarURl: " + userResponse.getAvatarUrl());

                }else {
                    showMs("Ket noi bi loi!!!");
                }
            }

            @Override
            public void onFailure(Call<UserGit> call, Throwable t) {
                Log.d(TAG, "Error: " + t.getMessage());
                call.cancel();
            }
        });
    }

    private void findViewByIds() {
        tvQuery = (TextView) findViewById(R.id.tv_query);
        tvQueryPath = (TextView) findViewById(R.id.tv_query_path);
    }

    private void showMs(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
