package com.bridgelabz.usermanagement.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.usermanagement.dto.ForgetDto;
import com.bridgelabz.usermanagement.dto.LoginDto;
import com.bridgelabz.usermanagement.dto.RegisterDto;
import com.bridgelabz.usermanagement.dto.SetPasswordDto;
import com.bridgelabz.usermanagement.dto.UpdateDto;
import com.bridgelabz.usermanagement.exception.CustomException;
import com.bridgelabz.usermanagement.model.LoginHistory;
import com.bridgelabz.usermanagement.model.Response;
import com.bridgelabz.usermanagement.model.ResponseLogin;
import com.bridgelabz.usermanagement.model.User;
import com.bridgelabz.usermanagement.repository.UserRepository;
import com.bridgelabz.usermanagement.utility.CommanLogFile;
import com.bridgelabz.usermanagement.utility.TokenUtility;
import com.bridgelabz.usermanagement.utility.Utility;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	ModelMapper mapper;
	@Autowired
	PasswordEncoder encoder;
	@Autowired
	UserRepository repository;
	@Autowired
	TokenUtility tokenUtility;
	@Autowired
	Utility utility;

	@Override
	public Response registerUser(RegisterDto register) {
		Optional<User> user1 = repository.findByUserName(register.getUserName());
		if (user1.isPresent())
			throw new CustomException.UserEmailAlreadyExist(CommanLogFile.User_already);
		User user = mapper.map(register, User.class);
		user.setPassword(encoder.encode(register.getPassword()));
		String verifyLink = tokenUtility.generateToken(register.getEmail());
		utility.sendNotification(verifyLink);
		repository.save(user);
		return new Response(200, CommanLogFile.User_registered, user);

	}

	@Override
	public Response verify(String token) {
		Optional<User> user = repository.findByEmail(TokenUtility.generateUserFromToken(token));
		if (!(user.isPresent()))
			throw new CustomException.UserEmailAlreadyExist(CommanLogFile.User_already);

		user.get().setVerifyStatus(true);
		repository.save(user.get());
		return new Response(200, CommanLogFile.User_verified, user.get());

	}

	@Override
	public ResponseLogin loginUser(LoginDto login) {
		Optional<User> user = repository.findByUserName(login.getUserName());
		if (!(user.isPresent()))
			throw new CustomException.UserNotFound(CommanLogFile.User_Not_Found);
		if (!(user.get().isVerifyStatus()))
			throw new CustomException.UserNotVerified(CommanLogFile.Not_verified);
		if (!encoder.matches(login.getPassword(), user.get().getPassword()))
			throw new CustomException.Password(CommanLogFile.incorrect_password);
		String token = tokenUtility.generateToken(login.getUserName());

		LoginHistory loginhistory = new LoginHistory();
		loginhistory.setLoginDate(Utility.CurrentDateTime());
		loginhistory.setUser(user.get());
		user.get().setLastAccessDate(Utility.CurrentDateTime());
		user.get().setStatus(true);
		user.get().getLoginList().add(loginhistory);
		repository.save(user.get());
		return new ResponseLogin(200, CommanLogFile.User_login, user.get(), token);
	}

	@Override
	public Response forgotPassword(ForgetDto forget) {
		Optional<User> user = repository.findByUserName(forget.getUserName());
		if (!(user.isPresent()))
			throw new CustomException.UserNotFound(CommanLogFile.User_Not_Found);
		String verifyLink = tokenUtility.generateToken(user.get().getEmail());
		utility.sendNotification(verifyLink);
		return new Response(200, CommanLogFile.User_mail, true);
	}

	@Override
	public Response setPassword(String token, SetPasswordDto set) {

		Optional<User> user = repository.findByEmail(TokenUtility.generateUserFromToken(token));
		if (!(user.isPresent()))
			throw new CustomException.UserNotFound(CommanLogFile.User_Not_Found);
		if (!(set.getPassword().equals(set.getRetypePassword())))
			throw new CustomException.UserNotFound(CommanLogFile.password_not_matched);
		User user1 = mapper.map(set, User.class);
		user1.setPassword(encoder.encode(user.get().getPassword()));
		repository.save(user1);
		return new Response(200, CommanLogFile.passwordChnge, true);
	}

	@Override
	public Response updateUser(String token, UpdateDto update) {
		String name = TokenUtility.generateUserFromToken(token);
		Optional<User> user1 = repository.findByUserName(name);
		System.out.println(user1.get().getEmail());
		if (!user1.isPresent())
			throw new CustomException.UserNotFound(CommanLogFile.User_Not_Found);
		if (!user1.get().isVerifyStatus())
			throw new CustomException.UserNotVerified(CommanLogFile.Not_verified);
		if (!user1.get().isStatus())
			throw new CustomException.UserNotVerified(CommanLogFile.not_active);
		user1.get().setAddress(update.getAddress());
		user1.get().setCountry(update.getCountry());
		user1.get().setDoB(update.getDoB());
		user1.get().setEmail(update.getEmail());
		user1.get().setGender(update.getGender());
		user1.get().setLastName(update.getLastName());
		user1.get().setMiddleName(update.getMiddleName());
		user1.get().setFirstName(update.getFirstName());
		user1.get().setPhone(update.getPhone());
		if (user1.get().getUserRole().equalsIgnoreCase("admin")) {
			user1.get().setPassword(encoder.encode(update.getPassword()));
			user1.get().setUserRole(update.getUserRole());
		}
		repository.save(user1.get());
		return new Response(200, CommanLogFile.user_updated, user1.get());
	}

	@Override
	public Response getUserList() {
		return new Response(200, CommanLogFile.List_user, repository.findAll());
	}

	@Override
	public Response getCountryList() {
		int count = 0;
		Map<String, Integer> countryName = new HashMap<String, Integer>();
		List<String> countrys = new ArrayList<String>();
		for (User user : repository.findAll()) {
			countrys.add(user.getCountry().toLowerCase());
		}
		List<String> uniqueCon = countrys.stream().sorted().collect(Collectors.toList());
		String x = uniqueCon.get(0);
		for (int i = 0; i < uniqueCon.size(); i++) {
			if (x.compareTo(uniqueCon.get(i)) == 0) {
				count++;
			} else {
				countryName.put(x, count);
				x = uniqueCon.get(i);
				i--;
				count = 0;
			}
		}
		countryName.put(x, count);
		Set<Map.Entry<String, Integer>> set = countryName.entrySet();
		ArrayList<Map.Entry<String, Integer>> listOfEntry = new ArrayList<Map.Entry<String, Integer>>(set);
		System.out.println(listOfEntry);
		return new Response(200, CommanLogFile.List_country, listOfEntry);
	}

	@Override
	public Response getGenderList() {
		int count = 0;
		Map<String, Integer> countryName = new HashMap<String, Integer>();
		List<String> genders = new ArrayList<String>();
		for (User user : repository.findAll()) {
			genders.add(user.getGender().toLowerCase());
		}
		List<String> uniqueCon = genders.stream().sorted().collect(Collectors.toList());

		String x = uniqueCon.get(0);
		for (int i = 0; i < uniqueCon.size(); i++) {
			if (x.compareTo(uniqueCon.get(i)) == 0) {
				count++;
			} else {
				countryName.put(x, count);
				x = uniqueCon.get(i);
				i--;
				count = 0;
			}
		}
		countryName.put(x, count);
		Set<Map.Entry<String, Integer>> set = countryName.entrySet();
		ArrayList<Map.Entry<String, Integer>> listOfEntry = new ArrayList<Map.Entry<String, Integer>>(set);
		System.out.println(listOfEntry);

//		genders.stream().distinct().collect(Collectors.toList());
//		List<String> uniqueCon = countrys.stream().distinct().collect(Collectors.toList());
//		System.out.println(uniqueCon);
		return new Response(200, CommanLogFile.List_Gender, listOfEntry);
	}

	@Override
	public Response getAgeGroup() {
		List<LocalDate> dobList = new ArrayList<LocalDate>();
		Map<String, Integer> ageGroup = new HashMap<String, Integer>();
		List<Long> ageList=new ArrayList<Long>();
		for(User user : repository.findAll()) {
			dobList.add(LocalDate.parse(user.getDoB()));
		}
        LocalDate endDate =(LocalDate.now()); 
		for(LocalDate startdate : dobList) {
			  ageList.add(ChronoUnit.YEARS.between(startdate, endDate)); 
		}
		
		
		
//		int a=0,b=0,c=0,d=0,e=0,k=0;
//		for (Long p: ageList) {
//			Long dob = p;
//			 if (dob != null) {
//			  if (dob < 18) {
//				  a++;
//			  } else if (dob < 20) {
//			   b++;
//			  } else if (dob < 30) {
//			   c++;
//			  } else if (dob < 40) {
//			   d++;
//			  } else if (dob < 50) {
//			   e++;
//			  } else {
//				  k++;
//				 }
		

		return new Response(200, CommanLogFile.List_Gender, ageList);
	}

	@Override
	public Response uploadProfile(String token, MultipartFile pic) {
		String name = TokenUtility.generateUserFromToken(token);
		Optional<User> user1 = repository.findByUserName(name);
		System.out.println(user1.get().getEmail());
		if (!user1.isPresent())
			throw new CustomException.UserNotFound(CommanLogFile.User_Not_Found);
		if (!user1.get().isVerifyStatus())
			throw new CustomException.UserNotVerified(CommanLogFile.Not_verified);
		if (!user1.get().isStatus())
			throw new CustomException.UserNotVerified(CommanLogFile.not_active);
		Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap("cloud_name", "dru7aoosj", "api_key",
				"488438116612752", "api_secret", "5ECajyTujyG7wCbS3yceMpSbZNY"));

		// Normalize file name
		User usernew = user1.get();
		String fileName = StringUtils.cleanPath(pic.getOriginalFilename());
		System.out.println(fileName);
