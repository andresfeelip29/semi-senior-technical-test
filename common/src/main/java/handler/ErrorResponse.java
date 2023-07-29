package handler;


import lombok.Builder;

@Builder
public record ErrorResponse(Integer code, String message) {
}
