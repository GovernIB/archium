package es.caib.archium.csgd.apirest.constantes;

public abstract class Servicios {

    //Series
    public static final String REMOVE_SERIE                 =   "/services/removeSerie";
    public static final String CREATE_SERIE                 =   "/services/createSerie";
    public static final String SET_SERIE                    =   "/services/setSerie";
    public static final String MOVE_SERIE                   =   "/services/moveSerie";
    public static final String GET_SERIE                    =   "/services/getSerie";
    public static final String GRANT_PERMISOS_SERIE         =   "/services/grantPermissionsOnSeries";
    public static final String CANCEL_PERMISOS_SERIE         =   "/services/cancelPermissionsOnSeries";

    //Funciones
    public static final String REMOVE_FUNCTION              =   "/services/removeFunction";
    public static final String CREATE_FUNCTION              =   "/services/createFunction";
    public static final String SET_FUNCTION                 =   "/services/setFunction";
    public static final String MOVE_FUNCTION                =   "/services/moveFunction";
    public static final String GET_FUNCTION                 =   "/services/getFunction";

    //Cuadro
    public static final String REMOVE_CLASSIFICATION_ROOT   =   "/services/removeClassificationRoot";
    public static final String CREATE_CLASSIFICATION_ROOT   =   "/services/createClassificationRoot";
    public static final String SET_CLASSIFICATION_ROOT      =   "/services/setClassificationRoot";
    public static final String GET_CLASSIFICATION_ROOT      =   "/services/getClassificationRoot";
}
