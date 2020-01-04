package com.bridgelabz.usermanagement.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.usermanagement.dto.ForgetDto;
import com.bridgelabz.usermanagement.dto.LoginDto;
import com.bridgelabz.usermanagement.dto.RegisterDto;
import com.bridgelabz.usermanagement.dto.SetPasswordDto;
import com.bridgelabz.usermanagement.dto.UpdateDto;
import com.bridgelabz.usermanagement.model.Response;
import com.bridgelabz.usermanagement.model.ResponseLogin;
import com.bridgelabz.usermanagement.model.User;
import com.bridgelabz.usermanagement.utility.CommanLogFile;

public interface UserService {

	public Response registerUser(RegisterDto register);
	public ResponseLogin loginUser(LoginDto login);
	public Response forgotPassword(ForgetDto forget);
	public Response setPassword(String token,SetPasswordDto set);
	public Response getUserList();
	public Response getCountryList();
	public Response getGenderList();
	public Response getAgeGroup();
	public Response uploadProfile(String token, MultipartFile pic);
	public Response removeProfile(String token);
	public Response logoutUser(String token);
	public Response verify(String token);
	public Response updateUser(String token,UpdateDto update);
	public Response registrationHistory(String token);
	
	
	
	
}
