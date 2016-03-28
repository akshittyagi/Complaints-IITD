package com.example.akty7.assignmenttwo.HelperClass;

import android.app.Application;

import java.net.CookieHandler;
import java.net.CookieManager;

/**
 * Created by akty7 on 29-Mar-16.
 */
public class Cookie extends Application{



    CookieHandler cookieManage;
    public void onCreate() {
        cookieManage= new CookieManager();
        CookieHandler.setDefault(cookieManage);
        super.onCreate();
    }

}
