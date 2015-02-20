
package diet.server.conversationhistory.ui;

import java.awt.Color;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import diet.server.Conversation;
import diet.server.Participant;
import diet.server.DeprecatedWindowController_MustReplace;
import diet.server.WindowAndRecipientsManagerPermissions;

public class JPermissionsTableModel extends AbstractTableModel {

  private Conversation c;
  TableModel thisTableModel;
  JTable jt;

  public JPermissionsTableModel(JTable jt,Conversation c) {
     super();
     this.c=c;
     this.jt = jt;
     this.thisTableModel = this;
  }

  public void refresh(){

        
        SwingUtilities.invokeLater(new Runnable(){public void run(){
      ///      jt.setRowSorter(new JDiETTableSorter(thisTableModel));
            fireTableStructureChanged();
            fireTableDataChanged(); 
            
            
            //System.err.println("REPAINTING -----------------------------------------------------------------------------------");
         }  });        
    }

  public boolean isCellEditable(int x, int y){
    return false;
  }

   public String getColumnName(int column){
     try{  
     Vector ps = c.getParticipants().getAllParticipants();
     if(column>ps.size())return " ";
     if(column==0){
         return "Participant";
     }
     else{
         Participant p = (Participant)ps.elementAt(column-1);
         return p.getUsername();
     }
     }catch(Exception e){
         return "SETTINGUP";
     }
   }

  public Object getValueAt(int x, int y){
     try{ 
     
         
     
     }catch (Exception e){
         return "DEPRECATED";
     } 
     return "DEPRECATED";
  }

  
  
  public int getRowCount(){
     try{ 
       //System.err.println("GETTING ROWCOUNT -----------------------------------------------------------------------------------"+c.getParticipants().getAllParticipants().size());  
       return c.getParticipants().getAllParticipants().size();
     }catch (Exception e){
         //System.err.println("ERROR DRAWING TABLEMODEL2 -----------------------------------------------------------------------------------");
         return 0;
     }  
  }
  public int getColumnCount(){
      
      try{ 
       //System.err.println("HAS COLUMNS "+c.getParticipants().getAllParticipants().size());   
       return c.getParticipants().getAllParticipants().size()+1;
     }catch (Exception e){
         //System.err.println("ERROR DRAWING TABLEMODEL -----------------------------------------------------------------------------------");
         return 0;
     }  
  }



}