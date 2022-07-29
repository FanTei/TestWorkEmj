package TestWorkEmj.TestWorkEmj.handlrer

import TestWorkEmj.TestWorkEmj.entity.Message
import com.fasterxml.jackson.databind.JsonNode

interface Handler {
    fun handle(data: JsonNode):Message
}