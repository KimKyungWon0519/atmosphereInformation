package server.kkw.atmosphereInformation.repository

import org.springframework.stereotype.Repository
import server.kkw.atmosphereInformation.service.LocalGovernmentCoordinatesService

/**
 * 행정구역(광역지방자치단체/기초지방자치단체)의 목록을 가져옴
 */
@Repository
class RegionsRepository(private val localGovernmentCoordinatesService: LocalGovernmentCoordinatesService) {
    /**
     * 모든 광역지방자치단체의 이름을 반환함
     *
     * @return
     * Set<String>
     *
     * 모든 광역지방자치단체의 이름
     */
    fun getAllMetropolitanNames() : Set<String> {
        return localGovernmentCoordinatesService.getAllMetropolitanNames()
    }
}