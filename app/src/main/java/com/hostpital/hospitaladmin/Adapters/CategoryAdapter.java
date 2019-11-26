package com.hostpital.hospitaladmin.Adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hostpital.hospitaladmin.Models.Category;
import com.hostpital.hospitaladmin.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Arbab on 8/3/2019.
 */

public class CategoryAdapter extends ArrayAdapter<Category> {
    private List<Category> categoryList = new ArrayList<>();

    public CategoryAdapter(@NonNull Context context, int resource, int spinnerText, @NonNull List<Category> categoryList) {
        super(context, resource, spinnerText, categoryList);
        this.categoryList = categoryList;
    }

    @Override
    public Category getItem(int position) {
        return categoryList.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position);

    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position);
    }

    /**
     * Gets the state object by calling getItem and
     * Sets the state name to the drop-down TextView.
     *
     * @param position the position of the item selected
     * @return returns the updated View
     */
    private View initView(int position) {
        Category category = getItem(position);
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.category_list, null);
        TextView textView =  v.findViewById(R.id.spinnerText);
        textView.setText(category.getCategory());
        return v;

    }
}
