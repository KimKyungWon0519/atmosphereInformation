package server.kkw.atmosphereInformation.config

import okhttp3.Interceptor
import okhttp3.Response
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class KmaForecastInterceptor: Interceptor {
    @Value("\${api.kmaForecast.serviceKey}")
    private lateinit var serviceKey: String
    private var dataType: String = "JSON"

    /**
     * 호출 URL에 serviceKey과 dataType을 추가
     */
    override fun intercept(chain: Interceptor.Chain): Response {
        val originRequest = chain.request()

        val url = originRequest.url.newBuilder().apply {
            addQueryParameter("serviceKey", serviceKey)
            addQueryParameter("dataType", dataType)
        }.build()

        val newRequest = originRequest.newBuilder().url(url).build()

        return chain.proceed(newRequest)
    }

}