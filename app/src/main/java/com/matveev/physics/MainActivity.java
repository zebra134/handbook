package com.matveev.physics;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.matveev.physics.show_formulas.FormulasSectionActivity;
import com.matveev.physics.show_theorems.LawsSectionActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.theorems)
    Button theorems;
    @BindView(R.id.formulas)
    Button formulas;
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        unbinder = ButterKnife.bind(this);
        theorems.setOnClickListener(v -> startActivity(
                new Intent(MainActivity.this, LawsSectionActivity.class))
        );
        formulas.setOnClickListener(v -> startActivity(
                        new Intent(MainActivity.this, FormulasSectionActivity.class))
        );
    }

    @Override
    protected void onDestroy() {
        if (unbinder != null){
            unbinder.unbind();
        }
        super.onDestroy();
    }
}
