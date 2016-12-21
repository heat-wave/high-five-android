package com.highfive.highfive.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.highfive.highfive.R;
import com.highfive.highfive.model.Job;

import java.util.ArrayList;
import java.util.List;

import static com.highfive.highfive.fragments.JobsListFragment.*;
/**
 * Created by heat_wave on 21.12.16.
 */

public class JobsListAdapter extends RecyclerView.Adapter<JobsListAdapter.ViewHolder> implements Filterable {

    private List<Job> jobList;
    private List<Job> original;
    private Filter jobsFilter;

    public JobsListAdapter(List<Job> list) {
        this.jobList = new ArrayList<>();
        this.original = new ArrayList<>();
        for (Job j : list) {
            jobList.add(j);
            original.add(j);
        }
    }

    public void clearFilter() {
        jobsFilter = null;
        jobList.clear();
        for (Job j : original) {
            jobList.add(j);
        }
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_order_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Job Job = jobList.get(position);
        holder.theme.setText(Job.getSubject());
        holder.description.setText(Job.getDescription());
    }

    @Override
    public int getItemCount() {
        return jobList.size();
    }

    @Override
    public Filter getFilter() {
        if (jobsFilter == null)
            jobsFilter = new JobsFilter();

        return jobsFilter;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView theme;
        TextView description;
        TextView date;

        public ViewHolder(View itemView) {
            super(itemView);
            theme = (TextView) itemView.findViewById(R.id.order_list_item_theme);
            description = (TextView) itemView.findViewById(R.id.order_list_item_discription);
            date = (TextView) itemView.findViewById(R.id.order_list_item_date);
        }
    }

    private class JobsFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint == null || constraint.length() == 0) {
                results.values = jobList;
                results.count = jobList.size();
            } else {
                String query = constraint.toString();
                List<Job> nOrderList = new ArrayList<>();
                int delimiterPos = query.indexOf("@");
                String mode = query.substring(0, delimiterPos);
                String selection = query.substring(delimiterPos + 1, query.length());

                switch (mode) {
                    case FILTER_CLASS:
                        switch (selection) {
                            case FILTER_CLASS_OPTION_SCHOOL:
                                for (Job p : jobList) {
                                    if (p.getType() == Job.JobClass.SCHOOL)
                                        nOrderList.add(p);
                                }
                                break;
                            case FILTER_CLASS_OPTION_STUDENT:
                                for (Job p : jobList) {
                                    if (p.getType() == Job.JobClass.STUDENT)
                                        nOrderList.add(p);
                                }
                                break;
                        }
                        break;
                }

                results.values = nOrderList;
                results.count = nOrderList.size();

            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {

            if (results.count == 0) {
                notifyItemRangeRemoved(0, getItemCount());
            } else {
                jobList = (List<Job>) results.values;
                notifyDataSetChanged();
            }
        }

    }
}
