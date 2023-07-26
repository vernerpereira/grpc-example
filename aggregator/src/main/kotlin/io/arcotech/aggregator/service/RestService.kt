package io.arcotech.aggregator.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class RestService(
    @Value("\${rest.square.service.endpoint}") private val baseUrl: String,
    private val restTemplate: RestTemplate = RestTemplate()
) {
    fun getUnaryResponse(number: Int): Any? {
        val map: MutableMap<Int, Int?> = HashMap()
        for (i in 1..number) {
            val responseEntity = restTemplate.getForEntity(
                baseUrl + String.format("/rest/square/unary/%d", i),
                Int::class.java
            )
            map[i] = responseEntity.body
        }
        return map
    }
}