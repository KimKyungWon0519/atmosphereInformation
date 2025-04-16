package server.kkw.atmosphereInformation.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import server.kkw.atmosphereInformation.repository.WeatherRepository
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RestController
@RequestMapping("/v1/weather/")
class WeatherController(private val weatherRepository: WeatherRepository) {
    /**
     * 모든 시의 날씨 데이터를 반환
     *
     * @return
     * ResponseEntity<Map<String, Any>>
     */
    @GetMapping("city/all")
    suspend fun allCityWeatherNow(): ResponseEntity<Map<String, Any>> {
        val dateTime = LocalDateTime.now()

        val cityWeatherObservations = weatherRepository.getAllCityWeather(
            baseDate = DateTimeFormatter.ofPattern("yyyyMMdd").format(dateTime).toLong(),
            baseTime = DateTimeFormatter.ofPattern("HHmm").format(dateTime).toInt()
        )

        return ResponseEntity.ok(
            mapOf(
                "time" to dateTime,
                "status" to 200,
                "data" to cityWeatherObservations
            )
        )
    }

    /**
     * 특정 시의 날씨 데이터를 반환
     *
     * @param name 특정 시 이름
     *
     * @return
     * ResponseEntity<Map<String, Any>>
     */
    @GetMapping("city/{name}")
    suspend fun cityWeatherNow(
        @PathVariable name: String
    ): ResponseEntity<Map<String, Any>> {
        val dateTime = LocalDateTime.now()

        val cityWeatherObservations = weatherRepository.getCityWeather(
            name = name,
            baseDate = DateTimeFormatter.ofPattern("yyyyMMdd").format(dateTime).toLong(),
            baseTime = DateTimeFormatter.ofPattern("HHmm").format(dateTime).toInt()
        )

        return ResponseEntity.ok(
            mapOf(
                "time" to dateTime,
                "status" to 200,
                "data" to cityWeatherObservations
            )
        )
    }
}