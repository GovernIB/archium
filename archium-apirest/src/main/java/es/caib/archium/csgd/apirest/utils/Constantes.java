package es.caib.archium.csgd.apirest.utils;

public class Constantes {

    // NAMESPACE
    public static final String ARCHIUM_NAMESPACE = "eemgde:";
    public static final String GDIB_NAMESPACE = "gdib:";

    // PROPIEDADES
    // Cuadro
    public static final String CODIGO_CUADRO = "codigo";
    public static final String CODIGO_CUADRO_QNAME = GDIB_NAMESPACE + CODIGO_CUADRO;
    public static final String ESTADO_CUADRO = "estado";
    public static final String ESTADO_CUADRO_QNAME = GDIB_NAMESPACE + ESTADO_CUADRO;

    // Funcion
    public static final String CODIGO_FUNCION = "codigo_funcion";
    public static final String CODIGO_FUNCION_QNAME = GDIB_NAMESPACE + CODIGO_FUNCION;
    public static final String FUNCION_PADRE = "funcion_padre";
    public static final String FUNCION_PADRE_QNAME = GDIB_NAMESPACE + FUNCION_PADRE;
    public static final String ESTADO_FUNCION = "estado";
    public static final String ESTADO_FUNCION_QNAME = GDIB_NAMESPACE + ESTADO_FUNCION;


    // Serie
    public static final String CODIGO_CLASIFICACION = "codigo_clasificacion";
    public static final String CODIGO_CLASIFICACION_QNAME = ARCHIUM_NAMESPACE + CODIGO_CLASIFICACION;
    public static final String DENOMINACION_CLASE = "denominacion_clase";
    public static final String DENOMINACION_CLASE_QNAME = ARCHIUM_NAMESPACE + DENOMINACION_CLASE;
    public static final String TIPO_CLASIFICACION = "tipo_clasificacion";
    public static final String TIPO_CLASIFICACION_QNAME = ARCHIUM_NAMESPACE + TIPO_CLASIFICACION;
    public static final String LOPD = "lopd";
    public static final String LOPD_QNAME = ARCHIUM_NAMESPACE + LOPD;
    public static final String CONFIDENCIALIDAD = "confidencialidad";
    public static final String CONFIDENCIALIDAD_QNAME = ARCHIUM_NAMESPACE + CONFIDENCIALIDAD;
    public static final String TIPO_ACCESO = "tipo_acceso";
    public static final String TIPO_ACCESO_QNAME = ARCHIUM_NAMESPACE + TIPO_ACCESO;
    public static final String CODIGO_CAUSA_LIMITACION = "codigo_causa_limitacion";
    public static final String CODIGO_CAUSA_LIMITACION_QNAME = ARCHIUM_NAMESPACE + CODIGO_CAUSA_LIMITACION;
    public static final String CONDICION_REUTILIZACION = "condicion_reutilizacion";
    public static final String CONDICION_REUTILIZACION_QNAME = ARCHIUM_NAMESPACE + CONDICION_REUTILIZACION;
    public static final String VALOR_SECUNDARIO = "valor_secundario";
    public static final String VALOR_SECUNDARIO_QNAME = ARCHIUM_NAMESPACE + VALOR_SECUNDARIO;
    public static final String TIPO_VALOR = "tipo_valor";
    public static final String TIPO_VALOR_QNAME = ARCHIUM_NAMESPACE + TIPO_VALOR;
    public static final String TIPO_DICTAMEN = "tipo_dictamen";
    public static final String TIPO_DICTAMEN_QNAME = ARCHIUM_NAMESPACE + TIPO_DICTAMEN;
    public static final String ACCION_DICTAMINADA = "accion_dictaminada";
    public static final String ACCION_DICTAMINADA_QNAME = ARCHIUM_NAMESPACE + ACCION_DICTAMINADA;
    public static final String PLAZO_ACCION_DICTAMINADA = "plazo_accion_dictaminada";
    public static final String PLAZO_ACCION_DICTAMINADA_QNAME = ARCHIUM_NAMESPACE + PLAZO_ACCION_DICTAMINADA;
    public static final String PLAZO_UNIDAD_ACCION_DICTAMINADA = "plazo_unidad_accion_dictaminada";
    public static final String PLAZO_UNIDAD_ACCION_DICTAMINADA_QNAME = ARCHIUM_NAMESPACE + PLAZO_UNIDAD_ACCION_DICTAMINADA;
    public static final String DOCUMENTO_ESENCIAL = "documento_esencial";
    public static final String DOCUMENTO_ESENCIAL_QNAME = ARCHIUM_NAMESPACE + DOCUMENTO_ESENCIAL;
    public static final String PLAZO_RESELLADO = "plazo_resellado";
    public static final String PLAZO_RESELLADO_QNAME = ARCHIUM_NAMESPACE + PLAZO_RESELLADO;
    public static final String PLAZO_UNIDAD_RESELLADO = "plazo_unidad_resellado";
    public static final String PLAZO_UNIDAD_RESELLADO_QNAME = ARCHIUM_NAMESPACE + PLAZO_UNIDAD_RESELLADO;



    public enum CodigosRespuesta {

        HTTP_RESPUESTA_OK(200),
        ERROR_CONEXION(600),
        ERROR_DESCONOCIDO_PETICION(601);


        private int value;

        CodigosRespuesta(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}
