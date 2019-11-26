package com.hostpital.hospitaladmin.Models;

import java.util.List;

/**
 * Created by Arbab on 8/3/2019.
 */

public class Category {
    private String categoty;
    private int id;
    private List<String> Subcategoty;

    public Category() {
    }

    public Category(String categoty, List<String> Subcategoty, int id) {
        this.categoty = categoty;
        this.Subcategoty = Subcategoty;
        this.id = id;
    }

    public String getCategory() {
        return categoty;
    }

    public List<String> getSubcategoty() {
        return Subcategoty;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
