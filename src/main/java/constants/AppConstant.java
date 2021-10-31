package constants;

import utils.LogLevel;

public class AppConstant {
  public static final LogLevel LOG_LEVEL = LogLevel.DEBUG;
  public static final String API_KEY = "rxAZ0Miah9lUfSr1AzSspS37pjIrCrBx7mDrGahdTCw";

  private AppConstant() {
    throw new Error("Can't initiate a singleton class");
  }
}
