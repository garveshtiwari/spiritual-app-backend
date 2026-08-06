package com.garveshtiwari.spiritual_app_backend.preference.dto;

import com.garveshtiwari.spiritual_app_backend.common.enums.AppLanguage;
import com.garveshtiwari.spiritual_app_backend.common.enums.AppTheme;
import com.garveshtiwari.spiritual_app_backend.common.enums.Sampradaya;
import com.garveshtiwari.spiritual_app_backend.common.enums.VoiceType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
public class PreferenceRequest {

    private AppLanguage language;

    private AppTheme theme;

    private Boolean notificationsEnabled;

    private LocalTime reminderTime;

    private Sampradaya sampradaya;

    private VoiceType voiceType;
}