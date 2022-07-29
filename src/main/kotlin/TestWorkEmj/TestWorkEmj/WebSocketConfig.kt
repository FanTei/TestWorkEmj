package TestWorkEmj.TestWorkEmj

import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.config.annotation.*

@Configuration @EnableWebSocket
class WSConfig : WebSocketConfigurer {
    override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
        registry.addHandler(SequencesHandler(), "/chat").setAllowedOrigins("http://localhost:3000").withSockJS()
    }
}