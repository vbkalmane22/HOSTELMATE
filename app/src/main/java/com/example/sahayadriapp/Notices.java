package com.example.sahayadriapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class Notices extends AppCompatActivity {

    private ListView pdfListView;
    private List<String> pdfList;
    private ArrayAdapter<String> pdfAdapter;

    private FirebaseStorage storage;
    private StorageReference storageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notices);

        pdfListView = findViewById(R.id.pdf_list_view);

        pdfList = new ArrayList<>();
        pdfAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, pdfList);
        pdfListView.setAdapter(pdfAdapter);

        storage = FirebaseStorage.getInstance("gs://sahayadri-app.appspot.com");
        storageRef = storage.getReference();

        loadPDFsFromFirebase();

        pdfListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String selectedPDF = pdfList.get(position);
                openPDF(selectedPDF);
            }
        });
    }

    private void loadPDFsFromFirebase() {
        storageRef.listAll().addOnSuccessListener(listResult -> {
            for (StorageReference item : listResult.getItems()) {
                item.getDownloadUrl().addOnSuccessListener(uri -> {
                    String downloadUrl = uri.toString();
                    String pdfName = item.getName();

                    pdfList.add(pdfName);
                    pdfAdapter.notifyDataSetChanged();
                });
            }
        });
    }

    private void openPDF(String pdfName) {
        StorageReference pdfRef = storageRef.child(pdfName);
        pdfRef.getDownloadUrl().addOnSuccessListener(uri -> {
            String downloadUrl = uri.toString();

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.parse(downloadUrl), "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            try {
                startActivity(intent);
            } catch (Exception e) {
                // Handle the exception or show an error message
            }
        });
    }
}