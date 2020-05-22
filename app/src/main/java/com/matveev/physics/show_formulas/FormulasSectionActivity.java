package com.matveev.physics.show_formulas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.matveev.physics.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FormulasSectionActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.kinematics)
    Button kinematics;
    @BindView(R.id.dynamics)
    Button dynamics;
    @BindView(R.id.statics)
    Button statics;
    @BindView(R.id.hydrostatics)
    Button hydrostatics;
    @BindView(R.id.pulse)
    Button pulse;
    @BindView(R.id.energy)
    Button energy;
    @BindView(R.id.molecularPhysics)
    Button molecularPhysics;
    @BindView(R.id.thermodynamics)
    Button thermodynamics;
    @BindView(R.id.electrostatics)
    Button electrostatics;
    @BindView(R.id.electricity)
    Button electricity;
    @BindView(R.id.magnetism)
    Button magnetism;
    @BindView(R.id.vibrations)
    Button vibrations;
    @BindView(R.id.optics)
    Button optics;
    @BindView(R.id.atomicPhysics)
    Button atomicPhysics;
    @BindView(R.id.theoryOfRelativity)
    Button theoryOfRelativity;

    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulas_section);

        unbinder = ButterKnife.bind(this);

        kinematics.setOnClickListener(this);
        dynamics.setOnClickListener(this);
        statics.setOnClickListener(this);
        hydrostatics.setOnClickListener(this);
        pulse.setOnClickListener(this);
        energy.setOnClickListener(this);
        molecularPhysics.setOnClickListener(this);
        thermodynamics.setOnClickListener(this);
        electrostatics.setOnClickListener(this);
        electricity.setOnClickListener(this);
        magnetism.setOnClickListener(this);
        vibrations.setOnClickListener(this);
        optics.setOnClickListener(this);
        atomicPhysics.setOnClickListener(this);
        theoryOfRelativity.setOnClickListener(this);
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
        Intent intent = new Intent(FormulasSectionActivity.this, ShowFormulasActivity.class);
        intent.putExtra("section", section);
        startActivity(intent);
    }
}
