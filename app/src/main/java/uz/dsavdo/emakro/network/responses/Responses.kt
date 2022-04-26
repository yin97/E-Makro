package uz.dsavdo.emakro.network.responses


data class AuthResponse(
    val currentStage:Int?=null,
    val nextStage:Int?=null,
    val message:String?=null,
    val status:Boolean?=null
)