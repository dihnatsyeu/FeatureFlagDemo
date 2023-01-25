package com.example.mettle.feature.flag.repository;

import com.example.mettle.feature.flag.model.FeatureFlag;
import java.util.List;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface FeatureFlagRepository  extends ListCrudRepository<FeatureFlag, Long> {

    @Transactional
    List<FeatureFlag> findFeatureFlagByAccount(String account);
}
