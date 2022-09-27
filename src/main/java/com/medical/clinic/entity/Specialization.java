package com.medical.clinic.entity;

public enum Specialization {
    CARDILOGIST("Cardiologist"),
    IMMUNOLOGIST("Immunologist"),
    DERMATOLOGIST("Dermatologist"),
    FAMILY_PHYSICIAN("Family Physician");

    private String specialization;
    Specialization(String  spec) {
        this.specialization = spec;
    }

    public String getSpecialization() {
        return specialization;
    }

    @Override
    public String toString() {
        return  specialization;

    }
}
