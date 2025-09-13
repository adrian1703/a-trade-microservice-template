package a.trade.microservice.template.test

import a.trade.microservice.runtime_api.RestApiPlugin
import a.trade.microservice.runtime_api.RuntimeApi
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router

class TestRestApiPlugin : RestApiPlugin {

    override fun getRouter(runtimeApi: RuntimeApi?): RouterFunction<ServerResponse> {
        return router {
            GET(
                "/hello4"
            ) { ServerResponse.ok().bodyValue("hello") }
        }
    }
}