package com.garveshtiwari.spiritual_app_backend
        .intelligence.context.bookmark;

import com.garveshtiwari.spiritual_app_backend
        .bookmark.entity.Bookmark;
import com.garveshtiwari.spiritual_app_backend
        .bookmark.repository.BookmarkRepository;
import com.garveshtiwari.spiritual_app_backend
        .intelligence.context.ContextBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BookmarkContextBuilder
        implements ContextBuilder {

    private static final int LIMIT = 5;

    private final BookmarkRepository bookmarkRepository;

    @Override
    public String buildContext(
            Long userId
    ) {

        List<Bookmark> bookmarks =
                bookmarkRepository
                        .findByUserIdOrderByCreatedAtDesc(
                                userId
                        );

        if (bookmarks.isEmpty()) {
            return "";
        }

        StringBuilder context =
                new StringBuilder();

        context.append("""
                Recent bookmarks:

                """);

        int count = Math.min(
                LIMIT,
                bookmarks.size()
        );

        for (int i = 0; i < count; i++) {

            Bookmark bookmark = bookmarks.get(i);

            context.append(
                    "Verse ID: "
            ).append(
                    bookmark.getVerse().getId()
            ).append(
                    "\n"
            );

            if (bookmark.getNote() != null) {

                context.append(
                        "Note: "
                ).append(
                        bookmark.getNote()
                ).append(
                        "\n"
                );
            }

            context.append("\n");
        }

        return context.toString();
    }
}