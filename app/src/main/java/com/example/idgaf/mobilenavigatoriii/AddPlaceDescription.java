package com.example.idgaf.mobilenavigatoriii;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.idgaf.mobilenavigatoriii.Activities.MainActivity;
import com.example.idgaf.mobilenavigatoriii.R;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class AddPlaceDescription extends AppCompatActivity implements View.OnClickListener {


    private Button dButton;
    private EditText desciption;

    private StorageReference mStorageRef;


    private static final String Tag = "AddPlaceDescription";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_place_description);

        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        desciption = (EditText) findViewById(R.id.description_add_description);
        dButton = (Button) findViewById(R.id.button_uploadDescription);
        dButton.setOnClickListener(this);

        getIncomingIntent();
    }

    private void getIncomingIntent(){
        Log.d(Tag, "getIncomingIntent: called");

        if(getIntent().hasExtra("image_content_addedPlace") && getIntent().hasExtra("image_name_addedPlace")){
            Log.d(Tag, "getIncomingIntent: called ");

            String url = getIntent().getStringExtra("image_content_addedPlace");
            String imageName = getIntent().getStringExtra("image_name_addedPlace");
            setImage(url, imageName);
        }
    }

    private void setImage(String imageUrl, String imageName){

        TextView placeName= findViewById(R.id.place_name_title);
        placeName.setText(imageName);

        ImageView imagePlace = findViewById(R.id.place_addDescription);
        Uri fileUri = Uri.parse(imageUrl);
        imagePlace.setImageURI(fileUri);

    }

    private void uploadDescription(){

        EditText desc = (EditText) findViewById(R.id.description_add_description);
        String description = desc.getText().toString();
        String uploadKey = getIntent().getStringExtra("upload_id");
        FirebaseDatabase.getInstance().getReference("uploads").child(uploadKey).child("mDescription").setValue(description);

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);

        dButton.setEnabled(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_uploadDescription:
                uploadDescription();
                break;
        }
    }
}
