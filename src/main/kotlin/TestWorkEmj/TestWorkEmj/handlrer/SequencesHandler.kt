package TestWorkEmj.TestWorkEmj.handlrer

import TestWorkEmj.TestWorkEmj.entity.Message
import TestWorkEmj.TestWorkEmj.entity.User
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler
import java.util.concurrent.atomic.AtomicLong

class SequencesHandler : TextWebSocketHandler() {

    private val sessionList = HashMap<WebSocketSession, User>()
    private var atomic = AtomicLong(0)
    val handlerContainer = HandlerContainer()

    @Throws(Exception::class)
    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        sessionList -= session
    }

    public override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        val json: JsonNode = ObjectMapper().readTree(message?.payload)
        val type = json.get("type").asText()
        if(type=="connection")
             {
                val user = User(atomic.getAndIncrement())
                sessionList.put(session!!, user)
                emit(session, Message("users", sessionList.values))
            }
        else
        {
            val data = json.get("data")
            handlerContainer.retrieveHandler(type)?.let { broadcast(it.handle(data)) }
        }
    }

    fun emit(session: WebSocketSession, msg: Message) =
        session.sendMessage(TextMessage(jacksonObjectMapper().writeValueAsBytes(msg)))

    fun broadcast(msg: Message) = sessionList.forEach { emit(it.key, msg) }

}