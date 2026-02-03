package es.caib.archium.ejb.service;

import java.io.InputStream;

/**
 * Serveis relacionats amb la taula de valoració
 * La taula de valoració és la representació PDF d'un dictamen
 */
public interface TaulaValoracioService {

    /**
     * Permet generar en calent la taula de valoració d'un dictamen (mentre aquest no es trobi en estat VIGENT)
     * o bé, obtenir la taula de valoració del gestor documental (si està en estat VIGENT)
     * @param idDictamen
     * @return
     */
    InputStream getTaulaValoracioPdf(Long idDictamen);
}
