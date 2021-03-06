package com.xml.organvlasti.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.xmldb.api.base.XMLDBException;

import com.xml.organvlasti.dto.JwtResponseDTO;
import com.xml.organvlasti.dto.LoginDTO;
import com.xml.organvlasti.dto.UserDTO;
import com.xml.organvlasti.model.TUser;
import com.xml.organvlasti.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	public UserService userService;

	@RequestMapping(value = "/register", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin
	public @ResponseBody ResponseEntity<Boolean> register(@RequestBody UserDTO dto) throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException{
		boolean result = userService.register(dto);
		if (result) {
			return new ResponseEntity<Boolean>(result, HttpStatus.OK);
		}
		return new ResponseEntity<Boolean>(result, HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin
	public @ResponseBody ResponseEntity<JwtResponseDTO> login(@RequestBody LoginDTO dto) {
		JwtResponseDTO jwt = userService.logIn(dto);
		if (jwt != null) {
			return new ResponseEntity<JwtResponseDTO>(jwt, HttpStatus.OK);
		}
		return new ResponseEntity<JwtResponseDTO>(jwt, HttpStatus.BAD_REQUEST);
	}
}
