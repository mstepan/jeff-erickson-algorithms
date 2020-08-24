package com.max.erickson.greedy;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

enum StableMatchingInputGenerator {

    INST;

    private static final ThreadLocalRandom RAND = ThreadLocalRandom.current();

    public GeneratedInput generate(int size) {

        String[] allDoctors = new String[size];
        for (int i = 0; i < allDoctors.length; ++i) {
            allDoctors[i] = "doctor-" + i;
        }

        String[] allHospitals = new String[size];
        for (int i = 0; i < allHospitals.length; ++i) {
            allHospitals[i] = "hospital-" + i;
        }

        Map<String, String[]> doctorPrefs = new HashMap<>();

        for (String singleDoctor : allDoctors) {
            doctorPrefs.put(singleDoctor, copyAndShuffle(allHospitals));
        }

        Map<String, String[]> hospitalPrefs = new HashMap<>();
        for (String singleHospital : allHospitals) {
            hospitalPrefs.put(singleHospital, copyAndShuffle(allDoctors));
        }

        return new GeneratedInput(doctorPrefs, hospitalPrefs);
    }

    private static String[] copyAndShuffle(String[] arr) {
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
