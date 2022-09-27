package com.medical.clinic.entity;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@DynamicUpdate
@Table(name = "users")
@Data
@NoArgsConstructor
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date appointmentDate;

    @Enumerated(EnumType.STRING)
    private Slot appointmentSlot;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctorId")
    private ClinicUser doctor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patientId")
    private ClinicUser patient;

    @Enumerated(EnumType.STRING)
    private  AppointmentStatus  status;
}
