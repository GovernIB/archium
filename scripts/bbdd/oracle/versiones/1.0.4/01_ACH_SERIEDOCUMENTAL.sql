ALTER TABLE ACH_SERIEDOCUMENTAL ADD ENVIATSAT NUMBER(1,0);
 
COMMENT ON COLUMN ACH_SERIEDOCUMENTAL.ENVIATSAT IS 'Indica si la sèrie documental ha estat creada al ''Sistema de Apoyo a la Tramitación'' (SAT, Alfresco DM)'; 
