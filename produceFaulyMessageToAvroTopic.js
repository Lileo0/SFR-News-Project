const { Kafka } = require('kafkajs');
const avro = require('avsc');

// Define Avro schema
const avroSchema = {
    type: 'record',
    name: 'NewsArticle',
    fields: [
        { name: 'title', type: 'string' },
        { name: 'date', type: 'string' },
        { name: 'text', type: 'string' },
    ]
};

// Create Avro type from schema
const type = avro.Type.forSchema(avroSchema);

// Create Kafka producer
const kafka = new Kafka({
    brokers: ['localhost:9092'],
    clientId: 'avro-producer'
});

const producer = kafka.producer();

const produceMessage = async () => {
    const message = {
        title: 'John Doe',
        date: 'bb',
        text: 'aa'
    };

    // Serialize message to Avro binary format
    const serializedMessage = type.toBuffer(message);

    await producer.send({
        topic: 'news-input',
        messages: [
            { value: serializedMessage }
        ]
    });

    console.log('Message sent successfully!');
};

const run = async () => {
    await producer.connect();
    await produceMessage();
    await producer.disconnect();
};

run().catch(console.error);
