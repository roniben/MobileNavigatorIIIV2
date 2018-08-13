package com.example.idgaf.mobilenavigatoriii.AdaptersConstructors;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.idgaf.mobilenavigatoriii.R;
import com.example.idgaf.mobilenavigatoriii.Activities.SelectedPlaceActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder>{

    private static final String TAG = "ImageAdapter";

    private Context mContext;
    private List<Upload> mUploads;
    private FragmentManager fm;

    public ImageAdapter(Context context, List<Upload> uploads) {
        mContext = context;
        mUploads = uploads;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.image_item, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called");

        final Upload uploadCurrent = mUploads.get(position);
        final String value = uploadCurrent.getName();

        holder.textViewName.setText(uploadCurrent.getName());
        holder.textDescription.setText(uploadCurrent.getmDescription());

        Picasso.with(mContext)
                .load(uploadCurrent.getImageUrl())
                .fit()
                .centerCrop()
                .into(holder.imageViewTxt);

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, String.format("onClick: called%s", value));

                Toast.makeText(mContext, uploadCurrent.getName(), Toast.LENGTH_SHORT).show();

//                Intent intent = new Intent(mContext, SelectedPlacePagerAdapter.class);
                Intent intent = new Intent(mContext, SelectedPlaceActivity.class);
                intent.putExtra("image_content", uploadCurrent.getImageUrl());
                intent.putExtra("image_name", uploadCurrent.getName());
                intent.putExtra("image_description", uploadCurrent.getmDescription());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }


    public class ImageViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewName;
        public ImageView imageViewTxt;
        public TextView textDescription;
        public RelativeLayout parentLayout;


        public ImageViewHolder(View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.text_view_name);
            imageViewTxt = itemView.findViewById(R.id.image_view_upload);
            parentLayout = itemView.findViewById(R.id.parentLayout);
            textDescription =itemView.findViewById(R.id.image_description_cut);
        }
    }


//    private void openFragment(String name, String url, String description){
//        Bundle bundle = new Bundle();
//        bundle.putString("name_key", name);
//        bundle.putString("url_key", url);
//        bundle.putString("description_key", description);
//
//        SelectedPlaceFragment selectedPlaceFragment = new SelectedPlaceFragment();
//        selectedPlaceFragment.setArguments(bundle);
//
//        FragmentManager fragmentManager = getFragmentManager();
//  }
}
