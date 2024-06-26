package com.obs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.obs.entity.Appointment;

@Repository
public interface AppointmentRepository extends CrudRepository<Appointment, Long> {

	List<Appointment> findAll();

	@Query(value = "select * from appointment where user_id =?1", nativeQuery = true)
	List<Appointment> findByUserId(Long userId);
}
