package a.trade.microservice.template.test

import a.trade.microservice.runtime_api.AsyncTaskManager
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

}