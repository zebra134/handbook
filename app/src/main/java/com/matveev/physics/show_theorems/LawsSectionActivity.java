package com.matveev.physics.show_theorems;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.matveev.physics.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class LawsSectionActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.mechanics)
    Button mechanics;
    @BindView(R.id.thermodynamics)
    Button thermodynamics;
    @BindView(R.id.electricity)
    Button electricity;
    @BindView(R.id.optics)
    Button optics;
    @BindView(R.id.atomicPhysics)
    Button atomicPhysics;

    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laws_section);

        unbinder = ButterKnife.bind(this);

        mechanics.setOnClickListener(this);
        thermodynamics.setOnClickListener(this);
        electricity.setOnClickListener(this);
        optics.setOnClickListener(this);
        atomicPhysics.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        if (unbinder != null){
            unbinder.unbind();
        }
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        Button button = findViewById(v.getId());
        String section = button.getText().toString();
        Intent intent = new Intent(LawsSectionActivity.this, ShowLawsActivity.class);
        intent.putExtra("section", section);
        startActivity(intent);
    }
}
