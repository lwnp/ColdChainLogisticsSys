package com.xzit.common.user.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.xzit.common.user.model.dto.UserDetailsDTO;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserDetailsDTODeserializer extends StdDeserializer<UserDetailsDTO> {

    public UserDetailsDTODeserializer() {
        this(null);
    }

    protected UserDetailsDTODeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public UserDetailsDTO deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);

        Long id = node.get("id").asLong();
        String username = node.get("username").asText();
        String password = node.get("password").asText();
        Long userInfoId = node.get("userInfoId").asLong();
        Boolean isDisable = node.get("isDisable").asBoolean();
        List<String> roles = new ArrayList<>();
        JsonNode rolesNode = node.get("roles");
        if (rolesNode != null && rolesNode.isArray()) {
            for (JsonNode role : rolesNode) {
                roles.add(role.asText());
            }
        }

        UserDetailsDTO userDetailsDTO = new UserDetailsDTO();
        userDetailsDTO.setId(id);
        userDetailsDTO.setUsername(username);
        userDetailsDTO.setPassword(password);
        userDetailsDTO.setUserInfoId(userInfoId);
        userDetailsDTO.setIsDisable(isDisable);
        userDetailsDTO.setRoles(roles);

        return userDetailsDTO;
    }
}
