package com.xzit.commonhardware.serializer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.xzit.commonhardware.entity.SensorData;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SenorDataDeserializer extends StdDeserializer<SensorData> {
    public SenorDataDeserializer() {
        this(null);
    }
    protected SenorDataDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public SensorData deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JacksonException {
        JsonNode node = jp.getCodec().readTree(jp);
        Double speed=node.get("speed").asDouble();
        Double temperature=node.get("temperature").asDouble();
        JsonNode gasNode=node.get("gasComposition");
        Map<String,Double> gasComposition=new HashMap<>();
        gasNode.fields().forEachRemaining(entry -> gasComposition.put(entry.getKey(), entry.getValue().asDouble()));
        String location=node.get("location").asText();
        return SensorData.builder()
                .speed(speed)
                .gasComposition(gasComposition)
                .location(location)
                .temperature(temperature)
                .build();
    }
}
