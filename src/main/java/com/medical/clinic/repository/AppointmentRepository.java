package com.medical.clinic.repository;

import com.medical.clinic.entity.Appointment;
import com.medical.clinic.entity.ClinicUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepository  extends JpaRepository<Appointment, Long> {

    List<Appointment> findByPatient(ClinicUser patient);
}
