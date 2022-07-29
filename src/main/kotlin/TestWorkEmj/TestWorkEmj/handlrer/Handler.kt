package TestWorkEmj.TestWorkEmj.handlrer

import TestWorkEmj.TestWorkEmj.entity.Message
import com.fasterxml.jackson.databind.JsonNode
import org.springframework.messaging.handler.MessagingAdviceBean
import org.springframework.web.socket.WebSocketSession

interface Handler {
    fun handle(data: JsonNode):Message
}