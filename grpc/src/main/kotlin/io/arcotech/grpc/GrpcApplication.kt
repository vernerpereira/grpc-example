package io.arcotech.grpc

import io.grpc.BindableService
import io.grpc.ServerBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class GrpcApplication(
	val services: List<BindableService>,
	@Value("\${grpc.port}") val grpcPort: Int
): ApplicationRunner {
	override fun run(args: ApplicationArguments?) {
		val serverBuilder = ServerBuilder.forPort(grpcPort)
		services.forEach{
			serverBuilder.addService(it)
		}
		serverBuilder
			.build()
			.start()
	}
}

fun main(args: Array<String>) {
	runApplication<GrpcApplication>(*args)
}
