package com.example.digitallibrary.models.enums;

public enum TransactionStatus {
    PENDING,//intermediate state - can be success or failed
    SUCCESS,//terminal state
    FAILED//terminal state
}
