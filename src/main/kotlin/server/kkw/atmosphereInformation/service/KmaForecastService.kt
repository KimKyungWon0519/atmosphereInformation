package server.kkw.atmosphereInformation.service

import org.springframework.stereotype.Service
import server.kkw.atmosphereInformation.model.KmaForecastResponse

@Service
class KmaForecastService(private val kmaForecastApi: KmaForecastApi) {
    /**
     * 초단기실황조회
     *
     * X, Y 좌표는 별첨 엑셀 좌표 확인
     * @param pageNo 한 페이지 결과
     * @param baseData 발표일자
     * @param baseTime 발표시각
     * @param nx 예보지점 X 좌표
     * @param ny 예보지점 Y 좌표
     */
    fun getUltraSrtNcst(
        pageNo: Int,
        baseData: Long,
        baseTime: Int,
        nx: Short,
        ny: Short,
    ): KmaForecastResponse? {
        val response = kmaForecastApi.getUltraSrtNcst(
            pageNo, baseData, baseTime, nx, ny
        ).execute()

        return if (response.isSuccessful) response.body()!! else null
    }
}