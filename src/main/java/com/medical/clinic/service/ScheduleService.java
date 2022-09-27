package com.medical.clinic.service;

import com.medical.clinic.entity.ClinicUser;
import com.medical.clinic.entity.DoctorAvailableSlot;
import com.medical.clinic.entity.Slot;
import com.medical.clinic.repository.ClinicUserRepository;
import com.medical.clinic.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private ClinicUserRepository userRepository;

    public String showSchedule(Model model){
        ArrayList<DoctorAvailableSlot>  doctorAvailableSlots = new ArrayList<DoctorAvailableSlot>();

        for (int i = 0; i<18 ; i++) {
            doctorAvailableSlots.add(new DoctorAvailableSlot());
            doctorAvailableSlots.get(i).setDate(new Date());
            for (Slot s : Slot.values()) {
                if (s.getSlotNo() == i+1) {
                    doctorAvailableSlots.get(i).setSlot(s);
                    doctorAvailableSlots.get(i).setDate(new Date());
                    break;
                }

            }
        }
        AvailableSlotWrapper  doctorAvailableSlotWrapper = new AvailableSlotWrapper();
        doctorAvailableSlotWrapper.setdoctorAvailableSlotList(doctorAvailableSlots);
        model.addAttribute("doctorAvailableSlotWrapper", doctorAvailableSlotWrapper);
        return "doctor-set-schedule";
    }

//    public String saveSchedule( @ModelAttribute("doctorAvailableSlots") List<DoctorAvailableSlot> doctorAvailableSlots, BindingResult result, Principal principal) {
//
//        if (result.hasErrors()) {
//            return "doctor-set-schedule";
//        }
//
//        scheduleRepository.saveAll(doctorAvailableSlots);
//        return "index";
//    }

    public String saveSchedule(@ModelAttribute("doctorAvailableSlotWrapper")  AvailableSlotWrapper doctorAvailableSlots, Model mode, BindingResult result, Principal principal, RedirectAttributes redirAttrs) {

        String username = principal.getName();
        Optional<ClinicUser> existUser;
        if (userRepository.existsByUsername(username))
            existUser = userRepository.findByUsername(username);
        else
            throw new UsernameNotFoundException("User not found");

        ClinicUser currentDoctor = existUser.get();
        for (int i = 0; i < doctorAvailableSlots.size(); i++) {
            doctorAvailableSlots.get(i).setDoctor(currentDoctor);
        }

        if (result.hasErrors()) {
            return "doctor-set-schedule";
        }

        for (int i = 0; i <doctorAvailableSlots.size() ; i++) {
            // handel reptation record by seting PK
            Optional<DoctorAvailableSlot> das = scheduleRepository.findByDateAndSlotAndDoctorId(doctorAvailableSlots.get(i).getDate(),
                    doctorAvailableSlots.get(i).getSlot());
                  //  doctorAvailableSlots.get(i).getDoctor().getId());

            if (das.isPresent()){
                doctorAvailableSlots.get(i).setId(das.get().getId());
            }
            scheduleRepository.save(doctorAvailableSlots.get(i));

        }
        redirAttrs.addFlashAttribute("message", "You have saved your schedule successfully");
        return "redirect:/doctor/schedule";
        
        
    }
}
