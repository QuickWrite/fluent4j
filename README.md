# fluent4j
 A Java library that implements [Mozillas Fluent project](https://www.projectfluent.org/).
 
## Usage

You could either use the library directly without all of the abstraction and create all of the
objects by yourself:

```java
FluentResource resource = FluentParser.parse("emails = You have { $unreadEmails } unread emails.");
FluentBundle bundle = new ResourceFluentBundle(ULocale.ENGLISH, resource);

FluentArgs arguments = new ResourceFluentArguments();
arguments.setNamed("unreadEmails", new NumberLiteral(10));

System.out.println(bundle.getMessage("emails", arguments));
```

or you could use the builders that the library provides for this:

```java
FluentBundle bundle = new FluentBundleBuilder(ULocale.ENGLISH, "emails = You have { $unreadEmails } unread emails.")
                          .build();
FluentArgs arguments = new FluentArgsBuilder().setNamed("unreadEmails", 10).build();

System.out.println(bundle.getMessage("emails", arguments));
```

In both cases they would print the message `You have 10 unread emails.`.

## License
This project is licensed under the permissive [Apache 2.0 license](LICENSE).
