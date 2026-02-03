WITH jerarquia_f AS (
    SELECT
        f.ID AS ident,
        f.ordre,
        f.codi,
        f.funcio_pare,
        f.tipusserie_id,
        f.nom,
        f.nomcas,
        LEVEL AS nivell,
        SYS_CONNECT_BY_PATH(f.ordre, '--> ') AS ruta
    FROM archium.ach_funcio f
    WHERE f.quadreclassificacio_id = :quadre
    START WITH f.funcio_pare IS NULL
    CONNECT BY PRIOR f.ID = f.funcio_pare
    ORDER SIBLINGS BY f.ordre, f.tipusserie_id, f.codi
),

/* =========================================================
   CTE RECURSIVO: SUBE POR LOS PADRES DE CADA FUNCIÓN
   ========================================================= */
ancestros (ident, ancestro_id, tipusserie_id, lvl) AS (
    -- nivel 1 = el nodo actual
    SELECT
        f.ID AS ident,
        f.ID AS ancestro_id,
        f.tipusserie_id,
        1 AS lvl
    FROM archium.ach_funcio f

    UNION ALL

    -- subir a su padre
    SELECT
        a.ident,
        f.funcio_pare AS ancestro_id,
        p.tipusserie_id,
        a.lvl + 1
    FROM ancestros a
    JOIN archium.ach_funcio f
        ON f.ID = a.ancestro_id
    JOIN archium.ach_funcio p
        ON p.ID = f.funcio_pare
),

/* =========================================================
   Para cada función, quedarse con el tipusserie más cercano
   ========================================================= */
tipusserie_final AS (
    SELECT
        ident,
        tipusserie_id,
        ROW_NUMBER() OVER (PARTITION BY ident ORDER BY lvl DESC) AS rn
    FROM ancestros
    WHERE tipusserie_id IS NOT NULL
)

SELECT
    fun.ordre,
    fun.nivell,
    fun.codi AS codi_funcio,
    fun.nom ||
        CASE WHEN fun.funcio_pare IS NULL THEN ' (' || fun.codi || ')' ELSE '' END
        AS ambit_funcio,
    fun.nomcas ||
        CASE WHEN fun.funcio_pare IS NULL THEN ' (' || fun.codi || ')' ELSE '' END
        AS ambit_funcio_es,
    ser.codi AS codi_serie,
    ser.nom AS nom_serie,
    ser.nomcas AS nom_serie_es,
    ser.enviatSAT,
    ts.nom AS tipusserie,
    ts.nomcas AS tipusserie_cas

FROM jerarquia_f fun

LEFT JOIN tipusserie_final tf
       ON tf.ident = fun.ident AND tf.rn = 1

LEFT JOIN archium.ach_tipusserie ts
       ON ts.id = tf.tipusserie_id

LEFT JOIN archium.ach_seriedocumental ser
       ON ser.funcio_id = fun.ident
      AND (LOWER(ser.estat) = :estatSerie OR :estatSerie IS NULL)