package com.max.erickson.greedy;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

enum StableMatchingInputGenerator {

    INST;

    private static final String[] ALL_DOCTORS = "ABCDEFG".split("");
    private static final String[] ALL_HOSPITALS = "HIJKLMN".split("");

    private static final ThreadLocalRandom RAND = ThreadLocalRandom.current();

    public GeneratedInput generate() {

        Map<String, String[]> doctorPrefs = new HashMap<>();

        for(String singleDoctor : ALL_DOCTORS ){
            doctorPrefs.put(singleDoctor, copyAndShuffle(ALL_HOSPITALS));
        }

        Map<String, String[]> hospitalPrefs = new HashMap<>();
        for(String singleHospital : ALL_HOSPITALS ){
            hospitalPrefs.put(singleHospital, copyAndShuffle(ALL_DOCTORS));
        }

        return new GeneratedInput(doctorPrefs, hospitalPrefs);
    }

    private static String[] copyAndShuffle(String[] arr){
        String[] resArr = Arrays.copyOf(arr, arr.length);
        randomShuffle(resArr);
        return resArr;
    }

    private static void randomShuffle(String[] arr) {

        final int length = arr.length;

        for (int offset = 0; offset < length - 1; ++offset) {
            int swapIndex = offset + RAND.nextInt(length - offset);

            String temp = arr[offset];
            arr[offset] = arr[swapIndex];
            arr[swapIndex] = temp;
        }

    }

    public static class GeneratedInput {
        private final Map<String, String[]> doctorPreferences;
        private final Map<String, String[]> hospitalPreferences;

        GeneratedInput(Map<String, String[]> doctorPreferences, Map<String, String[]> hospitalPreferences) {
            this.doctorPreferences = doctorPreferences;
            this.hospitalPreferences = hospitalPreferences;
        }

        public Map<String, String[]> getDoctorPreferences() {
            return doctorPreferences;
        }

        public Map<String, String[]> getHospitalPreferences() {
            return hospitalPreferences;
        }
    }

}
