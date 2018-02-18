
package com.android.project3.recipesapp.data;

import javax.annotation.Generated;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Ingredient implements Parcelable
{

    @SerializedName("quantity")
    @Expose
    private int quantity;
    @SerializedName("measure")
    @Expose
    private String measure;
    @SerializedName("ingredient")
    @Expose
    private String ingredient;
    public final static Parcelable.Creator<Ingredient> CREATOR = new Creator<Ingredient>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Ingredient createFromParcel(Parcel in) {
            Ingredient instance = new Ingredient();
            instance.quantity = ((int) in.readValue((int.class.getClassLoader())));
            instance.measure = ((String) in.readValue((String.class.getClassLoader())));
            instance.ingredient = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public Ingredient[] newArray(int size) {
            return (new Ingredient[size]);
        }

    }
    ;

    /**
     * 
     * @return
     *     The quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * 
     * @param quantity
     *     The quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * 
     * @return
     *     The measure
     */
    public String getMeasure() {
        return measure;
    }

    /**
     * 
     * @param measure
     *     The measure
     */
    public void setMeasure(String measure) {
        this.measure = measure;
    }

    /**
     * 
     * @return
     *     The ingredient
     */
    public String getIngredient() {
        return ingredient;
    }

    /**
     * 
     * @param ingredient
     *     The ingredient
     */
    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(quantity);
        dest.writeValue(measure);
        dest.writeValue(ingredient);
    }

    public int describeContents() {
        return  0;
    }

}
