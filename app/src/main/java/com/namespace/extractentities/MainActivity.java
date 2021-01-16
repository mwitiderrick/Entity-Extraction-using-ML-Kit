package com.namespace.extractentities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.nl.entityextraction.EntityAnnotation;
import com.google.mlkit.nl.entityextraction.EntityExtraction;
import com.google.mlkit.nl.entityextraction.EntityExtractionParams;
import com.google.mlkit.nl.entityextraction.EntityExtractor;
import com.google.mlkit.nl.entityextraction.EntityExtractorOptions;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        editText = findViewById(R.id.editText);
    }
    public void extractEntities(View view){
        String text = String.valueOf(editText.getText());
        EntityExtractor entityExtractor =
                EntityExtraction.getClient( new EntityExtractorOptions.Builder(EntityExtractorOptions.ENGLISH)
                                .build());

        entityExtractor
                .downloadModelIfNeeded()
                .addOnSuccessListener(
                        aVoid -> {
                            // Model downloading succeeded, you can call the extraction API here.
                            EntityExtractionParams params = new EntityExtractionParams
                                    .Builder(text)
                                    .build();

                            entityExtractor
                                    .annotate(params)
                                    .addOnSuccessListener(new OnSuccessListener<List<EntityAnnotation>>() {
                                        @Override
                                        public void onSuccess(List<EntityAnnotation> entityAnnotations) {
                                            // Annotation process was successful, you can parse the EntityAnnotations list here.
                                            textView.setText("");
                                            textView.append(entityAnnotations.toString());
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            // Check failure message here.
                                        }
                                    });
                        })
                .addOnFailureListener(
                        exception -> {
                            // Model downloading failed.
                        });

    }
}