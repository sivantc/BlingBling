package com.blingbling.sivant.blingbling;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.Toast;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

/**
 * Created by Sivan on 06/04/2017.
 */
//fix bug, when i choose busniess type twice
public abstract class MutualFunc extends AppCompatActivity {
    private final int PICK_PLACE = 1;
    private final int PICK_IMAGE = 2;

    abstract protected void setInfoInUtils();
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_PLACE && resultCode == RESULT_OK) {
                displaySelectedPlaceFromPlacePicker(data);
        }
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            displaySelectedImage(data);
        } else {
            Log.e("request Error", "didn't find");

        }
    }


    protected void pickBusniessAddress(Activity activity) {
        PlacePicker.IntentBuilder intentBuilder = new PlacePicker.IntentBuilder();
        try {
            Intent intent = intentBuilder.build(activity);
            startActivityForResult(intent, PICK_PLACE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    protected void selectImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent.createChooser(intent, "Select an image"), PICK_IMAGE);
    }


    protected void displaySelectedPlaceFromPlacePicker(Intent data) {
        Place placeSelected = PlacePicker.getPlace(data, UtilsBlingBling.getCurrentContextName());
        String busniessName = placeSelected.getName().toString();
        String busniessAddress = placeSelected.getAddress().toString();
        MyLocation location = new MyLocation(busniessName);
        location.setLongitude(placeSelected.getLatLng().longitude);
        location.setLatitude(placeSelected.getLatLng().latitude);
        UtilsBlingBling.setLocation(location);
        String phoneNumber = placeSelected.getPhoneNumber().toString();
        UtilsBlingBling.getEd_busniess_name().setText(busniessName);
        UtilsBlingBling.getEd_busniess_address().setText(busniessAddress);
        UtilsBlingBling.getEd_phone_number().setText(phoneNumber);
    }

    protected void displaySelectedImage(Intent data) {
        UtilsBlingBling.setUri_filePath_busienssSpace(data.getData());
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(UtilsBlingBling.getCurrentContextName().getContentResolver(), UtilsBlingBling.getUri_filePath_busienssSpace());
            UtilsBlingBling.getImage_view_choosen_image().setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void uploadFile(String imageName) {
        if (UtilsBlingBling.getUri_filePath_busienssSpace() != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Uploading....");
            progressDialog.show();
            String udid = UtilsBlingBling.getFirebaseAute().getCurrentUser().getUid();
            StorageReference busniessSpaceRef = UtilsBlingBling.getStorageReference().child("images/busniess/space/" + udid +"/"+ imageName +".jpg");
            busniessSpaceRef.putFile(UtilsBlingBling.getUri_filePath_busienssSpace() )
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(UtilsBlingBling.getCurrentContextName(), "File uploading", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            progressDialog.dismiss();
                            Toast.makeText(UtilsBlingBling.getCurrentContextName(), exception.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            @SuppressWarnings("VisibleForTests") double progress = (100.0 * taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                            progressDialog.setMessage(progress + "% uploading...");
                        }
                    });
        }
        else
            Toast.makeText(this, "Upload image fail! please choose image" , Toast.LENGTH_SHORT).show();


    }

    protected void selectBusniessType() {
        final String[] stringArray_all_busneiss_type = getResources().getStringArray(R.array.selected_busniess_type);
        boolean[] booleanArray_checked_busneiss_type_items = new boolean [stringArray_all_busneiss_type.length];
        AlertDialog.Builder alertBox_busneiss_type_items = new AlertDialog.Builder(this);
        alertBox_busneiss_type_items.setTitle("Choose your busniess type");
        alertBox_busneiss_type_items.setMultiChoiceItems(stringArray_all_busneiss_type, booleanArray_checked_busneiss_type_items, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int position, boolean is_check) {
                if(is_check){
                    if(!UtilsBlingBling.getArrayList_selected_busniess_type_items().contains(position))
                        UtilsBlingBling.getArrayList_selected_busniess_type_items().add(position);
                    else
                        UtilsBlingBling.getArrayList_selected_busniess_type_items().remove(position);
                }

            }
        });
        alertBox_busneiss_type_items.setCancelable(false);
        alertBox_busneiss_type_items.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                String item = "";
                for(int i = 0; i < UtilsBlingBling.getArrayList_selected_busniess_type_items().size(); i++) {
                    item = item + stringArray_all_busneiss_type[UtilsBlingBling.getArrayList_selected_busniess_type_items().get(i)];
                    if(i != UtilsBlingBling.getArrayList_selected_busniess_type_items().size()-1)
                        item = item + ", ";
                }
                UtilsBlingBling.getTextView_selected_busniess_type().setText(item);
            }
        });
        AlertDialog mDialog = alertBox_busneiss_type_items.create();
        mDialog.show();
    }

    protected void seek_bar_km(SeekBar seekBar, int max, String progressBarText, String progressBarUnit) {
        UtilsBlingBling.setProgressBarText(progressBarText);
        UtilsBlingBling.setProgressBarUnit(progressBarUnit);
        seekBar.setMax(max);
        seekBar.setProgress(UtilsBlingBling.getProgressBar());
        seekBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        UtilsBlingBling.getTextViewProgress().setText(UtilsBlingBling.getProgressBarText() + progress + UtilsBlingBling.getProgressBarUnit());
                        UtilsBlingBling.setProgressBar(progress);
                    }
                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                }
        );
    }


}
