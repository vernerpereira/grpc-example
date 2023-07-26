package io.arcotech.aggregator.controller

import io.arcotech.aggregator.service.GrpcService
import kotlinx.coroutines.runBlocking
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class GrpcController(
    private val service: GrpcService
){
    @GetMapping(value = ["/grpc/unary/{number}"])
    fun findSquareSimple(
        @PathVariable number: Int
    ): Any? {
        return runBlocking {
            service.findSquareUnary(number)
        }
    }
}