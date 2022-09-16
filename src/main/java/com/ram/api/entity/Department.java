package com.ram.api.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.Range;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Department {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@NotEmpty(message = "Department Name should be provided!!!")
	private String name;
	
	@NotEmpty(message = "Department Address should be provided!!!")
	private String address;
	
	@Range(min = 1, message = "Valid Pincode should be provided!!!")
	@Positive(message = "Should be Number!!!")
	private int pincode;
}
