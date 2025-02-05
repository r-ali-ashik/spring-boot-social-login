package com.rali.util;

import com.nimbusds.oauth2.sdk.util.CollectionUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AppUtils {

    private static final String EMAIL_REGEX =
            "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\"
                    + ".[a-zA-Z0-9-]+)*$";

    private static final String MATH_ML_PATTERN = ".*<math[^>]*>.*</math>.*";
    private static final String MOBILE_REGEX = "(^(\\+88|0088)?(01){1}[3456789]{1}(\\d){8})$";
    public static final String DIGIT_FOUR_OTP = "%04d";

    private static ModelMapper modelMapper;
    private static ModelMapper getModelMapper() {
        if (modelMapper == null) {
            modelMapper = new ModelMapper();
            modelMapper.getConfiguration().setAmbiguityIgnored(true).setSkipNullEnabled(true);
        }
        return modelMapper;
    }

    public static String generateRandomString() {
        return UUID.randomUUID().toString();
    }
    public static int generateRandomNumber() {
        int x = 1 + (int)(Math.random() * ((100 - 1) + 1));
        return x;
    }
    public static String generateRandomUUID() {
        return UUID.randomUUID().toString();
    }
    public static boolean isEmpty(CharSequence charSequence) {
        return StringUtils.isEmpty(charSequence);
    }
    public static boolean isEmpty(Collection collection) {
        return CollectionUtils.isEmpty(collection);
    }
    public static boolean isNull(Object object) {
        return Objects.isNull(object);
    }
    public static boolean isNotNull(Object object) {
        return Objects.nonNull(object);
    }

    public static boolean isValidEmail(String email) {
        return email.matches(EMAIL_REGEX);
    }
    public static boolean isValidMobile(String mobile) {
        return mobile.matches(MOBILE_REGEX);
    }
    public static String getRandomOtp() {
        return String.format(DIGIT_FOUR_OTP, new Random().nextInt(10000));
    }

    public static<T> List<T> nullSafeList(List<T> list){
        return list == null ? Collections.emptyList() : list;
    }

    public static<T> Set<T> nullSafeSet(Set<T> set){
        return set == null ? Collections.emptySet() : set;
    }

    public static void copyNonNullProperties(Object destination, Object source) throws IllegalAccessException, InvocationTargetException {
        new BeanUtilsBean() {
            @Override
            public void copyProperty(Object dest, String name, Object value)
                    throws IllegalAccessException, InvocationTargetException {
                if (value != null) {
                    super.copyProperty(dest, name, value);
                }
            }
        }.copyProperties(destination, source);
    }

    public static  <S, D> D copyProperties(S source, Class<D> destinationType) {
        return getModelMapper().map(source, destinationType);
    }

    public static boolean hasMathMLTag(String input) {
        Matcher matcher = getMathmlPattern().matcher(input);
        return matcher.matches();
    }

    private static Pattern getMathmlPattern() {
        Pattern pattern = Pattern.compile(MATH_ML_PATTERN, Pattern.DOTALL);
        return pattern;
    }
}
