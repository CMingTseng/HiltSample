package com.kapri.hiltapplication.di

import com.kapri.hiltapplication.network.CountryClient
import com.kapri.hiltapplication.network.CountryService
import com.kapri.hiltapplication.network.HttpRequestInterceptor
import com.kapri.hiltapplication.utils.Constants
import com.skydoves.sandwich.coroutines.CoroutinesDataSourceCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
//import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
//@InstallIn(ApplicationComponent::class)
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpRequestInterceptor())
            .build()
    }

    //Ref : https://stackoverflow.com/questions/58677339/moshi-1-9-x-cannot-serialize-kotlin-type
    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
//    .add(AbsPlayerConverter)
//    .add(AbsRegionConverter)
//    .add(AbsTournamentConverter)
//    .add(MatchConverter)
//    .add(RankedPlayerConverter)
//    .add(SimpleDateConverter)
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(CoroutinesDataSourceCallAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun provideCountryService(retrofit: Retrofit): CountryService {
        return retrofit.create(CountryService::class.java)
    }

    @Provides
    @Singleton
    fun provideCountryClient(countryService: CountryService): CountryClient {
        return CountryClient(countryService)
    }
}