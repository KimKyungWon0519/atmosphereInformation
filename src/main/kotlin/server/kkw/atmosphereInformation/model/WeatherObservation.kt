package server.kkw.atmosphereInformation.model

import kotlinx.serialization.Serializable

/**
 * 날씨 데이터
 *
 * @property name 도시 이름
 * @property observation 날씨 실황 값 모음
 */
@Serializable
data class WeatherObservation(val name: String, val observation: Set<Observation>)

