/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package naive_bayes;

/**
 *
 * @author Scarletta's
 */
public class ListElement {
    private String DisAttrName;
    private float count;
    
    public ListElement () {
        DisAttrName = "";
        this.count = 0;
    }
    public ListElement (String name, float count) {
        DisAttrName = name;
        this.count = count;
    }
    //Setter
    public void setDisAttrName (String name) {
        DisAttrName = name;
    }
    
    public void setCount (float num) {
        count = num;
    }
    
    //Getter
    public String getDisAttrName() {
        return DisAttrName;
    }
    
    public float getCount() {
        return count;
    }
    
}
