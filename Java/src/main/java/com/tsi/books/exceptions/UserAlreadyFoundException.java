package com.tsi.books.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "User nick used")
public class UserAlreadyFoundException extends RuntimeException {

    private static final long serialVersionUID = 6600084908618284957L;
}
