package com.example.ngothihuyen.chattok.View;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.ngothihuyen.chattok.AdapterView.AdapterMessage;
import com.example.ngothihuyen.chattok.Model.Message;
import com.example.ngothihuyen.chattok.MyParcelable;
import com.example.ngothihuyen.chattok.Presentation.ChatPresenter;
import com.example.ngothihuyen.chattok.Presentation.IChatPresenter;
import com.example.ngothihuyen.chattok.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;

import hani.momanii.supernova_emoji_library.Actions.EmojIconActions;
import hani.momanii.supernova_emoji_library.Helper.EmojiconEditText;
import hani.momanii.supernova_emoji_library.Helper.EmojiconTextView;

public class FlagmentChat extends AppCompatActivity  implements IChatView{

    private static final int IMAGE_REQUEST=1;
    private Uri imageUri;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private DatabaseReference reference;
    private StorageTask uploadTask;
     private AppBarLayout appBarLayout;
    private ImageButton btSend;
    private ImageButton btSendImage;
    private ImageButton btSendIcon;
    private Toolbar toolbar;
    private TextView userName;
    private EmojiconEditText edtInput;
    private  View rootView;
    private TextView nameUser;
    private EmojIconActions emojIcon;
    private FirebaseAuth Auth=FirebaseAuth.getInstance();
    private FirebaseDatabase database=FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference;
    private FirebaseAuth auth=FirebaseAuth.getInstance();
    private String ConverID;
    public final  ArrayList<Message>listItems  = new ArrayList<>();
    private FirebaseDatabase  firebaseDatabase  = FirebaseDatabase.getInstance();
   private DatabaseReference dataRf =  firebaseDatabase.getReference("Messages");
   private  String nameUserSend;
   private RecyclerView listMessage;
    public AdapterMessage adapter;
    private int flag=0;
  private   ChatPresenter chatPresenter=new ChatPresenter();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_chat);
        listMessage=(RecyclerView) findViewById(R.id.recycle_list);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getBaseContext());
        linearLayoutManager.setStackFromEnd(true);
        listMessage.setLayoutManager(linearLayoutManager);
        btSend=(ImageButton) findViewById(R.id.bt_send);
        edtInput=(EmojiconEditText) findViewById(R.id.input);
        nameUser=(TextView)findViewById(R.id.name_user);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        rootView = findViewById(R.id.activity_main);
        btSendImage=(ImageButton)findViewById(R.id.bt_sendImage);
        btSendIcon=(ImageButton)findViewById(R.id.bt_sendIcon);

        emojIcon=new EmojIconActions(this,rootView,edtInput,btSendIcon);
        emojIcon.ShowEmojIcon();
        emojIcon.setIconsIds(R.drawable.ic_action_keyboard, R.drawable.smiley);

        setSupportActionBar(toolbar);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference("uploads");

       // userName=(TextView)findViewById(R.id.username);
        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createMessage(edtInput.getText().toString(),Auth.getCurrentUser().getUid().toString());
                edtInput.setText("");
            }
        });
        btSendIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendIcon();
            }
        });

        btSendImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendImage();
            }
        });
       Bundle b = getIntent().getExtras();
        MyParcelable obj = (MyParcelable) b. getParcelable("Conversation");
        ConverID=obj.getmData().toString();
        Log.d("conver",ConverID.toString());
         MyParcelable obj2=(MyParcelable)b.getParcelable("User_ID");
         nameUserSend=obj2.getmData().toString();
         nameUser.setText(nameUserSend);


         DisplayMessage();
    }

    private void sendIcon() {

        emojIcon.setUseSystemEmoji(true);
        edtInput.setUseSystemDefault(true);


    }

    private String  getFileExtension(Uri uri)
    {
        ContentResolver contentResolver=getApplicationContext().getContentResolver();
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void SendImage() {

        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMAGE_REQUEST);
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

       /* final ProgressDialog pd=new ProgressDialog(FlagmentChat.class);
        pd.setMessage("Uploading!");
        pd.show();*/

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
                        createMessageImage(mUri,Auth.getCurrentUser().getUid().toString());
                       // pd.dismiss();
                        finish();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Failed!", Toast.LENGTH_SHORT).show();
                       // pd.dismiss();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                  //  pd.dismiss();
                }
            });
        }
        else
        {
            Toast.makeText(getApplicationContext(),"No image selected!",Toast.LENGTH_SHORT).show();
        }
    }

    private void createMessageImage(String mUri, String s) {
        Message message=new Message();
        message.MessageImage(mUri,s);
        String messId=dataRf.push().getKey();
        message.setConversation(ConverID);
        dataRf.child(messId).setValue(message);

        dataRf=database.getReference("Conversation");
        dataRf.child(ConverID).child("lastMessage").setValue(messId);
        dataRf.child(ConverID).child("lasttimeUpdate").setValue(message.getTime());
    }

    private void createMessage(String input,String UserID) {
        databaseReference=database.getReference("Messages");
        Message message=new Message(input,UserID);
        String messId=databaseReference.push().getKey();
         message.setConversation(ConverID);
        databaseReference.child(messId).setValue(message);

        databaseReference=database.getReference("Conversation");
        databaseReference.child(ConverID).child("lastMessage").setValue(messId);
        databaseReference.child(ConverID).child("lasttimeUpdate").setValue(message.getTime());

    }

       private void DisplayMessage()
       {
             adapter=new AdapterMessage(this,listItems);
             listMessage.setAdapter(adapter);
             adapter.notifyItemInserted(listItems.size()-1);
             listMessage.scrollToPosition(listItems.size()-1);
             listItems.clear();
           chatPresenter.getMessage(this);


       }

    @Override
    public void getListMessage(Message message) {

        if(message.getConversation().compareTo(ConverID)==0) {
            {

                for(int i=0;i<listItems.size();i++)
                {
                    Message mes=listItems.get(i);
                    if(message.getIdMessage().compareTo(mes.getIdMessage())==0)
                    {
                        listItems.set(i,message);
                        flag=1;
                        adapter.notifyDataSetChanged();
                    }

                }
                if(flag==0) {
                    listItems.add(message);
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }


}