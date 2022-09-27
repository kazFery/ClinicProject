package com.medical.clinic.service;

import com.medical.clinic.entity.DoctorAvailableSlot;
import com.medical.clinic.entity.Slot;
import com.medical.clinic.repository.AppointmentRepository;
import com.medical.clinic.repository.ScheduleRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class AppointmentService {

    @Autowired
    private final AppointmentRepository appointmentRepository;

    @Autowired
    private final ScheduleRepository scheduleRepository;


    public String getAppointmentForm(Model model) {
        boolean showSearch = false;

        Date  date = new Date();
        model.addAttribute("date", date);
        return "appointment-search-form";
    }

    public List<DoctorAvailableSlot> searchAvailableSlot(Date wantedDate) {
        List<DoctorAvailableSlot> das = scheduleRepository.findByDateAndIsAvailable(wantedDate);
        return das;
    }

    public String showsearchAppointmentForm(Model model, String date) throws ParseException {
        System.out.println(date);
        Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(date);
        List<DoctorAvailableSlot> das = searchAvailableSlot(date1);
        boolean showSearch = false;
        if (!das.isEmpty()) {
            model.addAttribute("availableDoctors", das);
            showSearch = true;
        }
        model.addAttribute("showSearch", showSearch);
        model.addAttribute("availableDoctors", das);
        return "appointment-search-form";
    }

}
