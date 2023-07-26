package io.arcotech.aggregator.controller

import io.arcotech.aggregator.service.RestService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class RestController(
    private val service: RestService
){
    @GetMapping(value = ["/rest/unary/{number}"])
    fun getResponseUnary(
        @PathVariable number: Int
    ): Any? {
        return service.getUnaryResponse(number)
    }
}