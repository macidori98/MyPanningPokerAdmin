package com.example.mypanningpokeradmin.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mypanningpokeradmin.Adapter.HomePageRecyclerviewAdapter;
import com.example.mypanningpokeradmin.Interface.AddGroupsDialogListener;
import com.example.mypanningpokeradmin.Interface.OnItemClickListener;
import com.example.mypanningpokeradmin.Model.Groups;
import com.example.mypanningpokeradmin.R;
import com.example.mypanningpokeradmin.Utils.Constant;
import com.example.mypanningpokeradmin.Utils.FragmentNavigation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static androidx.recyclerview.widget.RecyclerView.VERTICAL;

public class AdminHomePageFragment extends Fragment implements AddGroupsDialogListener {
    public static final String TAG = AdminHomePageFragment.class.getSimpleName();

    private View view;
    private RecyclerView recyclerView_admin_home_page;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;
    private List<Groups> groupsList;
    private HomePageRecyclerviewAdapter mAdapter;
    private FloatingActionButton fab_add_groups;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.admin_home_page_fragment, container, false);
        recyclerView_admin_home_page = view.findViewById(R.id.admin_home_page_fragment_recyclerView);
        mDatabase = FirebaseDatabase.getInstance();

        groupsList = new ArrayList<>();
        mRef = mDatabase.getReference(Constant.GROUPS);
        groupsList = new ArrayList<>();
        fab_add_groups = view.findViewById(R.id.floatingActionButton_add_groups);
        getGroups();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(VERTICAL);
        recyclerView_admin_home_page.setLayoutManager(linearLayoutManager);

        fab_add_groups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addGroup();

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void addGroup(){
        AddGroupDialog addGroupDialog = new AddGroupDialog();
        addGroupDialog.setListener(this);
        addGroupDialog.show(getActivity().getSupportFragmentManager(),"Add new group");
    }

    private void getGroups(){
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot groups: dataSnapshot.getChildren()){
                    String name = groups.child(Constant.NAME).getValue().toString();
                    String id = groups.child(Constant.ID).getValue().toString();
                    boolean active = Boolean.valueOf(groups.child(Constant.ACTIVE).getValue().toString());
                    int time = Integer.valueOf(groups.child(Constant.ACTIVE_TIME).getValue().toString());
                    String key = groups.getKey();
                    Groups groupsModel = new Groups(id,name, active, time,key);
                    //Constant.GROUPS_KEY.add(groups.getKey());
                    groupsList.add(groupsModel);
                }
                mAdapter = new HomePageRecyclerviewAdapter(getContext(),groupsList);
                mAdapter.setOnClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        Constant.SELECTED_GROUP = groupsList.get(position);
                        FragmentNavigation.getInstance(getContext()).replaceFragment(new AdminGroupQuestionFragment(), R.id.fragment_content);
                        //Toast.makeText(getContext(), "ide katt, majd question list", Toast.LENGTH_SHORT).show();
                    }
                });
                recyclerView_admin_home_page.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void applyGroups(Groups group) {
        groupsList.add(group);
        mAdapter.notifyItemInserted(groupsList.size());
    }


}
