package uz.dsavdo.emakro.network

import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uz.dsavdo.emakro.app.MainApplication.Companion.context
import uz.dsavdo.emakro.network.Constants.Companion.BASE_URL
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

    val api: MakroService by lazy {
        retrofit.create(MakroService::class.java)
    }
}