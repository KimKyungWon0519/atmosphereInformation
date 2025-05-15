package server.kkw.atmosphereInformation.service

import org.springframework.stereotype.Service
import server.kkw.atmosphereInformation.exception.APIRequestException
import server.kkw.atmosphereInformation.model.KmaForecastResponse

/**
 * [KmaForecastApi]를 호출하는 서비스
 */
@Service
class KmaForecastService(private val kmaForecastApi: KmaForecastApi) {
    /**
     * 초단기실황조회
     *
     * X, Y 좌표는 별첨 엑셀 좌표 확인
     * @param pageNo 한 페이지 결과
     * @param baseDate 발표일자
     * @param baseTime 발표시각
     * @param nx 예보지점 X 좌표
     * @param ny 예보지점 Y 좌표
     *
     * @return
     * Result<[KmaForecastResponse]>
     *
     * 특정 좌표의 초단기실황값. API 에서 오류 발생 시 Exception과 함께 반환
     */
    suspend fun getUltraSrtNcst(
        pageNo: Int,
        baseDate: String,
        baseTime: String,
        nx: Short,
        ny: Short,
    ): KmaForecastResponse {
        val kmaForecastResponse = kmaForecastApi.getUltraSrtNcst(
            pageNo, baseDate, baseTime, nx, ny
        )

        return when(kmaForecastResponse.header.resultCode) {
            "00" -> kmaForecastResponse
            "03" -> throw APIRequestException(200, kmaForecastResponse.header.resultMsg)
            else -> throw  APIRequestException(500, kmaForecastResponse.header.resultMsg)
        }
    }
}