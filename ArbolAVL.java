package arbolavl;

/**
 *
 * @author elcachorrohumano
 */
public class ArbolAVL<T extends Comparable<T>> {

    private NodoAVL<T> raiz;

    public ArbolAVL(T elem) {
        raiz = new NodoAVL(elem);
    }

    public NodoAVL<T> getRaiz() {
        return raiz;
    }
    

    public int alturaArbol(NodoAVL<T> actual) {

        if (actual == null) {
            return 0;
        }

        int f1 = alturaArbol(actual.getIzq()) + 1;
        int f2 = alturaArbol(actual.getDer()) + 1;
        return Math.max(f1, f2);

    }

    public int calculaFe(NodoAVL<T> actual) {
        NodoAVL<T> subDer = actual.getDer();
        NodoAVL<T> subIzq = actual.getIzq();
        return alturaArbol(subDer) - alturaArbol(subIzq);
    }

    public NodoAVL<T> rotacionIzqIzq(NodoAVL<T> actual) {
        NodoAVL<T> alfa = actual;
        NodoAVL<T> beta = alfa.getIzq();
        NodoAVL<T> C = beta.getDer();

        if (alfa == this.getRaiz()) {
            this.raiz = beta;
        }

        beta.setPapa(alfa.getPapa());
        alfa.setPapa(beta);
        alfa.setIzq(C);

        if (C != null) {
            C.setPapa(alfa);
        }
        beta.setDer(alfa);

        if (beta.getPapa() != null) {
            if (beta.getPapa().getIzq() == alfa) {
                beta.getPapa().setIzq(beta);
            } else {
                beta.getPapa().setDer(beta);
            }
        }
        alfa.setFe(calculaFe(alfa));
        beta.setFe(calculaFe(beta));

        return beta;
    }

    public NodoAVL<T> rotacionDerDer(NodoAVL<T> actual) {
        NodoAVL<T> alfa = actual;
        NodoAVL<T> beta = alfa.getDer();
        NodoAVL<T> b = beta.getIzq();

        if (alfa == this.raiz) {
            this.raiz = beta;
        }

        beta.setPapa(alfa.getPapa());
        alfa.setPapa(beta);
        if (b != null) {
            b.setPapa(alfa);
        }
        alfa.setDer(b);
        beta.setIzq(alfa);

        if (beta.getPapa() != null) {
            if (beta.getPapa().getIzq() == alfa) {
                beta.getPapa().setIzq(beta);
            } else {
                beta.getPapa().setDer(beta);
            }
        }
        alfa.setFe(calculaFe(alfa));
        beta.setFe(calculaFe(beta));

        return beta;
    }

    public NodoAVL<T> rotacionIzqDer(NodoAVL<T> actual) {
        NodoAVL<T> alfa = actual;
        NodoAVL<T> beta = alfa.getIzq();
        NodoAVL<T> gama = beta.getDer();
        NodoAVL<T> b = gama.getIzq();
        NodoAVL<T> c = gama.getDer();

        if (alfa == this.raiz) {
            this.raiz = gama;
        }
        gama.setPapa(alfa.getPapa());
        if (b != null) {
            b.setPapa(beta);
        }
        beta.setDer(b);
        if (c != null) {
            c.setPapa(alfa);
        }
        alfa.setIzq(c);
        beta.setPapa(gama);
        alfa.setPapa(gama);
        gama.setIzq(beta);
        gama.setDer(alfa);

        if (gama.getPapa() != null) {
            if (gama.getPapa().getIzq() == alfa) {
                gama.getPapa().setIzq(gama);
            } else {
                gama.getPapa().setDer(gama);
            }
        }
        alfa.setFe(calculaFe(alfa));
        beta.setFe(calculaFe(beta));
        gama.setFe(calculaFe(gama));

        return gama;
    }

    public NodoAVL<T> rotacionDerIzq(NodoAVL<T> actual) {
        NodoAVL<T> alfa = actual;
        NodoAVL<T> beta = alfa.getDer();
        NodoAVL<T> gama = beta.getIzq();
        NodoAVL<T> b = gama.getIzq();
        NodoAVL<T> c = gama.getDer();

        if (alfa == this.raiz) {
            this.raiz = gama;
        }
        gama.setPapa(alfa.getPapa());
        if (b != null) {
            b.setPapa(alfa);
        }
        alfa.setDer(b);
        if (c != null) {
            c.setPapa(beta);
        }
        beta.setIzq(c);
        gama.setDer(beta);
        gama.setIzq(alfa);
        beta.setPapa(gama);
        alfa.setPapa(gama);

        if (gama.getPapa() != null) {
            if (gama.getPapa().getIzq() == alfa) {
                gama.getPapa().setIzq(gama);
            } else {
                gama.getPapa().setDer(gama);
            }
        }
        alfa.setFe(calculaFe(alfa));
        beta.setFe(calculaFe(beta));
        gama.setFe(calculaFe(gama));

        return gama;

    }

