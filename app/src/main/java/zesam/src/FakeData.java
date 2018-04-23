package zesam.src;

import java.util.ArrayList;
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
        list.add("Pelle Jansson (" + company + ")");
        list.add("Kalle Jonsson (" + company + ")");
        list.add("Leif Olsson (" + company + ")");
        list.add("Lisa Andersson (" + company + ")");
        list.add("Ingemar Stenmark (" + company + ")");

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
