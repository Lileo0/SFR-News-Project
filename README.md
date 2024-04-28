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

* Build the project to create the API Delegate and DTO from the news API Specification
* Start the Application
### How to run SFR-News-Provider Springboot Application
* Run the docker-compose file
* Build and run the Springboot application

Additional Notes: The application can only work if the docker containers from SFR-News-Projects have been started and a schema has been registered, due to its usage of the schema registry.
## Exercise 4
### Backend System and Database

##### What happens if the microservice goes down and cannot process the messages from Kafka anymore?
The microservice comes back up and resumes consumption. Depending on the Kafka consumer group's configuration, other consumer instances in the same group may take over processing those messages in the meantime.
##### What happens if the microservice consumes the messages and goes down while processing the message?
If the acknowledgment mode is set to automatic, the message will be considered processed as soon as it is read from Kafka, 
and if the microservice crashes before completing processing, the message will not be reprocessed automatically. 
However, if the acknowledgment mode is set to ackMode="manual", the microservice can acknowledge the message manually after it has been successfully processed, 
ensuring that the message will be reprocessed if the microservice crashes before acknowledgment.
##### What happens when the microservice consumes the message but cannot write the event into the database as it is unavailable?
The microservice should ideally handle such failures gracefully, log the error, and retry writing to the database after a certain delay or backoff strategy. 
If the failure persists despite retries, the message may need to be forwarded to a dead-letter queue for further analysis or manual intervention.
##### Can you span a transaction across reading from Kafka and writing to the database?
Spring Kafka provides support for exactly-once message processing through transactional semantics when combined with a transactional database.

##### Why did you decide on the given database model (SQL, NoSQL like document store, column store, or graph database)?
Given that the data that is to be saved in the database always follows the same structure and at the current implementation no join statements are needed, the decision was made to use an SQL database, in this case postgres.

##### What guarantees does the database give you if you want to join different entities?
PostgreSQL offers join capabilities, we can use SQL JOIN operations. PostgreSQL ensures consistency and transactional integrity during joins, adhering to ACID properties.

##### Describe how the database scales (leader/follower, sharding/partitioning, ...) horizontally to multiple instances?
For horizontal scaling, PostgreSQL supports several approaches. We would probably set up a leader/follower replication. Set up leader/follower replication. The leader handles writes, while followers replicate data for read scalability.
Some other possibilities would be partitioning (divide the news table based on news providers or authors perhaps) or setting up a load balancer which would distribute incoming requests across multiple PostreSQL instances.

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


