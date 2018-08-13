package com.example.idgaf.mobilenavigatoriii.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.idgaf.mobilenavigatoriii.addPlaces;
import com.example.idgaf.mobilenavigatoriii.AdaptersConstructors.ImageAdapter;
import com.example.idgaf.mobilenavigatoriii.R;
import com.example.idgaf.mobilenavigatoriii.AdaptersConstructors.Upload;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PlacesFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "PlacesFragment";

    private RecyclerView nRecyclerView;
    private ImageAdapter nAdapter;

    private ProgressBar mProgressCircle;

    private List<Upload> mUploads;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_places, container, false);
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.add_placeBtn);
        fab.setOnClickListener(this);

        mProgressCircle = view.findViewById(R.id.progress_circle);

        nRecyclerView = view.findViewById(R.id.recycler_view);
        nRecyclerView.setHasFixedSize(true);
        nRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        mProgressCircle.setVisibility(View.VISIBLE);

        mUploads = new ArrayList<>();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("uploads");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mUploads.clear();

                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Upload upload = postSnapshot.getValue(Upload.class);
                    mUploads.add(upload);
                    mProgressCircle.setVisibility(View.INVISIBLE);
                }
                nAdapter = new ImageAdapter(getActivity(), mUploads);
                nRecyclerView.setAdapter(nAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressCircle.setVisibility(View.INVISIBLE);
            }
        });


        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_placeBtn:
                Intent intent = new Intent(getContext(), addPlaces.class);
                startActivity(intent);
                break;
        }
    }
}
