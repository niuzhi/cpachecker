package pack1; 


import org.eclipse.jdt.internal.core.search.SubTypeSearchJob;

import pack3.*;

public class DynamicBindingCall  {
  
  public static void main(String[] args){
        
    SuperType1 obj3 = new SuperType1(1 , 2);
    SuperType1 obj1 = null;
    
    int n = 2;
    int n2 = 4;
    
    if(n == n2) {
      obj1 = new SubType1(1 ,1);
    } else {
      obj1 = new SubSubType1( 1 , 2);
    }
            
    obj1.add();
    Interface1 obj2 = null; 
    
    if(n == n2){
     obj2 = new SubSubType1(3 , 3);
    }else {
     obj2 = new SubType1(3 , 3); 
    }
    
    boolean b11 = false;
    if( obj2 instanceof SubType1) {
      b11 = true;
    }
    
    assert b11;
    
    boolean b10 = obj2 instanceof Interface1;
    
    boolean b1 = obj2.compare();
    assert b1;
     
    
    Interface1 obj4 = null; 
    
    
    
    obj2 = new SubSubType1(3 , 3);
    
    obj4 = obj2;

    b1 = obj4.compare();
    assert b1;    
  }
}