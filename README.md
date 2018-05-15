# Mongo-IOT
Demo project with 4 sub-projects written in Java: a web service, a random generator, an analyser and an alert manager.

Warning : This project is not meant to be the perfect solution for IOT and analytic projects. This is just a showcase of different Java and MongoDB features and maybe a starting point for better ideas and architectures. Also, this architecture is clearly missing load balancers and there are way smarter ways to get the same result in Slack as well.

![Architecture schema](/images/Mongo-IOT.png)

This demo simulates hundreds of computers into several datacenters being monitored (hard drive temperature here but sky is the limit).
The goal is to collect all the sensor metrics into MongoDB and then analyse that with change streams and finally push a report into Slack.

# The web service
In the mongo-iot folder you will find the web service.

It's developed with Spring boot and the MongoDB Java driver (*not* with the Spring Data MongoDB) and implements a simple POST method that writes into MongoDB.

# The generator
In the generator folder, you will find the code that sends POST queries to the web service. The sensors metrics are generated with some randomness.

# The analyser
In the analyser folder, you will find a dual thread process.
The first thread reads the change stream of sensors and push the document into a list.
The second thread (which starts every 10 seconds) reads that list, calculates the average temperature in the datacenter, sends a report into MongoDB and clears the list.

# The alert-manager
In the alert-manager folder, you will find the code that reads a change stream of reports and sends important reports into Slack.
