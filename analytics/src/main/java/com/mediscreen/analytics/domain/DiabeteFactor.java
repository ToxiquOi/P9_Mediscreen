package com.mediscreen.analytics.domain;

import lombok.Getter;

@Getter
public enum DiabeteFactor {
    HEMO_AC1("Hémoglobine AC1"),
    MICROALBUMINE("Microalbumine"),
    TAILLE("Taille"),
    POIDS("Poids"),
    FUMEUR("Fumeur"),
    ANORMAL("Anormal"),
    CHOLESTEROL("Cholestérol"),
    VERTIGE("Vertige"),
    RECHUTE("Rechute"),
    REACTION("Réaction"),
    ANTICORPS("Anticorps");

    private final String value;

    DiabeteFactor(String value) {
        this.value = value;
    }
}
