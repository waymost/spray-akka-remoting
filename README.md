spray-akka-remoting
===================

Instructions

1. Run two sbt sessions.
2. In the first, go to project actor and type re-start.
3. In the second, go to project web and type test.

In the console output of the web test, you'll see "onSuccess => 6", but the route will timeout.
