package io.arcotech.grpc.service

import io.arcotech.Input
import io.arcotech.Output
import io.arcotech.SquareRpcGrpcKt
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.springframework.stereotype.Service

@Service
class GrpcService : SquareRpcGrpcKt.SquareRpcCoroutineImplBase()
{
    override suspend fun findSquareUnary(request: Input): Output {
        return Output.newBuilder().apply {
            number = request.number
            result = request.number * request.number
        }.build()
    }

    override fun findSquareServerStream(request: Input): Flow<Output> {
        return flow {
            val outputBuilder = Output.newBuilder()

            for (i in 1..request.number) {
                Thread.sleep(2_000)
                outputBuilder.number = i
                outputBuilder.result = i * i

                emit(
                    outputBuilder.build()
                )
            }
        }
    }

    override suspend fun findSquareClientStream(requests: Flow<Input>): Output {
        val outputBuilder = Output.newBuilder()
        val numberList = mutableListOf<Int>()
        val resultList = mutableListOf<Int>()

        requests.collect{request ->
            numberList.add(request.number)
            resultList.add(request.number * request.number)
        }

        outputBuilder.number = numberList.max()
        outputBuilder.result = resultList.max()
        return outputBuilder.build()
    }

    override fun findSquareFullDuplexStream(requests: Flow<Input>): Flow<Output> {
        return flow {
            val outputBuilder = Output.newBuilder()

            requests.collect{request ->
                for (i in 1..request.number) {
                    Thread.sleep(2_000)
                    outputBuilder.number = i
                    outputBuilder.result = i * i

                    emit(
                        outputBuilder.build()
                    )
                }
            }
        }
    }
}