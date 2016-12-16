package com.highfive.highfive.fragments;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.highfive.highfive.R;
import com.highfive.highfive.services.auth.Authenticator;
import com.highfive.highfive.services.messaging.Chat;
import com.highfive.highfive.services.messaging.ChatHolder;
import com.nononsenseapps.filepicker.FilePickerActivity;

import java.io.File;
import java.util.ArrayList;

import id.zelory.compressor.Compressor;

import static com.highfive.highfive.services.Constants.FILE_CODE;

/**
 * Created by heat_wave on 12/4/16.
 */


public class ChatFragment extends Fragment {

    private RecyclerView chatView;
    private FirebaseRecyclerAdapter chatAdapter;
    private EditText message;
    private DatabaseReference ref;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_chat, container, false);
        message = (EditText) v.findViewById(R.id.message_text);
        v.findViewById(R.id.send_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref.push().setValue(new Chat(Authenticator.getCurrentUser().getEmail(),
                        Authenticator.getCurrentUser().getUid(), message.getText().toString()));
                message.setText("");
            }
        });
        v.findViewById(R.id.attach_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), FilePickerActivity.class);

                i.putExtra(FilePickerActivity.EXTRA_ALLOW_MULTIPLE, false);
                i.putExtra(FilePickerActivity.EXTRA_ALLOW_CREATE_DIR, false);
                i.putExtra(FilePickerActivity.EXTRA_MODE, FilePickerActivity.MODE_FILE);

                i.putExtra(FilePickerActivity.EXTRA_START_PATH, Environment.getExternalStorageDirectory().getPath());

                startActivityForResult(i, FILE_CODE);
            }
        });
        chatView = (RecyclerView) v.findViewById(R.id.chat);
        chatView.setHasFixedSize(true);
        chatView.setLayoutManager(new LinearLayoutManager(getContext()));

        ref = FirebaseDatabase.getInstance().getReference();
        ref.limitToLast(5).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot msgSnapshot : snapshot.getChildren()) {
                    Chat msg = msgSnapshot.getValue(Chat.class);
                    Log.i("Chat", msg.getName() + ": " + msg.getText());
                }
            }

            @Override
            public void onCancelled(DatabaseError firebaseError) {
                Log.e("Chat", "The read failed: " + firebaseError.getDetails());
            }
        });

        chatAdapter = new FirebaseRecyclerAdapter<Chat, ChatHolder>(Chat.class, android.R.layout.two_line_list_item, ChatHolder.class, ref) {
            @Override
            public void populateViewHolder(ChatHolder chatMessageViewHolder, Chat chatMessage, int position) {
                chatMessageViewHolder.setName(chatMessage.getName());
                chatMessageViewHolder.setText(chatMessage.getText());
            }
        };
        chatView.setAdapter(chatAdapter);

        return v;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == FILE_CODE && resultCode == Activity.RESULT_OK) {
            Uri uri = null;
            if (data.getBooleanExtra(FilePickerActivity.EXTRA_ALLOW_MULTIPLE, false)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    ClipData clip = data.getClipData();

                    if (clip != null) {
                        for (int i = 0; i < clip.getItemCount(); i++) {
                            uri = clip.getItemAt(i).getUri();
                        }
                    }
                } else {
                    ArrayList<String> paths = data.getStringArrayListExtra
                            (FilePickerActivity.EXTRA_PATHS);

                    if (paths != null) {
                        for (String path: paths) {
                            uri = Uri.parse(path);
                        }
                    }
                }

            } else {
                uri = data.getData();
            }
            File original = new File(uri.getPath());
            File result = Compressor.getDefault(getContext()).compressToFile(original);
            Toast.makeText(getContext(), result.length() + " vs. " + original.length(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        chatAdapter.cleanup();
    }
}