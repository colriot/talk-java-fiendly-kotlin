@file:JvmName("ReverserUtils")
@file:JvmMultifileClass

package util

inline fun String.reverse() =
    StringBuilder(this).reverse().toString()

inline fun <reified T> reversedClassName() =
    T::class.java.simpleName.reverse()


inline fun <T> Iterable<T>.forEachReversed(action: (T) -> Unit) {
  for (element in this.reversed()) action(element)
}
