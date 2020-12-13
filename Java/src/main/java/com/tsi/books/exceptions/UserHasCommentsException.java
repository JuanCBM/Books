package com.tsi.books.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "User has comments")
public class UserHasCommentsException extends RuntimeException {

    private static final long serialVersionUID = 5093230260891805409L;
}
