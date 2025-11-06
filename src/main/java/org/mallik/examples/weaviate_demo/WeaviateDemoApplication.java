package org.mallik.examples.weaviate_demo;

import io.weaviate.client.Config;
import io.weaviate.client.WeaviateClient;
import io.weaviate.client.base.Result;
import io.weaviate.client.v1.schema.model.DataType;
import io.weaviate.client.v1.schema.model.Property;
import io.weaviate.client.v1.schema.model.WeaviateClass;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class WeaviateDemoApplication {

	public static void main(String[] args) {

        SpringApplication.run(WeaviateDemoApplication.class, args);
        String collectionName = "Products";
        // Define collection properties
        Property titleProperty = Property.builder()
                .name("name")
                .description("Title Property Description...")
                .dataType(Arrays.asList(DataType.TEXT))
                .build();
        Property bodyProperty = Property.builder()
                .name("description")
                .description("Body Property Description...")
                .dataType(Arrays.asList(DataType.TEXT))
                .build();
        Property priceProperty = Property.builder()
                .name("price")
                .description("Body Property Description...")
                .dataType(Arrays.asList(DataType.TEXT))
                .build();
        Property imageProperty = Property.builder()
                .name("image")
                .description("Body Property Description...")
                .dataType(Arrays.asList(DataType.BLOB))
                .build();
        Property videoProperty = Property.builder()
                .name("video")
                .description("Body Property Description...")
                .dataType(Arrays.asList(DataType.BLOB))
                .build();

// Add the defined properties to the collection
        WeaviateClass articleCollection = WeaviateClass.builder()
                .className(collectionName)
                .description(" collection Description...")
                .properties(Arrays.asList(titleProperty, bodyProperty, priceProperty, imageProperty, videoProperty))
                .build();


        Result<Boolean> result = client.schema().classCreator()
                .withClass(articleCollection)
                .run();
	}

}
