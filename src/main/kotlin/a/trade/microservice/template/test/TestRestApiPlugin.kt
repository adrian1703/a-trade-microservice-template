package a.trade.microservice.template.test

import a.trade.microservice.runtime_api.RestApiPlugin
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType

class TestRestApiPlugin : RestApiPlugin {
    override fun getRestResources(): List<Any?> {
        return listOf(TestRestResource())
    }
}

@Path("/test")
class TestRestResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    fun test() = "Test"
}
