## Simulation of Super Simple Stock Market

use:
	mvn clean install

for compile, run test and run simulation (in SECONDS and not MINUTES).

This project is an implementation of a generic Simple Stock Market.

Classes' organization and API are inspired to a Domain Drive Design, but without spring. Each classes have static constructor to instance other services, for this release.

For cache proposal, there are used API of Guava.

Test infrastructure is based on JUnit. The tests are very small, works as integration test from the top level of the application.

One Simulation class run a scenario of working of the application.

TODO:
1. Add Spring
2. Change Geometrical Mean calculation
3. Add Drools
