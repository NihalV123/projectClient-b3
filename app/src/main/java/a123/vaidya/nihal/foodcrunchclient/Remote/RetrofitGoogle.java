package a123.vaidya.nihal.foodcrunchclient.Remote;

import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitGoogle {
    private static Retrofit retrofit=null;
    public static Retrofit getGoogleClient (String baseURL)
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
