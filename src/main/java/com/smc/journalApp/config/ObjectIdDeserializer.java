package com.smc.journalApp.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.bson.types.ObjectId;

import java.io.IOException;

public class ObjectIdDeserializer extends StdDeserializer<ObjectId> {

    public ObjectIdDeserializer() {
        this(null);
    }

    public ObjectIdDeserializer(Class<?> vc) {
        super(vc);
    }

//    @Override
//    public ObjectId deserialize(JsonParser jsonParser, DeserializationContext ctx) throws IOException {
//        String id = jsonParser.getText();
//        return new ObjectId(id);
//    }
    @Override
    public ObjectId deserialize(JsonParser jsonParser, DeserializationContext ctx) throws IOException {
        String id = jsonParser.getText();
        if (id == null || id.isEmpty() || id.length() != 24) {
            // Return null or throw custom exception if preferred
            return null;
        }
        return new ObjectId(id);
    }

}
