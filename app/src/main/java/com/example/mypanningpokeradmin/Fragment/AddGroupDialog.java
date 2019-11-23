package com.example.mypanningpokeradmin.Fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.mypanningpokeradmin.Interface.AddGroupsDialogListener;
import com.example.mypanningpokeradmin.Model.Groups;
import com.example.mypanningpokeradmin.R;
import com.example.mypanningpokeradmin.Utils.Constant;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddGroupDialog extends AppCompatDialogFragment {
    private Groups groups;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;
    private AddGroupsDialogListener listener;

    private EditText group_id, group_name, group_question_time;

    public AddGroupDialog(){}

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_group_dialog, null);
        group_id = view.findViewById(R.id.editText_add_group_id);
        group_name = view.findViewById(R.id.editText_add_group_name);
        group_question_time = view.findViewById(R.id.editText_add_group_time);
        doSomething(builder,view);
        return builder.create();
    }

    private void doSomething(AlertDialog.Builder builder, View view){
        builder.setView(view).setTitle("Add group").setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mDatabase = FirebaseDatabase.getInstance();
                mRef = mDatabase.getReference(Constant.GROUPS);

                String id = group_id.getText().toString();
                String name = group_name.getText().toString();
                String time = group_question_time.getText().toString();
                String KEY = mRef.push().getKey();
                Groups groups = new Groups(id, name, false, Integer.valueOf(time), KEY);
                listener.applyGroups(groups);
                mRef.child(KEY).setValue(groups);
                //Constant.GROUPS_KEY.add(KEY);
                Toast.makeText(getContext(), R.string.group_added, Toast.LENGTH_SHORT).show();
            }
        });
    }

    void setListener(AddGroupsDialogListener listener){
        this.listener = listener;
    }

}
