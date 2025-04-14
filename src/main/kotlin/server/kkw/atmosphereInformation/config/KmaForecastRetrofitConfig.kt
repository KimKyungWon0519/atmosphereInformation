package server.kkw.atmosphereInformation.config

import okhttp3.OkHttpClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import server.kkw.atmosphereInformation.service.KmaForecastApi

@Configuration
class KmaForecastRetrofitConfig {
    @Value("\${api.kmaForecast.baseURL}")
    private lateinit var baseURL: String

    @Bean
    fun okHttpClient(kmaForecastInterceptor: KmaForecastInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(kmaForecastInterceptor)
            .build()
    }

    @Bean
    fun retrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(baseURL).addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient).build()
    }

    @Bean
    fun kmaForecastApi(retrofit: Retrofit): KmaForecastApi {
        return retrofit.create(KmaForecastApi::class.java)
    }
}