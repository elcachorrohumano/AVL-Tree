package arbolavl;

import static arbolavl.TreePrinter.print;

/**
 *
 * @author elcachorrohumano
 */
public class ArbolAVLmain {
    
    public static void main(String[] args) {
        
        ArbolAVL<Integer> a1 = new ArbolAVL(1);
        for (int i = 2; i < 32; i++)
            a1.add(i);
        
        a1.remove(18);
        a1.remove(13);
        a1.remove(16);
        

        
        
        print(a1.getRaiz());
        
        
    }
    
}
