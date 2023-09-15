package kg.daveem.onlinestore.di

import android.app.Application
import kg.daveem.onlinestore.db.AppDatabase
import kg.daveem.onlinestore.ui.ProductCategoryViewModel
import kg.daveem.onlinestore.ui.ProductDetailViewModel
import kg.daveem.onlinestore.ui.ProductListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class AppModule : Application() {

    override fun onCreate() {
        super.onCreate()

        val appModule = module {
            factory { AppDatabase.getInstance(androidContext().applicationContext).productDao() }

            viewModel { ProductCategoryViewModel(get()) }
            viewModel { ProductListViewModel(get()) }
            viewModel { ProductDetailViewModel(get()) }
        }

        startKoin {
            androidContext(this@AppModule)
            modules(appModule)
        }
    }
}