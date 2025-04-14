package server.kkw.atmosphereInformation.service

import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.springframework.stereotype.Service
import server.kkw.atmosphereInformation.model.CityCoordinate
import server.kkw.atmosphereInformation.utils.isBlank
import server.kkw.atmosphereInformation.utils.toShort

/**
 * 시도별 x, y 데이터를 관리
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
     *  city_coordinates 의 첫번쨰 시트 데이터
     */
    private val sheet: Sheet by lazy {
        workbook.getSheetAt(0)
    }

    /**
     * 모든 시 좌표 값을 가져옴
     *
     * @return 중복 값을 제거 모든 시의 [CityCoordinate] 데이터
     */
    fun getAllCitiesCoord(): Set<CityCoordinate> {
        val cities: MutableSet<CityCoordinate> = mutableSetOf()

        val filterData: List<Row> = sheet.filter { row ->
            row.getCell(3).isBlank()
        }

        filterData.forEach { row ->
            cities.add(
                CityCoordinate(
                    row.getCell(2).toString(), row.getCell(5).toShort(), row.getCell(6).toShort()
                )
            )
        }

        return cities
    }

    /**
     * 특정 시 좌표 값을 가져옴
     *
     * @return 특정 시의 [CityCoordinate] 데이터
     */
    fun getCityCoord(name: String): CityCoordinate {
        val rowData: Row = sheet.single { row ->
            row.getCell(3).isBlank() && row.getCell(2).toString().compareTo(name) == 0
        }

        return CityCoordinate(
            rowData.getCell(2).toString(), rowData.getCell(5).toShort(), rowData.getCell(6).toShort(),
        )
    }
}