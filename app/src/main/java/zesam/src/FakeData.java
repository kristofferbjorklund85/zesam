package zesam.src;

import java.util.ArrayList;
import java.util.List;

public class FakeData {

    public List getCompanies() {

        ArrayList<Company> compList = new ArrayList<>();
        compList.add(new Company("1", "Volvo"));
        compList.add(new Company("2", "Ericsson"));
        compList.add(new Company("3", "NCC"));

        for(int i = 4; i < 25; i++) {
            compList.add(new Company("" + i, "Company" + i));
        }

        return compList;
    }


    public List getContacts() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("Pelle Jansson");
        list.add("Kalle Jonsson");
        list.add("Leif Olsson");

        return list;
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

}
