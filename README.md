# What is Kafka?
Kafka = Distributed Messaging system

- Producers
- Kafka (Broker)
- Consumer

Producer --> Kafka --> Consumers

### Real world example
- Swiggy order placed -> Producer
- Kafka - Holds the order count
- Delivery service -> Consumer

# Setup Kafka Locally
## Install Docker
Install docker and ensure it is working well in terminal

## Open the terminal (Cmd)
1. Open the terminal with project root path.
```bash
cd {root_directory}
```

2. Make sure to write the docker compose file? <br>
What is Docker compose file? <br>
**It is a set of rules or instruction provided to docker.**

3. Create `docker-compose.yml`
Refer - https://hub.docker.com/r/apache/kafka
```yml
 version: '3'
 services:
   zookeeper:
     image: confluentinc/cp-zookeeper:latest
     environment:
       ZOOKEEPER_CLIENT_PORT: 2181

   kafka:
     image: confluentinc/cp-kafka:latest
     ports:
       - "9092:9092"
     environment:
       KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
       KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://localhost:9092
       KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1
       KAFKA_PROCESS_ROLES: broker
```

4. Start or run KAFKA
```bash
docker-compose up -d 
```

5. Create Topic
```bash
docker exec -it <container_id> bash 
```

```bash
kafka-topics --create \
--topic my-topic \
--bootstrap-server localhost:9092 \
--partitions 1 \
--replication-factor 1
```

6. Send message via Producers
```bash
kafka-console-producer \
--topic my-topic \
--bootstrap-server localhost:9092
```

7. Receive message as a Consumer
```bash
 kafka-console-consumer \
--topic my-topic \
--bootstrap-server localhost:9092 \
--from-beginning
```









