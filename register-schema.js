const request = require('request');

const SCHEMA_REGISTRY_URL = 'http://localhost:8090';
const SUBJECT = 'news-article-value';

// Avro Schema
const AVRO_SCHEMA = {
    "type": "record",
    "name": "NewsArticle",
    "fields": [
        {"name": "title", "type": "string"},
        {"name": "date", "type": "string"},
        {"name": "text", "type": "string"},
        {"name": "author", "type": "string"}
    ]
};

// Send a POST request to register the schema
request.post({
    url: `${SCHEMA_REGISTRY_URL}/subjects/${SUBJECT}/versions`,
    headers: {
        'Content-Type': 'application/vnd.schemaregistry.v1+json'
    },
    json: { schema: JSON.stringify(AVRO_SCHEMA) }
}, (error, response, body) => {
    if (error) {
        console.error('Error registering schema:', error.message);
        return;
    }
    if (!response || response.statusCode !== 200) {
        console.error('Failed to register schema:', body);
        return;
    }
    console.log('Schema registered successfully!');
    console.log('Response:', body);
});
