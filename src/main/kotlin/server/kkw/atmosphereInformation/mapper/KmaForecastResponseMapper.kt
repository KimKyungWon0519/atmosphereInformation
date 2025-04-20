package server.kkw.atmosphereInformation.mapper

import server.kkw.atmosphereInformation.model.WeatherObservation
import server.kkw.atmosphereInformation.model.Item
import server.kkw.atmosphereInformation.model.Items
import server.kkw.atmosphereInformation.model.KmaForecastResponse
import server.kkw.atmosphereInformation.model.Observation

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
        item.map { it.toObservation() }.toSet()
    )

/**
 * [Items]의 [Item]을 [WeatherObservation]로 변환
 *
 * @return [WeatherObservation]
 */
fun Item.toObservation(): Observation =
    Observation(
        category,
        obsrValue.toDouble()
    )
