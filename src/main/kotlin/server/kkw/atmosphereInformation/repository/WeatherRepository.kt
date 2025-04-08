package server.kkw.atmosphereInformation.repository

import org.springframework.stereotype.Repository
import server.kkw.atmosphereInformation.mapper.toCityWeatherObservation
import server.kkw.atmosphereInformation.model.CityWeatherObservation
import server.kkw.atmosphereInformation.service.CityCoordinatesService
import server.kkw.atmosphereInformation.service.KmaForecastService

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
    suspend fun getAllCityWeather(baseData: Long, baseTime: Int): Set<CityWeatherObservation> {
        val cityCoordinates = cityCoordinatesService.getAllCitiesCoord()
        val cityWeatherObservations = mutableSetOf<CityWeatherObservation>()

        cityCoordinates.forEach {
            val kmaForecastResponse = kmaForecastService.getUltraSrtNcst(
                baseData = baseData,
                baseTime = baseTime,
                pageNo = 1,
                nx = it.x,
                ny = it.y
            )

            cityWeatherObservations.add(kmaForecastResponse.response.body.items.toCityWeatherObservation(it.name))
        }

        return cityWeatherObservations
    }
}