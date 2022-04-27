package uz.dsavdo.emakro.network.api

import retrofit2.Response
import retrofit2.http.*
import uz.dsavdo.emakro.network.responses.AuthResponse
import uz.dsavdo.emakro.network.responses.CardResponse
import uz.dsavdo.emakro.network.responses.LoginResponse

interface MakroService {

    @POST("check-phone")
    @FormUrlEncoded
    suspend fun checkPhone(
        @Field("phone") phone:String
    ):Response<AuthResponse>

    @POST("login")
    @FormUrlEncoded
    suspend fun login(
        @Field("phone") phone:String,
        @Field("pin_code") pinCode:String
    ):Response<LoginResponse>

    @POST("check-sms")
    @FormUrlEncoded
    suspend fun checkSms(
        @Field("phone") phone:String,
        @Field("sms") sms:String
    ):Response<AuthResponse>

    @POST("register")
    @FormUrlEncoded
    suspend fun register(
        @Field("phone") phone:String,
        @Field("sms") sms:String,
        @Field("pin_code") pinCode: String
    ):Response<AuthResponse>

    @GET("getcardbyphone")
    suspend fun getCardByPhone(
        @Query("phone") phone: String
    ):Response<CardResponse>
}