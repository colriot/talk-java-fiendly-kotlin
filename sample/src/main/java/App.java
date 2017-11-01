import analytics.Analytics;
import analytics.EmptyPlugin;
import analytics.Event;
import analytics.LoggerPlugin;
import analytics.Plugin;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kotlin.Unit;
import retrofit.Client;
import retrofit.Retrofit;
import sample.Api;
import sample.SegmentPlugin;
import util.ReverserUtils;
import util.UtilKt;

import static analytics.Analytics.USER_ID;
import static java.util.Arrays.asList;

public class App {
  public static void main(String[] args) {

    useAnalytics();

    useUtils();

    useRetrofit();

    useMisc();
  }




  private static void useAnalytics() {

    // Overloads
    Analytics.send(new Event("only_name_event"));


    // Statics
    final Map<String, Object> props = new HashMap<>();
    props.put(USER_ID, 1235);
    props.put("my_custom_attr", true);

    Analytics.send(new Event("custom_event", props));


    // Renaming
    boolean hasPlugins = Analytics.hasPlugins();


    // Accessing fields, constants
    Analytics.addPlugin(Analytics.EMPTY_PLUGIN); // dry-run

    final LoggerPlugin loggerPlugin = new LoggerPlugin("ALog");
    System.out.println("Logger TAG: " + loggerPlugin.tag);


    // Wildcards

    final List<EmptyPlugin> pluginsToSet = Arrays.asList(loggerPlugin, new SegmentPlugin());

    // This will cause incompatible types error, if you add
    // @JvmSuppressWildcards on addPlugins().
    Analytics.addPlugins(pluginsToSet);

    final List<? extends Plugin> plugins = Analytics.getPlugins();
    displayPlugins(plugins);


    // Fails due to added @JvmWildcard annotation for getPlugins()
    //Analytics.getPlugins().add(new EmptyPlugin());

    // Will cause a ClassCastException - this is not a mutable ArrayList any more
    //((ArrayList<Plugin>) Analytics.getPlugins()).add(new EmptyPlugin());
  }


  private static void displayPlugins(List<? extends Plugin> plugins) {
    System.out.println("Following plugins installed: ");
    for (Plugin plugin : plugins) {
      System.out.println(plugin.getClass());
    }
  }




  private static void useUtils() {
    System.out.println(ReverserUtils.reverse("Test"));


    ReverserUtils.printReversedSum(asList(1, 2, 3, 4, 5));

    ReverserUtils.printReversedConcatenation(asList("1", "2", "3", "4", "5"));
  }




  private static void useRetrofit() {
    // Builders
    final Retrofit retrofit = new Retrofit.Builder() //
        .baseUrl("https://api.domain.com") //
        .client(new Client()) //
        .build();



    // Class<?> handling. Internal visibility.
    final Api api = retrofit
        // visible under another name, but it's fake
        //.validate$production_sources_for_module_library_main()

        .validate$library()     // the real one is not visible, but accessible!
        .create(Api.class);

    api.sendMessage("Hello from Java");
  }




  private static void useMisc() {
    // Inline functions. Unit.
    final List<Integer> list = asList(1, 2, 3, 4);
    ReverserUtils.forEachReversed(list, integer -> {
      System.out.println(integer);

      // Can not use method reference because of this.
      return Unit.INSTANCE;
    });


    // Inline functions with reified type params
    // are not accessible in Java.
    //System.out.print(ReverserUtils.reversedClassName<String>());


    UtilKt.FIXME();

    // No Unreachable code warning
    System.out.println("Works!");
  }
}
