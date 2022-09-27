package com.medical.clinic.controller;

import com.medical.clinic.entity.Appointment;
import com.medical.clinic.repository.AppointmentRepository;
import com.medical.clinic.repository.ClinicUserRepository;
import com.medical.clinic.service.AppointmentService;
import com.medical.clinic.service.ScheduleService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.security.RolesAllowed;
import java.text.ParseException;
import java.util.Date;

@Controller
@AllArgsConstructor
public class AppointmentController {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private AppointmentService appointmentService;

    @RolesAllowed({ "ROLE_ADMIN", "ROLE_PATIENT" })
    @GetMapping("/patient/appointments")
    public String ShowAppointmentFrom(Model model) {
        return  appointmentService.getAppointmentForm(model);
    }


    @RolesAllowed({ "ROLE_ADMIN", "ROLE_PATIENT" })
    @PostMapping("/patient/appointments")
    public String getSearch(Model model, @RequestParam(name = "date") String wantedDate) throws ParseException {
        return  appointmentService.showsearchAppointmentForm(model, wantedDate);
    }
}
