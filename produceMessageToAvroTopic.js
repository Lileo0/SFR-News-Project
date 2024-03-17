const registry = require('avro-schema-registry')('http://localhost:8090');
const { Kafka } = require('kafkajs');
const avro = require('avsc');

const kafka = new Kafka({
    brokers: ['localhost:9092'],
    clientId: 'avro-producer'
});

const schema = {
    type: 'record',
    name: 'NewsArticle',
    fields: [
        { name: 'title', type: 'string' },
        { name: 'date', type: 'string' },
        { name: 'text', type: 'string' },
        { name: 'author', type: 'string' },
    ]
};
const message = {
    title: 'John Doe',
    date: 'bb',
    text: 'aa',
    author: 'dd'
};

const producer = kafka.producer();
let test
registry.encodeMessage('news-input', schema, message)
    .then((msg) => {
        console.log(msg);   // <Buffer 00 00 00 00 01 18 74 65 73 74 20 6d 65 73 73 61 67 65>
        test = msg
        return registry.decode(msg);
    })


const produceMessage = async () => {
    let ss
    const message = {
        title: 'John Doe',
        date: 'bb',
        text: 'aa',
        author: 'dd'
    };
    await registry.encodeMessage('news-input', schema, message)
        .then((msg) => {
            console.log(msg);   // <Buffer 00 00 00 00 01 18 74 65 73 74 20 6d 65 73 73 61 67 65>
            ss = msg
            return msg
        })

    await producer.send({
        topic: 'news-input',
        messages: [
            { value: ss }
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

/*registry.encodeById(1, message)
    .then((msg) => {
        console.log(msg);   // <Buffer 00 00 00 00 01 18 74 65 73 74 20 6d 65 73 73 61 67 65>

        return registry.decode(msg);
    })
*/