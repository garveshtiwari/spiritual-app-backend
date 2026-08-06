package com.garveshtiwari.spiritual_app_backend.preference.service;

import com.garveshtiwari.spiritual_app_backend.book.entity.Book;
import com.garveshtiwari.spiritual_app_backend.book.repository.BookRepository;
import com.garveshtiwari.spiritual_app_backend.common.exception.BadRequestException;
import com.garveshtiwari.spiritual_app_backend.common.exception.ResourceNotFoundException;
import com.garveshtiwari.spiritual_app_backend.preference.dto.BookPreferenceRequest;
import com.garveshtiwari.spiritual_app_backend.preference.dto.BookPreferenceResponse;
import com.garveshtiwari.spiritual_app_backend.preference.dto.PreferenceRequest;
import com.garveshtiwari.spiritual_app_backend.preference.dto.PreferenceResponse;
import com.garveshtiwari.spiritual_app_backend.preference.entity.UserPreference;
import com.garveshtiwari.spiritual_app_backend.preference.entity.UserPreferredBook;
import com.garveshtiwari.spiritual_app_backend.preference.mapper.BookPreferenceMapper;
import com.garveshtiwari.spiritual_app_backend.preference.mapper.PreferenceMapper;
import com.garveshtiwari.spiritual_app_backend.preference.repository.UserPreferenceRepository;
import com.garveshtiwari.spiritual_app_backend.preference.repository.UserPreferredBookRepository;
import com.garveshtiwari.spiritual_app_backend.user.entity.User;
import com.garveshtiwari.spiritual_app_backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PreferenceServiceImpl implements PreferenceService {

    private final UserRepository userRepository;

    private final BookRepository bookRepository;

    private final UserPreferenceRepository userPreferenceRepository;

    private final UserPreferredBookRepository userPreferredBookRepository;

    private final PreferenceMapper preferenceMapper;

    private final BookPreferenceMapper bookPreferenceMapper;

    private User getCurrentUser() {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return userRepository
                .findByEmail(email)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "User not found."
                        )
                );
    }

    @Override
    public PreferenceResponse createPreference(
            PreferenceRequest request
    ) {

        User user = getCurrentUser();

        if (userPreferenceRepository.existsByUserId(user.getId())) {
            throw new BadRequestException(
                    "Preference already exists."
            );
        }

        UserPreference preference = UserPreference.builder()
                .user(user)
                .language(request.getLanguage())
                .theme(request.getTheme())
                .notificationsEnabled(
                        request.getNotificationsEnabled()
                )
                .reminderTime(request.getReminderTime())
                .sampradaya(request.getSampradaya())
                .voiceType(request.getVoiceType())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        preference = userPreferenceRepository.save(preference);

        return preferenceMapper.toResponse(preference);
    }

    @Override
    public PreferenceResponse getPreference() {

        User user = getCurrentUser();

        UserPreference preference = userPreferenceRepository
                .findByUserId(user.getId())
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Preference not found."
                        )
                );

        return preferenceMapper.toResponse(preference);
    }

    @Override
    public PreferenceResponse updatePreference(
            PreferenceRequest request
    ) {

        User user = getCurrentUser();

        UserPreference preference = userPreferenceRepository
                .findByUserId(user.getId())
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Preference not found."
                        )
                );

        preferenceMapper.updateEntity(
                request,
                preference
        );

        preference.setUpdatedAt(LocalDateTime.now());

        preference = userPreferenceRepository.save(preference);

        return preferenceMapper.toResponse(preference);
    }

    @Override
    public BookPreferenceResponse addPreferredBook(
            BookPreferenceRequest request
    ) {

        User user = getCurrentUser();

        UserPreference preference = userPreferenceRepository
                .findByUserId(user.getId())
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Preference not found."
                        )
                );

        Book book = bookRepository
                .findById(request.getBookId())
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Book not found."
                        )
                );

        boolean exists = userPreferredBookRepository
                .existsByUserPreferenceIdAndBookId(
                        preference.getId(),
                        book.getId()
                );

        if (exists) {
            throw new BadRequestException(
                    "Book already exists."
            );
        }

        UserPreferredBook userPreferredBook =
                UserPreferredBook.builder()
                        .userPreference(preference)
                        .book(book)
                        .createdAt(LocalDateTime.now())
                        .build();

        userPreferredBook =
                userPreferredBookRepository.save(
                        userPreferredBook
                );

        return bookPreferenceMapper.toResponse(
                userPreferredBook
        );
    }

    @Override
    public void removePreferredBook(
            Long bookId
    ) {

        User user = getCurrentUser();

        UserPreference preference = userPreferenceRepository
                .findByUserId(user.getId())
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Preference not found."
                        )
                );

        UserPreferredBook userPreferredBook =
                userPreferredBookRepository
                        .findByUserPreferenceIdAndBookId(
                                preference.getId(),
                                bookId
                        )
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "Preferred book not found."
                                )
                        );

        userPreferredBookRepository.delete(
                userPreferredBook
        );
    }

    @Override
    public List<BookPreferenceResponse> getPreferredBooks() {

        User user = getCurrentUser();

        UserPreference preference = userPreferenceRepository
                .findByUserId(user.getId())
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Preference not found."
                        )
                );

        return userPreferredBookRepository
                .findByUserPreferenceId(
                        preference.getId()
                )
                .stream()
                .map(bookPreferenceMapper::toResponse)
                .toList();
    }
}