
/* CREATE USER WWW_ARCHIUM IDENTIFIED BY xxxxxxxx; */

GRANT UNLIMITED TABLESPACE TO WWW_ARCHIUM;
GRANT CREATE SESSION TO WWW_ARCHIUM;
GRANT FORCE ANY TRANSACTION TO WWW_ARCHIUM;

GRANT SELECT ON "ARCHIUM"."ACH_APLICACIO_SEQ" TO WWW_ARCHIUM;
GRANT SELECT ON "ARCHIUM"."ACH_BUTLLETI_SEQ" TO WWW_ARCHIUM;
GRANT SELECT ON "ARCHIUM"."ACH_CATALEGSERIES_SEQ" TO WWW_ARCHIUM;
GRANT SELECT ON "ARCHIUM"."ACH_CATEGORIANTI_SEQ" TO WWW_ARCHIUM;
GRANT SELECT ON "ARCHIUM"."ACH_CAUSALIMITACIO_SEQ" TO WWW_ARCHIUM;
GRANT SELECT ON "ARCHIUM"."ACH_DICTAMENT_SEQ" TO WWW_ARCHIUM;
GRANT SELECT ON "ARCHIUM"."ACH_ENS_SEQ" TO WWW_ARCHIUM;
GRANT SELECT ON "ARCHIUM"."ACH_FAMILIAPROCEDIMENT_SEQ" TO WWW_ARCHIUM;
GRANT SELECT ON "ARCHIUM"."ACH_FASEARXIU_SEQ" TO WWW_ARCHIUM;
GRANT SELECT ON "ARCHIUM"."ACH_FORMAINICI_SEQ" TO WWW_ARCHIUM;
GRANT SELECT ON "ARCHIUM"."ACH_FUNCIO_SEQ" TO WWW_ARCHIUM;
GRANT SELECT ON "ARCHIUM"."ACH_LOPD_SEQ" TO WWW_ARCHIUM;
GRANT SELECT ON "ARCHIUM"."ACH_MATERIA_SEQ" TO WWW_ARCHIUM;
GRANT SELECT ON "ARCHIUM"."ACH_NIVELLELECTRONIC_SEQ" TO WWW_ARCHIUM;
GRANT SELECT ON "ARCHIUM"."ACH_NORMATIVA_SEQ" TO WWW_ARCHIUM;
GRANT SELECT ON "ARCHIUM"."ACH_PROCEDIMENT_SEQ" TO WWW_ARCHIUM;
GRANT SELECT ON "ARCHIUM"."ACH_QUADRECLASSIFICACIO_SEQ" TO WWW_ARCHIUM;
GRANT SELECT ON "ARCHIUM"."ACH_QUADRETIPUSDOCUMENTAL_SEQ" TO WWW_ARCHIUM;
GRANT SELECT ON "ARCHIUM"."ACH_SERIEARGEN_SEQ" TO WWW_ARCHIUM;
GRANT SELECT ON "ARCHIUM"."ACH_SERIEDOCUMENTAL_SEQ" TO WWW_ARCHIUM;
GRANT SELECT ON "ARCHIUM"."ACH_SERIERELACIONADA_SEQ" TO WWW_ARCHIUM;
GRANT SELECT ON "ARCHIUM"."ACH_SILENCI_SEQ" TO WWW_ARCHIUM;
GRANT SELECT ON "ARCHIUM"."ACH_TIPUSACCES_SEQ" TO WWW_ARCHIUM;
GRANT SELECT ON "ARCHIUM"."ACH_TIPUSCONTACTE_SEQ" TO WWW_ARCHIUM;
GRANT SELECT ON "ARCHIUM"."ACH_TIPUSDICTAMEN_SEQ" TO WWW_ARCHIUM;
GRANT SELECT ON "ARCHIUM"."ACH_TIPUSDOCUMENTAL_SEQ" TO WWW_ARCHIUM;
GRANT SELECT ON "ARCHIUM"."ACH_TIPUSNORMATIVA_SEQ" TO WWW_ARCHIUM;
GRANT SELECT ON "ARCHIUM"."ACH_TIPUSNTI_SEQ" TO WWW_ARCHIUM;
GRANT SELECT ON "ARCHIUM"."ACH_TIPUSPUBLIC_SEQ" TO WWW_ARCHIUM;
GRANT SELECT ON "ARCHIUM"."ACH_TIPUSSERIE_SEQ" TO WWW_ARCHIUM;
GRANT SELECT ON "ARCHIUM"."ACH_TIPUSVALOR_SEQ" TO WWW_ARCHIUM;
GRANT SELECT ON "ARCHIUM"."ACH_TRANSFERENCIA_SEQ" TO WWW_ARCHIUM;
GRANT SELECT ON "ARCHIUM"."ACH_VALORSECUNDARI_SEQ" TO WWW_ARCHIUM;



