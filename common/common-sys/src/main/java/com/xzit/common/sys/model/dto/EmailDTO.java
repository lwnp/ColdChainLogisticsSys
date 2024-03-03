package com.xzit.common.sys.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailDTO implements Serializable {
    @Serial
    private static final long serialVersionUID=5L;
    String email;

    String subject;

    String template;

    String content;
}
