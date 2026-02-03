-- =====================================================
-- Agregar columna DATA_CANVI_ESTAT a ACH_TIPUSDOCUMENTAL
-- =====================================================
ALTER TABLE ARCHIUM.ACH_TIPUSDOCUMENTAL ADD (DATA_CANVI_ESTAT DATE);

-- =====================================================
-- Agregar columna USUARI_ALTA a ACH_TIPUSDOCUMENTAL
-- =====================================================
ALTER TABLE ARCHIUM.ACH_TIPUSDOCUMENTAL ADD (USUARI_ALTA VARCHAR2(100));

-- =====================================================
-- Agregar columna USUARI_MODIFICACIO a ACH_TIPUSDOCUMENTAL
-- =====================================================
ALTER TABLE ARCHIUM.ACH_TIPUSDOCUMENTAL ADD (USUARI_MODIFICACIO VARCHAR2(100));

-- =====================================================
-- Agregar columna USUARI_CANVI_ESTAT a ACH_TIPUSDOCUMENTAL
-- =====================================================
ALTER TABLE ARCHIUM.ACH_TIPUSDOCUMENTAL ADD (USUARI_CANVI_ESTAT VARCHAR2(100));