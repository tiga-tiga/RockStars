package com.wbtech.rockstars.Fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.wbtech.rockstars.Commons.BaseActivity;
import com.wbtech.rockstars.Commons.BaseFragment;
import com.wbtech.rockstars.Managers.PreferencesManager;
import com.wbtech.rockstars.R;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends BaseFragment {

    public ProfileFragment() {
        // Required empty public constructor
    }

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private ImageView mIvProfile;
    private LinearLayout containerAddPicture;
    private EditText mEtName;
    private Bitmap mBitmap;

    private PreferencesManager mPreferencesManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        mPreferencesManager = PreferencesManager.getInstance(getActivity().getSharedPreferences("prefs", MODE_PRIVATE));
        mIvProfile = view.findViewById(R.id.iv_profile);
        containerAddPicture = view.findViewById(R.id.container_add_picture);
        mEtName = view.findViewById(R.id.et_profile_name);

        mBitmap = mPreferencesManager.getProfileImage();
        //set image from saved profile
        if (mBitmap != null) {
            mIvProfile.setImageBitmap(mBitmap);
        } else {
            //Show add picture text
            mIvProfile.setVisibility(View.GONE);
            containerAddPicture.setVisibility(View.VISIBLE);
            containerAddPicture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dispatchTakePictureIntent();
                }
            });

        }
        //set name from saved profile
        if (!mPreferencesManager.getProfileName().isEmpty()) {
            mEtName.setText(mPreferencesManager.getProfileName());
        }

        mIvProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dispatchTakePictureIntent();
            }
        });

        setProfileToolBar(view, getResources().getString(R.string.profile));


        view.findViewById(R.id.save_profile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveProfile();
            }
        });
        return view;
    }

    //Take picture method
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void saveProfile() {
        if (mBitmap != null && !mEtName.getText().toString().isEmpty()) {
            mPreferencesManager.saveProfile(mBitmap, mEtName.getText().toString());
            Toast.makeText(getContext(), "Profile saved", Toast.LENGTH_SHORT).show();
        } else {
            showAlertDialog(getContext(), "Please add a picture and a name.", new BaseActivity.AlertDialogListener() {
                @Override
                public void onConfirm() {

                }
            });
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            //get image bitmap from camera
            Bundle extras = data.getExtras();
            mBitmap = (Bitmap) extras.get("data");

            containerAddPicture.setVisibility(View.GONE);
            mIvProfile.setVisibility(View.VISIBLE);
            mIvProfile.setImageBitmap(mBitmap);
        }
    }


}
