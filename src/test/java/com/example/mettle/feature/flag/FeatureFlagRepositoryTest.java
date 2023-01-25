package com.example.mettle.feature.flag;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.example.mettle.feature.flag.model.FeatureFlag;
import com.example.mettle.feature.flag.repository.FeatureFlagRepository;
import com.example.mettle.feature.flag.utils.RandomDataFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;



public class FeatureFlagRepositoryTest extends BaseFeatureFlagTest {

    @Autowired
    private FeatureFlagRepository repository;

    @Test
    public void givenAllFieldEntity_WhenEntitySaveRequest_ThenEntitiySaved() {
        var featureFlag = FeatureFlag.builder()
                .name(RandomDataFactory.randomString())
                .account("user1")
                .isEnabled(true)
                .isGlobal(true)
                .build();
        var savedEntity = repository.save(featureFlag);

        var foundFlag = repository.findById(savedEntity.getId());
        assertThat(foundFlag.isPresent()).isTrue();
        assertThat(foundFlag.get()).isEqualTo(featureFlag);
    }

    @Test
    public void givenDefaultEntitiesFieldEntity_WhenEntitySaveRequest_ThenEntitySavedWithDefaults() {
        var featureFlag = FeatureFlag.builder()
                .name(RandomDataFactory.randomString())
                .account("user1")
                .build();
        var savedEntity = repository.save(featureFlag);

        var foundFlag = repository.findById(savedEntity.getId());
        assertThat(foundFlag.isPresent()).isTrue();
        assertThat(foundFlag.get().getIsGlobal()).isEqualTo(false);
        assertThat(foundFlag.get().getIsEnabled()).isEqualTo(false);
    }

    @Test
    public void givenTwoSameEntities_WhenEntitiesSaveRequestSend_ThenUniqueConstraintFired() {
        var featureFlag = FeatureFlag.builder()
                .name("Unique Name")
                .account("user1")
                .build();
        var savedEntity = repository.save(featureFlag);

        var featureFlag2 = FeatureFlag.builder()
                .name("Unique Name")
                .account("user1")
                .build();
        assertThatThrownBy(() -> repository.save(featureFlag2)).isInstanceOf(Exception.class);


    }

    @Test
    public void givenEntityWithUser_WhenEntitiesSearchByUser_ThenRecordIsReturned() {
        var featureFlag = FeatureFlag.builder()
                .name(RandomDataFactory.randomString())
                .account("user1")
                .isEnabled(true)
                .isGlobal(true)
                .build();
        repository.save(featureFlag);
        var foundEntities = repository.findFeatureFlagByAccount("user1");

        assertThat(foundEntities.size()).isEqualTo(1);
        assertThat(foundEntities.get(0)).isEqualTo(featureFlag);


    }
}
