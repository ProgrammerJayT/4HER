package com.forher;

import android.content.Context;

public class RegistrationForm {

    private static String regName;
    private static String regSurname;
    private static String regAddress;
    private static String regEmail;
    private static String regPhone;
    private static String regPassword;
    private Context regContext;

    public RegistrationForm(String regName, String regSurname, String regAddress, String regEmail, String regPhone, String regPassword, String regConfirm, Context regContext) {
        RegistrationForm.regName = regName;
        RegistrationForm.regSurname = regSurname;
        RegistrationForm.regAddress = regAddress;
        RegistrationForm.regEmail = regEmail;
        RegistrationForm.regPhone = regPhone;
        RegistrationForm.regPassword = regPassword;
        this.regContext = regContext;
    }

    public RegistrationForm() {
    }

    public String getRegName() {
        return regName;
    }

    public void setRegName(String regName) {
        RegistrationForm.regName = regName;
    }

    public String getRegSurname() {
        return regSurname;
    }

    public void setRegSurname(String regSurname) {
        RegistrationForm.regSurname = regSurname;
    }

    public String getRegAddress() {
        return regAddress;
    }

    public void setRegAddress(String regAddress) {
        RegistrationForm.regAddress = regAddress;
    }

    public String getRegEmail() {
        return regEmail;
    }

    public void setRegEmail(String regEmail) {
        RegistrationForm.regEmail = regEmail;
    }

    public String getRegPhone() {
        return regPhone;
    }

    public void setRegPhone(String regPhone) {
        RegistrationForm.regPhone = regPhone;
    }

    public String getRegPassword() {
        return regPassword;
    }

    public void setRegPassword(String regPassword) {
        RegistrationForm.regPassword = regPassword;
    }

    public Context getRegContext() {
        return regContext;
    }

    public void setRegContext(Context regContext) {
        this.regContext = regContext;
    }
}
