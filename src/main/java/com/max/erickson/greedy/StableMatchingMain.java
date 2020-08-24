package com.max.erickson.greedy;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public final class StableMatchingMain {

    public static void main(String[] args) throws Exception {

        long totalTime = 0L;
        final int IT_COUNT = 100;

        for (int it = 0; it < IT_COUNT; ++it) {

            StableMatchingInputGenerator.GeneratedInput input = StableMatchingInputGenerator.INST.generate(100);
            Map<String, String[]> doctors = input.getDoctorPreferences();
            Map<String, String[]> hospitals = input.getHospitalPreferences();

//        Map<String, String[]> doctors = new HashMap<>();
//        doctors.put("Xavier", new String[]{"Boston", "Atlanta", "Chicago"});
//        doctors.put("Yolanda", new String[]{"Atlanta", "Boston", "Chicago"});
//        doctors.put("Zeus", new String[]{"Atlanta", "Boston", "Chicago"});
//
//        Map<String, String[]> hospitals = new HashMap<>();
//        hospitals.put("Atlanta", new String[]{"Xavier", "Yolanda", "Zeus"});
//        hospitals.put("Boston", new String[]{"Yolanda", "Xavier", "Zeus"});
//        hospitals.put("Chicago", new String[]{"Xavier", "Yolanda", "Zeus"});

            long startTime = System.currentTimeMillis();
            List<DoctorAndHospital> matching = findStableMatching(doctors, hospitals);
            long endTime = System.currentTimeMillis();

            totalTime += (endTime - startTime);

            if (matching.isEmpty()) {
                System.out.println("No stable matching exists");
                return;
            }

            if (!isMatchingStable(matching, doctors, hospitals)) {
                throw new IllegalStateException("Matching not stable");
            }

//            for (DoctorAndHospital singleMatch : matching) {
//                System.out.println(singleMatch);
//            }
        }

        System.out.printf("time: %.2f ms%n", (((double)totalTime) / IT_COUNT));

        System.out.printf("StableMatchingMain completed. java version: %s%n", System.getProperty("java.version"));
    }

    /**
     * Check if matching is stable.
     */
    public static boolean isMatchingStable(List<DoctorAndHospital> matching, Map<String, String[]> doctors,
                                           Map<String, String[]> hospitals) {

        Map<String, String> hospitalToDoctor = new HashMap<>();
        for (DoctorAndHospital singleMatching : matching) {
            hospitalToDoctor.put(singleMatching.hospital, singleMatching.doctor);
        }

        for (DoctorAndHospital singleMatching : matching) {
            String doctor = singleMatching.doctor;
            String hospital = singleMatching.hospital;

            String[] doctorPrefs = doctors.get(doctor);

            for (int i = 0; i < doctorPrefs.length; ++i) {
                if (doctorPrefs[i].equals(hospital)) {
                    break;
                }

                String hospitalToCheck = doctorPrefs[i];

                if (!isStableForHospital(doctor, hospitals.get(hospitalToCheck), hospitalToDoctor.get(hospitalToCheck))) {
                    return false;
                }
            }
        }

        return true;
    }

    private static boolean isStableForHospital(String doctorToCheck, String[] hospitalPrefs, String matchedDoctor) {
        for (String singleHospitalPref : hospitalPrefs) {
            if (doctorToCheck.equals(singleHospitalPref)) {
                return false;
            }

            if (matchedDoctor.equals(singleHospitalPref)) {
                break;
            }

        }

        return true;
    }

    /**
     * Stable matching algorithm implementation using Boston pool (Gale-Shapley) greedy approach.
     */
    public static List<DoctorAndHospital> findStableMatching(Map<String, String[]> doctors, Map<String, String[]> hospitals) {

        Queue<HospitalState> hospitalsQueue = new ArrayDeque<>();
        Map<String, HospitalState> hospitalNameToState = new HashMap<>();

        for (String singleHospitalName : hospitals.keySet()) {
            HospitalState state = new HospitalState(singleHospitalName);

            hospitalNameToState.put(singleHospitalName, state);
            hospitalsQueue.add(state);
        }

        Map<String, DoctorState> doctorMatchings = new HashMap<>();

        while (!hospitalsQueue.isEmpty()) {
            HospitalState unmatchedHospital = hospitalsQueue.poll();

            String[] curHospitalPrefs = hospitals.get(unmatchedHospital.name);

            int doctorIndex = unmatchedHospital.index;

            for (; doctorIndex < curHospitalPrefs.length; ++doctorIndex) {
                String doctorToPropose = curHospitalPrefs[doctorIndex];

                Proposition proposition = propose(unmatchedHospital.name, doctorMatchings.get(doctorToPropose),
                                                  doctors.get(doctorToPropose));

                if (proposition.accepted) {
                    makeMatching(doctorMatchings, unmatchedHospital.name, doctorToPropose, doctors);
                    if (proposition.rejectedHospital != null) {
                        addRejectedHospitalToQueue(hospitalNameToState.get(proposition.rejectedHospital),
                                                   proposition.rejectedHospital, hospitalsQueue);
                    }
                    break;
                }
            }

            if (doctorIndex == curHospitalPrefs.length) {
                // can't find a match for a hospital, so stable matching is not possible here
                return Collections.emptyList();
            }
        }

        return combineAllMatchings(doctorMatchings);
    }

    private static Proposition propose(String hospital, DoctorState doctorState, String[] doctorPrefs) {

        if (doctorState == null) {
            return Proposition.accept(null);
        }

        for (int hospitalIndex = doctorState.index; hospitalIndex >= 0; --hospitalIndex) {
            if (doctorPrefs[hospitalIndex].equals(hospital)) {
                return Proposition.accept(doctorState.acceptedHospital);
            }
        }


        return Proposition.reject();
    }

    private static void makeMatching(Map<String, DoctorState> doctorMatchings, String hospital,
                                     String doctor, Map<String, String[]> doctors) {

        String[] hospitalsForDoctor = doctors.get(doctor);

        if (doctorMatchings.containsKey(doctor)) {
            DoctorState state = doctorMatchings.get(doctor);
            state.acceptedHospital = hospital;
            state.index = findHospitalIndex(hospital, hospitalsForDoctor, state.index);
        }
        else {
            DoctorState state = new DoctorState(doctor, hospital, findHospitalIndex(hospital, hospitalsForDoctor,
                                                                                    hospitalsForDoctor.length - 1));
            doctorMatchings.put(doctor, state);
        }

    }

    private static int findHospitalIndex(String hospitalToSearch, String[] hospitals, int searchStartIndex) {

        for (int i = searchStartIndex; i >= 0; --i) {
            if (hospitals[i].equals(hospitalToSearch)) {
                return i;
            }
        }

        throw new IllegalStateException("Can't find hospital: '" + hospitalToSearch + "' in hospitals: " +
                                                Arrays.asList(hospitals));
    }

    private static void addRejectedHospitalToQueue(HospitalState rejectedState, String rejectedHospital,
                                                   Queue<HospitalState> hospitalsQueue) {

        assert rejectedHospital != null : "'null' rejectedState detected for hospital name '" + rejectedHospital + "'";

        rejectedState.index += 1;

        hospitalsQueue.add(rejectedState);
    }

    private static List<DoctorAndHospital> combineAllMatchings(Map<String, DoctorState> matchings) {

        List<DoctorAndHospital> result = new ArrayList<>();
        for (Map.Entry<String, DoctorState> entry : matchings.entrySet()) {
            result.add(new DoctorAndHospital(entry.getKey(), entry.getValue().acceptedHospital));
        }

        return result;
    }

    static final class Proposition {
        final boolean accepted;
        final String rejectedHospital;

        static Proposition accept(String rejectedHospital) {
            return new Proposition(true, rejectedHospital);
        }

        static Proposition reject() {
            return new Proposition(false, null);
        }

        private Proposition(boolean accepted, String rejectedHospital) {
            this.accepted = accepted;
            this.rejectedHospital = rejectedHospital;
        }

        @Override
        public String toString() {
            return String.format("accepted: %b, rejected hospital: %s", accepted, rejectedHospital);
        }
    }

    static final class DoctorAndHospital {
        final String doctor;
        final String hospital;

        DoctorAndHospital(String doctor, String hospital) {
            this.doctor = doctor;
            this.hospital = hospital;
        }

        @Override
        public String toString() {
            return String.format("doctor: %s, hospital: %s", doctor, hospital);
        }

    }

    static final class HospitalState {
        final String name;
        int index;

        HospitalState(String name) {
            this.name = name;
            this.index = 0;
        }

        @Override
        public String toString() {
            return String.format("%s: index: %d", name, index);
        }
    }

    static final class DoctorState {
        final String name;
        String acceptedHospital;
        int index;

        DoctorState(String name, String acceptedHospital, int index) {
            this.name = name;
            this.acceptedHospital = acceptedHospital;
            this.index = index;
        }

        @Override
        public String toString() {
            return String.format("hospital: %s, index: %d", acceptedHospital, index);
        }

    }

}
