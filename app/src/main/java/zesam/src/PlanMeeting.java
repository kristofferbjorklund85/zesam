package zesam.src;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlanMeeting extends AppCompatActivity {

    FakeData fd;
    ArrayList<FakeData.Meeting> list;

    private RecyclerView recyclerView;
    private MeetingAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_meeting);

        Toolbar t = findViewById(R.id.toolbar_logged_in);
        setSupportActionBar(t);

        recyclerView = (RecyclerView) findViewById(R.id.plan_recycler);

        fd = new FakeData();
        list = fd.getFutureMeetings();
        adapter = new MeetingAdapter(this, list);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);


        adapter.notifyDataSetChanged();

    }

    public String shortenText(String text) {
        if(text.length() > 45) {
            text = text.substring(0,42) + "...";
        }

        return text;
    }

    public void importMeetings(View v) {
        ArrayList<FakeData.Meeting> importList = new ArrayList<>();
        HashMap<FakeData.Meeting, Boolean> mChecked = adapter.getmChecked();

        Log.d("List: ", "" + mChecked.size());

        for(int i = 0;i < mChecked.size(); i++) {
            if(mChecked.get(list.get(i))) {
                importList.add(list.get(i));
            }
        }

        fd.setMeetList(importList);

        Intent intent = new Intent(this, ListMeetings.class);
        startActivity(intent);
        finish();
    }

    public void logOut(View v) {
        MeetingSingleton.clearMeeting();
        Intent intent = new Intent(getApplicationContext(), Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void back(View v) {
        Intent intent = new Intent(this, Root.class);
        startActivity(intent);
    }


    public class MeetingAdapter extends RecyclerView.Adapter<MeetingAdapter.MyViewHolder> {
        private Context mContext;
        private List<FakeData.Meeting> meetList;
        private HashMap<FakeData.Meeting, Boolean> mChecked;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView listmeetingdate, listmeetingorganizer, listmeetingcompany, listmeetingcontact, listmeetingdesc;
            public CheckBox listmeetcheck;

            public MyViewHolder(View view) {
                super(view);

                listmeetingdate = (TextView) view.findViewById(R.id.listmeetingdate);
                listmeetingorganizer = (TextView) view.findViewById(R.id.listmeetingorganizer);
                listmeetingcompany = (TextView) view.findViewById(R.id.listmeetingcompany);
                listmeetingcontact = (TextView) view.findViewById(R.id.listmeetingcontact);
                listmeetingdesc = (TextView) view.findViewById(R.id.listmeetingdesc);
                listmeetcheck = (CheckBox) view.findViewById(R.id.plan_meet_check);

            }
        }

        public MeetingAdapter(Context mContext, List<FakeData.Meeting> meetList) {
            this.mContext = mContext;
            this.meetList = meetList;
            mChecked = new HashMap<>();
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.meetingcard, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            FakeData.Meeting meet = meetList.get(position);
            holder.listmeetingdate.setText(meet.date);
            holder.listmeetingorganizer.setText(meet.organizer);
            holder.listmeetingcompany.setText(meet.companyName);
            holder.listmeetingcontact.setText(meet.contact);
            holder.listmeetingdesc.setText(shortenText(meet.description));

            mChecked.put(meetList.get(position), false);

            holder.listmeetcheck.setOnCheckedChangeListener(null);

            if(mChecked.containsKey(meetList.get(position))) {
                holder.listmeetcheck.setChecked(mChecked.get(meetList.get(position)));
            }
            else {
                holder.listmeetcheck.setChecked(false);
            }
            holder.listmeetcheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    mChecked.put(meetList.get(position), isChecked);
                }
            });

        }

        @Override
        public int getItemCount() {
            return meetList.size();
        }

        public HashMap<FakeData.Meeting, Boolean> getmChecked() {
            return mChecked;
        }

    }

}
