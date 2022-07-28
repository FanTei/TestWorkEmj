package TestWorkEmj.TestWorkEmj

import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler

class Handler : TextWebSocketHandler() {

    public override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        println(message)
    }
}