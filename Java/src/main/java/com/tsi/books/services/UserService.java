package com.tsi.books.services;

import com.tsi.books.dtos.requests.UserRequestDto;
import com.tsi.books.dtos.responses.CommentDetailsResponseDto;
import com.tsi.books.dtos.responses.UserResponseDto;
import com.tsi.books.models.User;

import java.util.Collection;

public interface UserService {
    UserResponseDto findById(Long id);

    User findByNick(String nick);

    UserResponseDto save(UserRequestDto user);

    UserResponseDto updateMail(Long id, UserRequestDto user);

    UserResponseDto deleteById(Long id);

    Collection<CommentDetailsResponseDto> findCommentsById(Long id);

    Collection<UserResponseDto> findAll();
}
