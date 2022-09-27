package com.medical.clinic.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "available_slot", uniqueConstraints=
@UniqueConstraint(columnNames = {"date", "slot", "doctor_Id"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorAvailableSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    @Enumerated(EnumType.STRING)
    private Slot slot;

    private boolean isAvailable = false;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_Id")
    private ClinicUser doctor;

    public boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(boolean isAvailable) {
       this.isAvailable = isAvailable;
    }
}

