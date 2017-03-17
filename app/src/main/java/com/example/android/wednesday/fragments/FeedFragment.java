package com.example.android.wednesday.fragments;


import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.android.wednesday.R;
import com.example.android.wednesday.adapters.AskNowAdapter;
import com.example.android.wednesday.adapters.FeedAdapter;
import com.example.android.wednesday.models.AskQuestionModel;
import com.example.android.wednesday.models.ImageUriModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class FeedFragment extends Fragment {
    private RecyclerView recyclerView;
    private FeedAdapter adapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private FirebaseStorage mFirebaseStorage;
    private StorageReference mFeedPicturesStorageReference;
    Uri outputFileUri;
    Intent chooserIntent;
    int YOUR_SELECT_PICTURE_REQUEST_CODE = 1;
    ValueEventListener valueEventListener;
    private DatabaseReference databaseReference;
    List<ImageUriModel> dataSource;

    public FeedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataSource = new ArrayList<>();
        mFirebaseStorage = FirebaseStorage.getInstance();
        mFeedPicturesStorageReference = mFirebaseStorage.getReference().child("feedPhotos");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("feedPhotos");

        final File root = new File(Environment.getExternalStorageDirectory() + File.separator + "MyDir" + File.separator);
        root.mkdirs();
        final String fname = "img_" + "feed" +  System.currentTimeMillis() + ".jpg";
        final File sdImageMainDirectory = new File(root, fname);
        outputFileUri = Uri.fromFile(sdImageMainDirectory);

        // Camera.
        final List<Intent> cameraIntents = new ArrayList<Intent>();
        final Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        final PackageManager packageManager = getActivity().getPackageManager();
        final List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for(ResolveInfo res : listCam) {
            final String packageName = res.activityInfo.packageName;
            final Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(packageName);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            cameraIntents.add(intent);

            // Filesystem.
            final Intent galleryIntent = new Intent();
            galleryIntent.setType("image/*");
            galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

            // Chooser of filesystem options.
            chooserIntent = Intent.createChooser(galleryIntent, "Pick Image");

            // Add the camera options.
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[cameraIntents.size()]));

        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_feed, container, false);
        Button button = (Button) v.findViewById(R.id.post_feed_image);
                recyclerView = (RecyclerView) v.findViewById(R.id.feed_list);
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mLayoutManager);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImageIntent();
            }
        });


        if(valueEventListener == null) {
            attachDatabaseReadListener();
        }


        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == YOUR_SELECT_PICTURE_REQUEST_CODE) {
            if (resultCode == RESULT_OK)  {
                final boolean isCamera;
                if (data == null) {
                    isCamera = true;
                } else {
                    final String action = data.getAction();
                    if (action == null) {
                        isCamera = false;
                    } else {
                        isCamera = action.equals(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    }
                }

                Uri selectedImageUri;
                if (isCamera) {
                    selectedImageUri = outputFileUri;
                } else {
                    selectedImageUri = data == null ? null : data.getData();
                }

                CropImage.activity(selectedImageUri)
                        .setCropShape(CropImageView.CropShape.RECTANGLE)
//                        .setFixAspectRatio(true)
                        .start(getContext(), this);

            }
        }

        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                profileImageToDatabase(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }    }

    private void openImageIntent() {

// Determine Uri of camera image to save.
        startActivityForResult(chooserIntent, YOUR_SELECT_PICTURE_REQUEST_CODE);
    }

    public void profileImageToDatabase(Uri uri){
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
           mFeedPicturesStorageReference.child(user.getUid()).child("img_" + System.currentTimeMillis() + ".jpg").putFile(uri)
                    .addOnSuccessListener(getActivity(), new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ImageUriModel imageUriModel = new ImageUriModel(taskSnapshot.getDownloadUrl().toString());
                            databaseReference.child(user.getUid()).push().setValue(imageUriModel);

                        }

                    });

        }
        else {
            // No user is signed in
        }
    }

    private void attachDatabaseReadListener() {
        if (valueEventListener == null) {
            valueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    dataSource.clear();
                    for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                        for (DataSnapshot ds : childDataSnapshot.getChildren()) {
                            ImageUriModel model = ds.getValue(ImageUriModel.class);
                            dataSource.add(model);

                            adapter.notifyDataSetChanged();
                        }
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }

            };
            databaseReference.addValueEventListener(valueEventListener);
            adapter = new FeedAdapter(getContext(), dataSource);
            recyclerView.setAdapter(adapter);
        }
    }
    private void detachDatabaseReadListener(){
        if(valueEventListener != null){
            databaseReference.removeEventListener(valueEventListener);
            valueEventListener = null;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        detachDatabaseReadListener();
    }
}
