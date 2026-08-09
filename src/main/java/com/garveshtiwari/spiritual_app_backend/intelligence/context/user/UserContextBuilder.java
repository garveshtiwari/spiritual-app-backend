package com.garveshtiwari.spiritual_app_backend
        .intelligence.context.user;

import com.garveshtiwari.spiritual_app_backend
        .intelligence.context.ContextBuilder;
import com.garveshtiwari.spiritual_app_backend
        .user.entity.User;
import com.garveshtiwari.spiritual_app_backend
        .user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserContextBuilder
        implements ContextBuilder {

    private final UserRepository
            userRepository;

    @Override
    public String buildContext(
            Long userId,
            Long conversationId,
            String userMessage
    ) {

        User user =
                userRepository
                        .findById(userId)
                        .orElse(null);

        if (user == null) {
            return "";
        }

        return """
                User information:

                First name: %s
                Last name: %s
                Role: %s

                """
                .formatted(
                        user.getFirstName(),
                        user.getLastName(),
                        user.getRole()
                );
    }
}