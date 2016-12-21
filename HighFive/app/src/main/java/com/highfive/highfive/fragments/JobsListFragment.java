package com.highfive.highfive.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.highfive.highfive.R;
import com.highfive.highfive.adapters.JobsListAdapter;
import com.highfive.highfive.model.Job;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by heat_wave on 21.12.16.
 */

public class JobsListFragment extends Fragment {
    RecyclerView orderList;

    Spinner jobClass;
    Spinner subject;
    JobsListAdapter jobsAdapter;
    ArrayList<Job> jobs;

    public static final String FILTER_CLASS = "class";
    public static final String FILTER_CLASS_OPTION_SCHOOL = "school";
    public static final String FILTER_CLASS_OPTION_STUDENT = "student";
    public static final String FILTER_SUBJECT = "subject";
    public static final String FILTER_TIME = "time";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_jobs_list, container, false);
        jobClass = (Spinner) v.findViewById(R.id.job_class_spinner);
        jobClass.setAdapter(ArrayAdapter.createFromResource(getContext(),
                R.array.job_class, android.R.layout.simple_spinner_dropdown_item));
        jobClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch ((int) id) {
                    case 0:
                        subject.setAdapter(null);
                        break;
                    case 1:
                        subject.setAdapter(ArrayAdapter.createFromResource(getContext(),
                                R.array.subject_school, android.R.layout.simple_spinner_dropdown_item));
                        break;
                    case 2:
                        subject.setAdapter(ArrayAdapter.createFromResource(getContext(),
                                R.array.subject_university, android.R.layout.simple_spinner_dropdown_item));
                        break;
                }
                applyFilters();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                ArrayAdapter adapter = new ArrayAdapter<>(getActivity(),
                        android.R.layout.simple_spinner_item, Collections.emptyList());
                subject.setAdapter(adapter);
            }
        });
        subject = (Spinner) v.findViewById(R.id.job_subject_spinner);
        subject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                applyFilters();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        orderList = (RecyclerView) v.findViewById(R.id.jobs_list_recycler);
        jobs = new ArrayList<>();
        jobs.add(new Job(Job.JobClass.SCHOOL, "Математика", "интегралы"));
        jobs.add(new Job(Job.JobClass.STUDENT, "Мат. анализ", "интегралы"));
        jobsAdapter = new JobsListAdapter(jobs);
        orderList.setAdapter(jobsAdapter);
        return v;
    }

    private void applyFilters() {
        jobsAdapter.clearFilter();
        switch (jobClass.getSelectedItemPosition()) {
            case 1: // school
                jobsAdapter.getFilter().filter(FILTER_CLASS + "@" + FILTER_CLASS_OPTION_SCHOOL);
                switch (subject.getSelectedItemPosition()) {
                    case 1: //
                        //jobsAdapter.getFilter().filter(FILTER_SUBJECT + "@" + FILTER_CLASS_OPTION_SCHOOL);
                        break;
                    case 2: // student
                        //jobsAdapter.getFilter().filter(FILTER_SUBJECT + "@" + FILTER_CLASS_OPTION_STUDENT);
                        break;
                }
                break;
            case 2: // student
                jobsAdapter.getFilter().filter(FILTER_CLASS + "@" + FILTER_CLASS_OPTION_STUDENT);
                break;
        }

    }
}