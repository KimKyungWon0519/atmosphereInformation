package server.kkw.atmosphereInformation.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import server.kkw.atmosphereInformation.repository.RegionsRepository
import java.time.LocalDateTime

/**
행정구역명을 가져오는 API 컨트롤러
 */
@RestController
@RequestMapping("/v1/regions")
class RegionsController(private val regionsRepository: RegionsRepository) {
    /**
     * 모든 광역지방자치단체 이름을 반환함
     */
    @GetMapping("/metropolitan")
    fun getAllMetropolitanNames() : ResponseEntity<Map<String, Any>> {
        val dateTime = LocalDateTime.now()
        val metropolitanNames = regionsRepository.getAllMetropolitanNames()

        return ResponseEntity.ok(
            mapOf(
                "time" to dateTime,
                "status" to 200,
                "data" to metropolitanNames
            )
        )
    }
}