package TestWorkEmj.TestWorkEmj.handlrer

import TestWorkEmj.TestWorkEmj.SequencesGenerator
import TestWorkEmj.TestWorkEmj.entity.Message
import TestWorkEmj.TestWorkEmj.entity.User
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler

import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicLong


class SequencesHandler : TextWebSocketHandler() {

    private val sessionList = HashMap<WebSocketSession, User>()
    private var atomic = AtomicLong(0)
    private var executorService: ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor()

    @Throws(Exception::class)
    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        sessionList -= session
    }

    public override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        val json: JsonNode = ObjectMapper().readTree(message?.payload)
        val type = json.get("type").asText()
        when(type){
            "connection"->{
                val user = User(atomic.getAndIncrement())
                sessionList.put(session!!, user)
                emit(session, Message("users", sessionList.values))
            }
            "auto"->{
                val data = json.get("data")
                val task1 = Runnable {
                    val sequences: List<IntArray> = SequencesGenerator.generateFiveSequences(data.asInt())
                    broadcast(Message("auto", sequences))
                }
                val scheduledFuture: ScheduledFuture<*> =
                    executorService.scheduleAtFixedRate(task1, 0, 10, TimeUnit.SECONDS)
            }
            "generation"->{
                val data = json.get("data")
                broadcast(Message("generation",deleteDuplicateInArrays(SequencesGenerator.generateFiveSequences(data.asInt()))))
            }
        }
    }

    private fun emit(session: WebSocketSession, msg: Message) =
        session.sendMessage(TextMessage(jacksonObjectMapper().writeValueAsBytes(msg)))

    private fun broadcast(msg: Message) = sessionList.forEach { emit(it.key, msg) }

    private fun deleteDuplicateInArrays(sequences: List<IntArray>): List<IntArray> {
        return sequences.map { arr -> arr.distinct().take(6).toIntArray() }.toList()
    }

}