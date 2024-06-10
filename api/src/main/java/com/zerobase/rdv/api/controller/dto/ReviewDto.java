package com.zerobase.rdv.api.controller.dto;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class ReviewDto {
    public static class In {
        @NotNull
        private final Long reservationId;
        private String text;

        public In(Long reservationId, String text) {
            this.reservationId = reservationId;
            this.text = text;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public Long getReservationId() {
            return reservationId;
        }
    }

    public static class Out {
        @NotNull
        private final Long reviewId;
        @NotNull
        private final LocalDateTime lastModifiedTime;

        public Out(Long reviewId, LocalDateTime lastModifiedTime) {
            this.reviewId = reviewId;
            this.lastModifiedTime = lastModifiedTime;
        }

        public Long getReviewId() {
            return reviewId;
        }

        public LocalDateTime getLastModifiedTime() {
            return lastModifiedTime;
        }
    }

}
