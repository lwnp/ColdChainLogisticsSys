package com.xzit.common.user.validator;


import com.xzit.common.user.annotation.Phone;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class PhoneValidator implements ConstraintValidator<Phone, String> {

    private static final Pattern PHONE_PATTERN = Pattern.compile("^1[3456789]\\d{9}$");

    @Override
    public void initialize(Phone constraintAnnotation) {
    }

    @Override
    public boolean isValid(String phone, ConstraintValidatorContext constraintValidatorContext) {
        if (phone == null) {
            return true; // Allow null values
        }

        // 手机号正则匹配
        return PHONE_PATTERN.matcher(phone).matches() && isValidOperator(phone);
    }

    // 验证手机号的运营商
    private boolean isValidOperator(String phone) {
        // 中国电信号段
        String[] telecomSegments = {"133", "1349", "1410", "149", "153", "162", "170[0-2]", "173", "174[0-1]", "177", "180", "181", "189", "190", "191", "193", "199"};
        // 中国联通号段
        String[] unicomSegments = {"130", "131", "132", "140", "145", "146", "155", "156", "166", "167", "170[4-7]", "171", "175", "176", "185", "186", "196"};
        // 中国移动号段
        String[] mobileSegments = {"134[0-8]", "135", "136", "137", "138", "139", "1440", "147", "148", "150", "151", "152", "157", "158", "159", "165", "170[3-6]", "172", "178", "182", "183", "184", "187", "188", "195", "197", "198"};
        // 中国广电号段
        String[] broadcastSegments = {"192"};

        // 遍历各个运营商号段，判断手机号是否属于其中之一
        for (String segment : telecomSegments) {
            if (phone.matches("^" + segment + "\\d{7}$")) {
                return true;
            }
        }
        for (String segment : unicomSegments) {
            if (phone.matches("^" + segment + "\\d{7}$")) {
                return true;
            }
        }
        for (String segment : mobileSegments) {
            if (phone.matches("^" + segment + "\\d{7}$")) {
                return true;
            }
        }
        for (String segment : broadcastSegments) {
            if (phone.matches("^" + segment + "\\d{6}$")) {
                return true;
            }
        }

        return false;
    }
}
