package a123.vaidya.nihal.foodcrunchclient.Remote;

<<<<<<< HEAD
import a123.vaidya.nihal.foodcrunchclient.Model.DataMessage;
import a123.vaidya.nihal.foodcrunchclient.Model.MyResponse;
//import a123.vaidya.nihal.foodcrunchclient.Model.Sender;
=======
import a123.vaidya.nihal.foodcrunchclient.Model.MyResponse;
import a123.vaidya.nihal.foodcrunchclient.Model.Sender;
>>>>>>> old/master
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAA4S21fd0:APA91bFIAgjWvgWc83NcfGemj3qq0ZzZ7kSYZiDe0V7n0GAyuHVEL9RzZaBgvwCVFdnGyVGEjgJe5H14UbtFxk8nFO44QT1KjCr3-RcsUelkzKwutuUc2dVlhK46r0jmBrmqj_LVkB17"
            }

    )
    @POST("fcm/send")
<<<<<<< HEAD
    Call<MyResponse> sendNotification(@Body DataMessage body);
=======
    Call<MyResponse> sendNotification(@Body Sender body);
>>>>>>> old/master

}