//		fileName = emaiId + fileName;
		try {
			// Copy file to the target location (Replacing existing file with the same name)
			Path getPath = Paths.get(
					"/home/admin115/Documents/workspace-sts-3.9.10.RELEASE/zombie/FundooUser/src/main/resources/profiles");
			Path targetLocation = getPath.resolve(fileName);
			Files.copy(pic.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

			File toUpload = new File(targetLocation.toString());// upload on cloudinary
			@SuppressWarnings("rawtypes")
			Map uploadResult = cloudinary.uploader().upload(toUpload, ObjectUtils.emptyMap());

			usernew.setProfilePicture(uploadResult.get("secure_url").toString());
			System.out.println(usernew);
			repository.save(usernew);
			return new Response(200, "Profile pic uploaded", uploadResult.get("secure_url").toString());
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		return new Response(400, CommanLogFile.Failed_profile, false);
	}

	@Override
	public Response removeProfile(String token) {
		String name = TokenUtility.generateUserFromToken(token);
		Optional<User> user = repository.findByUserName(name);
		if (!user.isPresent())
			throw new CustomException.UserNotFound(CommanLogFile.User_Not_Found);
		if (!user.get().isVerifyStatus())
			throw new CustomException.UserNotVerified(CommanLogFile.Not_verified);
		if (!user.get().isStatus())
			throw new CustomException.UserNotVerified(CommanLogFile.not_active);
		String fileLocation = user.get().getProfilePicture();
		File file = new File(fileLocation);
		file.delete();
		user.get().setProfilePicture(null);
		repository.save(user.get());
		return new Response(200, CommanLogFile.Remove_profile, true);
	}

	@Override
	public Response logoutUser(String token) {
		Optional<User> user = repository.findByUserName(TokenUtility.generateUserFromToken(token));
		if (!user.isPresent())
			throw new CustomException.UserNotFound("User not found");
		if (!user.get().isVerifyStatus())
			throw new CustomException.UserNotVerified("Not verified user");
		if (!user.get().isStatus())
			throw new CustomException.UserNotVerified("Not active user");

		user.get().setStatus(false);
		repository.save(user.get());
		return new Response(200, CommanLogFile.LogOut, true);
	}

	@Override
	public Response registrationHistory(String token) {
		// TODO Auto-generated method stub
		return null;
	}

}
