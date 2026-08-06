package com.garveshtiwari.spiritual_app_backend.preference.mapper;

import com.garveshtiwari.spiritual_app_backend.preference.dto.PreferenceRequest;
import com.garveshtiwari.spiritual_app_backend.preference.dto.PreferenceResponse;
import com.garveshtiwari.spiritual_app_backend.preference.entity.UserPreference;
import org.springframework.stereotype.Component;

@Component
public class PreferenceMapper {

    public PreferenceResponse toResponse(
            UserPreference userPreference
    ) {

        return PreferenceResponse.builder()
                .id(userPreference.getId())
                .language(userPreference.getLanguage())
                .theme(userPreference.getTheme())
                .notificationsEnabled(
                        userPreference.getNotificationsEnabled()
                )
                .reminderTime(
                        userPreference.getReminderTime()
                )
                .sampradaya(
                        userPreference.getSampradaya()
                )
                .voiceType(
                        userPreference.getVoiceType()
                )
                .build();
    }

    public void updateEntity(
            PreferenceRequest request,
            UserPreference entity
    ) {

        entity.setLanguage(request.getLanguage());
        entity.setTheme(request.getTheme());
        entity.setNotificationsEnabled(
                request.getNotificationsEnabled()
        );
        entity.setReminderTime(
                request.getReminderTime()
        );
        entity.setSampradaya(
                request.getSampradaya()
        );
        entity.setVoiceType(
                request.getVoiceType()
        );
    }
}