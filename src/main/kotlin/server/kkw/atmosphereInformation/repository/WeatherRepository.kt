package server.kkw.atmosphereInformation.repository

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Repository
import server.kkw.atmosphereInformation.mapper.toWeatherObservation
import server.kkw.atmosphereInformation.model.LocalGovernmentCoordinate
import server.kkw.atmosphereInformation.model.WeatherObservation
import server.kkw.atmosphereInformation.service.KmaForecastService
import server.kkw.atmosphereInformation.service.LocalGovernmentCoordinatesService

/**
 * 지방자치단체의 좌표값을 이용하여 날씨 API 호출
 */
@Repository
class WeatherRepository(
    private val kmaForecastService: KmaForecastService,
    private val localGovernmentCoordinatesService: LocalGovernmentCoordinatesService
) {
    /**
     * 광역지방자치단체의 실시간 날씨 데이터를 가져옴
     *
     * @param baseDate 발표일자
     * @param baseTime 발표시각
     *
     * @return
     * Set<[WeatherObservation]>
     *
     * 각 광역지방자치단체 실시간 날씨 측정값, 특정 광역지방자치단체의 날씨 데이터가 없는 경우 observation를 {}처리
     */
    suspend fun getAllMetropolitanNowWeather(baseDate: String, baseTime: String): Set<WeatherObservation> =
        runBlocking {
            val localGovernmentCoordinates = localGovernmentCoordinatesService.getAllLocalGovernmentCoord()
            val weatherObservations = localGovernmentCoordinates.map {
                async {
                    getNowWeather(
                        localGovernmentCoordinate = it, baseDate = baseDate, baseTime = baseTime
                    )
                }
            }.awaitAll().toSet()

            return@runBlocking weatherObservations
        }

    /**
     * 특정 시의 날씨 데이터를 가져옴
     *
     * @param name 시 이름
     * @param baseDate 발표일자
     * @param baseTime 발표시각
     *
     * @return
     * [WeatherObservation]
     *
     * 특정 도시의 날씨 측정값, 특정 도시의 데이터가 없는 경우 observation를 {}처리
     */
    // TODO: 함수 이름 변경 및 문서 내용 변경
    suspend fun getCityWeather(
        name: String, baseDate: String, baseTime: String
    ): WeatherObservation {
        val cityCoordinate = localGovernmentCoordinatesService.getCityCoord(name)

        return getNowWeather(
            localGovernmentCoordinate = cityCoordinate, baseDate = baseDate, baseTime = baseTime
        )
    }

    /**
     * 지방자치단체의 실시간 날씨 데이터 API 결과를 처리하는 함수
     *
     * @param localGovernmentCoordinate 시도 좌표 모델
     * @param baseDate 발표일자
     * @param baseTime 발표시각
     *
     * @return
     * [WeatherObservation]
     *
     * 전달받은 좌표의 날씨 측정값, 데이터가 없는 경우 observation를 {}처리
     */
    private suspend fun getNowWeather(
        localGovernmentCoordinate: LocalGovernmentCoordinate, baseDate: String, baseTime: String
    ): WeatherObservation {
        val result = kmaForecastService.getUltraSrtNcst(
            baseDate = baseDate,
            baseTime = baseTime,
            pageNo = 1,
            nx = localGovernmentCoordinate.x,
            ny = localGovernmentCoordinate.y
        )

        return if (result.isSuccess && result.getOrNull() != null) {
            val kmaForecastResponse = result.getOrNull()!!

            kmaForecastResponse.body.items.toWeatherObservation(localGovernmentCoordinate.name)
        } else {
            WeatherObservation(localGovernmentCoordinate.name, mapOf())
        }
    }
}