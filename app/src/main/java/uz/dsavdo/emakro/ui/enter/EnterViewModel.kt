package uz.dsavdo.emakro.ui.enter

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uz.dsavdo.emakro.R
import uz.dsavdo.emakro.network.Constants
import uz.dsavdo.emakro.network.repository.MakroRepository
import uz.dsavdo.emakro.network.network.Resource
import uz.dsavdo.emakro.network.repository.DataStoreRepository
import uz.dsavdo.emakro.network.responses.AuthResponse
import uz.dsavdo.emakro.network.responses.CardResponse
import uz.dsavdo.emakro.network.responses.LoginResponse
import uz.dsavdo.emakro.ui.main.MainActivity
import uz.dsavdo.emakro.utills.SharedPrefs
import uz.dsavdo.emakro.utills.getDialogProgressBar
import uz.dsavdo.emakro.utills.showSnackbar
import uz.dsavdo.emakro.utills.showSnackbarWithMargin
import javax.inject.Inject

@HiltViewModel
class EnterViewModel @Inject constructor(
    private val repository: MakroRepository
) : ViewModel() {

    var phoneNumber = ""
    var registerSmsCode = ""
    val responseLogin = MutableLiveData<LoginResponse>()
    val responseGetCard = MutableLiveData<CardResponse>()

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
                            navigateNextPage(it, fragment)
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

    private fun navigateNextPage(it: Resource.Success<AuthResponse>, fragment: Fragment) {
        val res = it.data
        when (res.nextStage) {
            2 -> {
                if (res.message == "success") {
                    fragment.findNavController().navigate(R.id.checkSmsFragment)
                }
            }
            4 -> {
                if (res.message == "success") {
                    val bundle = Bundle()
                    bundle.putInt("stage", res.nextStage)
                    fragment.findNavController().navigate(R.id.screenLockFragment,bundle)
                }
            }
            3 -> {
                if (res.message == "success") {
                    val bundle = Bundle()
                    bundle.putInt("stage", res.nextStage)
                    fragment.findNavController().navigate(R.id.registerFragment,bundle)
                }
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
                            navigateNextPage(it, fragment)

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

    fun register(fragment: Fragment, phone: String, sms: String, pinCode: String) {

        viewModelScope.launch {
            val progressDialog = fragment.getDialogProgressBar().create()
            progressDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            try {
                repository.register(phone, sms, pinCode).onEach {
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
                            navigateNextPage(it, fragment)

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

    fun getCard(fragment: Fragment, phone: String) {

        viewModelScope.launch {
            try {
                repository.getCard(phone).onEach {
                    when (it) {
                        is Resource.Loading -> {
                        }
                        is Resource.Error -> {
                            fragment.showSnackbar(it.exception.message ?: "")
                        }
                        is Resource.Success -> {
                            responseGetCard.value = it.data
                        }
                        is Resource.GenericError -> {
                            fragment.showSnackbar(
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