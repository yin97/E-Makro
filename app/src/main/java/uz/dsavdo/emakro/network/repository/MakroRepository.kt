package uz.dsavdo.emakro.network.repository

import kotlinx.coroutines.flow.flow
import uz.dsavdo.emakro.network.Instance
import uz.dsavdo.emakro.network.network.Resource
import uz.dsavdo.emakro.network.network.safeApiCall
import javax.inject.Inject

class MakroRepository @Inject constructor() {

    suspend fun checkPhone(phone: String) = flow {
        emit(Resource.Loading)
        emit(safeApiCall { Instance.api.checkPhone(phone) })
    }

    suspend fun login(phone: String, pinCode: String) = flow {
        emit(Resource.Loading)
        emit(safeApiCall { Instance.api.login(phone, pinCode) })
    }

    suspend fun checkSms(phone: String, sms: String) = flow {
        emit(Resource.Loading)
        emit(safeApiCall { Instance.api.checkSms(phone, sms) })
    }

    suspend fun register(phone: String, sms: String, pinCode: String) = flow {
        emit(Resource.Loading)
        emit(safeApiCall { Instance.api.register(phone, sms, pinCode) })
    }

    suspend fun getCard(phone: String) = flow {
        emit(Resource.Loading)
        emit(safeApiCall { Instance.apiClient.getCardByPhone(phone) })
    }

}