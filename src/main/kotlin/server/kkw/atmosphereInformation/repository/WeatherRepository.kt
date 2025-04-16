package server.kkw.atmosphereInformation.repository

import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import org.springframework.stereotype.Repository
import server.kkw.atmosphereInformation.mapper.toCityWeatherObservation
import server.kkw.atmosphereInformation.model.CityCoordinate
import server.kkw.atmosphereInformation.model.CityWeatherObservation
import server.kkw.atmosphereInformation.service.CityCoordinatesService
import server.kkw.atmosphereInformation.service.KmaForecastService

/**
 * 시도 좌표값을 이용하여 날씨 API 호출
 */
@Repository
class WeatherRepository(
    private val kmaForecastService: KmaForecastService, private val cityCoordinatesService: CityCoordinatesService
) {
    /**
     * 모든 시의 날씨 데이터를 가져옴
     *
     * @param baseData 발표일자
     * @param baseTime 발표시각
     *
     * @return
     * Set<[CityWeatherObservation]>
     *
     * 도시 별 날씨 측정값, 특정 도시의 데이터가 없는 경우 해당 도시만 observation를 []처리
     */
    suspend fun getAllCityWeather(baseData: Long, baseTime: Int): Set<CityWeatherObservation> = runBlocking {
        val cityCoordinates = cityCoordinatesService.getAllCitiesCoord()
        val cityWeatherObservations = cityCoordinates.map {
            async {
                getWeather(
                    cityCoordinate = it, baseData = baseData, baseTime = baseTime
                )
            }
        }.awaitAll().toSet()

        return@runBlocking cityWeatherObservations
    }

    /**
     * 특정 시의 날씨 데이터를 가져옴
     *
     * @param name 시 이름
     * @param baseData 발표일자
     * @param baseTime 발표시각
     *
     * @return
     * [CityWeatherObservation]
     *
     * 특정 도시의 날씨 측정값, 특정 도시의 데이터가 없는 경우 observation를 []처리
     */
    suspend fun getCityWeather(
        name: String, baseData: Long, baseTime: Int
    ): CityWeatherObservation {
        val cityCoordinate = cityCoordinatesService.getCityCoord(name)

        return getWeather(
            cityCoordinate = cityCoordinate, baseData = baseData, baseTime = baseTime
        )
    }

    /**
     * 도시의 날씨 데이터 API 결과를 처리하는 함수
     *
     * @param cityCoordinate 시도 좌표 모델
     * @param baseData 발표일자
     * @param baseTime 발표시각
     *
     * @return
     * [CityWeatherObservation]
     *
     * 전달받은 좌표의 날씨 측정값, 데이터가 없는 경우 observation를 []처리
     */
    private suspend fun getWeather(
        cityCoordinate: CityCoordinate, baseData: Long, baseTime: Int
    ): CityWeatherObservation {
        val result = kmaForecastService.getUltraSrtNcst(
            baseData = baseData, baseTime = baseTime, pageNo = 1, nx = cityCoordinate.x, ny = cityCoordinate.y
        )

        return if (result.isSuccess && result.getOrNull() != null) {
            val kmaForecastResponse = result.getOrNull()!!

            kmaForecastResponse.body.items.toCityWeatherObservation(cityCoordinate.name)
        } else {
            CityWeatherObservation(cityCoordinate.name, setOf())
        }
    }
}