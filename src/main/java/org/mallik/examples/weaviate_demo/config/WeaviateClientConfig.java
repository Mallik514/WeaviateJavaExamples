package org.mallik.examples.weaviate_demo.config;

import io.weaviate.client.Config;
import io.weaviate.client.WeaviateClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WeaviateClientConfig {

    @Bean
    public WeaviateClient weaviateClient() {
        Config config = new Config("http", "localhost:8080");
        return new WeaviateClient(config);
    }
}
