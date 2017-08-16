package exercise.pclap.retrofitinandroid.api_server;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by PC Lap on 8/16/2017.
 */
public class APIClient {

    private static Retrofit retrofit = null;
    private static final String BASE_URL = "https://api.github.com/";
    private static final String BASE_URL_RE = "https://reqres.in";

    private static OkHttpClient getOKHttp(){
        // Khai bao log de lay ra gia tri cua request
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
//                                .readTimeout(1000, null)
//                                .connectTimeout(1000, null)
//                                .writeTimeout(1000, null)
                .addInterceptor(interceptor)
                .build();
        return client;
    }


    public static Retrofit getClient(){

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(getOKHttp())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;

    }

    public static Retrofit getClientReq(){
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL_RE)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getOKHttp())
                .build();
        return retrofit;
    }

}
