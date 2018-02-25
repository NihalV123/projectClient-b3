package a123.vaidya.nihal.foodcrunchclient.Remote;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface iGoogleService {
    @GET("maps/api/geocode/json")
    Call<String> getAddresName(@Url String url);
//    @GET("maps/api/directions/json")
//    Call<String> getDirections(@Query("origin") String origin , @Query("destination") String destination);

}
