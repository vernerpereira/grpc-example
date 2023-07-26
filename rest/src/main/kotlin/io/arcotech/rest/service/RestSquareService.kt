package io.arcotech.rest.service

import org.springframework.stereotype.Service

@Service
class RestSquareService {
    fun getSquareUnary(number: Int): Int {
        return number * number
    }
}