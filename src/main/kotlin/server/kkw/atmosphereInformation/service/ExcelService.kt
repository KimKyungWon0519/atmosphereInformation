package server.kkw.atmosphereInformation.service

import org.apache.commons.io.FileExistsException
import org.apache.poi.openxml4j.util.ZipSecureFile
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.ss.usermodel.WorkbookFactory
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service

@Service()
class ExcelService {
    fun read(fileName: String): Workbook {
        val resource = ClassPathResource("data/$fileName")

        if(!resource.exists()) {
            throw FileExistsException();
        }

        ZipSecureFile.setMinInflateRatio(0.005)

        return WorkbookFactory.create(resource.inputStream)
    }
}