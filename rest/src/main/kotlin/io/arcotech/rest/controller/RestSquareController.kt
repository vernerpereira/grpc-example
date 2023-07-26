package io.arcotech.rest.controller

import io.arcotech.rest.service.RestSquareService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class RestSquareController(
    private val squareService: RestSquareService
) {
    @GetMapping("/rest/square/unary/{number}")
    fun getSquareUnary(@PathVariable number: Int): Int {
        return squareService.getSquareUnary(number)
    }
}