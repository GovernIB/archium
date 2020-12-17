package es.caib.archium.persistence.funcional;

import java.util.Date;

/**
 * Todas las clases con fi pueden quedarse obsoletas, se utiliza esta clase abstracta para implementar la funcionalidad
 * para la comprobación de si estan obsoletas, si algun dia alguna entidad ha de comprobarlo de manera diferente
 * implementara la logica en la propia entidad en vez de utilizar esta clase abstracta
 *
 */
public abstract class ObsolescenteAbstract implements Obsolescente{

    /**
     * Si un elemento tiene fi informada y es anterior a la fecha actual, se considera obsoleto
     *
     * @return
     */
    @Override
    public boolean isObsolete() {
        if(this.getFi()==null) {
            return false;
        }
        if(this.getFi().before(new Date())){
            return true;
        }
        return false;
    }
}
