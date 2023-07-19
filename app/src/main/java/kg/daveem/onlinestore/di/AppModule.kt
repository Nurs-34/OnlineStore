package kg.daveem.onlinestore.di

import android.app.Application
import kg.daveem.onlinestore.db.AppDatabase
import kg.daveem.onlinestore.db.dao.ProductDao
import kg.daveem.onlinestore.ui.ProductCategoryViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class AppModule : Application() {

    override fun onCreate() {
        super.onCreate()

        val appModule = module {
            single { AppDatabase.getInstance(androidContext().applicationContext).productDao() }

            viewModel { ProductCategoryViewModel(get()) }
        }

        startKoin {
            androidContext(this@AppModule)
            modules(appModule)
        }


    }
}