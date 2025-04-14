package server.kkw.atmosphereInformation.model

/**
 * 기상청 단기예보 API 응답값 루트 모델
 * 자세한 변수 값은 [기상청_단기예보 ((구)_동네예보) 조회서비스](https://www.data.go.kr/tcs/dss/selectApiDataDetailView.do?publicDataPk=15084084)의 기능명세서 참고
 * @property [Response]
 */
data class KmaForecastResponse(
    val response: Response,
)

/**
 * @property [Header]
 * @property [Body]
 */
data class Response(
    val header: Header,
    val body: Body,
)

/**
 * @property resultCode 결과코드
 * @property resultMsg 결과메시지
 */
data class Header(
    val resultCode: String,
    val resultMsg: String,
)

/**
 * @property dataType 데이터 타입
 * @property items [Item]
 * @property pageNo 페이지 번호
 * @property numOfRows 한 페이지 결과 수
 * @property totalCount 전체 결과 수
 */
data class Body(
    val dataType: String,
    val items: Items,
    val pageNo: Long,
    val numOfRows: Long,
    val totalCount: Long,
)

/**
 * @property [Item]
 */
data class Items(
    val item: List<Item>,
)

/**
 * @property baseDate 발표일자
 * @property baseTime 발표시각
 * @property category 자료구분코드
 * @property nx 예보지점 X 좌표
 * @property ny 예보지점 Y 좌표
 * @property obsrValue 실황값
 */
data class Item(
    val baseDate: String,
    val baseTime: String,
    val category: String,
    val nx: Long,
    val ny: Long,
    val obsrValue: String,
)
