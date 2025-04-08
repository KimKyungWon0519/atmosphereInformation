package server.kkw.atmosphereInformation.service

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import server.kkw.atmosphereInformation.model.KmaForecastResponse

/**
 * 기상청 단기예보 API
 *
 * [기상청_단기예보 ((구)_동네예보) 조회서비스](https://www.data.go.kr/tcs/dss/selectApiDataDetailView.do?publicDataPk=15084084)
 */
interface KmaForecastApi {
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
     * @return [KmaForecastResponse]
     */
    @GET("getUltraSrtNcst")
    suspend fun getUltraSrtNcst(
        @Query("pageNo") pageNo: Int,
        @Query("base_date") baseDate: Long,
        @Query("base_time") baseTime: Int,
        @Query("nx") nx: Short,
        @Query("ny") ny: Short,
    ): KmaForecastResponse
}