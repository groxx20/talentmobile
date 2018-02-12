package com.talentomobile.assignment.di.modules

import com.talentomobile.assignment.BuildConfig
import com.talentomobile.assignment.di.scopes.PerService
import com.talentomobile.assignment.network.NetworkService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import rx.subscriptions.CompositeSubscription
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

/**
 * Created by pavel on 23/1/18.
 */

@Module
class NetworkModule  {


    @PerService
    @Provides
    fun providesNetworkService(retrofit: Retrofit): NetworkService {
        return retrofit.create(NetworkService::class.java)
    }

    @PerService
    @Provides
    fun providesCompositeSubscription(): CompositeSubscription {
        return CompositeSubscription()
    }


    @PerService
    @Provides
    fun providesRxAdapter(): RxJavaCallAdapterFactory {
        return RxJavaCallAdapterFactory.create()
    }

    @PerService
    @Provides
    fun providesGsonClient(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @PerService
    @Provides
    fun providesScalars(): ScalarsConverterFactory {
        return ScalarsConverterFactory.create()
    }

    @PerService
    @Provides
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient = OkHttpClient.Builder()
           // .cache(cache)
            .addInterceptor(httpLoggingInterceptor)
            .build()


    @Provides
    @PerService
    fun provideHttpLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BASIC else HttpLoggingInterceptor.Level.NONE
    }

  /*  @PerService
    @Provides
    fun provideCache(mApp: TalentoApp): Cache {
        val cacheSize = 10 * 1024 * 1024 // 10 MiB
        return Cache(mApp.cacheDir, cacheSize.toLong())
    } */


    @Provides
    @PerService
    fun providesApi(rxJavaCallAdapterFactory: RxJavaCallAdapterFactory, gsonConverterFactory: GsonConverterFactory, scalarsConverterFactory: ScalarsConverterFactory, okHttpClient: OkHttpClient): Retrofit {


        val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
            @Throws(java.security.cert.CertificateException::class)
            override fun checkClientTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {
            }

            @Throws(java.security.cert.CertificateException::class)
            override fun checkServerTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {
            }

            override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> {
                return arrayOf()
            }
        })
        // Install the all-trusting trust manager
        SSLContext.getInstance("SSL").init(null, trustAllCerts, java.security.SecureRandom())

        return Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(gsonConverterFactory)
                .addConverterFactory(scalarsConverterFactory)
                .addCallAdapterFactory(rxJavaCallAdapterFactory)
                .build()
    }
}