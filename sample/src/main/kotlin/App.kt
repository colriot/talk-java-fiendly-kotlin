
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
import util.forEachReversed
import util.printReversedSum
import util.reverse
import util.reversedClassName

fun main(args: Array<String>) {

  useAnalytics()

  useUtils()

  useRetrofit()

  useMisc()
}









private fun useAnalytics() {

  Analytics.send(Event("only_name_event"))


  val props = mapOf(
      USER_ID to 1235,
      "my_custom_attr" to true
  )

  Analytics.send(Event("custom_event", props))


  val hasPlugins = Analytics.hasPlugins


  Analytics.addPlugin(EMPTY_PLUGIN) // dry-run


  Analytics.addPlugins(listOf(LoggerPlugin("ALog"), SegmentPlugin()))

  val plugins = Analytics.getPlugins()
  displayPlugins(plugins)


//  Analytics.getPlugins().add(EmptyPlugin())
}













private fun displayPlugins(plugins: List<Plugin>) {
  println("Following plugins installed: ")
  plugins.forEach { println(it.javaClass) }
}























private fun useUtils() {
  listOf(1, 2, 3, 4, 5).printReversedSum()

  listOf("1", "2", "3", "4", "5").printReversedSum()


  println("Test".reverse())
}























private fun useRetrofit() {
  val retrofit = RetroBuilder()
      .baseUrl("https://api.domain.com")
      .client(Client())
      .build()



  val api = retrofit
      .create(Api::class)

  api.sendMessage("Hello from Kotlin")
}























  private fun useMisc() {
  listOf(1, 2, 3, 4).forEachReversed(::println)


  println(reversedClassName<String>())
}
