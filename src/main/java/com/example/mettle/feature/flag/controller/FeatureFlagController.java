package com.example.mettle.feature.flag.controller;

import com.example.mettle.feature.flag.api.request.CreateFlagRequest;
import com.example.mettle.feature.flag.model.FeatureFlag;
import com.example.mettle.feature.flag.services.FeatureFlagService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/feature_flag")
@RequiredArgsConstructor
@Slf4j
public class FeatureFlagController {

    private final FeatureFlagService featureFlagService;

    @GetMapping
    public List<FeatureFlag> getFeatureFlags() {
        return featureFlagService.listFeatureFlags().stream().filter(
                featureFlag -> featureFlag
                        .getIsEnabled() && (featureFlag.getIsGlobal() || featureFlag.getAccount().equals(
                        SecurityContextHolder.getContext().getAuthentication().getName()))).toList();
    }

    @GetMapping("/{account}")
    public List<FeatureFlag> getFeatureFlag(@NotBlank @PathVariable String account) {
        return featureFlagService.listFeatureFlags(account).stream().filter(FeatureFlag::getIsEnabled).toList();

    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public FeatureFlag createFeatureFlag(@Valid @RequestBody CreateFlagRequest createFlagRequest) {
        log.info("Request to create a flag {}", createFlagRequest);
        var featureFlag = RequestTransformer.extractFromRequest(createFlagRequest);
        return featureFlagService.createFlag(featureFlag);
    }

    private static class RequestTransformer {

        private static FeatureFlag extractFromRequest(CreateFlagRequest createFlagRequest) {
            return FeatureFlag.builder()
                    .name(createFlagRequest.getName())
                    .account(createFlagRequest.getUser())
                    .isEnabled(createFlagRequest.getIsEnabled())
                    .isGlobal(createFlagRequest.getIsGlobal())
            .build();
        }

    }
}
