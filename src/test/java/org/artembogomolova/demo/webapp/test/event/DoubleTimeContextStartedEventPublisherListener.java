package org.artembogomolova.demo.webapp.test.event;

import org.springframework.test.context.TestContext;

public class DoubleTimeContextStartedEventPublisherListener extends ContextStartedEventPublishingListener {

  private static final int RELOAD_TIME_EMULATION_COUNT = 2;

  @Override
  public void beforeTestMethod(TestContext testContext) {
    for (int i = 0; i < RELOAD_TIME_EMULATION_COUNT; i++) {
      publishStartedEvent(testContext);
    }
  }
}
