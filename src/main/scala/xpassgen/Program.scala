package xpassgen

import scala.io.Source
import java.security.SecureRandom

object Program extends App {
  val baseWords = {
    val s = getClass.getResourceAsStream("3esl.txt")
    try Source.fromInputStream(s, "utf-8").getLines.toVector
    finally s.close
  }

  def eligible(word: String): Boolean = (word.size >= 5) && (word.size <= 8) && (word forall (_.isLower))
  val words = baseWords filter eligible
  val wordCount = words.size
  val wordBits = Math.log(wordCount) / Math.log(2)
  println(f"Word list has $wordCount words, or about $wordBits%.1f bits per word")

  val rng = new SecureRandom
  println(s"Using SecureRandom: ${rng.getProvider} ${rng.getAlgorithm}")

  println()

  def nextWord = words(rng.nextInt(words.size))
  val randomWords = Iterator continually nextWord
  1 to 20 foreach { _ =>
    println(randomWords take 4 mkString " ")
  }
}
