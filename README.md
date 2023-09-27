# fluent4j
A Java library that implements [Mozillas Fluent project](https://www.projectfluent.org/).

## Installation
### Maven
```xml
<repositories>
    <repository>
        <id>quickwrite-net-fluent4j</id>
        <url>https://dl.cloudsmith.io/public/quickwrite-net/fluent4j/maven/</url>
    </repository>
</repositories>
```
```xml
<dependencies>
    <dependency>
        <groupId>net.quickwrite</groupId>
        <artifactId>fluent-builder</artifactId>
        <version>{{package-version}}</version>
    </dependency>
</dependencies>
```

### Gradle
```groovy
repositories {
    maven {
        url "https://dl.cloudsmith.io/public/quickwrite-net/fluent4j/maven/"
    }
}
```
```groovy
dependencies {
    implementation 'net.quickwrite:fluent-builder:{{package-version}}'
}
```

## What is `fluent4j`?
The `fluent4j` library is a Java implementation of [Mozillas Fluent project](https://www.projectfluent.org/) that 
intends to be extensible by default.

This means that custom constructs can be added to the basic fluent syntax so that the translation files can be used 
for the projects exact needs.

## Usage
So that the translation files can be used the files need to be parsed first:
```java
ResourceParser resourceParser = ResourceParserBuilder.defaultParser();

FluentResource resource = resourceParser.parse(FluentIteratorFactory.fromString("""
test = This is your fluent file

emails = You have { $unreadEmails } unread emails.
"""));
```

After you've created a single or multiple Resources you can bundle them in a Bundle with other data for use:
```java
FluentBundle bundle = FluentBundleBuilder.builder(Locale.ENGLISH)
                                         .addResource(resource)
                                         .build();
```

And now you can use the different messages for translation:
```java
System.out.println(bundle.resolveMessage("test", StringResultFactory.construct()).get());
```

```console
This is your fluent file
```

And you can also provide arguments for the messages:
```java
FluentArguments arguments = ArgumentListBuilder.builder()
                                               .add("unreadEmails", 5)
                                               .build();

System.out.println(bundle.resolveMessage("emails", arguments, StringResultFactory.construct()).get());
```

```console
You have 5 unread emails.
```

## License
This project is licensed under the permissive [Apache 2.0 license](LICENSE).
