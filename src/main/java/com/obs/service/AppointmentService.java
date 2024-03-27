package com.obs.service;

import java.util.List;

import com.obs.domain.Appointment;

public interface AppointmentService {
	Appointment createAppointment(Appointment appointment);

    List<Appointment> findAll();

    Appointment findAppointment(Long id);

    void confirmAppointment(Long id);

	List<Appointment> getAppointments(Long userId);
}
