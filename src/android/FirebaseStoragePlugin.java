package by.azharjaliawala.cordova.firebase;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageException;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.firebase.FirebaseException;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.PluginResult;

public class FirebaseStoragePlugin extends CordovaPlugin implements AuthStateListener {

    public void uploadPicture(String picUrl, String picName, CallbackContext callbackContext) {
        FirebaseStorage storage = FirebaseStorage.getInstance();

        // Create a storage reference from our app
        StorageReference storageRef = storage.getReference();

        Uri file = Uri.fromFile(new File(picUrl));
        StorageReference riversRef = storageRef.child("images/" + picName);
        uploadTask = riversRef.putFile(file);

        uploadTask.addOnFailureListener(cordova.getActivity(), new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                callbackContext.error(exception.getMessage());
            }
        }).addOnSuccessListener(cordova.getActivity(), new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata
                callbackContext.success(taskSnapshot.getMetadata());
            }
        });
    }
}
