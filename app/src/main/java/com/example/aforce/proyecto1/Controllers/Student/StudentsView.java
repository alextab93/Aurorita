package com.example.aforce.proyecto1.Controllers.Student;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.aforce.proyecto1.ListAdapter;
import com.example.aforce.proyecto1.R;
import com.example.aforce.proyecto1.models.Course;
import com.example.aforce.proyecto1.models.MyDatabase;
import com.example.aforce.proyecto1.models.Student;
import com.example.aforce.proyecto1.models.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.facebook.login.widget.ProfilePictureView.TAG;

/**
 * Created by mauricio on 17/04/17.
 */

public class StudentsView extends Fragment implements View.OnClickListener {

    String cursoId;

    private CardView cv;
    private ListView lv;
    private ListAdapter adapter;
    private ArrayList<Object> students;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //For now I'm gonna filter from users

        cursoId = getArguments().getString("itemId");

        getActivity().setTitle("Estudiantes");

        cv = (CardView) view.findViewById(R.id.cvNoContent);
        lv = (ListView) view.findViewById(R.id.lvStudents);
        students = new ArrayList<>();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(MyDatabase.CURSO_USUARIO);
        //TODO: change USUARIOS TO ESTUDIANTES
        com.getbase.floatingactionbutton.FloatingActionButton fab = (com.getbase.floatingactionbutton.FloatingActionButton) view.findViewById(R.id.subscribed);
        onClick(fab);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.students_view, container, false);
        com.getbase.floatingactionbutton.FloatingActionButton subs = (com.getbase.floatingactionbutton.FloatingActionButton) v.findViewById(R.id.subscribed);
        com.getbase.floatingactionbutton.FloatingActionButton req = (com.getbase.floatingactionbutton.FloatingActionButton) v.findViewById(R.id.requested);
        subs.setOnClickListener(this);
        req.setOnClickListener(this);
        return v;
    }

    public void seeSubscribed() {
        Toast.makeText(getContext(), "Test1", Toast.LENGTH_SHORT);
    }

    public void seeRequests() {
        Toast.makeText(getContext(), "Test2", Toast.LENGTH_SHORT);
    }

    @Override
    public void onClick(View v) {
        students = new ArrayList<>();

        switch (v.getId()) {
            case R.id.subscribed:
                databaseReference.orderByChild("cursoId").equalTo(cursoId).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        User u = dataSnapshot.getValue(User.class);
                        u.uid = dataSnapshot.getKey();
                        students.add(u);
                        adapter = new ListAdapter(getContext(), students);
                        lv.setDivider(null);
                        lv.setAdapter(adapter);
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                break;
            case R.id.requested:
                databaseReference.orderByChild("state").equalTo("pending").addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        User u = dataSnapshot.getValue(User.class);
                        u.uid = dataSnapshot.getKey();
                        students.add(u);
                        adapter = new ListAdapter(getContext(), students);
                        lv.setDivider(null);
                        lv.setAdapter(adapter);
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                break;
        }
    }
}
