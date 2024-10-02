package com.likelion.lionlib.exception;

public class ProfileNotFountException extends RuntimeException{

    public ProfileNotFountException() {
        super("Profile을 찾을 수 없습니다.");
    }

    public ProfileNotFountException(Long profileId) {
        super("Profile: " + profileId + " 를 찾을 수 없습니다.");
    }
}
