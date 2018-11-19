# Jntercept
Event-driver wrapper for the hacking game Intercept

# What is Intercept?
https://bubmet.itch.io/intercept

# Building the client

1. Register an account using the official Intercept client, which can be downloaded from the link above.

2. Create a Jntercept instance using those credentials:
```java
Jntercept client = new Jntercept("username", "password");
```
3. Addd your event listeners
```java
client.addEventListeners(new YourListener());
```
4. Build and connect to the Intercept server!
```java
client.build();
```