GRANT SELECT, UPDATE, INSERT, DELETE ON "ARCHIUM"."ACH_APLICACIO" TO WWW_ARCHIUM;
GRANT SELECT, UPDATE, INSERT, DELETE ON "ARCHIUM"."ACH_APLICACIO_SERIE" TO WWW_ARCHIUM;
GRANT SELECT, UPDATE, INSERT, DELETE ON "ARCHIUM"."ACH_BUTLLETI" TO WWW_ARCHIUM;
GRANT SELECT, UPDATE, INSERT, DELETE ON "ARCHIUM"."ACH_CATALEGSERIES" TO WWW_ARCHIUM;
GRANT SELECT, UPDATE, INSERT, DELETE ON "ARCHIUM"."ACH_CATEGORIANTI" TO WWW_ARCHIUM;
GRANT SELECT, UPDATE, INSERT, DELETE ON "ARCHIUM"."ACH_CAUSALIMITACIO" TO WWW_ARCHIUM;
GRANT SELECT, UPDATE, INSERT, DELETE ON "ARCHIUM"."ACH_CONTACTE_PROCEDIMENT" TO WWW_ARCHIUM;
GRANT SELECT, UPDATE, INSERT, DELETE ON "ARCHIUM"."ACH_DICTAMEN" TO WWW_ARCHIUM;
GRANT SELECT, UPDATE, INSERT, DELETE ON "ARCHIUM"."ACH_DICTAMEN_TIPUSDOCUMENTAL" TO WWW_ARCHIUM;
GRANT SELECT, UPDATE, INSERT, DELETE ON "ARCHIUM"."ACH_ENS" TO WWW_ARCHIUM;
GRANT SELECT, UPDATE, INSERT, DELETE ON "ARCHIUM"."ACH_FAMILIAPROCEDIMENT" TO WWW_ARCHIUM;
GRANT SELECT, UPDATE, INSERT, DELETE ON "ARCHIUM"."ACH_FASEARXIU" TO WWW_ARCHIUM;
GRANT SELECT, UPDATE, INSERT, DELETE ON "ARCHIUM"."ACH_FORMAINICI" TO WWW_ARCHIUM;
GRANT SELECT, UPDATE, INSERT, DELETE ON "ARCHIUM"."ACH_FUNCIO" TO WWW_ARCHIUM;
GRANT SELECT, UPDATE, INSERT, DELETE ON "ARCHIUM"."ACH_LIMITACIO_NORMATIVA_SERIE" TO WWW_ARCHIUM;
GRANT SELECT, UPDATE, INSERT, DELETE ON "ARCHIUM"."ACH_LOPD" TO WWW_ARCHIUM;
GRANT SELECT, UPDATE, INSERT, DELETE ON "ARCHIUM"."ACH_MATERIA" TO WWW_ARCHIUM;
GRANT SELECT, UPDATE, INSERT, DELETE ON "ARCHIUM"."ACH_MATERIA_PROCEDIMENT" TO WWW_ARCHIUM;
GRANT SELECT, UPDATE, INSERT, DELETE ON "ARCHIUM"."ACH_NIVELLELECTRONIC" TO WWW_ARCHIUM;
GRANT SELECT, UPDATE, INSERT, DELETE ON "ARCHIUM"."ACH_NORMATIVA" TO WWW_ARCHIUM;
GRANT SELECT, UPDATE, INSERT, DELETE ON "ARCHIUM"."ACH_NORMATIVA_DICTAMEN" TO WWW_ARCHIUM;
GRANT SELECT, UPDATE, INSERT, DELETE ON "ARCHIUM"."ACH_NORMATIVA_PROCEDIMENT" TO WWW_ARCHIUM;
GRANT SELECT, UPDATE, INSERT, DELETE ON "ARCHIUM"."ACH_NORMATIVA_SERIEDOCUMENTAL" TO WWW_ARCHIUM;
GRANT SELECT, UPDATE, INSERT, DELETE ON "ARCHIUM"."ACH_NORMATIVA_TIPUSDOCUMENTAL" TO WWW_ARCHIUM;
GRANT SELECT, UPDATE, INSERT, DELETE ON "ARCHIUM"."ACH_PROCEDIMENT" TO WWW_ARCHIUM;
GRANT SELECT, UPDATE, INSERT, DELETE ON "ARCHIUM"."ACH_QUADRECLASSIFICACIO" TO WWW_ARCHIUM;
GRANT SELECT, UPDATE, INSERT, DELETE ON "ARCHIUM"."ACH_QUADRETIPUSDOCUMENTAL" TO WWW_ARCHIUM;
GRANT SELECT, UPDATE, INSERT, DELETE ON "ARCHIUM"."ACH_SERIEARGEN" TO WWW_ARCHIUM;
GRANT SELECT, UPDATE, INSERT, DELETE ON "ARCHIUM"."ACH_SERIEDOCUMENTAL" TO WWW_ARCHIUM;
GRANT SELECT, UPDATE, INSERT, DELETE ON "ARCHIUM"."ACH_SERIEDOCUMENTAL_MIGRACIO" TO WWW_ARCHIUM;
GRANT SELECT, UPDATE, INSERT, DELETE ON "ARCHIUM"."ACH_SERIERELACIONADA" TO WWW_ARCHIUM;
GRANT SELECT, UPDATE, INSERT, DELETE ON "ARCHIUM"."ACH_SILENCI" TO WWW_ARCHIUM;
GRANT SELECT, UPDATE, INSERT, DELETE ON "ARCHIUM"."ACH_TIPUSACCES" TO WWW_ARCHIUM;
GRANT SELECT, UPDATE, INSERT, DELETE ON "ARCHIUM"."ACH_TIPUSCONTACTE" TO WWW_ARCHIUM;
GRANT SELECT, UPDATE, INSERT, DELETE ON "ARCHIUM"."ACH_TIPUSDICTAMEN" TO WWW_ARCHIUM;
GRANT SELECT, UPDATE, INSERT, DELETE ON "ARCHIUM"."ACH_TIPUSDOCUMENTAL" TO WWW_ARCHIUM;
GRANT SELECT, UPDATE, INSERT, DELETE ON "ARCHIUM"."ACH_TIPUSDOCUMENT_PROCEDIMENT" TO WWW_ARCHIUM;
GRANT SELECT, UPDATE, INSERT, DELETE ON "ARCHIUM"."ACH_TIPUSNORMATIVA" TO WWW_ARCHIUM;
GRANT SELECT, UPDATE, INSERT, DELETE ON "ARCHIUM"."ACH_TIPUSNTI" TO WWW_ARCHIUM;
GRANT SELECT, UPDATE, INSERT, DELETE ON "ARCHIUM"."ACH_TIPUSPUBLIC" TO WWW_ARCHIUM;
GRANT SELECT, UPDATE, INSERT, DELETE ON "ARCHIUM"."ACH_TIPUSPUBLIC_PROCEDIMENT" TO WWW_ARCHIUM;
GRANT SELECT, UPDATE, INSERT, DELETE ON "ARCHIUM"."ACH_TIPUSSERIE" TO WWW_ARCHIUM;
GRANT SELECT, UPDATE, INSERT, DELETE ON "ARCHIUM"."ACH_TIPUSVALOR" TO WWW_ARCHIUM;
GRANT SELECT, UPDATE, INSERT, DELETE ON "ARCHIUM"."ACH_TRANSFERENCIA" TO WWW_ARCHIUM;
GRANT SELECT, UPDATE, INSERT, DELETE ON "ARCHIUM"."ACH_VALORACIO" TO WWW_ARCHIUM;
GRANT SELECT, UPDATE, INSERT, DELETE ON "ARCHIUM"."ACH_VALORPRIMARI" TO WWW_ARCHIUM;
GRANT SELECT, UPDATE, INSERT, DELETE ON "ARCHIUM"."ACH_VALORSECUNDARI" TO WWW_ARCHIUM;