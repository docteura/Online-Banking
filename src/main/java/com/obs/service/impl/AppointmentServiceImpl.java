package com.obs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.obs.entity.Appointment;
import com.obs.repository.AppointmentRepository;
import com.obs.service.AppointmentService;

@Service
public class AppointmentServiceImpl implements AppointmentService {

	@Autowired
	private AppointmentRepository appointmentDao;

	public Appointment createAppointment(Appointment appointment) {
		return appointmentDao.save(appointment);
	}

	public List<Appointment> findAll() {
		return appointmentDao.findAll();
	}

	public Appointment findAppointment(Long id) {
		return null;
	}

	public void confirmAppointment(Long id) {
		Appointment appointment = findAppointment(id);
		appointment.setConfirmed(true);
		appointmentDao.save(appointment);
	}

	@Override
	public List<Appointment> getAppointments(Long userId) {
		return appointmentDao.findByUserId(userId);
	}
}
