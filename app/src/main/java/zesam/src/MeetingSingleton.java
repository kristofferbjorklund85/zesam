package zesam.src;

import java.util.ArrayList;

public class MeetingSingleton {
    String comapnyName = "";
    String companyId = "";
    String contact = "";
    String description = "";
    String date = "";
    String mapsURL = "";
    ArrayList<FakeData.Meeting> meetList = new ArrayList<>();

    private static MeetingSingleton ms = new MeetingSingleton();

    public static void setCompanyName(String name) {
        ms.comapnyName= name;
    }

    public static void setCompanyId(String companyId) {
        ms.companyId = companyId;
    }

    public static void setContact(String contact) {
        ms.contact = contact;
    }

    public static void setDescription(String description) {
        ms.description = description;
    }

    public static void setDate(String date) {
        ms.date = date;
    }

    public static void setMapsURL(String mapsURL) {
        ms.mapsURL = mapsURL;
    }

    public static void setMeetList(ArrayList<FakeData.Meeting> list) {
        for (FakeData.Meeting m: list) {
            ms.meetList.add(m);
        }
    }

    public static String getCompanyName() {
        return ms.comapnyName;
    }

    public static String getDescription() {
        return ms.description;
    }

    public static String getContact() {
        return ms.contact;
    }

    public static String getDate() {
        return ms.date;
    }

    public static MeetingSingleton getMeeting() {
        return ms;
    }

    public static ArrayList<FakeData.Meeting> getMeetList() {
        return ms.meetList;
    }

    public static void clearMeeting() {
        ms = new MeetingSingleton();
    }
}
