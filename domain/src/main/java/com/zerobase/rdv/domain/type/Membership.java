package com.zerobase.rdv.domain.type;

public enum Membership {
    BASIC {
        @Override
        public String toString() {
            return "ANONYMOUS";
        }
    },
    CUSTOMER {
        @Override
        public String toString() {
            return "CUSTOMER";
        }
    },
    BUSINESS {
        @Override
        public String toString() {
            return "BUSINESS";
        }
    },

    PARTNER {
        @Override
        public String toString() {
            return "PARTNER";
        }
    },

    MANAGER {
        @Override
        public String toString() {
            return "MANAGER";
        }
    }
}
