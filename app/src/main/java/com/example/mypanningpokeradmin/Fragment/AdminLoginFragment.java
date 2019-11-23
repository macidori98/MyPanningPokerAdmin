package com.example.mypanningpokeradmin.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mypanningpokeradmin.Model.Admin;
import com.example.mypanningpokeradmin.R;
import com.example.mypanningpokeradmin.Utils.Constant;
import com.example.mypanningpokeradmin.Utils.FragmentNavigation;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class AdminLoginFragment extends Fragment {

    public static final String TAG = AdminLoginFragment.class.getSimpleName();

    private View view;
    private EditText editText_admin_name;
    private Button button_admin_login;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.admin_login_fragment, container, false);

        view = initializeElements(view);

        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference(Constant.ADMIN_NAMES);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        button_admin_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enterToGroups();
            }
        });
    }

    private View initializeElements(View view){
        editText_admin_name = view.findViewById(R.id.editText_admin_login_name);
        button_admin_login = view.findViewById(R.id.button_admin_login);
        return view;
    }

    private void enterToGroups(){
        Constant.CURRENT_USER = editText_admin_name.getText().toString();
        if (isNameLengthOk()){
            mRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    boolean found = false;
                    for (DataSnapshot names : dataSnapshot.getChildren()){
                        if (names.child(Constant.NAME).getValue().toString().equals(Constant.CURRENT_USER)){
                            found = true;
                            Toast.makeText(getContext(),R.string.success, Toast.LENGTH_SHORT).show();
                            FragmentNavigation.getInstance(getContext()).replaceFragment(new AdminHomePageFragment(), R.id.fragment_content);
                            break;
                        }
                    }
                    if (!found) {
                        String admin_id = mRef.push().getKey();
                        Admin admin = new Admin(admin_id,Constant.CURRENT_USER);
                        mRef.child(Objects.requireNonNull(admin_id)).setValue(admin);
                        Toast.makeText(getContext(), R.string.success, Toast.LENGTH_SHORT).show();
                        FragmentNavigation.getInstance(getContext()).replaceFragment(new AdminHomePageFragment(), R.id.fragment_content);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        } else {
            Toast.makeText(getContext(), R.string.name_fail, Toast.LENGTH_SHORT).show();
        }

    }

    private boolean isNameLengthOk(){
        if (Constant.CURRENT_USER.length() >= 4){
            return true;
        }
        else {
            return false;
        }
    }
}
