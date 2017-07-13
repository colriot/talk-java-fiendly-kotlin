package analytics

import java.io.IOException
import java.util.*

object Analytics {
  const val VERSION = "0.0.1"

  private val plugins = arrayListOf<Plugin>()

  @JvmStatic @get:JvmName("hasPlugins") val hasPlugins get() = plugins.isNotEmpty()

  @JvmStatic var isInited: Boolean = false
  private set

  @JvmStatic fun init() {
    log("<Internal Init>")

    plugins.forEach { it.init() }
    isInited = true
  }

  @JvmStatic fun send(event: Event) {
    log("<Internal Send event>")

    plugins.forEach { 
      try {
        it.send(event)
      } catch (e: IOException) {
        log("WARN: ${it.javaClass.simpleName} fired IOE")
      } 
    }
  }

  @JvmStatic fun close() {
    log("<Internal Close>")

    plugins.forEach { it.close() }
    isInited = false
  }

  @JvmStatic fun addPlugin(plug: Plugin) {
    if (isInited) plug.init()
    plugins.add(plug)
  }

//  @JvmSuppressWildcards
  @JvmStatic fun addPlugins(plugs: List<Plugin>) {
    plugs.forEach { addPlugin(it) }
  }

  @JvmStatic fun getPlugins(): List<@JvmWildcard Plugin> = plugins.toImmutableList()

  private fun log(message: String) {
    println(message)
  }

  private fun <T> ArrayList<T>.toImmutableList() = when (size) {
    0 -> emptyList()
    1 -> Collections.singletonList(get(0))
    else -> Collections.unmodifiableList(this)
  }
}
