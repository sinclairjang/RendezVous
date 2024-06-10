package com.zerobase.rdv.domain.type;

public enum ReservationResult {
    CONFIRMED("예약 확정"),
    TIME_OUT("예약 확정를 시도했으나 시간 지연으로 인해 실패");

    ReservationResult(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    private final String resultMessage;

    public String getResultMessage() {
        return resultMessage;
    }
}
