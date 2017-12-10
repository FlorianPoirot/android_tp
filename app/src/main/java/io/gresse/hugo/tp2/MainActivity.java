package io.gresse.hugo.tp2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Display a simple chat connected to Firebase
 */
public class MainActivity extends AppCompatActivity implements ValueEventListener {

    public static final String TAG = MainActivity.class.getSimpleName();

    String              login;
    String              email;
    EditText            mInputEditText;
    Button              mSendButton;
    MessageAdapter      mMessageAdapter;

    DatabaseReference   mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        login = UserStorage.getName(this);
        email = UserStorage.getEmail(this);
        if (login == null || email == null){
            Intent intent = new Intent(this, NamePickerActivity.class);
            this.startActivity(intent);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        mInputEditText = findViewById(R.id.inputEditText);
        mSendButton = findViewById(R.id.sendButton);

        mMessageAdapter = new MessageAdapter(new ArrayList<Message>());

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mMessageAdapter);

        connectAndListenToFirebase();

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mInputEditText.getText().toString().isEmpty()){
                    return;
                }
                DatabaseReference newData = mDatabaseReference.push();
                newData.setValue(
                        new Message(email, mInputEditText.getText().toString(), login, 0L));
                mInputEditText.setText("");
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        mDatabaseReference.removeEventListener(this);
    }

    private void connectAndListenToFirebase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mDatabaseReference = database.getReference("chat/messages");

        mDatabaseReference.addValueEventListener(this);
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        Log.d(TAG, "dataChange : " + dataSnapshot);
        List<Message> items = new ArrayList<>();
        for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
            items.add(messageSnapshot.getValue(Message.class));
        }
        mMessageAdapter.setData(items);
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
