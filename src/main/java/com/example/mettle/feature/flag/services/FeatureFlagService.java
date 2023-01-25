package com.example.mettle.feature.flag.services;

import com.example.mettle.feature.flag.exceptions.FeatureFlagCreateException;
import com.example.mettle.feature.flag.model.FeatureFlag;
import com.example.mettle.feature.flag.repository.FeatureFlagRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class FeatureFlagService {

    private final FeatureFlagRepository repository;

    public List<FeatureFlag> listFeatureFlags() {
        return repository.findAll();
    }

    @PreAuthorize("#account == authentication.principal.username")
    public List<FeatureFlag> listFeatureFlags(String account) {
        return repository.findFeatureFlagByAccount(account);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public FeatureFlag createFlag(FeatureFlag featureFlag) {
        try {
            var savedEntity = repository.save(featureFlag);
            return repository.findById(savedEntity.getId()).orElseThrow(() -> new FeatureFlagCreateException("Created"
                    + " entity with id " + savedEntity.getId() + " is not found"));
        } catch (Exception e ) {
            log.error("Error while saving an entity {} to the persistence layer", featureFlag, e);
            throw new FeatureFlagCreateException("Cannot create an feature flag due to the error "
                    + featureFlag, e);
        }
    }
}
