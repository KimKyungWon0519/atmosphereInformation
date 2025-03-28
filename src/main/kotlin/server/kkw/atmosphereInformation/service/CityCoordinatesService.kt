package server.kkw.atmosphereInformation.service

import jakarta.annotation.PostConstruct
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.springframework.stereotype.Service

/**
 * 시도별 위도/경도 데이터를 관리
 */
@Service
class CityCoordinatesService(private val excelService: ExcelService) {
    /**
     * city_coordinates 엑셀 파일
     */
    private val workbook: Workbook by lazy {
        excelService.read("city_coordinates.xlsx")
    }

    /**
     *  city_coordinates의 첫번쨰 시트 데이
     */
    private val sheet: Sheet by lazy {
        workbook.getSheetAt(0)
    }
}