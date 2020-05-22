package com.matveev.physics.show_formulas;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.matveev.physics.R;
import com.matveev.physics.repo.database.Entities.ImageEntity;
import com.matveev.physics.repo.database.Formula;
import com.matveev.physics.utils.ViewModelFactory;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ShowFormulasActivity extends AppCompatActivity {

    @BindView(R.id.search)
    SearchView searchView;
    @BindView(R.id.list)
    RecyclerView formulasList;
    private Unbinder unbinder;
    private ShowFormulasActivityViewModel viewModel;
    private ViewModelFactory viewModelFactory;
    private FormulasAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        unbinder = ButterKnife.bind(this);
        viewModelFactory = new ViewModelFactory(getApplication());
        viewModel = new ViewModelProvider(this, viewModelFactory).get(ShowFormulasActivityViewModel.class);
        viewModel.getFormulasBySection().observe(this, formulas -> showFormulasList(formulas));
        viewModel.fetchFormulasBySection(getSection());

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter = (FormulasAdapter) formulasList.getAdapter();
                adapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    private void showFormulasList(List<Formula> formulas) {
        adapter = new FormulasAdapter(formulas, this);
        formulasList.setAdapter(adapter);
        formulasList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
    }

    @Override
    protected void onDestroy() {
        if (unbinder != null){
            unbinder.unbind();
        }
        super.onDestroy();
    }

    private String getSection(){
        Intent intent = getIntent();
        return intent.getStringExtra("section");
    }

    public class FormulasAdapter extends RecyclerView.Adapter<FormulasAdapter.PhysicsViewHolder> implements Filterable {

        private List<Formula> formulas;
        private List<Formula> formulasFull;
        private Context context;

        public FormulasAdapter(List<Formula> formulas, Context context) {
            this.formulas = formulas;
            this.context = context;
            if (formulas != null){
                this.formulasFull = new ArrayList<>(formulas);
            }
        }

        @NonNull
        @Override
        public PhysicsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_formulas_list, parent, false);
            return new PhysicsViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull PhysicsViewHolder holder, int position) {
            holder.bindData(formulas.get(position));
        }

        @Override
        public int getItemCount() {
            return formulas != null ? formulas.size() : 0;
        }

        @Override
        public Filter getFilter() {
            return formulasFilter;
        }

        private Filter formulasFilter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<Formula> filteredList = new ArrayList<>();
                if (constraint == null || constraint.length() == 0) {
                    filteredList.addAll(formulasFull);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();
                    for (Formula formula : formulasFull) {
                        if (formula.getTextEntity().getText().toLowerCase().contains(filterPattern)) {
                            filteredList.add(formula);
                        }
                    }
                }

                FilterResults results = new FilterResults();
                results.values = filteredList;

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (formulas != null) {
                    formulas.clear();
                    formulas.addAll((List) results.values);
                    notifyDataSetChanged();
                }
            }
        };

        public class PhysicsViewHolder extends RecyclerView.ViewHolder{

            @BindView(R.id.formulaText)
            TextView formulaText;
            @BindView(R.id.formulaImages)
            LinearLayout formulaImages;

            public PhysicsViewHolder(@NonNull View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }

            public void bindData(Formula formula){
                formulaText.setText(formula.getTextEntity().getText());
                for (ImageEntity imageEntity : formula.getImageEntities()){
                    Drawable image = getImg(imageEntity.getImg_path());
                    formulaImages.removeAllViewsInLayout();
                    newImage(formulaImages, image);
                }
            }

            private void newImage(LinearLayout imageList, Drawable image){
                ImageView imageView = new ImageView(ShowFormulasActivity.this);
                imageView.setImageDrawable(image);
                LayoutParams imageViewLayoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, getHeight(image));
                imageView.setLayoutParams(imageViewLayoutParams);
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);

                imageList.addView(imageView);
            }

            private int getHeight(Drawable image){
                int height = image.getIntrinsicHeight();
                if (height <= 20){
                    height = 40;
                } else if (height <= 50){
                    height = 100;
                } else if (height <= 100){
                    height = 200;
                } else {
                    height = 300;
                }
                return height;
            }

            private Drawable getImg(String imgPath){
                InputStream inputStream = null;
                Drawable drawable = null;
                try{
                    inputStream = context.getAssets().open(imgPath);
                    drawable = Drawable.createFromStream(inputStream, null);

                }
                catch (IOException e){
                    e.printStackTrace();
                }
                finally {
                    try{
                        if(inputStream!=null)
                            inputStream.close();
                    }
                    catch (IOException ex){
                        ex.printStackTrace();
                    }
                }
                return drawable;
            }
        }
    }
}
