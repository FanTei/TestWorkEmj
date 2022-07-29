package TestWorkEmj.TestWorkEmj.handlrer

import TestWorkEmj.TestWorkEmj.SequencesGenerator
import TestWorkEmj.TestWorkEmj.entity.Message
import com.fasterxml.jackson.databind.JsonNode

class AutoHandler : Handler {

    override fun handle(data: JsonNode): Message {
        val sequences: List<IntArray> = SequencesGenerator.generateFiveSequences(data.asInt())
        return Message("auto", sequences)
    }
}