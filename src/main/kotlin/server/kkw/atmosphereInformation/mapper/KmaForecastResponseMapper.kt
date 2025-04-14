package server.kkw.atmosphereInformation.mapper

import server.kkw.atmosphereInformation.model.CityWeatherObservation
import server.kkw.atmosphereInformation.model.Item
import server.kkw.atmosphereInformation.model.Items
import server.kkw.atmosphereInformation.model.KmaForecastResponse
import server.kkw.atmosphereInformation.model.Observation

/**
 * [KmaForecastResponse]의 [Items]를 [CityWeatherObservation]로 변환
 *
 * @param name 도시 이름
 *
 * @return [CityWeatherObservation]
 */
fun Items.toCityWeatherObservation(name: String): CityWeatherObservation =
    CityWeatherObservation(
        name,
        item.map { it.toObservation() }.toSet()
    )

/**
 * [Items]의 [Item]을 [CityWeatherObservation]로 변환
 *
 * @return [CityWeatherObservation]
 */
fun Item.toObservation(): Observation =
    Observation(
        category,
        obsrValue.toDouble()
    )
