package `in`.org.celesta.admin.api

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface RetrofitApi {

    @Multipart
    @POST("login.php")
    fun login(@Part("email") email: RequestBody, @Part("password") password: RequestBody): Call<LoginResponse>

    @Multipart
    @POST("checkin_checkout.php")
    fun checkUser(
        @Part("access_token") access_token: RequestBody, @Part("permit") permit: RequestBody,
        @Part("celestaid") celestaid: RequestBody, @Part("date_time") date_time: RequestBody,
        @Part("email") email: RequestBody
    ): Call<CheckinCheckoutResponse>

    @Multipart
    @POST("hospi_checkin_checkout.php")
    fun checkAccommodation(
        @Part("access_token") access_token: RequestBody, @Part("permit") permit: RequestBody,
        @Part("celestaid") celestaid: RequestBody, @Part("date_time") date_time: RequestBody,
        @Part("email") email: RequestBody
    ): Call<CheckinCheckoutResponse>

    @Multipart
    @POST("eventUsers.php")
    fun getEventRegisteredUsers(
        @Part("access_token") access_token: RequestBody, @Part("permit") permit: RequestBody,
        @Part("email") email: RequestBody, @Part("ev_id") ev_id: RequestBody
    ): Call<EventUsersResponse>

    @Multipart
    @POST("eventsDetail.php")
    fun getAllEvents(
        @Part("access_token") access_token: RequestBody, @Part("permit") permit: RequestBody,
        @Part("email") email: RequestBody
    ): Call<AllEventsResponse>

    @Multipart
    @POST("getDetails.php")
    fun getUserDetails(
        @Part("access_token") access_token: RequestBody, @Part("permit") permit: RequestBody,
        @Part("email") email: RequestBody, @Part("celestaid") celestaid: RequestBody
    ): Call<UserDetailsResponse>


    companion object Factory {
        fun create(): RetrofitApi {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://celesta.org.in/backend/admin/functions/app/")
                .build()

            return retrofit.create(RetrofitApi::class.java)
        }
    }
}