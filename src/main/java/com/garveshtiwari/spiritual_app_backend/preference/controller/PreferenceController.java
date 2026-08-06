package com.garveshtiwari.spiritual_app_backend.preference.controller;

import com.garveshtiwari.spiritual_app_backend.preference.dto.BookPreferenceRequest;
import com.garveshtiwari.spiritual_app_backend.preference.dto.BookPreferenceResponse;
import com.garveshtiwari.spiritual_app_backend.preference.dto.PreferenceRequest;
import com.garveshtiwari.spiritual_app_backend.preference.dto.PreferenceResponse;
import com.garveshtiwari.spiritual_app_backend.preference.service.PreferenceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/preferences")
@RequiredArgsConstructor
public class PreferenceController {

    private final PreferenceService preferenceService;

    @PostMapping
    public PreferenceResponse createPreference(
            @Valid @RequestBody PreferenceRequest request
    ) {

        return preferenceService.createPreference(
                request
        );
    }

    @GetMapping
    public PreferenceResponse getPreference() {

        return preferenceService.getPreference();
    }

    @PutMapping
    public PreferenceResponse updatePreference(
            @Valid @RequestBody PreferenceRequest request
    ) {

        return preferenceService.updatePreference(
                request
        );
    }

    @PostMapping("/books")
    public BookPreferenceResponse addPreferredBook(
            @Valid @RequestBody BookPreferenceRequest request
    ) {

        return preferenceService.addPreferredBook(
                request
        );
    }

    @DeleteMapping("/books/{bookId}")
    public void removePreferredBook(
            @PathVariable Long bookId
    ) {

        preferenceService.removePreferredBook(
                bookId
        );
    }

    @GetMapping("/books")
    public List<BookPreferenceResponse> getPreferredBooks() {

        return preferenceService.getPreferredBooks();
    }
}