# SFR-News-Project
## GitHub Link
https://github.com/Lileo0/SFR-News-Project

# SFR-News-Provider
## GitHub Link
https://github.com/Lileo0/SFR-News-Provider

## Setup
### How to run the SFR-News-Project:
* Run the docker-compose file

* Install required dependencies for the JavaScript via npm install 

* Run the produceMessageToAvroTopic JavaScript file to register a schema and publish a message into a topic
* Optional: You can also register a schema separately by running the register-schema JavaScript file

### How to run SFR-News-Provider Springboot Application
* Run the docker-compose file
* Build and run the Springboot application

Additional Notes: The application can only work if the docker containers from SFR-News-Projects have been started and a schema has been registered, due to its usage of the schema registry.

## Exercise 3
### How is the Schema validated based on your compatibility mode
For the compatibility mode we stuck with the default compatibility mode of the confluent schema registry, which is default.
Thus meaning that a newly provided schema is validated by comparing it to the previous version. Actions allowed are the deletion of fields and the addition of optional fields.

## Exercise 2
### Analyze how the following things are related:
Number of brokers: Number of kafka servers 

Number of partitions: As the name indicates this splits a topic into different partitions. 
These partitions receive topics in the round-robin format, which means incoming events get distributed equally and one after the other.
In order to publish a message into a specific partition it is required to provide a key.

Number of replicas: Replication means that data is written down not just to one broker, but many.
Replication is enabled on a topic level.
E.g. if the replica count is 3 it means that this message is also stored on 2 different brokers. 
You typically have multiple brokers to ensure that no data gets lost.

in.sync.replica: In order to keep all 3 replicas in sync we use leaders and followers.
A leader replica is a specified topic to which the producers send messages.
Consumers also typically fetch from the leader, but can be configured to do otherwise
Follower replicas will fetch data from the leader in order to keep in sync.
Once a leader has been defined and the followers are in sync that is called an in-sync replica set.
