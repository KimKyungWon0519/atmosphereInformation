package server.kkw.atmosphereInformation.exception

class APIRequestException(val statusCode: Int, override val message: String) : RuntimeException(message)