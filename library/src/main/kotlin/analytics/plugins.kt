package analytics

import java.io.IOException

interface Plugin {
  fun init()
  /** @throws IOException if sending failed */
  @Throws(IOException::class)
  fun send(event: Event)
  fun close()
}

open class EmptyPlugin : Plugin {
  override fun init() {}

  @Throws(IOException::class)
  override fun send(event: Event) {}

  override fun close() {}
}

class LoggerPlugin(val tag: String) : EmptyPlugin() {
  override fun send(event: Event) {
    println("$tag: [${event.name}] ${event.context}")
  }
}
