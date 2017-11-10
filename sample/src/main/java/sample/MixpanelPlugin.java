package sample;

import analytics.EmptyPlugin;
import analytics.Event;
import com.mixpanel.mixpanelapi.MixpanelAPI;
import java.io.IOException;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

public class MixpanelPlugin extends EmptyPlugin {

  private static final String EVENT_NAME = "event_name";

  private final MixpanelAPI api = new MixpanelAPI();

  @Override public void send(@NotNull Event event)
      throws IOException {

    final JSONObject jsonObject =
        new JSONObject(event.getContext());
    try {
      jsonObject.put(EVENT_NAME, event.getName());
    } catch (JSONException ignored) {
    }
    api.sendMessage(jsonObject);
  }
}
