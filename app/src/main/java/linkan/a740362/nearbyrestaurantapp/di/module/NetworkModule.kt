package linkan.a740362.nearbyrestaurantapp.di.module


import linkan.a740362.nearbyrestaurantapp.BuildConfig
import linkan.a740362.nearbyrestaurantapp.data.network.CoroutineApiService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import linkan.a740362.nearbyrestaurantapp.data.network.RxApiService
import linkan.a740362.nearbyrestaurantapp.di.annotation.CoroutineRetrofit
import linkan.a740362.nearbyrestaurantapp.di.annotation.RxRetrofit
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import okhttp3.logging.HttpLoggingInterceptor.Level.NONE
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {


    @Provides
    @Singleton
    internal fun provideCoroutineService(@CoroutineRetrofit retrofit: Retrofit) : CoroutineApiService = createService(retrofit, CoroutineApiService::class.java)

    @Provides
    @Singleton
    internal fun provideRxService(@RxRetrofit retrofit: Retrofit) : RxApiService = createService(retrofit, RxApiService::class.java)


    private fun<T> createService(retrofit: Retrofit, clazz : Class<T>) : T =  retrofit.create(clazz)


    @Provides
    @Singleton
    @CoroutineRetrofit
    internal fun provideCoroutineRetrofit(okHttpClient: OkHttpClient, gsonConverter : GsonConverterFactory): Retrofit {

        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverter)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }


    @Provides
    @Singleton
    @RxRetrofit
    internal fun provideRxRetrofit(okHttpClient: OkHttpClient, gsonConverter : GsonConverterFactory): Retrofit {

        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverter)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }



    @Provides
    @Singleton
    internal fun provideOKHttpClient(loggingInterceptor: HttpLoggingInterceptor, headerInterceptor: Interceptor)  : OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(headerInterceptor)
        .build()


    @Provides
    @Singleton
    internal fun provideLoggingInt() : HttpLoggingInterceptor {
            return HttpLoggingInterceptor().apply { level = if (BuildConfig.DEBUG) BODY else NONE }
    }

    @Provides
    @Singleton
    internal fun provideHeaderInt() : Interceptor {
        return Interceptor { chain ->
            val request = chain.request()
                .newBuilder()
                .addHeader("user-key", "4feaa2167c4dc6beadf629319423bd4b")
                .build()
            chain.proceed(request)
        }
    }


    @Provides
    @Singleton
    internal fun provideConverter(gson : Gson) : GsonConverterFactory = GsonConverterFactory.create(gson)


    @Provides
    @Singleton
    internal fun provideGson() : Gson = GsonBuilder().create()


}
