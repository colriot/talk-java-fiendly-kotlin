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
import kotlin.Unit;
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

    useUtils();

    useRetrofit();

    useMisc();
  }









  private static void useAnalytics() {

    Analytics.INSTANCE.send(new Event("only_name_event", Collections.emptyMap()));


    final Map<String, Object> props = new HashMap<>();
    props.put(USER_ID, 1235);
    props.put("my_custom_attr", true);

    Analytics.INSTANCE.send(new Event("custom_event", props));


    boolean hasPlugins = Analytics.INSTANCE.getHasPlugins();


    Analytics.INSTANCE.addPlugin(Analytics.INSTANCE.getEMPTY_PLUGIN()); // dry-run



    final List<EmptyPlugin> pluginsToSet =
        Arrays.asList(new LoggerPlugin("ALog"), new SegmentPlugin());

    Analytics.INSTANCE.addPlugins(pluginsToSet);

    final List<Plugin> plugins = Analytics.INSTANCE.getPlugins();
    displayPlugins(plugins);


    Analytics.INSTANCE.getPlugins().add(new EmptyPlugin());
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
    final Retrofit retrofit = new Retrofit.Builder() //
        .baseUrl("https://api.domain.com") //
        .client(new Client()) //
        .build();



    final Api api = retrofit
        .create(Api.class);

    api.sendMessage("Hello from Java");
  }























  private static void useMisc() {
    final List<Integer> list = asList(1, 2, 3, 4);
    ReverserKt.forEachReversed(list, integer -> {
      System.out.println(integer);

      return Unit.INSTANCE;
    });


    // Inline functions with reified type params
    // are not accessible in Java.
    //System.out.print(ReverserUtils.reversedClassName<String>());
  }
}
