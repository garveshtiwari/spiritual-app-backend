package com.garveshtiwari.spiritual_app_backend.intelligence.common.util;

public final class VectorUtils {

    private VectorUtils() {

    }

    public static String toPgVector(
            float[] vector
    ) {

        StringBuilder builder =
                new StringBuilder("[");

        for (int i = 0; i < vector.length; i++) {

            builder.append(vector[i]);

            if (i < vector.length - 1) {

                builder.append(",");
            }
        }

        builder.append("]");

        return builder.toString();
    }
}