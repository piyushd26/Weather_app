package com.example.weather_app.model.request;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class NetworkService {
    private static Retrofit retrofit = null;

    public NetworkService() {

    }
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    //http://34.253.70.4
    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl("https://api.openweathermap.org/")
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(new Gson()))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create());

    public static <S> S createService(Class<S> serviceClass) {
        return createService(serviceClass, null);
    }

    public static <S> S createService(Class<S> serviceClass, final String authToken) {
        try {
            if (authToken != null) {
                httpClient.addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();
                        // Request customization: add request headers
                        Request.Builder requestBuilder = original.newBuilder()
                                .method(original.method(), original.body());

                        if (requestBuilder != null) {
                            Request request = requestBuilder.build();
                            return chain.proceed(request);
                        }
                        return null;
                    }
                });

                httpClient.addNetworkInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request().newBuilder().addHeader("Connection", "close").build();
                        return chain.proceed(request);
                    }
                });
            }
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = httpClient.addInterceptor(interceptor).
                    connectTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS).build();
            retrofit = builder.client(client).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retrofit.create(serviceClass);
    }



    public static Retrofit getClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

//http://34.253.70.4
        retrofit = new Retrofit.Builder()
                .baseUrl("https://http://api.openweathermap.org/")
//                .baseUrl("http://103.54.102.133:3036")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit;



    }
}