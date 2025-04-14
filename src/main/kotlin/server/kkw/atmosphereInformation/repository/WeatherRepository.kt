package server.kkw.atmosphereInformation.repository

import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import org.springframework.stereotype.Repository
import server.kkw.atmosphereInformation.mapper.toCityWeatherObservation
import server.kkw.atmosphereInformation.model.CityWeatherObservation
import server.kkw.atmosphereInformation.service.CityCoordinatesService
import server.kkw.atmosphereInformation.service.KmaForecastService
import kotlin.system.measureTimeMillis

/**
 * 시도 좌표값을 이용하여 날씨 API 호출
 */
@Repository
class WeatherRepository(
    private val kmaForecastService: KmaForecastService,
    private val cityCoordinatesService: CityCoordinatesService
) {
    /**
     * 모든 시의 날씨 데이터를 가져옴
     *
     * @param baseData 발표일자
     * @param baseTime 발표시각
     *
     * @return Set<[CityWeatherObservation]>
     */
    suspend fun getAllCityWeather(baseData: Long, baseTime: Int): Set<CityWeatherObservation> = runBlocking {
        val cityCoordinates = cityCoordinatesService.getAllCitiesCoord()
        val cityWeatherObservations = cityCoordinates.map {
            async {
                val kmaForecastResponse = kmaForecastService.getUltraSrtNcst(
                    baseData = baseData,
                    baseTime = baseTime,
                    pageNo = 1,
                    nx = it.x,
                    ny = it.y
                )

                kmaForecastResponse.response.body.items.toCityWeatherObservation(it.name)
            }
        }.awaitAll().toSet()

        return@runBlocking cityWeatherObservations
    }
}