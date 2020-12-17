package es.caib.archium.back.test;

import es.caib.archium.services.FuncioFrontService;
import es.caib.archium.services.QuadreFrontService;
import es.caib.archium.services.SerieFrontService;

import javax.inject.Inject;

public class ConstrollerUtils {
    @Inject
    private QuadreFrontService cuadroService;
    @Inject
    private FuncioFrontService funcionService;
    @Inject
    private SerieFrontService serieService;

    // Fallan

    /* Serie de acceso libre, pero con causas de limitación */
    protected static final Long ID_SERIE_ACCESO_LIBRE_SIN_CAUSAS_LIMITACION = Long.valueOf(9997);
    /* Serie de acceso parcialmente restringido, pero sin causas de limitación */
    protected static final Long ID_SERIE_ACCESO_PR_SIN_CAUSAS_LIMITACION = Long.valueOf(9996);
    /* Serie sin funcion asignada */
    protected static final Long ID_SERIE_SIN_FUNCION_ASIGNADA = Long.valueOf(9995);
    /* Serie sin nivel ENS */
    protected static final Long ID_SERIE_SIN_ENS = Long.valueOf(9994);
    /* Serie sin nivel LOPD */
    protected static final Long ID_SERIE_SIN_LOPD = Long.valueOf(9993);
    /* Serie sin tipo de acceso */
    protected static final Long ID_SERIE_SIN_TIPO_ACCESO = Long.valueOf(9992);
    /* Serie sin describir el carácter esencial */
    protected static final Long ID_SERIE_SIN_CARACTER_ESENCIAL = Long.valueOf(9991);
    /* Serie con dictamen para el que no se ha especificado el tipo de dictamen */
    protected static final Long ID_SERIE_SIN_TIPO_DICTAMEN = Long.valueOf(9990);
    /* Serie sin valor primario */
    protected static final Long ID_SERIE_SIN_VALOR_PRIMARIO = Long.valueOf(9989);
    /* Serie con valor primario, pero sin plazo */
    protected static final Long ID_SERIE_VALOR_PRIMARIO_SIN_PLAZO = Long.valueOf(9988);
    /* Serie sin valor secundario especificado */
    protected static final Long ID_SERIE_SIN_VALOR_SECUNDARIO = Long.valueOf(9987);
    /* Serie con tipo de dictamen ET, pero sin plazo especificado */
    protected static final Long ID_SERIE_ET_SIN_PLAZO = Long.valueOf(9986);
    /* Serie con tipo de dictamen EP, pero sin tipos documentales en el dictamen */
    protected static final Long ID_SERIE_EP_SIN_TIPOS_DOCUMENTALES = Long.valueOf(9985);
    /* Serie con tipo de dictamen EP con tipos documentales en el dictamen, pero sin plazo general del dictamen ni del
    documento (debe estar rellenado al menos uno) */
    protected static final Long ID_SERIE_EP_SIN_PLAZO_TIPOS_DOC = Long.valueOf(9984);



    // Funcionan


