package com.example.quizappmasster.core.util;

import android.util.Log;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;

public class ObjectMapperUtils {
    private static ObjectMapper objectMapper = new ObjectMapper();

    private ObjectMapperUtils() {
    }

    public static <D, T> D stringToDTO(final String entity, Class<D> outClass) {
        try {
            return objectMapper.readValue(entity, outClass);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("error", e.getMessage());
        }
        return null;
    }

    public static <D, T> D stringToTypeReference(final String entitys, TypeReference<D> typeReference) {
        try {
            return objectMapper.readValue(entitys, typeReference);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("error", e.getMessage());
        }
        return null;
    }

    public static String dtoToString(final Object dto) {
        try {
            return objectMapper.writeValueAsString(dto);
        } catch (IOException e) {
            Log.e("error", e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public static <D, T> D dtoToMap(final Object dto, TypeReference<Map<String, String>> outClass) {
            return objectMapper.convertValue(dto, outClass);
    }
}
