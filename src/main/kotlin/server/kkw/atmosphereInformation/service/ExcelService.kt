package server.kkw.atmosphereInformation.service

import org.apache.commons.io.FileExistsException
import org.apache.poi.openxml4j.util.ZipSecureFile
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.ss.usermodel.WorkbookFactory
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service

/**
 * 엑셀 파일을 쓰고/읽기 하는 서비스
 */
@Service()
class ExcelService {
    /**
     * 특정 엑셀 파일의 워크북을 반환
     * @param fileName 파일 이름
     * @return Workbook
     */
    fun read(fileName: String): Workbook {
        val resource = ClassPathResource("data/$fileName")

        if (!resource.exists()) {
            throw FileExistsException()
        }

        ZipSecureFile.setMinInflateRatio(0.005)

        return WorkbookFactory.create(resource.inputStream)
    }

    /**
     * 셀이 비어있는 지 확인
     * @param cell 셀 데이터
     * @return 비었으면 true, 값이 존재하면 false
     */
    fun isCellBlank(cell: Cell): Boolean {
        return cell.toString().isBlank()
    }

    /**
     * 셀을 Double 자료형으로 변환 후 반한
     * @param cell 셀 데이터
     * @return Cell -> Double
     */
    fun getDoubleData(cell: Cell): Double {
        return cell.toString().toDouble()
    }
}