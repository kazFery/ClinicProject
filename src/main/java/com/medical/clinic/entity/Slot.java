package com.medical.clinic.entity;

public enum Slot {
    Slot1("8:00-8:30", 1),
    Slot2("8:30-9:00", 2),
    Slot3("9:00-9:30", 3),
    Slot4("9:30-10:00",4),
    Slot5("10:00-10:30", 5),
    Slot6("10:30-11:00",6),
    Slot7("11:00-11:30", 7),
    Slot8("11:30-12:00",8),
    Slot9("12:00-12:30",9),
    Slot10("12:30-13:00",10),
    Slot11("13:00-13:30", 11),
    Slot12("13:30-14:00",12),
    Slot13("14:00-14:30",13),
    Slot14("14:30-15:00",14),
    Slot15("15:00-15:30", 15),
    Slot16("15:30-16:00",16),
    Slot17("16:00-16:30", 17),
    Slot18("16:30-17:00",18);

    private String slotName;
    private int slotNo;

    Slot(String slot, int i) {
        this.slotName = slot;
        this.slotNo = i;
    }

    public String getSlotName() {
        return slotName;
    }
    public int getSlotNo(){
        return slotNo;
    }
    public void setSlotNo(int i){
        this.slotNo = i;
    }

}
