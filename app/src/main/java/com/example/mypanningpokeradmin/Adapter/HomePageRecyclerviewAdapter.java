package com.example.mypanningpokeradmin.Adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mypanningpokeradmin.Interface.OnItemClickListener;
import com.example.mypanningpokeradmin.Model.Groups;
import com.example.mypanningpokeradmin.R;
import com.example.mypanningpokeradmin.Utils.Constant;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class HomePageRecyclerviewAdapter extends RecyclerView.Adapter<HomePageRecyclerviewAdapter.MyViewHolder> {

    private Context context;
    private List<Groups> groupsList;
    private OnItemClickListener listener;
    private HomePageRecyclerviewAdapter.MyViewHolder bla;


    public HomePageRecyclerviewAdapter(Context context, List<Groups> groupsList){
        this.context = context;
        this.groupsList = groupsList;
    }

    @NonNull
    @Override
    public HomePageRecyclerviewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.admin_home_page_recyclerview_element, parent, false);
        return new MyViewHolder(itemView, listener);

    }

    @Override
    public void onBindViewHolder(@NonNull HomePageRecyclerviewAdapter.MyViewHolder holder, int position) {
        bla = holder;
        holder.id.setText(groupsList.get(position).getId());
        holder.name.setText(groupsList.get(position).getName());
        if (groupsList.get(position).isActive()){
            holder.active.setText(R.string.active);
            holder.active.setTextColor(ContextCompat.getColor(context,R.color.colorPrimary));
        } else {
            holder.active.setText(R.string.inactive);
            holder.active.setTextColor(ContextCompat.getColor(context,R.color.colorAccent));

        }
    }

    @Override
    public int getItemCount() {
        return groupsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView id, name, active;
        public Switch switch_activity;


        public MyViewHolder(View view, final OnItemClickListener listener) {
            super(view);
            id = view.findViewById(R.id.textView_admin_home_page_recyclerview_group_id);
            name = view.findViewById(R.id.textView_admin_home_page_recyclerview_group_name);
            active = view.findViewById(R.id.textView_admin_home_page_recyclerview_group_active);
            switch_activity = view.findViewById(R.id.switch_set_activity);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });

            switch_activity.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    final int position = getAdapterPosition();
                    if (switch_activity.isChecked()){
                        Toast.makeText(context,"on",Toast.LENGTH_SHORT).show();
                        /*FirebaseDatabase mDatabase;
                        final DatabaseReference mRef;
                        mDatabase = FirebaseDatabase.getInstance();
                        mRef = mDatabase.getReference(Constant.GROUPS);
                        mRef.orderByKey().equalTo(Constant.GROUPS_KEY.get(position)).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                                    String KEY = snap.getKey();
                                    mRef.child(KEY).child(Constant.ACTIVE).setValue(true);
                                    Toast.makeText(context, String.valueOf(position),Toast.LENGTH_SHORT).show();
                                    onBindViewHolder(bla,position);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });*/
                    } else {
                        Toast.makeText(context,"off",Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }

    }

    public void setOnClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

}
