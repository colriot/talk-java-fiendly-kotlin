import analytics.Analytics;
import analytics.EmptyPlugin;
import analytics.Event;
import analytics.LoggerPlugin;
import analytics.Plugin;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import retrofit.Client;
import retrofit.Retrofit;
import sample.Api;
import sample.SegmentPlugin;
import util.ReverserKt;
import util.SumsKt;

import static analytics.Analytics.USER_ID;
import static java.util.Arrays.asList;

public class App {
  public static void main(String[] args) {

    useAnalytics();


    //useUtils();


    //useRetrofit();


    //useMisc();
  }




  public static void useAnalytics() {
    // Statics
    final Map<String, Object> props = new HashMap<>();
    props.put(USER_ID, 1235);
    props.put("my_custom_attr", true);

    Analytics.INSTANCE.send(new Event("custom_event", props));


    // Overloads
    Analytics.INSTANCE.send(new Event("only_name_event", Collections.emptyMap()));


    // Renaming
    boolean hasPlugins = Analytics.INSTANCE.getHasPlugins();


    // Accessing fields, constants
    Analytics.INSTANCE.addPlugin(Analytics.INSTANCE.getEMPTY_PLUGIN()); // dry-run


    final LoggerPlugin loggerPlugin = new LoggerPlugin("ALog");
    System.out.println("Logger TAG: " + loggerPlugin.getTag());


    // Wildcards

    final List<EmptyPlugin> pluginsToSet =
        Arrays.asList(loggerPlugin, new SegmentPlugin());
    // This will cause incompatible types error, if you uncomment @JvmSuppressWildcards on addPlugins().
    Analytics.INSTANCE.addPlugins(pluginsToSet);

    final List<? extends Plugin> plugins = Analytics.INSTANCE.getPlugins();
    displayPlugins(plugins);






    // Will cause error if uncommented, due to @JvmWildcard
    // on getPlugins()
    //((ArrayList<Plugin>) Analytics.getPlugins()).add(new EmptyPlugin());
  }


  private static void displayPlugins(List<? extends Plugin> plugins) {
    System.out.println("Following plugins installed: ");
    for (Plugin plugin : plugins) {
      System.out.println(plugin.getClass());
    }
  }



















  private static void useUtils() {
    System.out.println(ReverserKt.reverse("Test"));


    SumsKt.printReversedSum(asList(1, 2, 3, 4, 5));

    SumsKt.printReversedConcatenation(
        asList("1", "2", "3", "4", "5"));
    }





















  private static void useRetrofit() {
    // Builders
    final Retrofit retrofit = new Retrofit.Builder() //
        .baseUrl("https://api.domain.com") //
        .client(new Client()) //
        .build();



    // Class<?> handling. Internal visibility.
    final Api api = retrofit
        //.validate()     // visible or not?
        .create(Api.class);

    api.sendMessage("Hello from Java");
  }


















  private static void useMisc() {
    // Inline functions. Unit.
    //final List<Integer> list = asList(1, 2, 3, 4);
    //ReverserUtils.forEachReversed(list, integer -> {
    //  System.out.println(integer);
    //
    //  // Can not use method reference because of this.
    //  return null;
    //});
    //
    //
    //
    //ReverserUtils.FIXME();

    System.out.println("Works!");

    // Inline functions with reified type params
    // are not accessible in Java.
    //System.out.print(ReverserUtils.reversedClassName<String>());
  }
}







