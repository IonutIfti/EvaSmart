package com.ii.onlinemarket.evasmart.user.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public enum CartStatus {

    OPEN("Open"),
    CHECKED_OUT("Checked Out"),
    EXPIRED("Expired");

    private final String displayName;
    CartStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