    public void add(T dato) {
        // Inserta normal
        if (raiz == null) {
            raiz = new NodoAVL<T>(dato);
            return;
        }
        NodoAVL<T> actual = raiz, papa = raiz;
        NodoAVL<T> nuevo = new NodoAVL<T>(dato);
        while (actual != null) {
            papa = actual;
            if (dato.compareTo(actual.getElem()) <= 0) {
                actual = actual.getIzq();
            } else {
                actual = actual.getDer();
            }
        }
        papa.cuelga(nuevo);

        actual = nuevo;
        // Actualizar factor de equilibrio
        boolean bandera = false;
        while (!bandera && papa != null) {
            papa.setFe(calculaFe(papa));
            if (papa.getFe() == 0) {
                bandera = true;
            } else {
                if (papa.getFe() >= 2) {
                    if (actual.getFe() >= 0) {
                        papa = rotacionDerDer(papa);
                    } else {
                        papa = rotacionDerIzq(papa);
                    }
                } else {
                    if (papa.getFe() <= -2) {
                        if (actual.getFe() <= 0) {
                            papa = rotacionIzqIzq(papa);
                        } else {
                            papa = rotacionIzqDer(papa);
                        }
                    }
                }

            }
            actual = papa;
            papa = actual.getPapa();
        }

    }

    public T remove(T elem) {
        
        NodoAVL<T> actual = busca(elem);
        NodoAVL<T> papa = actual.getPapa();
        NodoAVL<T> temp;
        //No está el dato a eliminar
        if (actual == null) {
            throw new RuntimeException();
        }
        T res = actual.getElem();//guarda el dato del actual

        // No tiene hijo
        if (actual.getIzq() == null && actual.getDer() == null) {
            //Si es la raíz
            if (actual == raiz) {
                raiz = null;
            }
            // Hijo derecho
            if ((res.compareTo(actual.getPapa().getElem())) > 0) //Si
            {
                papa.setDer(null);
            } else // Hijo izq
            {
                papa.setIzq(null);
            }
            actual.setPapa(null);
            temp = papa;
        }

        //Tiene un hijo
        if (actual.getIzq() == null || actual.getDer() == null) {
            NodoAVL<T> hijo;
            if (actual.getIzq() == null) {
                hijo = actual.getDer();
            } else {
                hijo = actual.getIzq();
            }

            if (actual.equals(raiz)) {
                raiz = hijo;
            } else {
                papa.cuelga(hijo);
            }
            actual.setPapa(null);
            temp = papa;
        } // Tiene 2 hijos
        else {
            NodoAVL<T> aux = actual.getDer();
            while (aux.getIzq() != null) {
                aux = aux.getIzq();
            }
            actual.setElem(aux.getElem());
            // Aux no es hijo de actual
            if (aux != actual.getDer()) {
                if (aux.getDer() == null) {//Aux es una hoja
                    aux.getPapa().setIzq(null);
                    aux.setPapa(null);
                } else { //Aux tiene un hijo derecho 
                    aux.getPapa().cuelga(aux.getDer());
                    aux.setPapa(null);
                }
                temp = actual;
            } // Aux si fue hijo de actual
            else {
                temp = aux.getPapa();
                if (aux.getDer() == null) { //Aux es hoja
                    actual.setDer(null);
                    aux.setPapa(null);
                } else {//Aux tiene hijo derecho
                    aux.getDer().setPapa(actual.getPapa());
                    aux.getPapa().setDer(aux.getDer());
                    aux.setDer(null);
                    aux.setPapa(null);
                }
            }
        }
        // Rotaciones
        boolean bandera = false;
        NodoAVL<T> papaTemp = temp.getPapa();
        while (!bandera && papaTemp != null) {
            papaTemp.setFe(calculaFe(papaTemp));
            if (papaTemp.getFe() == 1 || papaTemp.getFe() == -1) {
                bandera = true;
            }
            if (papaTemp.getFe() == 2) {
                if (actual.getFe() >= 0) {
                    papaTemp = rotacionDerDer(papaTemp);
                } else {
                    papaTemp = rotacionDerIzq(papaTemp);
                }
            }

            if (papaTemp.getFe() == -2) {
                if (actual.getFe() <= 0) {
                    papaTemp = rotacionIzqIzq(papaTemp);
                } else {
                    papaTemp = rotacionIzqDer(papaTemp);
                }
            }

            temp = papaTemp;
            papaTemp = papaTemp.getPapa();
        }

        return res;
    }
    
    private NodoAVL<T> busca(T dato) {
        NodoAVL<T> actual = this.raiz;
        while (actual != null && actual.getElem().compareTo(dato) != 0)
            if (dato.compareTo(actual.getElem()) <= 0)
                actual = actual.getIzq();
            else
                actual = actual.getDer();
        
        return actual;
    }

}
