package com.vannguyen.SpringBootProject.configurations.permissions;

public class Permission {
    public static final String[] NONE_PERMISSIONS = {
            "/",
    };
    public static final String[] READ_PERMISSIONS = {
            "/account", "/account/**",
            "/category", "/category/**",
            "/product", "/product/**",
    };
    public static final String[] WRITE_PERMISSIONS = {
            "/account", "/account/**",
            "/category", "/category/**",
            "/product", "/product/**",
    };
    public static final String[] EXECUTE_PERMISSIONS = {

    };

    public static final String READ = "READ";
    public static final String WRITE = "WRITE";
    public static final String EXECUTE = "EXECUTE";
}
