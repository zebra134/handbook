package com.matveev.physics.show_theorems;

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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.matveev.physics.R;
import com.matveev.physics.repo.database.Entities.LawsEntity;
import com.matveev.physics.repo.database.Formula;
import com.matveev.physics.show_formulas.ShowFormulasActivity;
import com.matveev.physics.show_formulas.ShowFormulasActivityViewModel;
import com.matveev.physics.utils.ViewModelFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ShowLawsActivity extends AppCompatActivity {

    @BindView(R.id.search)
    SearchView searchView;
    @BindView(R.id.list)
    RecyclerView formulasList;
    private Unbinder unbinder;
    private ShowLawsActivityViewModel viewModel;
    private ViewModelFactory viewModelFactory;
    private LawsAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        unbinder = ButterKnife.bind(this);
        viewModelFactory = new ViewModelFactory(getApplication());
        viewModel = new ViewModelProvider(this, viewModelFactory).get(ShowLawsActivityViewModel.class);
        viewModel.getLawsBySection().observe(this, laws -> showLawsList(laws));
        viewModel.fetchLawsBySection(getSection());

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter = (LawsAdapter) formulasList.getAdapter();
                adapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    private void showLawsList(List<LawsEntity> laws) {
        adapter = new LawsAdapter(laws, this);
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

    public class LawsAdapter extends RecyclerView.Adapter<ShowLawsActivity.LawsAdapter.LawsViewHolder> implements Filterable {

        private List<LawsEntity> laws;
        private List<LawsEntity> lawsFull;
        private Context context;

        public LawsAdapter(List<LawsEntity> laws, Context context) {
            this.laws = laws;
            this.context = context;
            if (laws != null){
                this.lawsFull = new ArrayList<>(laws);
            }
        }

        @NonNull
        @Override
        public ShowLawsActivity.LawsAdapter.LawsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_laws_list, parent, false);
            return new ShowLawsActivity.LawsAdapter.LawsViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ShowLawsActivity.LawsAdapter.LawsViewHolder holder, int position) {
            holder.bindData(laws.get(position));
        }

        @Override
        public int getItemCount() {
            return laws != null ? laws.size() : 0;
        }

        @Override
        public Filter getFilter() {
            return lawsFilter;
        }

        private Filter lawsFilter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<LawsEntity> filteredList = new ArrayList<>();
                if (constraint == null || constraint.length() == 0) {
                    filteredList.addAll(lawsFull);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();
                    for (LawsEntity law : lawsFull) {
                        if (law.getTitle().toLowerCase().contains(filterPattern)) {
                            filteredList.add(law);
                        }
                    }
                }

                FilterResults results = new FilterResults();
                results.values = filteredList;

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (laws != null) {
                    laws.clear();
                    laws.addAll((List) results.values);
                    notifyDataSetChanged();
                }
            }
        };

        public class LawsViewHolder extends RecyclerView.ViewHolder{

            @BindView(R.id.lawTitle)
            TextView lawTitle;
            @BindView(R.id.lawText)
            TextView lawText;
            @BindView(R.id.lawImages)
            LinearLayout lawImages;

            public LawsViewHolder(@NonNull View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }

            public void bindData(LawsEntity law){
                lawTitle.setText(law.getTitle());
                lawText.setText(law.getText());
                if (law.getImg_path() != null){
                    Drawable image = getImg(law.getImg_path());
                    lawImages.removeAllViewsInLayout();
                    newImage(lawImages, image);
                }
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

            private void newImage(LinearLayout imageList, Drawable image){
                ImageView imageView = new ImageView(ShowLawsActivity.this);
                imageView.setImageDrawable(image);
                LinearLayout.LayoutParams imageViewLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, getHeight(image));
                imageView.setLayoutParams(imageViewLayoutParams);
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);

                imageList.addView(imageView);
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
