package com.obs.controller;

import java.security.Principal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.obs.entity.Appointment;
import com.obs.entity.User;
import com.obs.service.AppointmentService;
import com.obs.service.UserService;

@Controller
@RequestMapping("/appointment")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public String listAppointment(Model model, Principal principal) {
    	 User user = userService.findByUsername(principal.getName());
        List<Appointment> appointments = appointmentService.getAppointments(user.getUserId());
        model.addAttribute("appointments", appointments);
        model.addAttribute("dateString", "");

        return "appointments";
    }
    
    @GetMapping("/create")
    public String createAppointment(Model model) {
        Appointment appointment = new Appointment();
        model.addAttribute("appointment", appointment);
        model.addAttribute("dateString", "");

        return "appointment";
    }

    @PostMapping("/create")
    public String createAppointmentPost(@ModelAttribute("appointment") Appointment appointment, @ModelAttribute("dateString") String date, Model model, Principal principal) throws ParseException {

        appointment.setDate(new Date());

        User user = userService.findByUsername(principal.getName());
        appointment.setUser(user);

        appointmentService.createAppointment(appointment);

        return "redirect:/appointment/list";
    }


}
