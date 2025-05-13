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
     * 광역지방자치단체 실시간 날씨 데이터를 반환
     *
     * @return
     * ResponseEntity<Map<String, Any>>
     */
    @GetMapping("metropolitan/all/now")
    suspend fun allMetropolitanNowWeather(): ResponseEntity<Map<String, Any>> {
        val dateTime = LocalDateTime.now()

        val cityWeatherObservations = weatherRepository.getAllMetropolitanNowWeather(
            baseDate = DateTimeFormatter.ofPattern("yyyyMMdd").format(dateTime),
            baseTime = DateTimeFormatter.ofPattern("HHmm").format(dateTime)
        )

        return ResponseEntity.ok(
            mapOf(
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
    // TODO: 함수 이름 변경 및 문서 내용 변경
    @GetMapping("city/{name}")
    suspend fun cityWeatherNow(
        @PathVariable name: String
    ): ResponseEntity<Map<String, Any>> {
        val dateTime = LocalDateTime.now()

        val cityWeatherObservations = weatherRepository.getCityWeather(
            name = name,
            baseDate = DateTimeFormatter.ofPattern("yyyyMMdd").format(dateTime),
            baseTime = DateTimeFormatter.ofPattern("HHmm").format(dateTime)
        )

        return ResponseEntity.ok(
            mapOf(
                "status" to 200,
                "data" to cityWeatherObservations
            )
        )
    }
}