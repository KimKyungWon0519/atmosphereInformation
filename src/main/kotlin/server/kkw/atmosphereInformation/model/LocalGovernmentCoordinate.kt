package server.kkw.atmosphereInformation.model

/**
 * 지방자치단체 좌표 데이터 클래스
 * @property name 시도 이름
 * @property x x 좌표
 * @property y y 좌표
 */
data class LocalGovernmentCoordinate(val name: String, val x: Short, val y: Short)