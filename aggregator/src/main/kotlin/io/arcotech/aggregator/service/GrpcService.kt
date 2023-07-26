package io.arcotech.aggregator.service

import io.arcotech.Input
import io.arcotech.SquareRpcGrpcKt
import io.grpc.ManagedChannelBuilder
import kotlinx.coroutines.flow.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class GrpcService(
    @Value("\${grpc.square.service.host}") private val host: String,
    @Value("\${grpc.square.service.port}") private val port: Int
) {
    suspend fun findSquareUnary(number: Int): Any? {
        val channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build()
        val stub = SquareRpcGrpcKt.SquareRpcCoroutineStub(channel)

        val inputBuilder = Input.newBuilder()

        val map: MutableMap<Int, Int?> = HashMap()
        for (i in 1..number) {
            inputBuilder.number = i
            val responseEntity = stub.findSquareUnary(inputBuilder.build())
            map[i] = responseEntity.result
        }
        channel.shutdownNow()
        return map
    }
}
