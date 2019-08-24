package by.azharjaliawala.cordova.firebase;

import by.chemerisuk.cordova.support.CordovaMethod;
import by.chemerisuk.cordova.support.ReflectiveCordovaPlugin;

import android.util.Log;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.apache.cordova.CallbackContext;

import org.json.JSONException;
import org.json.JSONObject;

public class FirebaseStoragePlugin extends ReflectiveCordovaPlugin {

    private static final String TAG = "FirebaseStorage";

    private FirebaseStorage storage;

    @Override
    protected void pluginInitialize() {
        Log.d(TAG, "Starting Firebase Storage plugin");

        this.storage = FirebaseStorage.getInstance();        
    }

    @CordovaMethod
    public void uploadPicture(String fileUri, String filePath, final CallbackContext callbackContext) {
        StorageReference storageRef = storage.getReference();

        Uri file = Uri.parse(fileUri);
        StorageReference imageRef = storageRef.child(filePath);
        UploadTask uploadTask = imageRef.putFile(file);

        uploadTask.addOnSuccessListener(cordova.getActivity(), new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata
                imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        callbackContext.success(uri.toString());
                    }
                });
            }
        }).addOnFailureListener(cordova.getActivity(), new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                callbackContext.error("Upload failed: " + exception.getMessage());
            }
        });
    }
}