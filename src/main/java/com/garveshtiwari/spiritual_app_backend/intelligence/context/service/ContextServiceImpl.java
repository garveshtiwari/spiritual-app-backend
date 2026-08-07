package com.garveshtiwari.spiritual_app_backend.intelligence.context.service;

import com.garveshtiwari.spiritual_app_backend
        .intelligence.context.ContextBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContextServiceImpl
        implements ContextService {

    private final List<ContextBuilder>
            contextBuilders;

    @Override
    public String buildContext(
            Long userId
    ) {

        StringBuilder context =
                new StringBuilder();

        for (ContextBuilder builder :
                contextBuilders) {

            String result =
                    builder.buildContext(
                            userId
                    );

            if (result != null &&
                    !result.isBlank()) {

                context.append(result);
                context.append("\n");
            }
        }

        return context.toString();
    }
}