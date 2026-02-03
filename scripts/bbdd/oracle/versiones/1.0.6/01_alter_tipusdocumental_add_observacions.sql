-- =====================================================
-- Agregar columna OBSERVACIONS a ACH_TIPUSDOCUMENTAL
-- =====================================================

ALTER TABLE ARCHIUM.ACH_TIPUSDOCUMENTAL ADD (OBSERVACIONS VARCHAR2(4000 CHAR));
