package orlov.surf.summer

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import orlov.surf.summer.school.BuildConfig
import timber.log.Timber

@HiltAndroidApp
class SurfApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

}