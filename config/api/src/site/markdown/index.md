## About Configuration API

API and SPI for context-specific configuration.

### Maven Dependency

```xml
<dependency>
  <groupId>io.wcm</groupId>
  <artifactId>io.wcm.config.api</artifactId>
  <version>0.5.2</version>
</dependency>
```

### Documentation

* [Terminology][terminology] and basic concepts
* [Configuration API usage][usage-api]
* [Configuration SPI usage][usage-spi]
* [API documentation][apidocs]
* [Changelog][changelog]



### Overview

Please read our [terminology][terminology] page first to understand the basic concepts.

[Configuration API][usage-api]: This is used by applications that want to access the configured context-specific
parameter-values (read-only).

[Configuration SPI][usage-spi]: This is used by applications that want to provide own parameter definitions
or want to customize the configuration management in any way supported.


[apidocs]: apidocs/
[changelog]: changes-report.html
[terminology]: terminology.html
[usage-api]: usage-api.html
[usage-spi]: usage-spi.html
