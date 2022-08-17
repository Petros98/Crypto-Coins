package task.coinstats.cryptocoins.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import task.coinstats.cryptocoins.BuildConfig
import task.coinstats.cryptocoins.data.api.CoinsApiService
import task.coinstats.cryptocoins.data.repositories.CoinsRepository
import task.coinstats.cryptocoins.data.repositories.CoinsRepositoryImpl
import task.coinstats.cryptocoins.domain.CoinsInteractor
import task.coinstats.cryptocoins.domain.CoinsUseCase
import task.coinstats.cryptocoins.utils.Constants.BASE_URL
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .build()
    } else {
        OkHttpClient
            .Builder()
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): CoinsApiService =
        retrofit.create(CoinsApiService::class.java)

    @Singleton
    @Provides
    fun provideCoinsRepository(apiService: CoinsApiService): CoinsRepository =
        CoinsRepositoryImpl(apiService)

    @Singleton
    @Provides
    fun provideCoinsUseCase(repository: CoinsRepository): CoinsInteractor = CoinsUseCase(repository)
}
