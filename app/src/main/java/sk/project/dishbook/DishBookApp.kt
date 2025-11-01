package sk.project.dishbook

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import sk.project.dishbook.di.appModule

class DishBookApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@DishBookApplication)
            modules(appModule)
        }
    }
}