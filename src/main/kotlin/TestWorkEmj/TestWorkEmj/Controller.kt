package TestWorkEmj.TestWorkEmj

import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Controller

@Controller
class Controller {

    @MessageMapping("/test")
    @SendTo("/topic/messages")
    fun send(message: String?): String? {
        println(message)
        return message
    }
}