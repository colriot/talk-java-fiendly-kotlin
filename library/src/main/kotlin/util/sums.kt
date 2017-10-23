package util

fun List<Int>.printReversedSum() {
  println(this.foldRight(0) { it, acc -> it + acc })
}

@JvmName("printReversedConcatenation")
fun List<String>.printReversedSum() {
  println(this.foldRight(StringBuilder()) { it, acc -> acc.append(it) })
}
