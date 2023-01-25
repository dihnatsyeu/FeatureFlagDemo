package com.example.mettle.feature.flag;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;

import com.example.mettle.feature.flag.utils.RandomDataFactory;
import com.example.mettle.feature.flag.utils.SecurityContextFiller;

import com.example.mettle.feature.flag.model.FeatureFlag;
import com.example.mettle.feature.flag.repository.FeatureFlagRepository;
import com.example.mettle.feature.flag.services.FeatureFlagService;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.access.AccessDeniedException;

public class FeatureFlagServiceTest extends BaseFeatureFlagTest {

    @Autowired
    private FeatureFlagService flagService;

    @MockBean
    private FeatureFlagRepository repository;

    @Test
    public void givenEntityToSave_WhenSaveRequestCalled_ThenEntityIsSaved() {
        SecurityContextFiller.setContextWithRoles("user1", "ROLE_ADMIN");
        var featureFlag = FeatureFlag.builder()
                .name(RandomDataFactory.randomString())
                .account("user1")
                .isEnabled(true)
                .isGlobal(true)
                .build();

        Mockito.when(repository.save(featureFlag)).thenReturn(featureFlag);
        Mockito.when(repository.findById(any())).thenReturn(Optional.of(featureFlag));

        var foundFlag = flagService.createFlag(featureFlag);
        assertThat(foundFlag).isEqualTo(featureFlag);

    }

    @Test
    public void givenEntityToSave_WhenSaveRequestCalledWithoutPermission_ThenExceptionIsThrown() {
        SecurityContextFiller.setContextWithRoles("user1", "ROLE_USER");
        var featureFlag = FeatureFlag.builder()
                .name("test name")
                .account("user1")
                .isEnabled(true)
                .isGlobal(true)
                .build();

        Mockito.when(repository.save(featureFlag)).thenReturn(featureFlag);
        Mockito.when(repository.findById(any())).thenReturn(Optional.of(featureFlag));

        assertThatThrownBy(() ->flagService.createFlag(featureFlag)).isInstanceOf(AccessDeniedException.class);
    }

    @Test
    public void givenTwoEntites_WhenSearchIsCalled_ThenAllRecordsreturned() {
        SecurityContextFiller.setContextWithRoles("user1", "ROLE_USER");
        var featureFlag1 = FeatureFlag.builder()
                .name(RandomDataFactory.randomString())
                .account("user1")
                .isEnabled(true)
                .isGlobal(true)
                .build();

        var featureFlag2 = FeatureFlag.builder()
                .name(RandomDataFactory.randomString())
                .account("user1")
                .isEnabled(true)
                .isGlobal(true)
                .build();

        Mockito.when(repository.findAll()).thenReturn(List.of(featureFlag1, featureFlag2));

        var foundFlags = flagService.listFeatureFlags();

        assertThat(foundFlags.size()).isEqualTo(2);
        assertThat(foundFlags).isEqualTo(List.of(featureFlag1, featureFlag2));
    }

    @Test
    public void givenEntityWithUser_WhenSearchIsMadeByUsername_ThenRecordReturned() {
        SecurityContextFiller.setContextWithRoles("user2", "ROLE_USER");
        var featureFlag = FeatureFlag.builder()
                .name("test name")
                .account("user2")
                .isEnabled(true)
                .isGlobal(true)
                .build();
        Mockito.when(repository.findFeatureFlagByAccount("user2")).thenReturn(List.of(featureFlag));

        var foundFlags = flagService.listFeatureFlags("user2");

        assertThat(foundFlags.size()).isEqualTo(1);
        assertThat(foundFlags.get(0)).isEqualTo(featureFlag);
    }
}
