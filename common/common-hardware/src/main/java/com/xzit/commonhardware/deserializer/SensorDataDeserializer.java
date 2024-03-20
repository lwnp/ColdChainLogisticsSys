package com.xzit.commonhardware.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xzit.commonhardware.entity.SensorData;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

public class SensorDataDeserializer extends JsonDeserializer<SensorData> {

    @Override
    public SensorData deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);

        Long id = node.get("id").asLong();
        Long carId = node.get("carId").asLong();
        Double carbonDioxide = node.get("carbonDioxide").asDouble();
        Double temperature = node.get("temperature").asDouble();
        JsonNode locationNode = node.get("location");
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Double> location = mapper.convertValue(locationNode, Map.class);

        SensorData sensorData = new SensorData();
        sensorData.setId(id);
        sensorData.setCarId(carId);
        sensorData.setCarbonDioxide(carbonDioxide);
        sensorData.setTemperature(temperature);
        sensorData.setLocation(location);

        return sensorData;
    }
}