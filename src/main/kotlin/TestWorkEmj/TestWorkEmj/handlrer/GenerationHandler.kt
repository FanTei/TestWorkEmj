package TestWorkEmj.TestWorkEmj.handlrer

import TestWorkEmj.TestWorkEmj.SequencesGenerator
import TestWorkEmj.TestWorkEmj.entity.Message
import com.fasterxml.jackson.databind.JsonNode

class GenerationHandler : Handler {
    override fun handle(data: JsonNode): Message {
        val sequences: List<IntArray> =
            deleteDuplicateInArrays(SequencesGenerator.generateFiveSequences(data.asInt()))
        return Message("generation", sequences)
    }

    private fun deleteDuplicateInArrays(sequences: List<IntArray>): List<IntArray> {
        return sequences.map { arr -> arr.distinct().take(6).toIntArray() }.toList()
    }
}