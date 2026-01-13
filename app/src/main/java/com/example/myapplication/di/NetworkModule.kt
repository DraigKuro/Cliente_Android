package com.example.myapplication.di

import com.example.myapplication.data.network.DishApiClient
import com.example.myapplication.BuildConfig
import com.example.myapplication.data.network.DrinkApiClient
import com.example.myapplication.data.network.MenuApiClient
import com.example.myapplication.data.network.OrderApiClient
import com.example.myapplication.data.network.PromotionApiClient
import com.example.myapplication.data.network.TableApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideDishApiClient(retrofit: Retrofit): DishApiClient {
        return retrofit.create(DishApiClient::class.java)
    }

    @Singleton
    @Provides
    fun provideDrinkApiClient(retrofit: Retrofit): DrinkApiClient {
        return retrofit.create(DrinkApiClient::class.java)
    }

    @Singleton
    @Provides
    fun provideMenuApiClient(retrofit: Retrofit): MenuApiClient {
        return retrofit.create(MenuApiClient::class.java)
    }

    @Singleton
    @Provides
    fun providePromotionApiClient(retrofit: Retrofit): PromotionApiClient {
        return retrofit.create(PromotionApiClient::class.java)
    }

    @Singleton
    @Provides
    fun provideTableApiClient(retrofit: Retrofit): TableApiClient {
        return retrofit.create(TableApiClient::class.java)
    }

    @Singleton
    @Provides
    fun provideOrderApiClient(retrofit: Retrofit): OrderApiClient {
        return retrofit.create(OrderApiClient::class.java)
    }
}