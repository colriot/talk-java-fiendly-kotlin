import analytics.Analytics;
import analytics.EmptyPlugin;
import analytics.Event;
import analytics.LoggerPlugin;
import analytics.Plugin;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import retrofit.Client;
import retrofit.Retrofit;
import sample.Api;
import sample.SegmentPlugin;
import util.ReverserUtils;

import static analytics.Model.USER_ID;
import static java.util.Arrays.asList;

public class App {
  public static void main(String[] args) {

    // Statics
    final Map<String, Object> props = new HashMap<>();
    props.put(USER_ID, 1235);
    props.put("my_custom_attr", true);

    Analytics.send(new Event("custom_event", props));


    // Overloads
    Analytics.send(new Event("only_name_event"));


    // Renaming
    boolean hasPlugins = Analytics.hasPlugins();

    ReverserUtils.printReversedSum(asList(1, 2, 3, 4, 5));

    ReverserUtils.printReversedConcatenation(asList("1", "2", "3", "4", "5"));

    System.out.println(ReverserUtils.reverse("Test"));


    // Accessing fields, constants
    final LoggerPlugin loggerPlugin = new LoggerPlugin("ALog");
    System.out.println("Logger TAG: " + loggerPlugin.tag);


    // Wildcards

    // This will cause incompatible types error, if you uncomment @JvmSuppressWildcards on addPlugins().
    Analytics.addPlugins(Arrays.<EmptyPlugin>asList(loggerPlugin, new SegmentPlugin()));

    final List<? extends Plugin> plugins = Analytics.getPlugins();
    displayPlugins(plugins);

    // Will cause error if uncommented, due to @JvmWildcard on getPlugins()
    //Analytics.getPlugins().add(new EmptyPlugin());


    // Builders
    final Retrofit retrofit = new Retrofit.Builder() //
        .baseUrl("https://api.domain.com") //
        .client(new Client()) //
        .build();


    // Class<?> handling. Internal visibility.
    final Api api = retrofit
        .validate$library_main()     // visible and actually accessible from Java
        .create(Api.class);

    api.sendMessage("Hello from Java");


    // Inline functions. Unit.
    final List<Integer> list = asList(1, 2, 3, 4);
    ReverserUtils.forEachReversed(list, integer -> {
      System.out.println(integer);
      return null;                    // Can not use method reference because of this.
    });

    // Inline functions with reified type params are not accessible in Java.
    //System.out.print(ReverserUtils.reversedClassName<String>());
  }

  private static void displayPlugins(List<? extends Plugin> plugins) {
    System.out.println("Following plugins installed: ");
    for (Plugin plugin : plugins) {
      System.out.println(plugin.getClass());
    }
  }
}
