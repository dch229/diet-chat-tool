/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diet.utils.postprocessing.spreadsheet.CombinedDyadDowngradeUpgrade;



/**
 *
 * @author sre
 */
public class Contribution1 {
    
    
    public int dels;
    public int edits;
    public String text;
    public String descType;
    public long sanity;
    public String type;
    
    public Contribution1(int dels, int edits, String text, String descType, long sanity, String type){
        this.dels=dels;
        this.edits=edits;
        this.text=text;
        this.descType = descType;
        if(descType.equalsIgnoreCase(""))this.descType="N";
        
        this.sanity = sanity;
        this.type=type;
    }
    
    
    
    
    
}
