package uz.dsavdo.emakro.app

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.yariksoffice.lingver.Lingver
import com.yariksoffice.lingver.store.PreferenceLocaleStore
import dagger.hilt.android.HiltAndroidApp
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import java.util.*

@HiltAndroidApp
class MainApplication:Application() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        var context: Context? = null
        const val LANGUAGE_ENGLISH = "en"
        const val LANGUAGE_RUSSIAN = "ru"
        const val LANGUAGE_UZBEKISTAN = "uz"

    }

    override fun onCreate() {
        super.onCreate()
        context = this
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(applicationContext)
            modules(listOf(appModule, viewModelsModule))
        }
        Lingver.init(this, PreferenceLocaleStore(this, Locale(LANGUAGE_RUSSIAN)))
    }
}