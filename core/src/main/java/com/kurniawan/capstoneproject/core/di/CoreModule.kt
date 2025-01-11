package com.kurniawan.capstoneproject.core.di

import androidx.room.Room
import com.kurniawan.capstoneproject.core.BuildConfig
import com.kurniawan.capstoneproject.core.data.NewsRepository
import com.kurniawan.capstoneproject.core.data.source.local.LocalDataSource
import com.kurniawan.capstoneproject.core.data.source.local.room.NewsDatabase
import com.kurniawan.capstoneproject.core.data.source.remote.RemoteDataSource
import com.kurniawan.capstoneproject.core.data.source.remote.network.ApiService
import com.kurniawan.capstoneproject.core.domain.repository.INewsRepository
import com.kurniawan.capstoneproject.core.utils.AppExecutors
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory { get<NewsDatabase>().newsDao() }
    single {
        val passphrase: ByteArray = SQLiteDatabase.getBytes("capstone".toCharArray())
        val factory = SupportFactory(passphrase)
        Room.databaseBuilder(
            androidContext(),
            NewsDatabase::class.java, "news.db"
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }
}

val networkModule = module {
    single {
        val hostname = "newsapi.org"
        val certificatePinner = CertificatePinner.Builder()
            .add(hostname, "sha256/Q/Dhu9FBOaTRCRL38yOpIxCg3wvkc5pnQhC7NVqnZoo=")
            .add(hostname, "sha256/kIdp6NNEd8wsugYyyIYFsi1ylMCED3hZbSR8ZFsa/A4=")
            .add(hostname, "sha256/mEflZT5enoR1FuXLgYYGqnVEoZvmf9c2bVBpiOjYQ0c=")
            .build()
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .certificatePinner(certificatePinner)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    factory { AppExecutors() }
    single<INewsRepository> { NewsRepository(get(), get(), get()) }
}