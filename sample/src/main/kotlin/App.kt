
import analytics.Analytics
import analytics.Analytics.EMPTY_PLUGIN
import analytics.Analytics.USER_ID
import analytics.Event
import analytics.LoggerPlugin
import analytics.Plugin
import retrofit.Client
import retrofit.RetroBuilder
import sample.Api
import sample.SegmentPlugin
import util.FIXME
import util.forEachReversed
import util.printReversedSum
import util.reverse
import util.reversedClassName

fun main(args: Array<String>) {

  useAnalytics()


//  useUtils()


//  useRetrofit()


//  useMisc()
}




private fun useAnalytics() {
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


  // Accessing fields, constants
  Analytics.addPlugin(EMPTY_PLUGIN) // dry-run


  val loggerPlugin = LoggerPlugin("ALog")
  println("Logger TAG: ${loggerPlugin.tag}")


  // Wildcards

  Analytics.addPlugins(listOf(loggerPlugin, SegmentPlugin()))

  val plugins = Analytics.getPlugins()
  displayPlugins(plugins)






  // Will cause error if uncommented, due to immutable
  // nature of List
//  Analytics.getPlugins().add(EmptyPlugin())
}


private fun displayPlugins(plugins: List<Plugin>) {
  println("Following plugins installed: ")
  plugins.forEach { println(it.javaClass) }
}

















private fun useUtils() {
  println("Test".reverse())


  listOf(1, 2, 3, 4, 5).printReversedSum()

  listOf("1", "2", "3", "4", "5").printReversedSum()
}





















private fun useRetrofit() {
  // Builders
  val retrofit = RetroBuilder() // Can use typealiases
      .baseUrl("https://api.domain.com")
      .client(Client())
      .build()



  // KClass<*> handling. Internal visibility.
  val api = retrofit
//      .validate()        // Not visible, because it's internal.
      .create(Api::class)

  api.sendMessage("Hello from Kotlin")
}


















private fun useMisc() {
  // Inline functions. Unit.
  listOf(1, 2, 3, 4).forEachReversed(::println)



  FIXME()

  println(reversedClassName<String>())
}













