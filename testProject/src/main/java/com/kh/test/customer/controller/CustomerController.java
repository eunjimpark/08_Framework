package com.kh.test.customer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kh.test.customer.model.dto.Customer;
import com.kh.test.customer.model.service.CustomerService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class CustomerController {
	
	private final CustomerService service;
	

	/** 고객추가
	 * @param inputCustomer : 입력된 회원 정보
	 * 			(CustomerName, CustomerTel, CustomerAddress)
	 * 
	 * @param memberAddress : 입력한 주소 input 3개의 값을 배열로 전달
	 * 
	 * @param ra : 리다이렉트 시 request scope로 데이터 전달하는 객체
	 * @return
	 */
	@PostMapping("plus")
	public String plus(Customer inputCustomer, 
			RedirectAttributes ra) {
		
		int result = service.plus(inputCustomer);
		
		String path = null;
		String message = null;
		
		if(result >0) {
			message = inputCustomer.getCustomerName()+"고객님 추가 성공!!!";
			path="/";
		}
		
		ra.addFlashAttribute("message", message);
		return "redirect:"+path;
	
		
	}

	
	

}
