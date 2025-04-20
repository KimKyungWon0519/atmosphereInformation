package server.kkw.atmosphereInformation.mapper

import server.kkw.atmosphereInformation.model.*

/**
 * [KmaForecastResponse]의 [Items]를 [WeatherObservation]로 변환
 *
 * @param name 도시 이름
 *
 * @return [WeatherObservation]
 */
fun Items.toWeatherObservation(name: String): WeatherObservation =
    WeatherObservation(
        name,
        item.associate { it.category to it.obsrValue }
    )
