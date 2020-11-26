
  CREATE TABLE "ARCHIUM"."ACH_APLICACIO" 
   (	"ID" NUMBER(*,0), 
	"NOM" VARCHAR2(100 CHAR), 
	"ESTAT" VARCHAR2(12 CHAR), 
	"PERFIL" VARCHAR2(9 CHAR), 
	"URIDEV" VARCHAR2(400 CHAR), 
	"URIPRO" VARCHAR2(400 CHAR), 
	"INICI" DATE, 
	"FI" DATE
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "ARCHIUM" ;

   COMMENT ON COLUMN "ARCHIUM"."ACH_APLICACIO"."PERFIL" IS 'Una aplicació, segons el perfil, podrà relacionar-se amb el Catàleg de sèries o amb l''inventari de procediment';

  CREATE TABLE "ARCHIUM"."ACH_APLICACIO_SERIE" 
   (	"SERIEDOCUMENTAL_ID" NUMBER(*,0), 
	"APLICACIO_ID" NUMBER(*,0), 
	"DESCRIPCIO" VARCHAR2(4000 CHAR), 
	"INICI" DATE, 
	"FI" DATE
   ) SEGMENT CREATION DEFERRED 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  TABLESPACE "ARCHIUM" ;

  CREATE TABLE "ARCHIUM"."ACH_BUTLLETI" 
   (	"ID" NUMBER(*,0), 
	"CODI" VARCHAR2(6 CHAR),
	"NOM" VARCHAR2(200 CHAR),
	"DESCRIPCIO" VARCHAR2(4000 CHAR)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "ARCHIUM" ;

  CREATE TABLE "ARCHIUM"."ACH_CATALEGSERIES" 
   (	"ID" NUMBER(*,0), 
	"NOM" VARCHAR2(200 CHAR), 
	"NOMCAS" VARCHAR2(200 CHAR), 
	"ESTAT" VARCHAR2(12 CHAR), 
	"INICI" DATE, 
	"VERSIO" VARCHAR2(10 CHAR), 
	"FI" DATE
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "ARCHIUM" ;

  CREATE TABLE "ARCHIUM"."ACH_CATEGORIANTI" 
   (	"ID" NUMBER(*,0), 
	"NOM" VARCHAR2(200 CHAR), 
	"NOMCAS" VARCHAR2(200 CHAR)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "ARCHIUM" ;

  CREATE TABLE "ARCHIUM"."ACH_CAUSALIMITACIO" 
   (	"ID" NUMBER(*,0), 
	"CODI" VARCHAR2(1 CHAR), 
	"NOM" VARCHAR2(200 CHAR), 
	"NOMCAS" VARCHAR2(200 CHAR)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "ARCHIUM" ;

  CREATE TABLE "ARCHIUM"."ACH_CONTACTE_PROCEDIMENT" 
   (	"PROCEDIMENT_ID" NUMBER(*,0), 
	"CONTACTE" VARCHAR2(1000 CHAR), 
	"TIPUSCONTACTE_ID" NUMBER(*,0)
   ) SEGMENT CREATION DEFERRED 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  TABLESPACE "ARCHIUM" ;

  CREATE TABLE "ARCHIUM"."ACH_DICTAMEN" 
   (	"ID" NUMBER(*,0), 
	"SERIEDOCUMENTAL_ID" NUMBER(*,0), 
	"TIPUSDICTAMEN_ID" NUMBER(*,0), 
	"ACCIODICTAMINADA" VARCHAR2(4000 CHAR), 
	"TERMINI" VARCHAR2(6 CHAR), 
	"INICI" DATE, 
	"DESTINATARISRESTRINGITS" VARCHAR2(4000 CHAR), 
	"FI" DATE, 
	"TIPUSACCES_ID" NUMBER(*,0), 
	"ENS_ID" NUMBER(*,0), 
	"LOPD_ID" NUMBER(*,0), 
	"CONDICIOREUTILITZACIO" VARCHAR2(4000 CHAR), 
	"SERIEESSENCIAL" NUMBER(1,0), 
	"APROVACIO" DATE, 
	"NORMATIVA_APROVACIO_ID" NUMBER(*,0), 
	"ESTAT" VARCHAR2(12 CHAR) DEFAULT 'Esborrany',
	"CODI" VARCHAR2(10 CHAR) DEFAULT 'CODIXXXX'
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "ARCHIUM" ;

   COMMENT ON COLUMN "ARCHIUM"."ACH_DICTAMEN"."CODI" IS 'Codi seqüencial que, idealment, hauria de començar pel següent valor de les taules de valoració ARGEN (actualment, 26/03/2020, seria valor 265)';

  CREATE TABLE "ARCHIUM"."ACH_DICTAMEN_TIPUSDOCUMENTAL" 
   (	"DICTAMEN_ID" NUMBER(*,0), 
	"TIPUSDOCUMENTAL_ID" NUMBER(*,0), 
	"TIPUSDICTAMEN_ID" NUMBER(*,0), 
	"CONSERVABLE" NUMBER(1,0), 
	"INICI" DATE, 
	"TERMINI" VARCHAR2(6 CHAR), 
	"FI" DATE
   ) SEGMENT CREATION DEFERRED 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  TABLESPACE "ARCHIUM" ;

  CREATE TABLE "ARCHIUM"."ACH_ENS" 
   (	"ID" NUMBER(*,0), 
	"NOM" VARCHAR2(200 CHAR), 
	"NOMCAS" VARCHAR2(200 CHAR), 
	"DESCRIPCIO" VARCHAR2(4000 CHAR), 
	"DESCRIPCIOCAS" VARCHAR2(4000 CHAR)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "ARCHIUM" ;

  CREATE TABLE "ARCHIUM"."ACH_FAMILIAPROCEDIMENT" 
   (	"ID" NUMBER(*,0), 
	"NOM" VARCHAR2(200 CHAR)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "ARCHIUM" ;

  CREATE TABLE "ARCHIUM"."ACH_FASEARXIU" 
   (	"ID" NUMBER(*,0), 
	"NOM" VARCHAR2(200 CHAR), 
	"NOMCAS" VARCHAR2(200 CHAR)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "ARCHIUM" ;

  CREATE TABLE "ARCHIUM"."ACH_FORMAINICI" 
   (	"ID" NUMBER(*,0), 
	"NOM" VARCHAR2(200 CHAR), 
	"DESCRIPCIO" VARCHAR2(4000 CHAR)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "ARCHIUM" ;

  CREATE TABLE "ARCHIUM"."ACH_FUNCIO" 
   (	"ID" NUMBER(*,0), 
	"CODI" VARCHAR2(10 CHAR), 
	"QUADRECLASSIFICACIO_ID" NUMBER(*,0), 
	"NOM" VARCHAR2(200 CHAR), 
	"NOMCAS" VARCHAR2(200 CHAR), 
	"FUNCIO_PARE" NUMBER(*,0), 
	"TIPUSSERIE_ID" NUMBER(*,0), 
	"ESTAT" VARCHAR2(12 CHAR), 
	"ORDRE" NUMBER(*,0) DEFAULT 100, 
	"INICI" DATE, 
	"MODIFICACIO" DATE, 
	"FI" DATE
   ) SEGMENT CREATION IMMEDIATE
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "ARCHIUM" ;

   COMMENT ON COLUMN "ARCHIUM"."ACH_FUNCIO"."TIPUSSERIE_ID" IS 'Opcional, perquè per defecte heretarà el valor de la funció pare';

  CREATE TABLE "ARCHIUM"."ACH_LIMITACIO_NORMATIVA_SERIE" 
   (	"SERIEDOCUMENTAL_ID" NUMBER(*,0), 
	"NORMATIVA_ID" NUMBER(*,0), 
	"INICI" DATE, 
	"FI" DATE, 
	"CAUSALIMITACIO_ID" NUMBER(*,0)
   ) SEGMENT CREATION DEFERRED 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  TABLESPACE "ARCHIUM" ;

  CREATE TABLE "ARCHIUM"."ACH_LOPD" 
   (	"ID" NUMBER(*,0), 
	"NOM" VARCHAR2(200 CHAR), 
	"NOMCAS" VARCHAR2(200 CHAR), 
	"DESCRIPCIO" VARCHAR2(4000 CHAR), 
	"DESCRIPCIOCAS" VARCHAR2(4000 CHAR)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "ARCHIUM" ;

  CREATE TABLE "ARCHIUM"."ACH_MATERIA" 
   (	"ID" NUMBER(*,0), 
	"NOM" VARCHAR2(200 CHAR)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "ARCHIUM" ;

  CREATE TABLE "ARCHIUM"."ACH_MATERIA_PROCEDIMENT" 
   (	"PROCEDIMENT_ID" NUMBER(*,0), 
	"MATERIA_ID" NUMBER(*,0)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "ARCHIUM" ;

  CREATE TABLE "ARCHIUM"."ACH_NIVELLELECTRONIC" 
   (	"ID" NUMBER(*,0), 
	"NOM" VARCHAR2(200 CHAR), 
	"DESCRIPCIO" VARCHAR2(4000 CHAR)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "ARCHIUM" ;

  CREATE TABLE "ARCHIUM"."ACH_NORMATIVA" 
   (	"ID" NUMBER(*,0), 
	"CODI" VARCHAR2(32 CHAR), 
	"NOM" VARCHAR2(2000 CHAR), 
	"NOMCAS" VARCHAR2(2000 CHAR), 
	"URI" VARCHAR2(400 CHAR), 
	"URIELI" VARCHAR2(400 CHAR), 
	"URIELICONSOLIDADA" VARCHAR2(400 CHAR), 
	"VIGOR" DATE, 
	"ESTAT" VARCHAR2(12 CHAR), 
	"DEROGACIO" DATE, 
	"NORMATIVA_VIGENT" NUMBER(*,0),
  "BUTLLETI_ID" NUMBER(*,0),
  "TIPUSNORMATIVA_ID" NUMBER(*,0)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "ARCHIUM" ;

   COMMENT ON COLUMN "ARCHIUM"."ACH_NORMATIVA"."NORMATIVA_VIGENT" IS 'Enllaç a la normativa vigent en cas de normativa derogada';

  CREATE TABLE "ARCHIUM"."ACH_NORMATIVA_DICTAMEN" 
   (	"DICTAMEN_ID" NUMBER(*,0), 
	"NORMATIVA_ID" NUMBER(*,0)
   ) SEGMENT CREATION DEFERRED 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  TABLESPACE "ARCHIUM" ;

   COMMENT ON TABLE "ARCHIUM"."ACH_NORMATIVA_DICTAMEN"  IS 'Representa el fonament jurídic en què es basen les decisions de conservació';

  CREATE TABLE "ARCHIUM"."ACH_NORMATIVA_PROCEDIMENT" 
   (	"PROCEDIMENT_ID" NUMBER(*,0), 
	"NORMATIVA_ID" NUMBER(*,0)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "ARCHIUM" ;

  CREATE TABLE "ARCHIUM"."ACH_NORMATIVA_SERIEDOCUMENTAL" 
   (	"SERIEDOCUMENTAL_ID" NUMBER(*,0), 
	"INICI" DATE, 
	"FI" DATE, 
	"NORMATIVA_ID" NUMBER(*,0)
   ) SEGMENT CREATION DEFERRED 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  TABLESPACE "ARCHIUM" ;

   COMMENT ON TABLE "ARCHIUM"."ACH_NORMATIVA_SERIEDOCUMENTAL"  IS 'Representa el marc legal de la sèrie documental';

  CREATE TABLE "ARCHIUM"."ACH_NORMATIVA_TIPUSDOCUMENTAL" 
   (	"TIPUSDOCUMENTAL_ID" NUMBER(*,0), 
	"NORMATIVA_ID" NUMBER(*,0)
   ) SEGMENT CREATION DEFERRED 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  TABLESPACE "ARCHIUM" ;

  CREATE TABLE "ARCHIUM"."ACH_PROCEDIMENT" 
   (	"ID" NUMBER(*,0), 
	"CODISIA" VARCHAR2(30 CHAR), 
	"NOM" VARCHAR2(2000 CHAR), 
	"OBJECTE" VARCHAR2(4000 CHAR), 
	"ESTAT" VARCHAR2(12 CHAR), 
	"DESTINATARIS" VARCHAR2(4000 CHAR), 
	"OBSERVACIONS" VARCHAR2(4000 CHAR), 
	"URI" VARCHAR2(400 CHAR), 
	"FAMILIAPROCEDIMENT_ID" NUMBER(*,0), 
	"FORMAINICI_ID" NUMBER(*,0), 
	"SILENCI_ID" NUMBER(*,0), 
	"NIVELLELECTRONIC_ID" NUMBER(*,0), 
	"SERIEDOCUMENTAL_ID" NUMBER(*,0), 
	"APLICACIO_ID" NUMBER(*,0), 
	"CODIROLSAC" VARCHAR2(32 CHAR), 
	"TERMINI" VARCHAR2(6 CHAR), 
	"TERMININOTIF" VARCHAR2(6 CHAR), 
	"FIVIAADMINISTRATIVA" NUMBER(1,0), 
	"TAXA" NUMBER(1,0), 
	"DIR3_RESOLVENT" VARCHAR2(9 CHAR), 
	"DIR3_INSTRUCTOR" VARCHAR2(9 CHAR), 
	"PUBLICACIO" DATE, 
	"CADUCITAT" DATE, 
	"GESTOR" VARCHAR2(100 CHAR), 
	"MODIFICACIO" DATE DEFAULT (sysdate)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "ARCHIUM" ;

   COMMENT ON COLUMN "ARCHIUM"."ACH_PROCEDIMENT"."GESTOR" IS 'Idealment hauria de ser una FK cap a una taula de serveis o unitats administratives';

  CREATE TABLE "ARCHIUM"."ACH_QUADRECLASSIFICACIO" 
   (	"ID" NUMBER(*,0),
	"NOM" VARCHAR2(200 CHAR),
	"NOMCAS" VARCHAR2(200 CHAR), 
	"ESTAT" VARCHAR2(12 CHAR), 
	"INICI" DATE, 
	"MODIFICACIO" DATE, 
	"VERSIO" VARCHAR2(10 CHAR) DEFAULT 1.0, 
	"FI" DATE
   ) SEGMENT CREATION IMMEDIATE
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "ARCHIUM" ;

  CREATE TABLE "ARCHIUM"."ACH_QUADRETIPUSDOCUMENTAL" 
   (	"ID" NUMBER(*,0), 
	"NOM" VARCHAR2(200 CHAR), 
	"NOMCAS" VARCHAR2(200 CHAR), 
	"ESTAT" VARCHAR2(12 CHAR), 
	"INICI" DATE, 
	"MODIFICACIO" DATE, 
	"VERSIO" VARCHAR2(10 CHAR) DEFAULT '1.0', 
	"FI" DATE
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "ARCHIUM" ;

  CREATE TABLE "ARCHIUM"."ACH_SERIEARGEN" 
   (	"ID" NUMBER(*,0), 
	"CODI" VARCHAR2(4 CHAR), 
	"NOM" VARCHAR2(200 CHAR)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "ARCHIUM" ;

   COMMENT ON COLUMN "ARCHIUM"."ACH_SERIEARGEN"."NOM" IS 'Sèries de l''arxiu general que són les que actualment s''utilitzen per a la gestió en paper';

  CREATE TABLE "ARCHIUM"."ACH_SERIEDOCUMENTAL" 
   (	"ID" NUMBER(*,0), 
	"CODI" VARCHAR2(10 CHAR), 
	"NOM" VARCHAR2(1000 CHAR), 
	"NOMCAS" VARCHAR2(1000 CHAR), 
	"CATALEGSERIES_ID" NUMBER(*,0), 
	"FUNCIO_ID" NUMBER(*,0), 
	"DESCRIPCIO" VARCHAR2(4000 CHAR), 
	"DESCRIPCIOCAS" VARCHAR2(4000 CHAR), 
	"RESUMMIGRACIO" VARCHAR2(4000 CHAR), 
	"DIR3_PROMOTOR" VARCHAR2(9 CHAR), 
	"TIPUSSERIE_ID" NUMBER(*,0), 
	"CODIIECISA" VARCHAR2(32 CHAR), 
	"ESTAT" VARCHAR2(12 BYTE)
   ) SEGMENT CREATION IMMEDIATE
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "ARCHIUM" ;

   COMMENT ON COLUMN "ARCHIUM"."ACH_SERIEDOCUMENTAL"."TIPUSSERIE_ID" IS 'Una sèrie pot redefinir el caràcter comú o específic que li arriba per la funció del QdC';

  CREATE TABLE "ARCHIUM"."ACH_SERIEDOCUMENTAL_MIGRACIO" 
   (	"SERIEDOCUMENTAL_ID" NUMBER(*,0), 
	"SERIEARGEN_ID" NUMBER(*,0)
   ) SEGMENT CREATION DEFERRED 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  TABLESPACE "ARCHIUM" ;

   COMMENT ON TABLE "ARCHIUM"."ACH_SERIEDOCUMENTAL_MIGRACIO"  IS 'Permet deixat documentat el procés de migració de les sèries ARGEN cap a les sèries documentals.';

  CREATE TABLE "ARCHIUM"."ACH_SERIERELACIONADA" 
   (	"ID" NUMBER(*,0), 
	"SERIEDOCUMENTAL_ID" NUMBER(*,0), 
	"SERIE_RELACIONADA" NUMBER(*,0), 
	"SERIEARGEN_RELACIONADA" NUMBER(*,0)
   ) SEGMENT CREATION DEFERRED 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  TABLESPACE "ARCHIUM" ;

  CREATE TABLE "ARCHIUM"."ACH_SILENCI" 
   (	"ID" NUMBER(*,0), 
	"NOM" VARCHAR2(200 CHAR)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "ARCHIUM" ;

  CREATE TABLE "ARCHIUM"."ACH_TIPUSACCES" 
   (	"ID" NUMBER(*,0), 
	"NOM" VARCHAR2(200 CHAR), 
	"NOMCAS" VARCHAR2(200 CHAR)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "ARCHIUM" ;

  CREATE TABLE "ARCHIUM"."ACH_TIPUSCONTACTE" 
   (	"ID" NUMBER(*,0), 
	"NOM" VARCHAR2(200 CHAR), 
	"NOMCAS" VARCHAR2(200 CHAR)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "ARCHIUM" ;

  CREATE TABLE "ARCHIUM"."ACH_TIPUSDICTAMEN" 
   (	"ID" NUMBER(*,0), 
	"CODI" VARCHAR2(2 CHAR), 
	"NOM" VARCHAR2(200 CHAR), 
	"NOMCAS" VARCHAR2(200 CHAR)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "ARCHIUM" ;

  CREATE TABLE "ARCHIUM"."ACH_TIPUSDOCUMENTAL" 
   (	"ID" NUMBER(*,0), 
	"CODI" VARCHAR2(12 CHAR), 
	"NOM" VARCHAR2(1000 CHAR), 
	"NOMCAS" VARCHAR2(1000 CHAR), 
	"QUADRETIPUSDOCUMENTAL_ID" NUMBER(*,0), 
	"TIPUSNTI_ID" NUMBER(*,0), 
	"DEFINICIO" VARCHAR2(4000 CHAR), 
	"DEFINICIOCAS" VARCHAR2(4000 CHAR), 
	"INICI" DATE, 
	"MODIFICACIO" DATE, 
	"ESTAT" VARCHAR2(12 CHAR), 
	"FI" DATE
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "ARCHIUM" ;

   COMMENT ON COLUMN "ARCHIUM"."ACH_TIPUSDOCUMENTAL"."CODI" IS 'Codi únic que referencia el tipus documental. Format TDxx-xxx';

  CREATE TABLE "ARCHIUM"."ACH_TIPUSDOCUMENT_PROCEDIMENT" 
   (	"PROCEDIMENT_ID" NUMBER(*,0), 
	"TIPUSDOCUMENTAL_ID" NUMBER(*,0), 
	"OBLIGATORI" NUMBER(1,0), 
	"RECAPITULATIU" NUMBER(1,0)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "ARCHIUM" ;

  CREATE TABLE "ARCHIUM"."ACH_TIPUSNORMATIVA" 
   (	"ID" NUMBER(*,0), 
	"NOM" VARCHAR2(200 CHAR)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "ARCHIUM" ;

  CREATE TABLE "ARCHIUM"."ACH_TIPUSNTI" 
   (	"ID" NUMBER(*,0), 
	"CODI" VARCHAR2(6 CHAR), 
	"NOM" VARCHAR2(200 CHAR), 
	"NOMCAS" VARCHAR2(200 CHAR), 
	"CATEGORIANTI_ID" NUMBER(*,0)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "ARCHIUM" ;

  CREATE TABLE "ARCHIUM"."ACH_TIPUSPUBLIC" 
   (	"ID" NUMBER(*,0), 
	"NOM" VARCHAR2(200 CHAR), 
	"DESCRIPCIO" VARCHAR2(4000 CHAR)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "ARCHIUM" ;

   COMMENT ON TABLE "ARCHIUM"."ACH_TIPUSPUBLIC"  IS 'Taula mestra que recull els blocs de destinataris, tal com està dissenyat a la Seu (ciutadania, empreses i administracions públiques)';

  CREATE TABLE "ARCHIUM"."ACH_TIPUSPUBLIC_PROCEDIMENT" 
   (	"PROCEDIMENT_ID" NUMBER(*,0), 
	"TIPUSPUBLIC_ID" NUMBER(*,0)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "ARCHIUM" ;

  CREATE TABLE "ARCHIUM"."ACH_TIPUSSERIE" 
   (	"ID" NUMBER(*,0), 
	"NOM" VARCHAR2(20 CHAR), 
	"NOMCAS" VARCHAR2(20 CHAR)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "ARCHIUM" ;

  CREATE TABLE "ARCHIUM"."ACH_TIPUSVALOR" 
   (	"ID" NUMBER(*,0), 
	"NOM" VARCHAR2(200 CHAR), 
	"NOMCAS" VARCHAR2(200 CHAR), 
	"DESCRIPCIO" VARCHAR2(4000 CHAR), 
	"DESCRIPCIOCAS" VARCHAR2(4000 CHAR)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "ARCHIUM" ;

  CREATE TABLE "ARCHIUM"."ACH_TRANSFERENCIA" 
   (	"ID" NUMBER(*,0), 
	"SERIEDOCUMENTAL_ID" NUMBER(*,0), 
	"FASEARXIU_ID" NUMBER(*,0), 
	"TERMINI" VARCHAR2(6 CHAR), 
	"INICI" DATE, 
	"FI" DATE
   ) SEGMENT CREATION DEFERRED 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  TABLESPACE "ARCHIUM" ;

  CREATE TABLE "ARCHIUM"."ACH_VALORACIO" 
   (	"ID" NUMBER(*,0), 
	"SERIEDOCUMENTAL_ID" NUMBER(*,0), 
	"VALORSECUNDARI_ID" NUMBER(*,0), 
	"INICI" DATE, 
	"FI" DATE
   ) SEGMENT CREATION DEFERRED 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  TABLESPACE "ARCHIUM" ;

   COMMENT ON TABLE "ARCHIUM"."ACH_VALORACIO"  IS 'Recull els valors arxivístics que va adquirint la sèrie';

  CREATE TABLE "ARCHIUM"."ACH_VALORPRIMARI" 
   (	"VALORACIO_ID" NUMBER(*,0), 
	"TERMINI" VARCHAR2(6 CHAR), 
	"TIPUSVALOR_ID" NUMBER(*,0)
   ) SEGMENT CREATION DEFERRED 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  TABLESPACE "ARCHIUM" ;

  CREATE TABLE "ARCHIUM"."ACH_VALORSECUNDARI" 
   (	"ID" NUMBER(*,0), 
	"NOM" VARCHAR2(200 CHAR), 
	"NOMCAS" VARCHAR2(200 CHAR), 
	"DESCRIPCIO" VARCHAR2(4000 CHAR), 
	"DESCRIPCIOCAS" VARCHAR2(4000 CHAR)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "ARCHIUM" ;