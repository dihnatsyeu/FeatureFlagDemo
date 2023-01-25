package com.example.mettle.feature.flag.api.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateFlagRequest {

    @NotEmpty
    private String name;
    private String user;
    private Boolean isEnabled;
    @NotNull
    private Boolean isGlobal;
}


