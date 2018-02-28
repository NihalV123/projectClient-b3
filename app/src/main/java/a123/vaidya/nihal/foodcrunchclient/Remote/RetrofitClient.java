package a123.vaidya.nihal.foodcrunchclient.Remote;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit=null;
    public static Retrofit getClient (String baseURL)
    {
        if(retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseURL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    //or add back gsonconverterfactory
                    .build();
        }
        return retrofit;
    }

}