    // Debera quedarse con el dictamen 9980, valoracion 181
    protected static final Long ID_SERIE_OK_1 = Long.valueOf(9980);
    /* Serie de acceso libre (sin causas de limitación), con dictamen de eliminación parcial */
    /* El plazo de la acción dictaminada será el máximo de los plazos de los tipos documentales */
    protected static final Long ID_SERIE_OK_2 = Long.valueOf(9981);
    /* Serie de acceso libre (sin causas de limitación), pendiente de dictamen */
    /* Varias valoraciones vigentes. Se cogerá la última de ellas */
    protected static final Long ID_SERIE_OK_3 = Long.valueOf(9999);
    /* Serie de acceso parcialmente restringido (con causas de limitación), dictamen eliminación parcial */
    /* Varias limitaciones de acceso. Se cogen las vigentes (las que no tienen fecha de fin) */
    /* Varios dictámenes. Ninguno VIGENT. Se coge el creado más recientemente */
    protected static final Long ID_SERIE_OK_4 = Long.valueOf(9998);


//    /**
//     * Genera dto de un cuadro de clasificacion
//     *
//     * @return
//     */
//    protected QuadreObject generarDTOCuadro() {
//        QuadreObject dto = new QuadreObject();
//        dto.setEstat("Vigent");
//        dto.setInici(new Date());
//        dto.setNom("CuadroTest");
//        dto.setNomCas("CuadroTestCas");
//        dto.setSynchronized(false);
//        return dto;
//    }

//    /**
//     * Genera el DTO de la funcion con los datos minimos
//     * No se informa:
//     * -tipoSerie
//     * -funcionPadre
//     *
//     * @return
//     */
//    protected FuncioObject generarDTOFuncion() {
//        FuncioObject dto = new FuncioObject();
//        dto.setSynchronized(false);
//        dto.setCodi("FunTest01");
//        dto.setEstat("Vigent");
//        dto.setInici(new Date());
//        dto.setModificacio(new Date());
//        dto.setOrdre(BigDecimal.ONE);
//        dto.setNom("FuncionTest");
//        dto.setNomcas("FuncionTestCas");
//        return dto;
//    }
//
//    protected DictamenObject crearDictamenDTO(SerieDocumentalObject serie){
//        DictamenObject dto = new DictamenObject();
//        dto.setCodi("DicTest01");
//        dto.setEstat("Esborrany");
//        dto.setInici(new Date());
//        EnsObject ens = new EnsObject();
//        ens.setId(Long.valueOf(1));
//        dto.setEns(ens);
//        LopdObject lopd = new LopdObject();
//        lopd.setId(Long.valueOf(1));
//        dto.setLopd(lopd);
//        dto.setSerieDocumental(serie);
//        TipuAccesObject tipo = new TipuAccesObject();
//        tipo.setId(Long.valueOf(1));
//        dto.setTipusAcces(tipo);
//        TipuDictamenObject td = new TipuDictamenObject();
//        td.setId(Long.valueOf(1));
//        dto.setTipusdictamen(td);
//
//    }

//    /**
//     * Genera el dto de serie
//     *
//     * @return
//     */
//    protected SerieDocumentalObject crearSerieDTO(Long idFuncio){
//        String codi = "serieTest1";
//        String nom = "serieTest";
//        String nomCas = "serieTestCas";
//        Long catalegSeriId = Long.valueOf(1);
//        String descripcio = "descripcion";
//        String descripcioCas = "";
//        String resumMigracio = "";
//        String dir3Promotor = null;
//        String estat = "Esborrany";
//        Long tipusSerieId = null;
//        String codiIecisa = "";
//
//        List<AplicacionObject> listaApps = new ArrayList<>();
////        listaApps.add(new AplicacionObject(1,"RIPEA"));
////        listaApps.add(new AplicacionObject(2,"Helium"));
//
//        List<SerieDocumentalObject> relateds = new ArrayList<>();
////        Seriedocumental entity = new Seriedocumental();
////        entity.setId(Long.valueOf(9999));
////        entity.setCodi("SD0999");
////        entity.setNom("Sèrie test 9999");
////        relateds.add(new SerieDocumentalObject(entity));
//
//        List<SerieArgenObject> argensList = new ArrayList<>();
////        argensList.add(new SerieArgenObject(1,"A01","MODIFICACIÓ DE CRÈDIT"));
////        argensList.add(new SerieArgenObject(2,"A02","BORSINS DE PERSONAL"));
//
//        List<LimitacioNormativaSerieObject> listaLNS = new ArrayList<>();
////        NormativaAprobacioObject normativa = new NormativaAprobacioObject();
////        normativa.setId(Long.valueOf(523));
////        normativa.setNom("Ordre TIN/2504/2010, de 20 de setembre, que desenvolupa el RD 39/1997, que aprova el Reglament dels Serveis de Prevenció, en el que es refereix a l'acreditació d'entitats especialitzades com a serveis de prevenció");
////        DualListModel<CausaLimitacioObject> dualListCausas = new DualListModel<>();
////        dualListCausas.setTarget(new ArrayList<>());
////        dualListCausas.getTarget().add(new CausaLimitacioObject(Long.valueOf(1),null,"Seguretat nacional",null));
////        dualListCausas.getTarget().add(new CausaLimitacioObject(Long.valueOf(2),null,"Defensa",null));
////        LimitacioNormativaSerieObject lns = new LimitacioNormativaSerieObject();
////        lns.setNormativa(normativa);
////        lns.setDualListCausas(dualListCausas);
////        listaLNS.add(lns);
//
//        List<NormativaAprobacioObject> normativasList = new ArrayList<>();
////        Normativa n = new Normativa();
////        n.setId(Long.valueOf(523));
////        n.setNom("Ordre TIN/2504/2010, de 20 de setembre, que desenvolupa el RD 39/1997, que aprova el Reglament dels Serveis de Prevenció, en el que es refereix a l'acreditació d'entitats especialitzades com a serveis de prevenció");
////        normativasList.add(new NormativaAprobacioObject(n));
////        n = new Normativa();
////        n.setId(Long.valueOf(524));
////        n.setNom("Real Decreto 459/2010,de 16 de abril, por el que se regulan las condiciones para el reconocimiento de efectos profesionales a títulos extranjeros de especialista en Ciencias de la Salud, obtenidos en Estados no miembros de la UE");
////        normativasList.add(new NormativaAprobacioObject(n));
//
//        ValoracioObject valoracio = new ValoracioObject();
////        valoracio.setAchValorprimaris(new ArrayList<>());
////        valoracio.getAchValorprimaris().add(new ValorPrimariObject(null,null,new TipuValorObject(Long.valueOf(1),"Administratiu","Administrativo","Valor que presenten els documents que es troben en plena vigència administrativa",null),1,"Anys"));
////        valoracio.getAchValorprimaris().add(new ValorPrimariObject(null,null,new TipuValorObject(Long.valueOf(2),"Fiscal","Fiscal","Valor que presenten els documents que tracten d'entrades, sortides i controls dels diners gestionats a una institució",null),2,"Mesos"));
////        valoracio.getAchValorprimaris().add(new ValorPrimariObject(null,null,new TipuValorObject(Long.valueOf(3),"Jurídic","Jurídico","Valor que presenten els documents probatoris que salvaguarden els drets dels governs, institucions i dels individus en general",null),3,"Dies"));
////        valoracio.setAchValorsecundari(new ValorSecundariObject(Long.valueOf(3),"Sense cobertura de qualificació","Sin cobertura de calificación","Ha d'emprar-se aquest valor fins que la sèrie pertinent estigui coberta per una norma de conservació",null));
//
//
//        return crearSerie(codi, nom, nomCas, catalegSeriId, idFuncio,
//                descripcio, descripcioCas, resumMigracio, dir3Promotor, estat, tipusSerieId,
//                codiIecisa, listaApps, relateds, argensList, listaLNS, normativasList, valoracio);
//    }


//
//    protected SerieDocumentalObject crearSerie(String codi, String nom, String nomCas, Long catalegSeriId, Long idFuncio,
//                         String descripcio, String descripcioCas, String resumMigracio, String dir3Promotor, String estat, Long tipusSerieId,
//                         String codiIecisa, List<AplicacionObject> listaApps, List<SerieDocumentalObject> relateds, List<SerieArgenObject> argensList, List<LimitacioNormativaSerieObject> listaLNS
//            , List<NormativaAprobacioObject> normativasList, ValoracioObject valoracio){
//        // TODO
//        return null;
//    }

//
//    /**
//     * Llama a sincronizar cuadro de clasaificacion
//     *
//     * @param dto
//     * @return
//     */
//    protected String sincronizarCuadro(CuadroClasificacion dto) {
//        try {
//            return cuadroService.synchronizeNode(dto);
//        } catch (Exception e) {
//            // Devuelve error si no se crea correctamente
//            Assert.fail("Se ha producido un error sincronizando el cuadro " + e);
//        }
//        return null;
//    }
//
//    /**
//     * Llama a sincronizar la funcion, devuelve la excepcion
//     *
//     * @param dto
//     * @return
//     */
//    protected String sincronizarFuncionSinCatch(Funcion dto, String parentId) throws CSGDException {
//        return funcionService.synchronizeNode(dto,parentId);
//    }
//
//    /**
//     * Llama al sincronizar funcion controlando la excepcion
//     *
//     * @param dto
//     * @return
//     */
//    protected String sincronizarFuncion(Funcion dto, String parentId) {
//        try {
//            return this.sincronizarFuncionSinCatch(dto,parentId);
//        } catch (Exception e) {
//            // Devuelve error si no se crea correctamente
//            Assert.fail("Se ha producido un error sincronizando la funcion " + e);
//        }
//        return null;
//    }
//
//    /**
//     * Llama a sincronizar la funcion, devuelve la excepcion
//     *
//     * @param dto
//     * @return
//     */
//    protected String sincronizarSerieSinCatch(Serie dto, String parentId) throws CSGDException {
//        return serieService.synchronizeNode(dto,parentId);
//    }
//
//    /**
//     * Llama al sincronizar serie controlando la excepcion
//     *
//     * @param dto
//     * @return
//     */
//    protected String sincronizarSerie(Serie dto, String parentId) {
//        try {
//           return this.sincronizarSerieSinCatch(dto,parentId);
//        } catch (Exception e) {
//            // Devuelve error si no se crea correctamente
//            Assert.fail("Se ha producido un error sincronizando la serie " + e);
//        }
//        return null;
//    }
//
//    /**
//     * Borra el cuadro indicado
//     *
//     * @param nodeId
//     */
//    protected void borrarCuadro(String nodeId) {
//        RootId rootId = new RootId(nodeId);
//        try {
//            cuadroService.deleteNode(rootId);
//        } catch (Exception e) {
//            // Devuelve error si no se borra correctamente
//            Assert.fail("Se ha producido un error borrando el cuadro " + e);
//        }
//    }
//
//
//
//
//    /**
//     * Borra la funcion y su padre
//     *
//     * @param cuadroId
//     * @param funciones
//     */
//    protected void borrarFuncion(String cuadroId, String... funciones) {
//        for(String funcionId : Arrays.asList(funciones)) {
//            borrarFuncion(funcionId);
//        }
//        borrarCuadro(cuadroId);
//    }
//
//    /**
//     * Borra la funcion
//     *
//     * @param funcionId
//     */
//    protected void borrarFuncion(String funcionId){
//        FunctionId nodeId = new FunctionId(funcionId);
//        try {
//            funcionService.deleteNode(nodeId);
//        } catch (Exception e) {
//            // Devuelve error si no se borra correctamente
//            Assert.fail("Se ha producido un error borrando la funcion " + e);
//        }
//    }
//
//    /**
//     * Borra la serie y sus padres
//     *
//     * @param cuadroId
//     * @param funciones
//     */
//    protected void borrarSerie(String cuadroId,String serieId, String... funciones) {
//        borrarSerie(serieId);
//        for(String funcionId : Arrays.asList(funciones)) {
//            borrarFuncion(funcionId);
//        }
//        borrarCuadro(cuadroId);
//    }
//
//    /**
//     * Borra la serie
//     *
//     * @param serieId
//     */
//    protected void borrarSerie(String serieId){
//        SerieId nodeId = new SerieId(serieId);
//        try {
//            serieService.deleteNode(nodeId);
//        } catch (Exception e) {
//            // Devuelve error si no se borra correctamente
//            Assert.fail("Se ha producido un error borrando la serie " + e);
//        }
//    }
//


}
