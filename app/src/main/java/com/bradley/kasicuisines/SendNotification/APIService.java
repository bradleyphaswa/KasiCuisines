package com.bradley.kasicuisines.SendNotification;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {

    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAz4vNzag:APA91bFaT17BuXcY_zTiUgNkN2J758GstR_cJD3NuMSsa9qIuwW1_wNom-iUbMo0k5G0Mt2cDMvprMn26CXovIQSXGPb4eLbnb_IWLh4muuDCBL2brJbG8z4Rk75p3GeiqLuwgYhmrfI"
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body NotificationSender body);
}
