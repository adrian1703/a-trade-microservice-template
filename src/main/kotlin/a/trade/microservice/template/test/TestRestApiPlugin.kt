package a.trade.microservice.template.test

import a.trade.microservice.runtime_api.AsyncTaskManager
import a.trade.microservice.runtime_api.ExecutorContext
import a.trade.microservice.runtime_api.RestApiPlugin
import a.trade.microservice.runtime_api.RuntimeApi
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router
import reactor.core.publisher.Mono

class TestRestApiPlugin : RestApiPlugin {

    private var runtimeApi: RuntimeApi? = null

    override fun getRouter(): RouterFunction<ServerResponse> {
        val plugin = this
        return router {
            GET(
                "/hello4"
            ) { ServerResponse.ok().bodyValue("hello") }
            // --- OpenAPI: /v1/test ---
            POST("/v1/test", accept(org.springframework.http.MediaType.APPLICATION_JSON), plugin::handleV1Test)
            POST("/v1/test2", accept(org.springframework.http.MediaType.APPLICATION_JSON), plugin::handleV1Test2)
        }
    }

    override fun init(runtimeApi: RuntimeApi?) {
        this.runtimeApi = runtimeApi
        AsyncTaskManager(runtimeApi!!)
    }

    private fun handleV1Test(request: ServerRequest): Mono<ServerResponse> {
        val success = { body: TestComponent -> ServerResponse.ok().bodyValue(body) }
        val failure = ServerResponse.badRequest().bodyValue(mapOf("error" to "Invalid input"))
        val body = request.bodyToMono(TestComponent::class.java)
        return body.flatMap(success).switchIfEmpty(failure)
    }

    private fun handleV1Test2(request: ServerRequest): Mono<ServerResponse> {
        val submit1 = runtimeApi!!.getExecutorService(ExecutorContext.DEFAULT).submit { println(1) }
        val submit2 = runtimeApi!!.getExecutorService(ExecutorContext.DEFAULT).submit {
            println(2)
            runtimeApi!!.getExecutorService(ExecutorContext.IO).submit { println(3) }
        }
        submit1.get()
        submit2.get()
        return ServerResponse.ok().bodyValue("hello")
    }

}