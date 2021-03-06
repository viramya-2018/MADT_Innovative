package com.example.madt_innovative;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by admin on 12-Mar-18.
 */

public class frag_home extends Fragment {

    DBAdapter dbAdapter;
    private TextView frag_home_trialTextView;
    private expandableListAdapter listAdapter;
    private ExpandableListView expListView;
    public List<String> listDataHeader;
    public HashMap<String, List<String>> listDataChild;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_home, container, false);
        frag_home_trialTextView = (TextView)v.findViewById(R.id.frag_home_trialTextView);

        expListView = (ExpandableListView)v.findViewById(R.id.frag_home_expandableLV);

        prepareNewListData();

        listAdapter = new expandableListAdapter(getContext(), listDataHeader, listDataChild);
        expListView.setAdapter(listAdapter);
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long l) {
                Intent i = new Intent(getContext(), class_a.class);

                DBAdapter_student dbAdapter_student = new DBAdapter_student(getContext());
                dbAdapter_student.open();
                dbAdapter_student.display(listDataHeader.get(groupPosition));

                String u = dbAdapter_student.classNameFromDB;
                String q = dbAdapter_student.rollNumberFromDB;

                i.putExtra("groupNumber", groupPosition);
                i.putExtra("className", u);
                i.putExtra("student_data", q);

                startActivity(i);

                Toast.makeText(getContext(), "" + listDataHeader.get(groupPosition) + " clicked!", Toast.LENGTH_LONG).show();
                return false;
            }
        });
        return v;
    }


    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Class A");
        listDataHeader.add("Class B");
        listDataHeader.add("Class C");

        // Adding child data
        List<String> classA = new ArrayList<String>();
        classA.add("Section");
        classA.add("Total students");

        List<String> classB = new ArrayList<String>();
        classB.add("Section");
        classB.add("Total students");

        List<String> classC = new ArrayList<String>();
        classC.add("Section");
        classC.add("Total students");

        listDataChild.put(listDataHeader.get(0), classA);
        listDataChild.put(listDataHeader.get(1), classB);
        listDataChild.put(listDataHeader.get(2), classC);
    }

    private void prepareNewListData(){
        dbAdapter = new DBAdapter(getContext());
        dbAdapter.open();
        dbAdapter.display();
        dbAdapter.close();
        listDataHeader = dbAdapter.listDataHeader;
        listDataChild = dbAdapter.listDataChild;
    }
}
