package com.highfive.highfive.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.highfive.highfive.Navigator;
import com.highfive.highfive.R;
import com.highfive.highfive.fragments.OrderDetailsFragment;
import com.highfive.highfive.model.Order;

import java.util.List;

/**
 * Created by dan on 22.01.17.
 */

public class OrderTeacherListAdapter extends RecyclerView.Adapter<OrderTeacherListAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(int item);
    }


    private List<Order> orderList;
    private OnItemClickListener listener;

    public OrderTeacherListAdapter(List<Order> list, OnItemClickListener listener) {
        this.orderList = list;
        this.listener = listener;
    }


    @Override
    public OrderTeacherListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_order_list_item, parent, false);
        return new OrderTeacherListAdapter.ViewHolder(v, listener);
    }

    @Override
    public void onBindViewHolder(OrderTeacherListAdapter.ViewHolder holder, int position) {
        Order order = orderList.get(position);
        holder.theme.setText(order.getTheme());
        holder.description.setText(order.getDescription());
        //holder.date.setText(order.getDate());
    }


    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView theme;
        TextView description;
        TextView date;
        OnItemClickListener listener;

        public ViewHolder(View itemView, OnItemClickListener listener) {
            super(itemView);
            this.listener = listener;
            theme = (TextView) itemView.findViewById(R.id.order_list_item_theme);
            description = (TextView) itemView.findViewById(R.id.order_list_item_discription);
            date = (TextView) itemView.findViewById(R.id.order_list_item_date);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onItemClick(getAdapterPosition());

            Log.i("sadasd", "asdsad");
        }
    }
}
