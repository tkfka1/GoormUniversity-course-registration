package com.finalproject.excption;

public class InvalidSigninInformation extends UniversityException {

    public static final String MESSAGE = "아이디 또는 비밀번호가 틀렸습니다.";

    public InvalidSigninInformation(){
        super(MESSAGE);
    }


    @Override
    public int getStatusCode() {
        return 400;
    }
}
