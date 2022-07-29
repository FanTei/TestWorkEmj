package TestWorkEmj.TestWorkEmj


import java.util.stream.IntStream
import kotlin.math.sqrt
import kotlin.random.Random
import kotlin.streams.toList

object SequencesGenerator {

    public fun generateFiveSequences(length: Int): List<IntArray> {
        val sequences: MutableList<IntArray> = mutableListOf()
        for (i in 0..4) {
            sequences.add(generateSubsequence(length))
        }
        return sequences.toList()
    }

    private fun isPrime(number: Int): Boolean {
        return IntStream.rangeClosed(2, sqrt(number.toDouble()).toInt()).toList().all { n -> number % n != 0 }
    }

    private fun generateSubsequence(length: Int): IntArray {
        val subsequence: IntArray = IntArray(length)
        val random: Random = Random
        for (i in subsequence.indices) {
            var number = random.nextInt(0, Int.MAX_VALUE)
            while (!isPrime(number))
                number = random.nextInt(0, Int.MAX_VALUE)
            subsequence[i] = number
        }
        return subsequence
    }
}