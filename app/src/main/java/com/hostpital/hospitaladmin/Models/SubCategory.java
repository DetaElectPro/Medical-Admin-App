package com.hostpital.hospitaladmin.Models;

/**
 * Created by Arbab on 8/3/2019.
 */

public class SubCategory {
    private String name;
//    private String categoty;
    private int id;
//    private List<String> Subcategoty;

    public SubCategory() {
    }

    public SubCategory(String name, int id) {
        this.name = name;
        this.id = id;
    }
//    public Category(String categoty, List<String> Subcategoty) {
//        this.categoty = categoty;
//        this.Subcategoty = Subcategoty;
////        this.id = id;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public List<String> getSubcategoty() {
//        return Subcategoty;
//    }
//
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
