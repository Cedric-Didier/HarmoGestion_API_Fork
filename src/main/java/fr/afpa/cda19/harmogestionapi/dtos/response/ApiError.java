package fr.afpa.cda19.harmogestionapi.dtos.response;

import java.time.LocalDateTime;
import java.util.Map;

public record ApiError(LocalDateTime timestamp, int status, String message,
                       Map<String, String> details) {

}
