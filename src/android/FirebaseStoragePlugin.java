package by.azharjaliawala.cordova.firebase;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageException;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.firebase.FirebaseException;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.PluginResult;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.json.JSONException;
import org.json.JSONObject;

public class FirebaseStoragePlugin extends CordovaPlugin {

    public void uploadPicture(String picUrl, String picName, final CallbackContext callbackContext) {
        FirebaseStorage storage = FirebaseStorage.getInstance();

        // Create a storage reference from our app
        StorageReference storageRef = storage.getReference();

        Uri file = Uri.fromFile(new File(picUrl));
        StorageMetadata metadata = new StorageMetadata.Builder()
                .setContentType("image/jpeg")
                .build();
        StorageReference imageRef = storageRef.child("images/"+file.getLastPathSegment());
        UploadTask uploadTask = imageRef.putFile(file, metadata);

        uploadTask.addOnSuccessListener(cordova.getActivity(), new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata
                callbackContext.success();
            }
        }).addOnFailureListener(cordova.getActivity(), new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                callbackContext.error("Upload failed: " + exception.getMessage());
            }
        });
    }
}
