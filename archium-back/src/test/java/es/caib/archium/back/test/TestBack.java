package es.caib.archium.back.test;

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.objects.SerieDocumentalObject;
import org.junit.Assert;
import org.junit.Test;

public class TestBack extends ConstrollerUtils {


    @Test
    public void test1() {
        try {
            serieService.synchronize(ID_SERIE_ACCESO_LIBRE_SIN_CAUSAS_LIMITACION);
        } catch (I18NException e) {
            // La sèrie té registres de causa limitació, de manera que no pot tenir tipus accés 'Lliure'
            Assert.assertEquals("validaciones.serie.codigoLimitacion",e.getMessage());
        }
    }

    @Test
    public void test2() {
        try {
            serieService.synchronize(ID_SERIE_ACCESO_PR_SIN_CAUSAS_LIMITACION);
        } catch (I18NException e) {
            // La sèrie NO té registres de causa limitació, pel que ha de tenir tipus accés 'Lliure'
            Assert.assertEquals("validaciones.serie.codigoLimitacion.sinCodigoLimitacion",e.getMessage());
        }
    }

    @Test
    public void test3() {
        try {
            serieService.synchronize(ID_SERIE_SIN_ENS);
        } catch (I18NException e) {
            // Error de validació. El dictamen ha de tenir informat la confidencialitat (ens)
            Assert.assertEquals("validaciones.dictamen.ens",e.getMessage());
        }
    }

    @Test
    public void test4() {
        try {
            serieService.synchronize(ID_SERIE_SIN_LOPD);
        } catch (I18NException e) {
            // Error de validació. El dictamen ha de tenir informat un nivell de lopd
            Assert.assertEquals("validaciones.dictamen.lopd",e.getMessage());
        }
    }

    @Test
    public void test5() {
        try {
            serieService.synchronize(ID_SERIE_SIN_TIPO_ACCESO);
        } catch (I18NException e) {
            // Error de validació. El dictamen ha de tenir informat un tipus d'accés
            Assert.assertEquals("validaciones.dictamen.tipusacce",e.getMessage());
        }
    }

    @Test
    public void test6() {
        try {
            serieService.synchronize(ID_SERIE_SIN_CARACTER_ESENCIAL);
        } catch (I18NException e) {
            // Error de validació. El dictamen ha de tenir informat el camp sèrie essencial
            Assert.assertEquals("validaciones.dictamen.seriesencial",e.getMessage());
        }
    }

    @Test
    public void test7() {
        try {
            serieService.synchronize(ID_SERIE_SIN_TIPO_DICTAMEN);
        } catch (I18NException e) {
            // Error de validació. El dictamen ha de tenir informat un tipus
            Assert.assertEquals("validaciones.dictamen.tipo",e.getMessage());
        }
    }

    @Test
    public void test8() {
        try {
            serieService.synchronize(ID_SERIE_SIN_VALOR_PRIMARIO);
        } catch (I18NException e) {
            // La sèrie ha de tenir en la seva valoració més recent, i sense data fi, alguna valoració primària
            Assert.assertEquals("validaciones.serie.tipoValor",e.getMessage());
        }
    }

    @Test
    public void test9() {
        try {
            serieService.synchronize(ID_SERIE_VALOR_PRIMARIO_SIN_PLAZO);
        } catch (I18NException e) {
            // El resultado de que no hay valoracion primaria es correcto ya que no permitira insertar en base de datos
            // un valor primario sin termino
            // La sèrie ha de tenir en la seva valoració més recent, i sense data fi, alguna valoració primària
            Assert.assertEquals("validaciones.serie.tipoValor",e.getMessage());
        }
    }

    @Test
    public void test10() {
        try {
            serieService.synchronize(ID_SERIE_SIN_VALOR_SECUNDARIO);
        } catch (I18NException e) {
            // El resultado de que no hay valoracion es correcto ya que no permitira insertar en base de datos
            // una valoración sin valor secundario
            // Error de validació. Les valoracions de la sèrie no poden estar buides
            Assert.assertEquals("validaciones.serie.valoracio",e.getMessage());
        }
    }

    @Test
    public void test11() {
        try {
            serieService.synchronize(ID_SERIE_ET_SIN_PLAZO);
        } catch (I18NException e) {
            //Si el tipus dictamen és 'ET', és obligatori informar un termini d'accion dictaminada
            Assert.assertEquals("validaciones.serie.tipoDictamen.ET.plazoAccionDictaminada",e.getMessage());
        }
    }

    @Test
    public void test12() {
        try {
            serieService.synchronize(ID_SERIE_EP_SIN_TIPOS_DOCUMENTALES);
        } catch (I18NException e) {
            // Si el tipus dictamen és 'EP', és obligatori que compti amb registres de tipus documentals
            Assert.assertEquals("validaciones.serie.tipoDictamen.EP.tipoDocumental",e.getMessage());
        }
    }

    @Test
    public void test13() {
        try {
            serieService.synchronize(ID_SERIE_EP_SIN_PLAZO_TIPOS_DOC);
        } catch (I18NException e) {
            // Si el tipus dictamen és 'EP', ha d'haver al menys dos registres de tipus documentals de l'dictamen,
            // un amb conservable a si i altres no
            Assert.assertEquals("validaciones.serie.tipoDictamen.EP.tipoDocumental.conservable",e.getMessage());
        }
    }

    @Test
    public void test1_OK() throws I18NException {
        SerieDocumentalObject node = serieService.synchronize(ID_SERIE_OK_1);

        Assert.assertNotNull(node);
    }

    @Test
    public void test2_OK() throws I18NException {
            SerieDocumentalObject node = serieService.synchronize(ID_SERIE_OK_2);

            Assert.assertNotNull(node);
    }

    @Test
    public void test3_OK() throws I18NException {
            SerieDocumentalObject node = serieService.synchronize(ID_SERIE_OK_3);

            Assert.assertNotNull(node);
    }

    @Test
    public void test4_OK() throws I18NException {
            SerieDocumentalObject node = serieService.synchronize(ID_SERIE_OK_4);

            Assert.assertNotNull(node);
    }



}
