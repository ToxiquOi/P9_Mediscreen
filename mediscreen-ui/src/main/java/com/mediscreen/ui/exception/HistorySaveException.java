package com.mediscreen.ui.exception;

public class HistorySaveException extends Exception {
    public HistorySaveException(String historyNotAdded) {
        super(historyNotAdded);
    }
}
