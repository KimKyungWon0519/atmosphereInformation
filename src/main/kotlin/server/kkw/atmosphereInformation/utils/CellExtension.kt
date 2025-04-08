package server.kkw.atmosphereInformation.utils

import org.apache.poi.ss.usermodel.Cell

/**
 * 셀이 비어있는 지 확인
 * @return 비었으면 true, 값이 존재하면 false
 */
fun Cell.isBlank(): Boolean {
    return this.toString().isBlank()
}

/**
 * 셀 값을 Double 형으로 변경
 * @return Cell -> Short
 */
fun Cell.toShort(): Short {
    return this.toString().substringBefore('.').toShort()
}