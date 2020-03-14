package com.example.sql.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sql.KEY;
import com.example.sql.R;
import com.example.sql.database.DBHelper;
import com.example.sql.model.Detail;
import com.example.sql.model.User;

import net.sqlcipher.database.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private TextView txtUserName;
    private Button btnDeleteUser,btnAddDetail,btnUpdateDetail,btnDeleteDetail;
    private EditText edtDay,edtNumber,edtContent;
    private RecyclerView rcvDetail;

    private List<Detail> details = new ArrayList<>();
    private Detail detailSave;
    private DetailAdapter detailAdapter;

    private MainActivity mainActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activity);

        SQLiteDatabase.loadLibs(this);

        initView();

        Intent intent = getIntent();
        final User user = (User) intent.getSerializableExtra(KEY.USER);
        assert user != null;
        txtUserName.setText(user.getUserName());

        details.addAll(DBHelper.getInstance(this).getAllDetail());
        detailAdapter = new DetailAdapter(this,details);
        rcvDetail.setLayoutManager(new LinearLayoutManager(this));
        rcvDetail.setAdapter(detailAdapter);

        btnDeleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentResult = new Intent();
                intentResult.putExtra(KEY.DELETE_USER,true);
                setResult(RESULT_OK, intentResult);
                finish();
            }
        });

        detailAdapter.setOnDetailItemClickListener(new DetailAdapter.OnDetailItemClickListener() {
            @Override
            public void OnItemClicked(int position) {
                detailSave = details.get(position);
                edtDay.setText(detailSave.getDay());
                edtNumber.setText(detailSave.getNumber());
                edtContent.setText(detailSave.getContent());
            }
        });

        btnAddDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper.getInstance(DetailActivity.this).insertNewDetail(user.getUseID(),edtDay.getText().toString(),edtNumber.getText().toString(),edtContent.getText().toString());
                reloadDetails();
            }
        });

        btnUpdateDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper.getInstance(DetailActivity.this).updateDetail(detailSave.getDetailsId(),
                        edtDay.getText().toString(),
                        edtNumber.getText().toString(),
                        edtContent.getText().toString());
                reloadDetails();
            }
        });

        btnDeleteDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper.getInstance(DetailActivity.this).deleteDetails(detailSave.getDetailsId());
                reloadDetails();
            }
        });
    }

    private void reloadDetails() {
        details.clear();
        details.addAll(DBHelper.getInstance(this).getAllDetail());
        detailAdapter.notifyDataSetChanged();
    }

    private void initView() {
        txtUserName =findViewById(R.id.txtUserName);
        btnDeleteUser =findViewById(R.id.btnDeleteUser);
        btnAddDetail =findViewById(R.id.btnAddDetail);
        btnUpdateDetail =findViewById(R.id.btnUpdateDetail);
        btnDeleteDetail =findViewById(R.id.btnDeleteDetail);
        edtDay =findViewById(R.id.edtDay);
        edtNumber =findViewById(R.id.edtNumber);
        edtContent =findViewById(R.id.edtContent);
        rcvDetail =findViewById(R.id.rcvDetail);
    }
}
