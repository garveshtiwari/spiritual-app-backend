package com.garveshtiwari.spiritual_app_backend.journal.controller;

import com.garveshtiwari.spiritual_app_backend.journal.dto.JournalRequest;
import com.garveshtiwari.spiritual_app_backend.journal.dto.JournalResponse;
import com.garveshtiwari.spiritual_app_backend.journal.service.JournalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/journals")
@RequiredArgsConstructor
public class JournalController {

    private final JournalService journalService;

    @PostMapping
    public JournalResponse createJournalEntry(
            @RequestBody JournalRequest request
    ) {

        return journalService.createJournalEntry(
                request
        );
    }

    @GetMapping
    public List<JournalResponse> getJournalEntries() {

        return journalService.getJournalEntries();
    }

    @GetMapping("/{journalId}")
    public JournalResponse getJournalEntry(
            @PathVariable Long journalId
    ) {

        return journalService.getJournalEntry(
                journalId
        );
    }

    @PutMapping("/{journalId}")
    public JournalResponse updateJournalEntry(
            @PathVariable Long journalId,
            @RequestBody JournalRequest request
    ) {

        return journalService.updateJournalEntry(
                journalId,
                request
        );
    }

    @DeleteMapping("/{journalId}")
    public void deleteJournalEntry(
            @PathVariable Long journalId
    ) {

        journalService.deleteJournalEntry(
                journalId
        );
    }
}