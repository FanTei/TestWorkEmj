package TestWorkEmj.TestWorkEmj

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler
import java.util.concurrent.atomic.AtomicLong

class SequencesHandler : TextWebSocketHandler() {

    val sessionList = HashMap<WebSocketSession, User>()
    var uids = AtomicLong(0)

    @Throws(Exception::class)
    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        sessionList -= session
    }

    public override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        val json = ObjectMapper().readTree(message?.payload)
        when (json.get("type").asText()) {
            "connection" -> {
                val user = User(uids.getAndIncrement())
                sessionList.put(session!!, user)
                emit(session, Message("users", sessionList.values))
                broadcastToOthers(session, Message("connection", user))
            }
            "say" -> {
                broadcast(Message("say", json.get("data").asText()))
            }
            "auto"->{
                broadcast(Message("auto",SequencesGenerator.generateFiveSequences(json.get("data").asText().toInt())))
            }
        }
    }


    fun emit(session: WebSocketSession, msg: Message) = session.sendMessage(TextMessage(jacksonObjectMapper().writeValueAsString(msg)))
    fun broadcast(msg: Message) = sessionList.forEach { emit(it.key, msg) }
    fun broadcastToOthers(me: WebSocketSession, msg: Message) = sessionList.filterNot { it.key == me }.forEach { emit(it.key, msg) }
}