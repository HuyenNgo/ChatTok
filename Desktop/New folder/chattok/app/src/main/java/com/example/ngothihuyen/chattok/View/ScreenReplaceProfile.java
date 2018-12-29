package com.example.ngothihuyen.chattok.View;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.Toast;

import com.example.ngothihuyen.chattok.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

public class ScreenReplaceProfile extends AppCompatActivity {

    private static final int IMAGE_REQUEST=1;
    private Button btSelectImage;
    private Button btCamera;
    private FirebaseDatabase firebase=FirebaseDatabase.getInstance();
    private DatabaseReference data=firebase.getReference("Users");
    private DatabaseReference reference;
    private FirebaseAuth auth=FirebaseAuth.getInstance();
    private String userID=auth.getCurrentUser().getUid();
    private Uri imageUri;
    private FirebaseStorage storage;
    private StorageReference storageReference;

    private StorageTask uploadTask;

    private final int PICK_IMAGE_REQUEST =100;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_replaceprofile);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference("uploads");

         btSelectImage=(Button)findViewById(R.id.selectpicture);
         btCamera=(Button)findViewById(R.id.camera);
         btSelectImage.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 openImage();

             }
         });
         btCamera.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 createpicture();
             }
         });


    }

    private void openImage() {

        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMAGE_REQUEST);
    }

    private String  getFileExtension(Uri uri)
    {
        ContentResolver contentResolver=getApplicationContext().getContentResolver();
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==IMAGE_REQUEST && resultCode==RESULT_OK && data!=null && data.getData()!=null)
        {
           imageUri=data.getData();

           if(uploadTask!=null && uploadTask.isInProgress())
           {
               Toast.makeText(getApplicationContext()," Uploading in process ",Toast.LENGTH_SHORT).show();
           }
           else {
               uploadImage();

           }
        }
    }

    private  void uploadImage()
    {

        final ProgressDialog pd=new ProgressDialog(ScreenReplaceProfile.this);
        pd.setMessage("Uploading!");
        pd.show();

        if (imageUri!=null)
        {
          final StorageReference fileReference =storageReference.child(System.currentTimeMillis()+"."+getFileExtension(imageUri));

          uploadTask=fileReference.putFile(imageUri);

          uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot,Task<Uri>>(){
              @Override
              public Task<Uri> then(@NonNull Task <UploadTask.TaskSnapshot> task) throws Exception {
                  if (!task.isSuccessful())
                  {
                      throw task.getException();
                  }
                  return fileReference.getDownloadUrl();
              }
          }).addOnCompleteListener(new OnCompleteListener<Uri>() {
              @Override
              public void onComplete(@NonNull Task<Uri> task) {
                  if(task.isSuccessful())
                  {
                      Uri downloadUri=task.getResult();
                       String mUri=downloadUri.toString();
                       reference=FirebaseDatabase.getInstance().getReference("Users").child(userID);
                      HashMap<String,Object> hashMap=new HashMap<>();
                      hashMap.put("avatar",mUri);
                      reference.updateChildren(hashMap);
                      pd.dismiss();

                      finish();
                  }
                  else
                  {
                      Toast.makeText(getApplicationContext(), "Failed!", Toast.LENGTH_SHORT).show();
                      pd.dismiss();
                  }
              }
          }).addOnFailureListener(new OnFailureListener() {
              @Override
              public void onFailure(@NonNull Exception e) {
                  Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                  pd.dismiss();
              }
          });
        }
        else
            {
                Toast.makeText(getApplicationContext(),"No image selected!",Toast.LENGTH_SHORT).show();
            }
    }


    private  void createpicture()
    {


    }
}
