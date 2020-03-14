package com.example.sql.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.sql.KEY;
import com.example.sql.R;
import com.example.sql.database.DBHelper;
import com.example.sql.model.User;

import net.sqlcipher.database.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText edtName, edtPhone;
    private Button btnAdd;
    private RecyclerView rcvUser;

    private UserListAdapter userListAdapter;

    private List<User> users = new ArrayList<>();
    private User userSave;
    private int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SQLiteDatabase.loadLibs(this);

        initView();

        users.addAll(DBHelper.getInstance(this).getAllUser());
        userListAdapter = new UserListAdapter(this, users);
        rcvUser.setLayoutManager(new LinearLayoutManager(this));
        rcvUser.setAdapter(userListAdapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!(edtName.getText().toString().isEmpty() || edtPhone.getText().toString().isEmpty())) {
                    DBHelper.getInstance(MainActivity.this).insertNewUser(edtName.getText().toString(), edtPhone.getText().toString());
                    reloadUser();
                }
            }
        });

        userListAdapter.setOnUserItemClickListener(new UserListAdapter.OnUserItemClickListener() {
            @Override
            public void OnItemClicked(int position) {
                Bundle bundle = new Bundle();
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                userSave = users.get(position);
                bundle.putSerializable(KEY.USER, userSave);
                intent.putExtras(bundle);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

    }

    private void reloadUser() {
        users.clear();
        users.addAll(DBHelper.getInstance(this).getAllUser());
        userListAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                assert data != null;
                if (data.getBooleanExtra(KEY.DELETE_USER, false)) {
                    users.remove(userSave);
                    DBHelper.getInstance(this).deleteUser(userSave.getUseID());
                    userListAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    private void initView() {
        edtName = findViewById(R.id.edtName);
        edtPhone = findViewById(R.id.edtPhone);
        btnAdd = findViewById(R.id.btnAdd);
        rcvUser = findViewById(R.id.rcvUser);
    }
}
