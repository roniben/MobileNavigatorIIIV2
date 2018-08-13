package com.example.idgaf.mobilenavigatoriii;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.idgaf.mobilenavigatoriii.R;
import com.example.idgaf.mobilenavigatoriii.AdaptersConstructors.Upload;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class addPlaces extends AppCompatActivity implements View.OnClickListener {

    private static final int PICK_IMAGE_REQUEST_CODE = 1;

    private Button chooseFileBtn, nextBtn;
    private ImageView imageView;
    private EditText placeName;
    private Uri uri;
    private ImageButton backBtn;

    private BroadcastReceiver mBroadcastReceiver;
    private WifiManager mWifiManager;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;

    private UploadTask mUploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_places);


        mWifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        chooseFileBtn = (Button) findViewById(R.id.choose_image_button);
        nextBtn = (Button) findViewById(R.id.next_button);
        imageView = (ImageView) findViewById(R.id.upload_image_view);
        placeName = (EditText) findViewById(R.id.enter_place_name);


        chooseFileBtn.setOnClickListener(this);
        nextBtn.setOnClickListener(this);

        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");


        //toolbar part
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST_CODE);
    }

    private String getFileExtension(Uri uri) {

        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));

    }

    private void uploadPlaceImage() {
        if (uri != null) {
            nextBtn.setEnabled(false);
            final StorageReference fileReference = mStorageRef.child(System.currentTimeMillis() + "." + getFileExtension(uri));
            mUploadTask = fileReference.putFile(uri);

            Task<Uri> urlTask = mUploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        Toast.makeText(addPlaces.this, "Upload Failed", Toast.LENGTH_SHORT).show();
                        throw task.getException();
                    }
                    // Continue with the task to get the download URL
                    return fileReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {

                    if (task.isSuccessful()) {

                        String desc= "No Description";
                        Uri downloadUri = task.getResult();

                        Toast.makeText(addPlaces.this, "Add image details", Toast.LENGTH_LONG).show();

                        Upload upload = new Upload(placeName.getText().toString().trim(), downloadUri.toString(), desc);
                        String uploadId = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId).setValue(upload);

                        String name = placeName.getText().toString();
                        String place = uri.toString();

                        Intent intent = new Intent(addPlaces.this, AddPlaceDescription.class);
                        intent.putExtra("image_content_addedPlace", place);
                        intent.putExtra("image_name_addedPlace", name);
                        intent.putExtra("upload_id", uploadId);

                        startActivity(intent);

                        nextBtn.setEnabled(true);
                        placeName.setText("");
                        imageView.setImageResource(android.R.color.transparent);
                        uri = null;

                    } else {
                        Toast.makeText(addPlaces.this, "Upload failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.choose_image_button:
                openFileChooser();
                break;
            case R.id.next_button:
                uploadPlaceImage();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST_CODE && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            uri = data.getData();
            Picasso.with(this)
                    .load(uri)
                    .into(imageView);
        }
    }
}
