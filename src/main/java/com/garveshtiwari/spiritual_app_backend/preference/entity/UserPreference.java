package com.garveshtiwari.spiritual_app_backend.preference.entity;

import com.garveshtiwari.spiritual_app_backend.common.enums.AppLanguage;
import com.garveshtiwari.spiritual_app_backend.common.enums.AppTheme;
import com.garveshtiwari.spiritual_app_backend.common.enums.Sampradaya;
import com.garveshtiwari.spiritual_app_backend.common.enums.VoiceType;
import com.garveshtiwari.spiritual_app_backend.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "user_preferences")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPreference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(
            name = "user_id",
            nullable = false,
            unique = true
    )
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AppLanguage language;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AppTheme theme;

    @Column(
            name = "notifications_enabled",
            nullable = false
    )
    private Boolean notificationsEnabled;

    @Column(name = "reminder_time")
    private LocalTime reminderTime;

    @Enumerated(EnumType.STRING)
    private Sampradaya sampradaya;

    @Enumerated(EnumType.STRING)
    @Column(name = "voice_type")
    private VoiceType voiceType;

    @Column(
            name = "created_at",
            nullable = false
    )
    private LocalDateTime createdAt;

    @Column(
            name = "updated_at",
            nullable = false
    )
    private LocalDateTime updatedAt;
}
