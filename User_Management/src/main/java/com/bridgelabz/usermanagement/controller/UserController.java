package com.bridgelabz.usermanagement.controller;


import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.usermanagement.dto.ForgetDto;
import com.bridgelabz.usermanagement.dto.LoginDto;
import com.bridgelabz.usermanagement.dto.RegisterDto;
import com.bridgelabz.usermanagement.dto.SetPasswordDto;
import com.bridgelabz.usermanagement.dto.UpdateDto;
import com.bridgelabz.usermanagement.model.Response;
import com.bridgelabz.usermanagement.model.ResponseLogin;
import com.bridgelabz.usermanagement.service.UserService;

@RestController
public class UserController {
	@Autowired
	UserService service;
	
	
	@PostMapping("register")
	public ResponseEntity<Response> reisterUser(@Valid @RequestBody RegisterDto register) {

		return new ResponseEntity<Response>((service.registerUser(register)), HttpStatus.OK);
	}

	@PutMapping("verification")
	public ResponseEntity<Response> verifyUser(@RequestHeader String token){
		
		return new ResponseEntity<Response>((service.verify(token)),HttpStatus.OK);
	}

	@PutMapping("forgetpassword")
	public ResponseEntity<Response> forgetpassword(@RequestBody ForgetDto forget){
		
		return new ResponseEntity<Response>((service.forgotPassword(forget)),HttpStatus.OK);
	}
	@PutMapping("setpassword")
	public ResponseEntity<Response> setpassword(@RequestHeader String token,@RequestBody SetPasswordDto set){
		
		return new ResponseEntity<Response>((service.setPassword(token,set)),HttpStatus.OK);
	}
	@PostMapping("login")
	public ResponseEntity<ResponseLogin> login(@RequestBody LoginDto login){
		
		return new ResponseEntity<ResponseLogin>((service.loginUser(login)),HttpStatus.OK);
	}
	@GetMapping("userlist")
	public ResponseEntity<Response>userList(){
		
		return new ResponseEntity<Response>((service.getUserList()),HttpStatus.OK);
	}
	@GetMapping("genderlist")
	public ResponseEntity<Response>gender(){
		
		return new ResponseEntity<Response>((service.getGenderList()),HttpStatus.OK);
	}
	@GetMapping("countrylist")
	public ResponseEntity<Response>countryList(){
		
		return new ResponseEntity<Response>((service.getCountryList()),HttpStatus.OK);
	}
	@PutMapping("updateuser")
	public ResponseEntity<Response>updateuser(@RequestHeader String token,@RequestBody UpdateDto update){
		
		return new ResponseEntity<Response>((service.updateUser(token, update)),HttpStatus.OK);
	}
	@PutMapping("logout")
	public ResponseEntity<Response>logout(@RequestHeader String token){
		
		return new ResponseEntity<Response>((service.logoutUser(token)),HttpStatus.OK);
	}
	@GetMapping("agegroup")
	public ResponseEntity<Response>agegroup(){
		
		return new ResponseEntity<Response>((service.getAgeGroup()),HttpStatus.OK);
	}
	@PostMapping("uploadprofile")
	public ResponseEntity<Response>uploadprofile(@RequestHeader String token,@RequestBody MultipartFile img){
		
		return new ResponseEntity<Response>((service.uploadProfile(token, img)),HttpStatus.OK);
	}
	@DeleteMapping("removeprofile")
	public ResponseEntity<Response>removeprofile(@RequestHeader String token){
		
		return new ResponseEntity<Response>((service.removeProfile(token)),HttpStatus.OK);
	}
}
