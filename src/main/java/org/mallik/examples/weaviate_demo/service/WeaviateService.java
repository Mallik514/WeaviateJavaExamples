package org.mallik.examples.weaviate_demo.service;

import io.weaviate.client.WeaviateClient;
import io.weaviate.client.v1.data.model.WeaviateObject;
import io.weaviate.client.v1.schema.model.WeaviateClass;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class WeaviateService {

    private final WeaviateClient weaviateClient;
    public WeaviateService(WeaviateClient weaviateClient) {
        this.weaviateClient = weaviateClient;
    }

    public Boolean createCollection(WeaviateClass weaviateClass) {
        return weaviateClient.schema().classCreator()
                .withClass(weaviateClass)
                .run()
                .getResult();
    }

    public WeaviateClass getCollection(String collectionName) {
        return weaviateClient.schema().classGetter()
                .withClassName(collectionName)
                .run()
                .getResult();
    }

    public Boolean deleteCollection(String collectionName) {
        return weaviateClient.schema().classDeleter()
                .withClassName(collectionName)
                .run()
                .getResult();
    }

    public WeaviateObject insertData(String collectionName, Map<String, Object> dataObject) {
        return weaviateClient.data().creator()
                .withClassName(collectionName)
                .withProperties(dataObject)
                .run()
                .getResult();
    }
}
