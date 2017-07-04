import analytics.Analytics
import analytics.Event
import analytics.LoggerPlugin
import analytics.Plugin
import analytics.USER_ID
import retrofit.Client
import retrofit.RetroBuilder
import sample.Api
import sample.SegmentPlugin
import util.forEachReversed
import util.printReversedSum
import util.reverse
import util.reversedClassName

fun main(args: Array<String>) {

  // Statics
  val props = mapOf(
      USER_ID to 1235,
      "my_custom_attr" to true
  )

  Analytics.send(Event("custom_event", props))


  // Overloads
  Analytics.send(Event("only_name_event"))


  // Renaming
  val hasPlugins = Analytics.hasPlugins

  listOf(1, 2, 3, 4, 5).printReversedSum()

  listOf("1", "2", "3", "4", "5").printReversedSum()

  println("Test".reverse())


  // Accessing fields, constants
  val loggerPlugin = LoggerPlugin("ALog")
  println("Logger TAG: ${loggerPlugin.tag}")


  // Wildcards

  Analytics.addPlugins(listOf(loggerPlugin, SegmentPlugin()))

  val plugins = Analytics.getPlugins()
  displayPlugins(plugins)

  // Will cause error if uncommented, due to immutable nature of List
//  Analytics.getPlugins().add(EmptyPlugin())


  // Builders
  val retrofit = RetroBuilder()     // Can use typealiases
      .baseUrl("https://api.domain.com")
      .client(Client())
      .build()


  // KClass<*> handling. Internal visibility.
  val api = retrofit
//      .validate()                // Not visible, because it's internal.
      .create(Api::class)

  api.sendMessage("Hello from Kotlin")


  // Inline functions. Unit.
  listOf(1, 2, 3, 4).forEachReversed(::println)

  print(reversedClassName<String>())
}

private fun displayPlugins(plugins: List<Plugin>) {
  println("Following plugins installed: ")
  plugins.forEach { println(it.javaClass) }
}
