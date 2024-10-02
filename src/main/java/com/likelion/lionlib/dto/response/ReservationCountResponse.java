package com.likelion.lionlib.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonNaming(value = PropertyNamingStrategy.class)
public class ReservationCountResponse {
    private Long count;

    public static ReservationCountResponse fromEntity(Long reservCount) {
        return ReservationCountResponse.builder()
                .count(reservCount)
                .build();
    }
}
