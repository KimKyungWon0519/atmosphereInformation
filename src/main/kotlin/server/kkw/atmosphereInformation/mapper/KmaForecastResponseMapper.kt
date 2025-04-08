package server.kkw.atmosphereInformation.mapper

import server.kkw.atmosphereInformation.model.CityWeatherObservation
import server.kkw.atmosphereInformation.model.Item
import server.kkw.atmosphereInformation.model.Items
import server.kkw.atmosphereInformation.model.Observation

fun Items.toCityWeatherObservation(name: String): CityWeatherObservation =
    CityWeatherObservation(
        name,
        item.map { it.toObservation() }.toSet()
    )

fun Item.toObservation(): Observation =
    Observation(
        category,
        obsrValue.toDouble()
    )
