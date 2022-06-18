package com.example.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.entity.UserEntity;
import com.example.repository.UserRepository;
import com.example.shared.Utils;
import com.example.shared.dto.UserDto;

@Service
public class UserService implements UserDetailsService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public List<UserDto> getUsers(int page, int limit) {
		Pageable pageable = PageRequest.of(page, limit);
		Page<UserEntity> usersPage = this.userRepository.findAll(pageable);
		List<UserEntity> users = usersPage.getContent();
		
		List<UserDto> returnValue = new ArrayList<>();
		for (UserEntity userEntity : users) {
			UserDto userDto = new UserDto();
			BeanUtils.copyProperties(userEntity, userDto);
			returnValue.add(userDto);
		}

		return returnValue;
	}
	
	public UserDto getUserByUserId(String userId) {
		UserEntity user = this.userRepository.findByUserId(userId);
		if (user == null) {
			throw new UsernameNotFoundException(userId);
		}

		UserDto returnValue = new UserDto();
		BeanUtils.copyProperties(user, returnValue);
		return returnValue;
	}

	@Transactional
	public UserDto createUser(UserDto userDto) {

		UserEntity storedUserEntity = this.userRepository.findByEmail(userDto.getEmail());
		if (storedUserEntity != null) {
			throw new RuntimeException( userDto.getEmail() + " is already taken." );
		}

		UserEntity userEntity = new UserEntity();
		BeanUtils.copyProperties(userDto, userEntity);
		userEntity.setUserId(Utils.generateUserId());
		userEntity.setEncryptedPassword(this.passwordEncoder.encode(userDto.getPassword()));

		UserEntity createdUserEntity = this.userRepository.save(userEntity);

		UserDto returnValue = new UserDto();
		BeanUtils.copyProperties(createdUserEntity, returnValue);

		return returnValue;
	}

	@Transactional
	public UserDto updateUser(String userId, UserDto userDto) {
		UserEntity userEntity = this.userRepository.findByUserId(userId);
		if (userEntity == null) {
			throw new UsernameNotFoundException(userId);
		}

		userEntity.setFirstName(userDto.getFirstName());
		userEntity.setLastName(userDto.getLastName());

		UserEntity updatedUserEntity = this.userRepository.save(userEntity);

		UserDto returnValue = new UserDto();
		BeanUtils.copyProperties(updatedUserEntity, returnValue);

		return returnValue;
	}

	@Transactional
	public void deleteUser(String userId) {
		UserEntity userEntity = this.userRepository.findByUserId(userId);
		if (userEntity == null) {
			throw new UsernameNotFoundException(userId);
		}

		this.userRepository.delete(userEntity);
	}

	public UserDto getUser(String email) {
		UserEntity user = this.userRepository.findByEmail(email);
		
		if (user == null) {
			throw new UsernameNotFoundException(email);
		}

		UserDto returnValue = new UserDto();
		BeanUtils.copyProperties(user, returnValue);

		return returnValue;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		logger.info("★loadUserByUsername");

		UserEntity user = this.userRepository.findByEmail(email);

		if (user == null) {
			throw new UsernameNotFoundException(email);
		}

		return new User(user.getEmail(), user.getEncryptedPassword(), new ArrayList<>());
	}
}
