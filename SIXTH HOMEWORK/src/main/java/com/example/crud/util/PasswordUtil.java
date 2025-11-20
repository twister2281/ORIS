package com.example.crud.util;

import org.mindrot.jbcrypt.BCrypt;

public final class PasswordUtil {
    private PasswordUtil(){}
    public static String hash(String raw){ return BCrypt.hashpw(raw, BCrypt.gensalt(12)); }
    public static boolean verify(String raw, String hash){ return BCrypt.checkpw(raw, hash); }
}

