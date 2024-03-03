# SFR-News-Project
## Github Link
https://github.com/Lileo0/SFR-News-Project
## Setup
* Run the docker-compose file

* Install required dependencies for the javascript via npm install 

* Run the java script file to publish a message into a topic
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

in.sync.replica: In order to keep aöö 3 replicas in sync we use leaders and followers.
A leader replica is a specified topic to which the producers sends messages.
Consumers also typically fetch from the leader, but can be configured to do otherwise
Follower replicas will fetch data from the leader in order to keep in sync.
Once a leader has been defined and the followers are in sync that is called a in-sync replica set.

