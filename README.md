localhost:8083/connectors/ 
#### post


```
{
"name": "outbox-connector3", 
"config": {
    "connector.class": "io.debezium.connector.postgresql.PostgresConnector",
    "tasks.max": "1",
    "database.hostname": "postgres",
    "database.port": "5432",
    "database.user": "postgres",
    "database.password": "postgres",
    "database.dbname" : "postgres",
    "database.server.name": "dbserver1",
    "schema.include.list": "public",
    "table.include.list": "public.outboxevent",
    "time.precision.mode": "connect",
    "tombstones.on.delete":"false",
    "transforms":"outbox",
    "transforms.outbox.type":"io.debezium.transforms.outbox.EventRouter",
    "transforms.outbox.route.topic.replacement": "${routedByValue}",
    "transforms.outbox.table.field.event.id":"id",
    "transforms.outbox.table.field.event.key":"aggregate_id",
    "transforms.outbox.table.field.event.payload":"payload",
    "transforms.outbox.table.field.event.payload.id":"aggregate_id",
    "transforms.outbox.route.by.field":"topic_name",
    "transforms.outbox.table.fields.additional.placement":"topic_name:header:type",
    "topic.prefix":"dddddddd",
    "plugin.name": "pgoutput",
    "schema.history.internal.kafka.bootstrap.servers": "broker:9092"
  }
}
```