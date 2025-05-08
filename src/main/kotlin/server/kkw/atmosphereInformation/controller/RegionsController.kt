package server.kkw.atmosphereInformation.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
행정구역명을 가져오는 API 컨트롤러
 */
@RestController
@RequestMapping("/v1/regions")
class RegionsController {
    /**
     * 모든 광역지방자치단체 이름을 반환함
     */
    @GetMapping("/metropolitan")
    fun getMetropolitan() : String {
        return "Hello World"
    }
}