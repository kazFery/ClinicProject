package com.medical.clinic.repository;

import com.medical.clinic.entity.DoctorAvailableSlot;
import com.medical.clinic.entity.Slot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<DoctorAvailableSlot, Long> {



   // @Query("SELECT u FROM DoctorAvailableSlot u WHERE u.doctor_id = ?1 AND u.date = ?2")
    List<DoctorAvailableSlot> findAllByDateAndDoctorId(Long doctorID, Date date);

    @Query("SELECT u FROM DoctorAvailableSlot u WHERE  u.date = ?1 AND u.slot = ?2")
    Optional<DoctorAvailableSlot>  findByDateAndSlotAndDoctorId(Date date, Slot slot);

    @Query("SELECT u FROM DoctorAvailableSlot u WHERE  u.date = ?1 AND u.isAvailable = TRUE")
    List<DoctorAvailableSlot> findByDateAndIsAvailable(Date date);
}
