package uz.dsavdo.emakro.network.api

import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import uz.dsavdo.emakro.network.responses.AuthResponse

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
    ):Response<AuthResponse>

    @POST("check-sms")
    @FormUrlEncoded
    suspend fun checkSms(
        @Field("phone") phone:String,
        @Field("sms") sms:String
    ):Response<AuthResponse>
}