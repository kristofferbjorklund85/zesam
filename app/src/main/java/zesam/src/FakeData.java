package zesam.src;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class FakeData {

    public List getCompanies() {

        ArrayList<Company> compList = new ArrayList<>();
        compList.add(new Company("0", ""));
        compList.add(new Company("1", "Volvo"));
        compList.add(new Company("2", "Ericsson"));
        compList.add(new Company("3", "NCC"));

        for(int i = 4; i < 25; i++) {
            compList.add(new Company("" + i, "Company" + i));
        }

        return compList;
    }


    public List getContacts(String company) {
        ArrayList<String> list = new ArrayList<String>();
        list.add("");
        list.add("Pelle Jansson (" + company + ")");
        list.add("Kalle Jonsson (" + company + ")");
        list.add("Leif Olsson (" + company + ")");
        list.add("Lisa Andersson (" + company + ")");
        list.add("Ingemar Stenmark (" + company + ")");

        return list;
    }

    public ArrayList<Meeting> getFutureMeetings() {
        int extra = 3600000;
        long startDate = Calendar.getInstance().getTimeInMillis();


        ArrayList<Meeting> compList = new ArrayList<>();
        for(int i = 1; i < 8; i++) {
            String text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";
            compList.add(new Meeting("Volvo", "Peter Andersson", text, startDate + (extra * i), "Arne Andersson"));
        }

        return compList;
    }

    class Company {
        String id;
        String name;

        public Company(String id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    class Meeting {
        String comapnyName = "";
        String contact = "";
        String description = "";
        String date = "";
        String organizer;

        public Meeting(String cName, String contact, String desc, long date, String organizer) {
            String myFormat = "yyyy MMM dd HH:mm"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
            Date dt = new Date(date);

            this.comapnyName = cName;
            this.contact = contact;
            this.description = desc;
            this.date = sdf.format(dt);
            this.organizer = organizer;
        }
    }

}
