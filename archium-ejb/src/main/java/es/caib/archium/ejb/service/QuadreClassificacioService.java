package es.caib.archium.ejb.service;

import es.caib.archium.commons.i18n.Idioma;
import es.caib.archium.commons.model.Estat;
import es.caib.archium.ejb.objects.QuadreClassificacioDto;
import es.caib.archium.ejb.objects.SerieDto;
import es.caib.archium.persistence.dao.DAO;
import es.caib.archium.persistence.model.Quadreclassificacio;

import javax.ejb.Local;
import java.io.InputStream;
import java.util.List;

@Local
public interface QuadreClassificacioService extends DAO<Quadreclassificacio, Long> {
    public Long getQuadreClassificacioPerDefecte();

    public List<SerieDto> getQuadreClassificacio(Long quadreId, Idioma idioma, Estat estat);

    public List<QuadreClassificacioDto> getLlistaQuadreClassificacio(String estat, Idioma idioma);

    public InputStream generaPdfQuadreQuassificacio(List<SerieDto> seriesDto, Idioma idioma);

	public InputStream generaPdfQuadreQuassificacioNuevo(List<SerieDto> seriesDto, String titol, Idioma idioma);
}
