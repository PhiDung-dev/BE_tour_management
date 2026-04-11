package com.example.BE_tour_management.service;

import com.example.BE_tour_management.dto.request.UserCreateRequest;
import com.example.BE_tour_management.dto.request.UserUpdateRequest;
import com.example.BE_tour_management.dto.response.UserResponse;
import com.example.BE_tour_management.entity.Account;
import com.example.BE_tour_management.entity.User;
import com.example.BE_tour_management.exception.AppException;
import com.example.BE_tour_management.exception.ErrorCode;
import com.example.BE_tour_management.mapper.UserMapper;
import com.example.BE_tour_management.repository.AccountRepository;
import com.example.BE_tour_management.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {

    UserRepository userRepository;
    AccountRepository accountRepository;
    UserMapper userMapper;

    public UserResponse createUser(UserCreateRequest request) {
        Account account = accountRepository.findById(request.getAccountId()).orElseThrow(()->new AppException(ErrorCode.ACCOUNT_NOT_FOUND));
        User user = userMapper.toUser(request);
        if(account.getUser()!=null){
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        user.setAccount(account);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public List<UserResponse> readUsers() {
        return userMapper.toUserResponseList(userRepository.findAll());
    }

    public UserResponse readUser(String id) {
        return userMapper.toUserResponse(userRepository.findById(id).orElseThrow(()->new AppException(ErrorCode.USER_NOT_FOUND)));
    }

    public UserResponse updateUser(String id, UserUpdateRequest request) {
        User user = userRepository.findById(id).orElseThrow(()->new AppException(ErrorCode.USER_NOT_FOUND));
        userMapper.updateUser(user, request);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public void deleteUser(String id) {
        if(!userRepository.existsById(id)) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }
        userRepository.deleteById(id);
    }

}
