package TestWorkEmj.TestWorkEmj


import java.util.Collections
import java.util.stream.IntStream
import kotlin.math.sqrt
import kotlin.streams.toList

object NumbersGenerator {
    public fun generate(length: Int): List<Int> {
        return listOf(1, 23, 4, 546, 456, 46, 456, 7).filter { isPrime(it) }
    }

    private fun isPrime(number: Int): Boolean {
        return IntStream.rangeClosed(2, sqrt(number.toDouble()).toInt()).toList().all { n -> number % n != 0 }
    }
}