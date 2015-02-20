/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package client;


 import java.io.Serializable;
import java.util.Vector;

public class Game
    implements Serializable {
  
  Vector mazes = new Vector();
  

  public Game(Vector v) {
    mazes = v;
      }

  
  public Maze getMazeNo(int i) {
    return (Maze) mazes.elementAt(i);
  }

  public Vector getAllMazes() {
    return mazes;
  }

  public int getCurrentMazeNumberOfMaze(Maze m){
       int idx =-1;
       try{
            idx = mazes.indexOf(m);
                
       }catch (Exception e){
            e.printStackTrace();
            return -1;
       }
       return idx;
  }
  }
  
 

  
