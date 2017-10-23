package sample;

import analytics.EmptyPlugin;
import analytics.Event;
import com.segment.analytics.Analytics;
import com.segment.analytics.messages.TrackMessage;
import org.jetbrains.annotations.NotNull;

public class SegmentPlugin extends EmptyPlugin {
  private final Analytics analytics =
      Analytics.builder("key_test").build();

  @Override public void send(@NotNull Event event) {
    analytics.enqueue(TrackMessage
        .builder(event.getName())
        .properties(event.getContext()));
  }
}
