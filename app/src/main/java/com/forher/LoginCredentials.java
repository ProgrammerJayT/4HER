package com.forher;

import android.content.Context;

public class LoginCredentials {

    private static String email;
    private static String password;
    private Context context;

    public LoginCredentials() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        LoginCredentials.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        LoginCredentials.password = password;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public LoginCredentials(String email, String password, Context context) {
        LoginCredentials.email = email;
        LoginCredentials.password = password;
        this.context = context;
    }
}
