## Simulation of Super Simple Stock Market

Download the project and use:

	mvn clean install

for compile, run test and run simulation (in SECONDS and not MINUTES).

This project is an implementation of a generic Simple Stock Market.

Classes' organization and API are inspired at Domain Drive Design.

For implementing a context to store data, is used the cache implementation of Guava.

Test infrastructure is with JUnit. The tests are very small, works as integration test from the top level of the application.

One Simulation class run a scenario of working of the application.

![alt tag](https://github.com/dsabia/jp/blob/master/SuperSimpleStockMarket/doc/Composite%20Class%20Structures.png)

Possible improvements to do:

1. Add Drools
