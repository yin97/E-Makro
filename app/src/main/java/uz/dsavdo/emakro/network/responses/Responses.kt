package uz.dsavdo.emakro.network.responses

import com.google.gson.annotations.SerializedName


data class AuthResponse(
    val currentStage: Int? = null,
    val nextStage: Int? = null,
    val message: String? = null,
    val status: Boolean? = null
)

data class LoginResponse(
    val status: Boolean? = null,
    val data: UserData? = null
)

data class UserData(
    val id: Int? = null,
    val phone: String? = null,
    @SerializedName("created_at")
    val createdTime: String? = null,
    val token: String? = null,
    val expires: Int? = null
)

data class CardResponse(
    val id: Long? = null,
    val bot_id: Long? = null,
    val client_id: Long? = null,
    val amount: String? = null,
    val artix_id: String? = null,
    val expences_last_month: String? = null,
    val balance_changed: Any? = null,
    val ss_id: Any? = null,
    val push_token: Any? = null,
    val incomes: Any? = null,
    val sms_sent: Int? = null
)