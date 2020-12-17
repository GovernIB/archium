package es.caib.archium.persistence.funcional;

import java.io.Serializable;
import java.util.Date;

/**
 * Todas las clases con fi pueden quedarse obsoletas, se utiliza esta interfaz para añadir la funcionalidad
 * que implemente la logica para la comprobación de si estan obsoletas
 *
 */
public interface Obsolescente extends Serializable {

    boolean isObsolete();

    Date getFi();
}
