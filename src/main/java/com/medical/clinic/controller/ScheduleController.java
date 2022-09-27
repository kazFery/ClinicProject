package com.medical.clinic.controller;

import com.medical.clinic.entity.DoctorAvailableSlot;
import com.medical.clinic.repository.ScheduleRepository;
import com.medical.clinic.service.AvailableSlotWrapper;
import com.medical.clinic.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;


    @GetMapping("/doctor/schedule")
    public String  showSchedule(Model model, Principal principal){
        return scheduleService.showSchedule(model);
    }

    @PostMapping("/doctor/schedule2")
    public String saveSchedule(@ModelAttribute("doctorAvailableSlotWrapper") AvailableSlotWrapper doctorAvailableSlots, Model model, BindingResult result, Principal principal, RedirectAttributes redirAttrs){
        return scheduleService.saveSchedule(doctorAvailableSlots, model, result, principal, redirAttrs) ;
    }
}
