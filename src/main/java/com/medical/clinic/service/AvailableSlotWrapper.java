package com.medical.clinic.service;

import com.medical.clinic.entity.DoctorAvailableSlot;

import java.util.ArrayList;

public class AvailableSlotWrapper {
    public ArrayList<DoctorAvailableSlot> doctorAvailableSlotList;

    public ArrayList<DoctorAvailableSlot> getdoctorAvailableSlotList() {
        return doctorAvailableSlotList;
    }

    public void setdoctorAvailableSlotList(ArrayList<DoctorAvailableSlot> doctorAvailableSlotList) {
        this.doctorAvailableSlotList = doctorAvailableSlotList;
    }

    public int size(){
        return doctorAvailableSlotList.size();
    }

    public  DoctorAvailableSlot get(int i){
        return doctorAvailableSlotList.get(i);
    }

    public void add(DoctorAvailableSlot  a){
        doctorAvailableSlotList.add(a);
    }

}
