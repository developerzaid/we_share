package com.hazyaz.weshare.users.donater;

public class MyListData {
    private String donation_name, donation_desc, donation_loc;

    public MyListData(String donation_name, String donation_desc, String donation_loc) {
        this.donation_name = donation_name;
        this.donation_desc = donation_desc;
        this.donation_loc = donation_loc;
    }

    public String getDonation_name() {
        return donation_name;
    }

    public void setDonation_name(String donation_name) {
        this.donation_name = donation_name;
    }

    public String getDonation_desc() {
        return donation_desc;
    }

    public void setDonation_desc(String donation_desc) {
        this.donation_desc = donation_desc;
    }

    public String getDonation_loc() {
        return donation_loc;
    }

    public void setDonation_loc(String donation_loc) {
        this.donation_loc = donation_loc;
    }
}
