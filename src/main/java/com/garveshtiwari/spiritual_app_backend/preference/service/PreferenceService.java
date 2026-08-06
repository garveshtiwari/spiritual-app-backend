package com.garveshtiwari.spiritual_app_backend.preference.service;

import com.garveshtiwari.spiritual_app_backend.preference.dto.BookPreferenceRequest;
import com.garveshtiwari.spiritual_app_backend.preference.dto.BookPreferenceResponse;
import com.garveshtiwari.spiritual_app_backend.preference.dto.PreferenceRequest;
import com.garveshtiwari.spiritual_app_backend.preference.dto.PreferenceResponse;

import java.util.List;

public interface PreferenceService {

    PreferenceResponse createPreference(
            PreferenceRequest request
    );

    PreferenceResponse getPreference();

    PreferenceResponse updatePreference(
            PreferenceRequest request
    );

    BookPreferenceResponse addPreferredBook(
            BookPreferenceRequest request
    );

    void removePreferredBook(
            Long bookId
    );

    List<BookPreferenceResponse> getPreferredBooks();
}