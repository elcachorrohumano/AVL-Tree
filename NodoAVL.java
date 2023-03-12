package arbolavl;

/**
 *
 * @author elcachorrohumano
 */
public class NodoAVL<T extends Comparable<T>> implements PrintableNode<T>{
    
    private T elem;
    private NodoAVL<T> izq, der, papa;
    private int fe;
    
    public NodoAVL (T elem) {
        this.elem = elem;
        this.der = null;
        this.izq = null;
        this.papa = null;
        fe = 0;
    }
    
    public int getFe() {
        return fe;
    }
    
    public void setFe(int fe) {
        this.fe = fe;
    }

    public T getElem() {
        return elem;
    }

    public NodoAVL<T> getIzq() {
        return izq;
    }

    public NodoAVL<T> getDer() {
        return der;
    }

    public void setElem(T elem) {
        this.elem = elem;
    }

    public void setIzq(NodoAVL<T> izq) {
        this.izq = izq;
    }

    public void setDer(NodoAVL<T> der) {
        this.der = der;
    }
    
    public void setPapa(NodoAVL<T> nodo){
        this.papa = nodo;
    }
    
    public NodoAVL<T> getPapa() {
        return this.papa;
    }
    
    public void cuelga(NodoAVL<T> hijo) {
        
        if (hijo == null)
            return;
        if (hijo.getElem().compareTo(elem) <= 0)
            izq = hijo;
        else
            der = hijo;
        
        hijo.setPapa(this);
       
    }
    
    public String toString() {
        return this.elem.toString();
    }
    
}
