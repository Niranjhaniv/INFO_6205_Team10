/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticalgorithm.domain;

/**
 *
 * @author Niranjanii
 */
public class Professor {
  
    private String id;
    private String name;
    
    public Professor(String id,String name)
    {
        this.id= id;
        this.name=name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() { return name; }
    
    
    
}
