package uz.dsavdo.emakro.ui.enter

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uz.dsavdo.emakro.network.MakroRepository
import uz.dsavdo.emakro.network.network.Resource
import uz.dsavdo.emakro.network.responses.AuthResponse
import uz.dsavdo.emakro.utills.getDialogProgressBar
import uz.dsavdo.emakro.utills.showSnackbar
import uz.dsavdo.emakro.utills.showSnackbarWithMargin
import javax.inject.Inject

@HiltViewModel
class EnterViewModel @Inject constructor(private val repository: MakroRepository) : ViewModel() {

    val responseCheckPhone = MutableLiveData<AuthResponse>()
    val responseLogin = MutableLiveData<AuthResponse>()
    val responseCheckSms = MutableLiveData<AuthResponse>()
    var phoneNumber = ""

    fun checkPhone(fragment: Fragment, phone: String) {
        phoneNumber = phone
        viewModelScope.launch {
            val progressDialog = fragment.getDialogProgressBar().create()
            progressDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            try {
                repository.checkPhone(phone).onEach {
                    when (it) {
                        is Resource.Loading -> {
                            progressDialog.show()
                        }
                        is Resource.Error -> {
                            progressDialog.dismiss()
                            fragment.showSnackbarWithMargin(it.exception.message ?: "")
                        }
                        is Resource.Success -> {
                            progressDialog.dismiss()
                            responseCheckPhone.value = it.data
                        }
                        is Resource.GenericError -> {
                            progressDialog.dismiss()
                            fragment.showSnackbarWithMargin(
                                it.errorResponse.jsonResponse.getString(
                                    "message"
                                )
                            )
                            Log.e("TAG", "regions: $it")
                        }
                    }
                }.launchIn(viewModelScope)
            } catch (e: Exception) {
                Log.d("TAG", "getShowrooms: ${e.message}")
            }
        }
    }

    fun login(fragment: Fragment, phone: String, pinCode: String) {

        viewModelScope.launch {
            val progressDialog = fragment.getDialogProgressBar().create()
            progressDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            try {
                repository.login(phone, pinCode).onEach {
                    when (it) {
                        is Resource.Loading -> {
                            progressDialog.show()
                        }
                        is Resource.Error -> {
                            progressDialog.dismiss()
                            fragment.showSnackbarWithMargin(it.exception.message ?: "")
                        }
                        is Resource.Success -> {
                            progressDialog.dismiss()
                            responseLogin.value = it.data
                        }
                        is Resource.GenericError -> {
                            progressDialog.dismiss()
                            fragment.showSnackbarWithMargin(
                                it.errorResponse.jsonResponse.getString(
                                    "message"
                                )
                            )
                            Log.e("TAG", "regions: $it")
                        }
                    }
                }.launchIn(viewModelScope)
            } catch (e: Exception) {
                Log.d("TAG", "getShowrooms: ${e.message}")
            }
        }
    }

    fun checkSms(fragment: Fragment, phone: String, sms: String) {

        viewModelScope.launch {
            val progressDialog = fragment.getDialogProgressBar().create()
            progressDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            try {
                repository.checkSms(phone, sms).onEach {
                    when (it) {
                        is Resource.Loading -> {
                            progressDialog.show()
                        }
                        is Resource.Error -> {
                            progressDialog.dismiss()
                            fragment.showSnackbarWithMargin(it.exception.message ?: "")
                        }
                        is Resource.Success -> {
                            progressDialog.dismiss()
                            responseCheckSms.value = it.data
                        }
                        is Resource.GenericError -> {
                            progressDialog.dismiss()
                            fragment.showSnackbarWithMargin(
                                it.errorResponse.jsonResponse.getString(
                                    "message"
                                )
                            )
                            Log.e("TAG", "regions: $it")
                        }
                    }
                }.launchIn(viewModelScope)
            } catch (e: Exception) {
                Log.d("TAG", "getShowrooms: ${e.message}")
            }
        }
    }

}