package com.medical.clinic.controller;

import com.medical.clinic.constraint.Others;
import com.medical.clinic.constraint.Registration;
import com.medical.clinic.entity.ClinicUser;
import com.medical.clinic.entity.Role;
import com.medical.clinic.entity.Specialization;
import com.medical.clinic.service.ClinicUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.security.Principal;

@Controller
public class ClinicUserController {

    @Autowired
    ClinicUserService  clinicUserService;


    @GetMapping({"/", "/index"})
    public String getIndex() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        // redirect in post handler, upon successful authentication, plus show a Flash message confirming
        return  "login";
    }

    @RequestMapping("/patientDashboard")
    public String userDashboard() {
        return "patient-dashboard";
    }

    @RequestMapping("/doctorDashboard")
    public ModelAndView doctorrDashboard() {
        return new ModelAndView("doctor-dashboard");}

    @RequestMapping("/adminDashboard")
    public ModelAndView adminDashboard() {
        return new ModelAndView("admin-dashboard");
    }

    @GetMapping("/login_err")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        return clinicUserService.showRegistrationForm(model);
    }

    @PostMapping("/process_register")
    public String processRegister(@Validated(Registration.class)  ClinicUser clinicUser, BindingResult result) {
        return clinicUserService.processRegister(clinicUser, result);
    }
    //@RolesAllowed({ "ROLE_ADMIN", "ROLE_PATIENT" })
    @GetMapping("/profile")
    public ModelAndView showPatientProfileForm( Principal principal) {
        return clinicUserService.showUserProfileForm(principal);
    }

    //@RolesAllowed({ "ROLE_ADMIN", "ROLE_PATIENT" })
    @PostMapping("/process_profile")
    public String saveProfile(@Validated(Others.class)@ModelAttribute("clinicUser") ClinicUser clinicUser, BindingResult result, Principal principal) {
        return clinicUserService.processProfile( clinicUser, result, principal);
    }

    @RolesAllowed({ "ROLE_ADMIN" })
    @GetMapping("/admin/list_doctors")
    public ModelAndView getAllDoctors() {
        return clinicUserService.showAllDoctors();
    }

    @RolesAllowed({ "ROLE_ADMIN" })
    @GetMapping("/admin/list_patients")
    public ModelAndView getAllPatients() {
        return clinicUserService.showAllPatients();
    }
    @RolesAllowed({ "ROLE_ADMIN" })
    @GetMapping("/admin/deleteDoctor")
    public String deleteDoctor(@RequestParam Long doctorId,  RedirectAttributes redirAttrs){
        return clinicUserService.deleteDoctor(doctorId, redirAttrs);
    }

    @RolesAllowed({ "ROLE_ADMIN" })
    @GetMapping("/admin/deletePatient")
    public String deletePatient(@RequestParam Long patientId,  RedirectAttributes redirAttrs){
        return clinicUserService.deletePatient(patientId, redirAttrs);
    }

    @RolesAllowed({ "ROLE_ADMIN" })
    @GetMapping("/admin/updateUser/{id}")
    public ModelAndView showUpdateForm(@PathVariable Long id){
       return clinicUserService.showUpdateForm(id);
    }

    @RolesAllowed({ "ROLE_ADMIN" })
    @PostMapping("/admin/updateUser")
    public String saveUpdateForm(@Validated(Others.class) @ModelAttribute("clinicUser") ClinicUser clinicUser, BindingResult result){
        return clinicUserService.saveUpdateForm(clinicUser, result);
    }

    @RolesAllowed({ "ROLE_ADMIN" })
    @GetMapping("/adddoctor")
    public String showDoctorForm(Model model) {
        return clinicUserService.showDoctorForm(model);
    }


    @RolesAllowed({ "ROLE_ADMIN" })
    @PostMapping("/adddoctor")
    public String processAddDoctor(@Validated(Registration.class)  ClinicUser clinicUser, BindingResult result) {
        return clinicUserService.processAddDoctor(clinicUser, result);
    }

    @GetMapping("/member-status")
    public String memberStatus(){
        return "member-status";
    }



}
