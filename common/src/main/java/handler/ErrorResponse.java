package handler;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public record ErrorResponse(Integer code, String message) {
}
