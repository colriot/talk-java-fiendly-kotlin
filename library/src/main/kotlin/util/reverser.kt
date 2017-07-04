@file:JvmName("ReverserUtils")
//@file:JvmMultifileClass

package util

fun String.reverse() = StringBuilder(this).reverse().toString()

inline fun <T> Iterable<T>.forEachReversed(action: (T) -> Unit): Unit {
  for (element in this.reversed()) action(element)
}

inline fun <reified T> reversedClassName() = T::class.java.simpleName.reverse()

fun List<Int>.printReversedSum() {
  println(this.foldRight(0) { it, acc -> it + acc })
}

@JvmName("printReversedConcatenation")
fun List<String>.printReversedSum() {
  println(this.foldRight(StringBuilder()) { it, acc -> acc.append(it) })
}
