package uz.dsavdo.emakro.network

import android.util.Log
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uz.dsavdo.emakro.app.MainApplication.Companion.context
import uz.dsavdo.emakro.network.Constants.Companion.BASE_URL
import uz.dsavdo.emakro.network.Constants.Companion.CLIENT_BASE_URL
import uz.dsavdo.emakro.network.api.MakroService
import java.util.concurrent.TimeUnit

object Instance {
    private val retrofit by lazy {
        Retrofit.Builder().client(
            OkHttpClient().newBuilder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(
                    ChuckerInterceptor.Builder(context!!)
                        .collector(ChuckerCollector(context!!))
                        .maxContentLength(250000L)
                        .redactHeaders(emptySet())
                        .alwaysReadResponseBody(false)
                        .build()
                )
                .build()
        )
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val retrofitClient by lazy {
        Retrofit.Builder().client(
            OkHttpClient().newBuilder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(object : Interceptor {
                    override fun intercept(chain: Interceptor.Chain): Response {
                        try {
                            val request = chain.request().newBuilder()
                            request.addHeader("Content-Type", "application/json")
                            request.addHeader(
                                "X-Authorization",
                                "Bearer 777916493:AAHo74x9173OfTM8qV3-E9I409eW7egzHaA"
                            )
                            return chain.proceed(request.build())
                        } catch (e: Exception) {
                            Log.d("TAG", "intercept: ${e.message}")
                        }
                        return chain.proceed(chain.request())
                    }
                }
                )
                .addInterceptor(
                    ChuckerInterceptor.Builder(context!!)
                        .collector(ChuckerCollector(context!!))
                        .maxContentLength(250000L)
                        .redactHeaders(emptySet())
                        .alwaysReadResponseBody(false)
                        .build()
                )
                .build()
        )
            .baseUrl(CLIENT_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: MakroService by lazy {
        retrofit.create(MakroService::class.java)
    }
    val apiClient: MakroService by lazy {
        retrofitClient.create(MakroService::class.java)
    }
}