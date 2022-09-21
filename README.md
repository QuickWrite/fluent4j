# fluent4j
A Java library that implements [Mozillas Fluent project](https://www.projectfluent.org/).

## Installation
### Maven
```xml
<repositories>
    ...
    <repository>
        <id>quickwrite-net-fluent4j</id>
        <url>https://dl.cloudsmith.io/public/quickwrite-net/fluent4j/maven/</url>
    </repository>
    ...
</repositories>
```
```xml
<dependencies>
    ...
    <dependency>
        <groupId>quickwrite-net</groupId>
        <artifactId>fluent4j</artifactId>
        <version>{{package-version}}</version>
    </dependency>
    ...
</dependencies>
```

### Gradle
```groovy
repositories {
    ...
    maven {
        url "https://dl.cloudsmith.io/public/quickwrite-net/fluent4j/maven/"
    }
    ...
}
```
```groovy
dependencies {
    ...
    implementation 'net.quickwrite:fluent4j:{{package-version}}'
    ...
}
```

## Usage

You could either use the library directly without all of the abstraction and create all of the
objects by yourself:

```java
FluentResource resource = FluentParser.parse("emails = You have { $unreadEmails } unread emails.");
FluentBundle bundle = new ResourceFluentBundle(ULocale.ENGLISH, resource);

FluentArgs arguments = new FluentArguments();
arguments.setNamed("unreadEmails", new NumberLiteral(10));

System.out.println(bundle.getMessage("emails", arguments).get());
```

or you could use the builders that the library provides for this:

```java
FluentBundle bundle = new FluentBundleBuilder(ULocale.ENGLISH, "emails = You have { $unreadEmails } unread emails.")
        .build();

FluentArgs arguments = new FluentArgsBuilder().set("unreadEmails", 10).build();

System.out.println(bundle.getMessage("emails", arguments).get());
```

In both cases they would print the message `You have 10 unread emails.`.

## License
This project is licensed under the permissive [Apache 2.0 license](LICENSE).
