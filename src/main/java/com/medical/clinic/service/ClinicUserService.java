package com.medical.clinic.service;

import com.medical.clinic.entity.ClinicUser;
import com.medical.clinic.entity.Role;
import com.medical.clinic.repository.ClinicUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ClinicUserService implements UserDetailsService {


    @Autowired
    private final ClinicUserRepository userRepository;
    @Autowired
    private Validator validator;
    public String showRegistrationForm(Model model) {
        ClinicUser clinicUser = new ClinicUser();
        clinicUser.setRole(Role.PATIENT);
        model.addAttribute("clinicUser", clinicUser);
        return "signup_form";
    }

    public String processRegister(@Valid ClinicUser clinicUser, BindingResult result) {

        if (result.hasErrors()) {
            return "signup_form";
        }

        if (!userRepository.findByUsername(clinicUser.getUsername()).isPresent()) {
            if (clinicUser.getPassword().equals(clinicUser.getPasswordRepeat())) {
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                String encodedPassword = passwordEncoder.encode(clinicUser.getPassword());
                clinicUser.setPassword(encodedPassword);
                clinicUser.setPasswordRepeat(encodedPassword);
                System.out.println(clinicUser.getRole());

            }
            userRepository.save(clinicUser);
            return "login";
        }
        return "signup_form";
    }

    public ModelAndView showUserProfileForm(Principal principal) {
        String username = principal.getName();
        Optional<ClinicUser> currentUser;
        if (userRepository.existsByUsername(username))
            currentUser = userRepository.findByUsername(username);
        else
            throw new UsernameNotFoundException("User not found");

        ModelAndView mav = new ModelAndView("profile_form");
        ClinicUser clinicUser = currentUser.get();

        mav.addObject("clinicUser", clinicUser);
        return mav;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public String processProfile(ClinicUser clinicUser, BindingResult result, Principal principal) {
        String username = principal.getName();
        Optional<ClinicUser> existUser;
        if (userRepository.existsByUsername(username))
            existUser = userRepository.findByUsername(username);
        else
            throw new UsernameNotFoundException("User not found");

        ClinicUser currentUser = existUser.get();

        currentUser.setLastname(clinicUser.getLastname());
        currentUser.setFirstname(clinicUser.getFirstname());
        currentUser.setDOB(clinicUser.getDOB());
        currentUser.setGender(clinicUser.getGender());
        currentUser.setPhoneNo(clinicUser.getPhoneNo());

        if (result.hasErrors()) {
            return "profile_form";
        }
        userRepository.save(currentUser);
        return "index";
    }

    public ModelAndView showAllDoctors() {
        ModelAndView mav = new ModelAndView("admin-dashboard");
        List<ClinicUser> doctors = userRepository.findAllByRole(Role.DOCTOR);
        mav.addObject("doctors", doctors);
        boolean showDoctor = true;
        mav.addObject("showDoctor", showDoctor);
        return mav;
    }

    public ModelAndView showAllPatients() {
        ModelAndView mav = new ModelAndView("admin-dashboard");
        List<ClinicUser> patients = userRepository.findAllByRole(Role.PATIENT);
        boolean showPatient = true;
        mav.addObject("showPatient", showPatient);
        mav.addObject("patients", patients);
        return mav;
    }


    public String deleteDoctor(Long doctorId, RedirectAttributes redirAttrs) {
        userRepository.deleteById(doctorId);
       // redirAttrs.addFlashAttribute("message", "The doctor with id" + doctorId + "deleted successfully");
        return "redirect:/admin/list_doctors";
    }

    public String deletePatient(Long patientId, RedirectAttributes redirAttrs) {
        userRepository.deleteById(patientId);
      //  redirAttrs.addFlashAttribute("message", "The patient with id" + patientId + "deleted successfully");
        return "redirect:/admin/list_patients";
    }

    public ModelAndView showUpdateForm(Long userId){
        ModelAndView mav = new ModelAndView("profile_doctor");
        ClinicUser clinicUser = userRepository.findById(userId).get();
        mav.addObject("clinicUser", clinicUser);
        return mav;
    }


    public String saveUpdateForm(ClinicUser clinicUser, BindingResult result) {
        Optional<ClinicUser> existUser;
        if (userRepository.existsById(clinicUser.getId()))
            existUser = userRepository.findById(clinicUser.getId());
        else
            throw new UsernameNotFoundException("User not found");

        ClinicUser currentUser = existUser.get();

        currentUser.setLastname(clinicUser.getLastname());
        currentUser.setFirstname(clinicUser.getFirstname());
        currentUser.setDOB(clinicUser.getDOB());
        currentUser.setGender(clinicUser.getGender());
        currentUser.setPhoneNo(clinicUser.getPhoneNo());
        if (result.hasErrors()) {
            return "profile_doctor";
        }
        System.out.println(clinicUser.getEmail());
        userRepository.save(currentUser);
        if (currentUser.getRole().equals(Role.DOCTOR))
            return "redirect:/admin/list_doctors";
        else
            return "redirect:/admin/list_patients";
    }

    public String showDoctorForm(Model model) {
        ClinicUser clinicUser = new ClinicUser();
        clinicUser.setRole(Role.DOCTOR);
        model.addAttribute("clinicUser", clinicUser);
        return "Doctor_form";
    }

    public String processAddDoctor(ClinicUser clinicUser, BindingResult result) {
        if (result.hasErrors()) {
            return "Doctor_form";
        }

        if (!userRepository.findByUsername(clinicUser.getUsername()).isPresent()) {
            if (clinicUser.getPassword().equals(clinicUser.getPasswordRepeat())) {
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                String encodedPassword = passwordEncoder.encode(clinicUser.getPassword());
                clinicUser.setPassword(encodedPassword);
                clinicUser.setPasswordRepeat(encodedPassword);
                System.out.println(clinicUser.getRole());

            }
            userRepository.save(clinicUser);
            return "/admin/list_doctors";
        }
        return "Doctor_form";
    }
}