package com.garveshtiwari.spiritual_app_backend
        .intelligence.exception;

public class AiResponseParsingException
        extends AiException {

    public AiResponseParsingException(
            String message
    ) {

        super(message);
    }

    public AiResponseParsingException(
            String message,
            Throwable cause
    ) {

        super(
                message,
                cause
        );
    }
}