package com.garveshtiwari.spiritual_app_backend
        .intelligence.context.preference;

import com.garveshtiwari.spiritual_app_backend
        .intelligence.context.ContextBuilder;
import com.garveshtiwari.spiritual_app_backend
        .preference.entity.UserPreference;
import com.garveshtiwari.spiritual_app_backend
        .preference.repository.UserPreferenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PreferenceContextBuilder
        implements ContextBuilder {

    private final UserPreferenceRepository
            preferenceRepository;

    @Override
    public String buildContext(
            Long userId,
            Long conversationId,
            String userMessage
    ) {

        UserPreference preference =
                preferenceRepository
                        .findByUserId(userId)
                        .orElse(null);

        if (preference == null) {
            return "";
        }

        return """
                User preferences:

                Language: %s
                Theme: %s
                Notifications enabled: %s
                Reminder time: %s
                Sampradaya: %s
                Voice type: %s

                """
                .formatted(
                        preference.getLanguage(),
                        preference.getTheme(),
                        preference.getNotificationsEnabled(),
                        preference.getReminderTime(),
                        preference.getSampradaya(),
                        preference.getVoiceType()
                );
    }
}