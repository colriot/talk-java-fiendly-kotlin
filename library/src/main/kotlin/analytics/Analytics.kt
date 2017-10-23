package analytics

import java.io.IOException
import java.util.*

object Analytics {
  const val USER_ID: String = "user_id"
  val EMPTY_PLUGIN = EmptyPlugin()

  private val plugins = arrayListOf<Plugin>()

  val hasPlugins
    get() = plugins.isNotEmpty()

  var isInited: Boolean = false
  private set

  fun init() {
    log("<Internal Init>")

    plugins.forEach { it.init() }
    isInited = true
  }

  fun send(event: Event) {
    log("<Internal Send event>")

    plugins.forEach {
      try {
        it.send(event)
      } catch (e: IOException) {
        log("WARN: ${it.javaClass.simpleName} fired IOE")
      } 
    }
  }

  fun close() {
    log("<Internal Close>")

    plugins.forEach { it.close() }
    isInited = false
  }

  fun addPlugin(plug: Plugin) {
    if (isInited) plug.init()
    plugins.add(plug)
  }

  fun addPlugins(plugs: List<Plugin>) {
    plugs.forEach { addPlugin(it) }
  }

  fun getPlugins(): List<Plugin> = plugins

  private fun log(message: String) {
    println(message)
  }

  private fun <T> ArrayList<T>.toImmutableList() =
      when (size) {
        0 -> emptyList()
        1 -> Collections.singletonList(get(0))
        else -> Collections.unmodifiableList(this)
      }
}
