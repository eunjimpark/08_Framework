package com.kh.test.customer.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@NoArgsConstructor
@ToString
@Builder
@AllArgsConstructor
public class Customer {

	   private int 		customerNo;
	   private String 	customerName;
	   private String 	cutomerPhone;
	   private String 	customerAddress;
}
