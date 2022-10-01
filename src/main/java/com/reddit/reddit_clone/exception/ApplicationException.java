package com.reddit.reddit_clone.exception;

import org.springframework.mail.MailException;

public class ApplicationException extends RuntimeException {
    public ApplicationException(String s) {
        super(s);
    }
}
