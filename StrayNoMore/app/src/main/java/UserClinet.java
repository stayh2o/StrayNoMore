import android.view.textclassifier.ConversationActions;

import com.android.volley.Response;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UserClinet {
    @Multipart
    @POST("upload")
    Call<ResponseBody> uploadPhoto(
            @Part("found_by_user") RequestBody found_by_user,
            @Part MultipartBody.Part photo,
            @Part("found_lat") RequestBody found_lat,
            @Part("found_lon") RequestBody found_lon,
            @Part("landmark") RequestBody landmark
    );
}
