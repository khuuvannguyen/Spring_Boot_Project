package com.vannguyen.SpringBootProject.domain.constants;

public class Permission {
    public static final String[] NONE_PATTERNS = {
            "/", "/home",
    };
    public static final String[] READ_PATTERNS = {
            "/account", "/account/**",
            "/category", "/category/**",
            "/product", "/product/**",
    };
    public static final String[] WRITE_PATTERNS = {
            "/account", "/account/**",
            "/category", "/category/**",
            "/product", "/product/**",
    };
    public static final String[] EXECUTE_PATTERNS = {

    };

    public static final String READ = "READ";
    public static final String WRITE = "WRITE";
    public static final String EXECUTE = "EXECUTE";
}
