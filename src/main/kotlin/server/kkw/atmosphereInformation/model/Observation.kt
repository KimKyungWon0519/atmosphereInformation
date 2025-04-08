package server.kkw.atmosphereInformation.model

/**
 * 날씨 실황값
 *
 * @property category 데이터 타입
 * @property observation 실황 값
 */
data class Observation(val category: String, val data: Double)