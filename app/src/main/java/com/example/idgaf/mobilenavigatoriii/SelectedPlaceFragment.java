package com.example.idgaf.mobilenavigatoriii.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.idgaf.mobilenavigatoriii.R;
import com.squareup.picasso.Picasso;


public class SelectedPlaceFragment extends Fragment {

    private static final String TAG = "SelectedPlaceFragment";

    View mselectedFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mselectedFragment = inflater.inflate(R.layout.fragment_selectedplacefragment2, container, false);
        Log.e(TAG, "onCreateView:  started");


        savedInstanceState = getArguments();

        if (null != savedInstanceState) {

           String murl = savedInstanceState.getString("image_content");
           String mimagename = savedInstanceState.getString("image_name");
           String mdescription = savedInstanceState.getString("image_description");

            TextView placeName = mselectedFragment.findViewById(R.id.selected_image_name);
            placeName.setText(mimagename);

            TextView desc = mselectedFragment.findViewById(R.id.selected_image_description);
            desc.setText(mdescription);
            ImageView imagePlace = mselectedFragment.findViewById(R.id.selected_place_image);
            Picasso
                    .with(getActivity())
                    .load(murl).fit()
                    .centerCrop()
                    .into(imagePlace);
        }
        return mselectedFragment;

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        setRetainInstance(true);
        if (outState != null) {
            String url = outState.getString("image_content");
            String imageName = outState.getString("image_name");
            String description = outState.getString("image_description");

            outState.putString("image_url_state", url);
            outState.putString("image_name_state", imageName);
            outState.putString("image_description_state", description);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            savedInstanceState = getArguments();

            if (null != savedInstanceState) {

                String murl = savedInstanceState.getString("image_url_state");
                String mimagename = savedInstanceState.getString("image_name_state");
                String mdescription = savedInstanceState.getString("iimage_description_state");

                TextView placeName = mselectedFragment.findViewById(R.id.selected_image_name);
                placeName.setText(mimagename);

                TextView desc = mselectedFragment.findViewById(R.id.selected_image_description);
                desc.setText(mdescription);
                ImageView imagePlace = mselectedFragment.findViewById(R.id.selected_place_image);
                Picasso
                        .with(getActivity())
                        .load(murl).fit()
                        .centerCrop()
                        .into(imagePlace);
            }
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            savedInstanceState = getArguments();

            if (null != savedInstanceState) {

                String murl = savedInstanceState.getString("image_url_state");
                String mimagename = savedInstanceState.getString("image_name_state");
                String mdescription = savedInstanceState.getString("iimage_description_state");

                TextView placeName = mselectedFragment.findViewById(R.id.selected_image_name);
                placeName.setText(mimagename);

                TextView desc = mselectedFragment.findViewById(R.id.selected_image_description);
                desc.setText(mdescription);
                ImageView imagePlace = mselectedFragment.findViewById(R.id.selected_place_image);
                Picasso
                        .with(getActivity())
                        .load(murl).fit()
                        .centerCrop()
                        .into(imagePlace);
            }
        }
    }
}
