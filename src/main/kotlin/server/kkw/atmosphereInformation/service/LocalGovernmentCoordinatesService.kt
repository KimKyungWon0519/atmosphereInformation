package server.kkw.atmosphereInformation.service

import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.springframework.stereotype.Service
import server.kkw.atmosphereInformation.model.LocalGovernmentCoordinate
import server.kkw.atmosphereInformation.utils.isBlank
import server.kkw.atmosphereInformation.utils.toShort

/**
 * 지방자치단체 x, y 데이터를 관리
 */
@Service
class LocalGovernmentCoordinatesService(private val excelService: ExcelService) {
    /**
     * local_government_coordinates 엑셀 파일
     */
    private val workbook: Workbook by lazy {
        excelService.read("local_government_coordinates.xlsx")
    }

    /**
     *  local_government_coordinates 의 첫번쨰 시트 데이터
     */
    private val sheet: Sheet by lazy {
        workbook.getSheetAt(0)
    }

    /**
     * 모든 광역지방자치단체의 이름을 반환
     */
    fun getAllMetropolitan() {

    }

    /**
     * 꽝역지방자치단체 좌표 값을 가져옴
     *
     * @return
     * Set<[LocalGovernmentCoordinate]>
     *
     * 중복 값을 제거 모든 시의 [LocalGovernmentCoordinate] 데이터
     */
    fun getAllLocalGovernmentCoord(): Set<LocalGovernmentCoordinate> {
        val cities: MutableSet<LocalGovernmentCoordinate> = mutableSetOf()

        val filterData: List<Row> = sheet.filter { row ->
            row.getCell(3).isBlank()
        }

        filterData.forEach { row ->
            cities.add(
                LocalGovernmentCoordinate(
                    row.getCell(2).toString(), row.getCell(5).toShort(), row.getCell(6).toShort()
                )
            )
        }

        return cities
    }

    /**
     * 시도 이름을 이용하여 특정 시 좌표 값을 가져옴
     *
     * @return
     * [LocalGovernmentCoordinate]
     *
     * 특정 시의 [LocalGovernmentCoordinate] 데이터
     */
    //TODO: 차후 이름 및 문서 변경
    fun getCityCoord(name: String): LocalGovernmentCoordinate {
        val rowData: Row = sheet.single { row ->
            row.getCell(3).isBlank() && row.getCell(2).toString().compareTo(name) == 0
        }

        return LocalGovernmentCoordinate(
            rowData.getCell(2).toString(), rowData.getCell(5).toShort(), rowData.getCell(6).toShort(),
        )
    }
}