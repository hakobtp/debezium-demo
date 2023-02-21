package com.debezium.demo.common.debezium;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("embeddedDebezium")
public class EmbeddedDebeziumConfig {

    @Bean
    public io.debezium.config.Configuration debeziumConnector() {
//        org.apache.kafka.connect.storage.FileOffsetBackingStore

        return io.debezium.config.Configuration.create()
                //configure postgres connector
                .with("name", "outbox-connector")
                .with("connector.class", "io.debezium.connector.postgresql.PostgresConnector")
                .with("offset.storage.topic", "debezium_demo_connect_offsets")
                .with("bootstrap.servers", "localhost:9092")
                .with("offset.storage.partitions", 3)
                .with("offset.storage.replication.factor", -1)
                .with("offset.storage", "org.apache.kafka.connect.storage.KafkaOffsetBackingStore")
//                .with("offset.storage.file.filename", "/home/hakob/Desktop/debezium-demo/src/main/resources/offsets.dat")
                .with("offset.flush.interval.ms", "60000")
                .with("cleanup.policy", "compact")
                //configure database location
                .with("database.hostname", "localhost")
                .with("database.port", "5432")
                .with("database.user", "postgres")
                .with("database.password", "postgres")
                .with("database.dbname", "postgres")
                .with("database.server.name", "dbserver1")
                .with("schema.include.list", "public")
                .with("table.include.list", "public.outboxevent")
                // Debezium only sends events for the modifications of outbox table and not all tables
                .with("include.schema.changes", "false")
                .with("transforms", "outbox")
                .with("transforms.outbox.type", "io.debezium.transforms.outbox.EventRouter")
                .with("transforms.outbox.route.topic.replacement", "${routedByValue}")
                .with("transforms.outbox.table.field.event.id", "id")
                .with("transforms.outbox.table.field.event.key", "aggregate_id")
                .with("transforms.outbox.table.field.event.payload", "payload")
                .with("transforms.outbox.table.field.event.payload.id", "aggregate_id")
                .with("transforms.outbox.route.by.field", "topic_name")
                .with("transforms.outbox.table.fields.additional.placement", "topic_name:header:type")
                .with("topic.prefix", "tp")
                .with("plugin.name", "pgoutput")
                .with("schema.history.internal.kafka.bootstrap.servers", "localhost:9092")
                .with("schema.history.internal.kafka.topic", "schema-changes.inventory")


//                .with("database.history", "io.debezium.relational.history.FileDatabaseHistory")
//                .with("schema.history.internal.kafka.bootstrap.servers", "localhost:9092")
//                .with("database.history.file.filename", "/home/hakob/Desktop/debezium-demo/src/main/resources/dbhistory.dat")
                .build();


    }

//    {

//            "config": {

//                "tasks.max": "1",

//                "": "",

//                "": "",
//                ,

//                "time.precision.mode": "connect",
//                "tombstones.on.delete":"false",
//                ,

//                "schema.history.internal.kafka.bootstrap.servers": "broker:9092"
//    }
//    }

}
