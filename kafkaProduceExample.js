const { Kafka } = require('kafkajs');

async function produceMessage() {
    const kafka = new Kafka({
        brokers: ['localhost:9092']
    });

    const producer = kafka.producer();

    await producer.connect();

    const topic = 'dev-newsfeed-article';
    const message = {
        value: 'Hello Kafka on dev!'
    };

    await producer.send({
        topic,
        messages: [message]
    });

    console.log('Message sent successfully');

    await producer.disconnect();
}

produceMessage().catch(console.error);
