package adrian.kuhn

import a.trade.microservice.runtime_api.HelloInterface

class Hi: HelloInterface {
    override fun hello(): String {
        return "Hi"
    }
}