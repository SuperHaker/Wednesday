package com.example.android.wednesday.models;

import java.io.Serializable;

/**
 * Created by hp pc on 1/27/2017.
 */

public class CategoryModel implements Serializable{

    public String categoryName;
    public String categoryPhoto;

    public CategoryModel(){}


    public CategoryModel(String categoryName){
        this.categoryName = categoryName;

    }

    public CategoryModel(String categoryName, String categoryPhoto){
        this.categoryName = categoryName;
        this.categoryPhoto = categoryPhoto;
    }

}
