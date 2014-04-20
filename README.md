# Weather Report

[Blog Post Guide](http://blog.markfayngersh.com/glassware-getting-started-developing-native-apps-for-google-glass)

![Prompt](https://dl.dropboxusercontent.com/s/syrqacnuf3a8t3s/Screenshot%202014-04-19%2019.40.46.png?dl=1&token_hash=AAHYp98qd1dqAhM8dlp1CtEQh-Mv_TJ-d30B10_NFlII6Q)

![Activity](https://dl.dropboxusercontent.com/s/lnkl46m2imtsl4k/Screenshot%202014-04-19%2019.40.55.png?dl=1&token_hash=AAHT2VKIzr-Onq7xSCtDu3XUfDqcFt_TzfYCqpmhA9tQbA)

Very basic Glassware that displays temperature and reads aloud weather description for a given city.

Uses [Open Weather Map API](http://openweathermap.org/API) to fetch basic weather data.

## Dependencies

**App** - These dependencies are used by the main application

    $ ./gradlew app:dependencies --configuration compile
      > com.mcxiaoke.volley:library:1.0.+ -> 1.0.3

**Test** - These additional dependencies are used solely for testing

    $ ./gradlew app:dependencies --configuration androidTestCompile
      > org.hamcrest:hamcrest-all:1.3

## Installation

Connect your Google Glass device and run:

    $ ./gradlew installDebug

## Tests

Unit tests are written using JUnit 3 and [Hamcrest](https://github.com/hamcrest/JavaHamcrest).

To run the test suite, connect your Glass and execute:

    $ ./gradlew connectedAndroidTest
