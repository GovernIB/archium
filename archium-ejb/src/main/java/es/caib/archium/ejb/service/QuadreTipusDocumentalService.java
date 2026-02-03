package es.caib.archium.ejb.service;

import es.caib.archium.commons.i18n.Idioma;
import es.caib.archium.commons.model.Estat;
import es.caib.archium.ejb.objects.QuadreTipusDocumentalsDto;
import es.caib.archium.persistence.dao.DAO;
import es.caib.archium.persistence.model.QuadreTipusDocumental;

import javax.ejb.Local;

import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;

import java.io.InputStream;

@Local
public interface QuadreTipusDocumentalService extends DAO<QuadreTipusDocumental, Long> {

    public QuadreTipusDocumental getQuadreTipusDocumentalPerDefecte();

    public Long getQuadreTipusDocumentalPerDefecteId();

    public QuadreTipusDocumentalsDto getQuadreTipusDocumental(Long quadreId, Idioma idioma, Estat estat);

    public InputStream generaPdfQuadreTipusDocumentals(QuadreTipusDocumentalsDto quadreTipusDocumental, Idioma idioma);
    
    public Cell getFormatedCell();
    
    public Paragraph getFormatedParagraph(String paragraph);

}