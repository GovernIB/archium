package es.caib.archium.ejb;//package es.caib.archium.ejb;
//
//import es.caib.archium.ejb.service.FirmaServidorService;
//import org.fundaciobit.apisib.apifirmasimple.v1.ApiFirmaEnServidorSimple;
//import org.fundaciobit.apisib.apifirmasimple.v1.beans.*;
//import org.fundaciobit.apisib.apifirmasimple.v1.jersey.ApiFirmaEnServidorSimpleJersey;
//import org.fundaciobit.apisib.core.exceptions.AbstractApisIBException;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import javax.annotation.PostConstruct;
//import javax.annotation.security.RolesAllowed;
//import javax.ejb.Stateless;
//import java.io.ByteArrayInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.Properties;
//
//@Stateless
//@RolesAllowed({"ACH_GESTOR"})
//public class FirmaServidorEJB implements FirmaServidorService {
//
//    protected String endpoint;
//    protected String username;
//    protected String password;
//
//    protected String perfil;
//
//    private ApiFirmaEnServidorSimple apiFirmaEnServidorSimpleJersey;
//
//    protected final Logger log = LoggerFactory.getLogger(this.getClass());
//
//    @PostConstruct
//    public void init() {
//        log.debug("Inicialitzant servei de signatura en servidor...");
//
//
//        try (InputStream input = FirmaServidorEJB.class.getClassLoader().getResourceAsStream("archium.properties")) {
//
//            Properties prop = new Properties();
//
//            if (input == null) {
//                log.error("No s'ha trobat el fitxer de properties archium.properties");
//                return;
//            }
//
//            //load a properties file from class path, inside static method
//            prop.load(input);
//
//            endpoint = prop.getProperty("apifirmaenservidorsimple.endpoint");
//            username = prop.getProperty("apifirmaenservidorsimple.username");
//            password = prop.getProperty("apifirmaenservidorsimple.password");
//            perfil = prop.getProperty("apifirmaenservidorsimple.perfilapiserver");
//
//            apiFirmaEnServidorSimpleJersey = new ApiFirmaEnServidorSimpleJersey(endpoint, username, password);
//
//        } catch (IOException ex) {
//            log.error("Error accedint a les properties d'archium", ex);
//            return;
//        }
//        log.debug("Inicialitzat servei de signatura en servidor. Usuari: " + username + ". Perfil: " + perfil + ". Endpoint: " + endpoint);
//    }
//
//    public InputStream signar(FirmaSimpleFile fileToSign, String motiuSignatura) {
//        // ======  DADES ESPECIFIQUES DE LA FIRMA =======
//
//        String signID = "1";
//        String name = fileToSign.getNom();
//        String location = "Palma";
//        int signNumber = 1;
//        String languageSign = "ca";
//
//
//        FirmaSimpleFileInfoSignature fileInfoSignature = new FirmaSimpleFileInfoSignature(
//                fileToSign, signID, name, motiuSignatura, location, signNumber, languageSign, null);
//
//        FirmaSimpleSignDocumentRequest signature;
//        signature = new FirmaSimpleSignDocumentRequest(getDadesComunesSignatura(), fileInfoSignature);
//
//        FirmaSimpleSignatureResult fullResults = null;
//        try {
//            fullResults = apiFirmaEnServidorSimpleJersey.signDocument(signature);
//        } catch (AbstractApisIBException e) {
//            throw new RuntimeException(e);
//        }
//
//        FirmaSimpleStatus transactionStatus = fullResults.getStatus();
//
//        int status = transactionStatus.getStatus();
//
//        switch (status) {
//
//            case FirmaSimpleStatus.STATUS_INITIALIZING: // = 0;
//                log.error("Status inicialitzant: Inicialitzant firma en servidor... error desconegut!");
//                return null;
//
//            case FirmaSimpleStatus.STATUS_IN_PROGRESS: // = 1;
//                log.error("Status in progress: En procés de firmar en servidor... error desconegut!");
//                return null;
//
//            case FirmaSimpleStatus.STATUS_FINAL_ERROR: // = -1;
//            {
//                log.error("Error durant la realització de les firmes: "
//                        + transactionStatus.getErrorMessage());
//
//                String desc = transactionStatus.getErrorStackTrace();
//                if (desc != null) {
//                    log.error("Stacktrace: "
//                            + transactionStatus.getErrorMessage());
//                }
//                return null;
//            }
//
//            case FirmaSimpleStatus.STATUS_CANCELLED: // = -2;
//            {
//                log.error("Procés de signatura cancel·lat "
//                        + transactionStatus.getErrorMessage());
//                return null;
//            }
//
//            case FirmaSimpleStatus.STATUS_FINAL_OK: // = 2;
//            {
//                {
//                    log.debug("Signature [" + fullResults.getSignID() + " ]");
//                    int estat = fullResults.getStatus().getStatus();
//                    log.debug("  STATUS SIGNATURE CODE = " + estat);
//
//                    switch (estat) {
//
//                        case FirmaSimpleStatus.STATUS_INITIALIZING: // = 0;
//                            log.debug("  RESULT: STATUS_INITIALIZING => Incoherent Status");
//                            break;
//
//                        case FirmaSimpleStatus.STATUS_IN_PROGRESS: // = 1;
//                            log.debug("  RESULT: STATUS_IN_PROGRESS => Incoherent Status");
//                            break;
//
//                        case FirmaSimpleStatus.STATUS_FINAL_ERROR: // = -1;
//                            log.error("  RESULT: Error en la firma: "
//                                    + fullResults.getStatus().getErrorMessage());
//                            break;
//
//                        case FirmaSimpleStatus.STATUS_CANCELLED: // = -2;
//                            log.error("  RESULT: L'usuari ha cancelat la firma.");
//                            break;
//
//                        case FirmaSimpleStatus.STATUS_FINAL_OK: // = 2;
//                            log.debug("Signatura de document OK");
//                            FirmaSimpleFile fsf = fullResults.getSignedFile();
//                            return new ByteArrayInputStream(fsf.getData());
//
//                    }
//
//                } // Final for de fitxers firmats
//            } // Final Case Firma OK
//        } // Final Switch Firma
//        return null;
//    }
//
//    private FirmaSimpleCommonInfo getDadesComunesSignatura() {
//        String languageUI = "ca";
//        // En API de Firma Simple en Servidor sempre valdran null
//        final String username = null;
//        final String administrationID = null;
//        final String signerEmail = "arxiu@caib.es";
//
//        return new FirmaSimpleCommonInfo(perfil, languageUI, username, administrationID, signerEmail);
//
//    }
//
//    public String getEndpoint() {
//        return endpoint;
//    }
//
//    public void setEndpoint(String endpoint) {
//        this.endpoint = endpoint;
//    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public String getPerfil() {
//        return perfil;
//    }
//
//    public void setPerfil(String perfil) {
//        this.perfil = perfil;
//    }
//}
