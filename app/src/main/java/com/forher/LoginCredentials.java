package com.forher;

import android.content.Context;

public class LoginCredentials {

    private String email;
    private String password;
    private Context context;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public LoginCredentials(String email, String password, Context context) {
        this.email = email;
        this.password = password;
        this.context = context;
    }
}
