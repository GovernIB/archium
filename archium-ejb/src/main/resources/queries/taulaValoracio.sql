SELECT
    dic.codi              AS taula_avaluacio,
    serie.codi            AS codi_serie,
    serie.nom             AS nom_serie,
    tipusserie.nom        AS tipus_serie,
    serie.descripcio      AS descripcio_serie,
    fun.nom
    ||
    CASE
        WHEN fun.funcio_pare IS NOT NULL THEN
                (
                    SELECT
                        ' ('
                        || CONNECT_BY_ROOT upper(funroot.nom)
                        || ')'
                    FROM
                        ach_funcio funroot
                    WHERE
                        funroot.id = fun.id
                    START WITH
                        funroot.funcio_pare IS NULL
                    CONNECT BY
                        PRIOR funroot.id = funroot.funcio_pare
                )
        ELSE
            ''
    END
    AS materia_aka_funcio,
    (
        SELECT
            regexp_replace(dbms_xmlgen.convert(XMLSERIALIZE(CONTENT XMLAGG(xmlelement(e, '- ' || nor.nom).extract('/*')
                ORDER BY
                    nor.nom
            ) INDENT),
                                               1),
                           '<.+?>') AS list
        FROM
                 (
                SELECT
                    norser.normativa_id
                FROM
                    ach_normativa_seriedocumental norser
                WHERE
                    serie.id = norser.seriedocumental_id
                UNION
                SELECT
                    norprc.normativa_id
                FROM
                         ach_normativa_procediment norprc
                    JOIN ach_procediment prc ON prc.id = norprc.procediment_id
                WHERE
                    serie.id = prc.seriedocumental_id
            ) marc
            JOIN ach_normativa nor ON nor.id = marc.normativa_id
    )                     AS marc_legal,
    (
        SELECT
            regexp_replace(dbms_xmlgen.convert(XMLSERIALIZE(CONTENT XMLAGG(xmlelement(e, '- '
                                                                                         || pro.nom
                                                                                         || CASE
                WHEN pro.codisia IS NULL THEN
                    ''
                ELSE ' (SIA: '
                     || pro.codisia
                     || ')'
            END || CHR(10), xmlagg(xmlelement(e, '@tab@' || '- '
                                      || doc.nom))).extract('/*')
                ORDER BY
                    pro.nom, doc.nom
            ) INDENT),
                                               1),
                           '<.+?>') AS list
        FROM
                 ach_procediment pro
            JOIN ach_procediment_serie         prs ON prs.procediment_id = pro.id
            JOIN ach_tipusdocument_procediment docobligatori ON pro.id = docobligatori.procediment_id
            JOIN ach_tipusdocumental           doc ON doc.id = docobligatori.tipusdocumental_id
        WHERE
                prs.seriedocumental_id = serie.id
            AND docobligatori.obligatori = 1
        GROUP BY
            pro.nom,
            pro.codisia,
            doc.nom
    ) AS documents_obligatoris_comuns,
    (
        SELECT
            regexp_replace(dbms_xmlgen.convert(XMLSERIALIZE(CONTENT XMLAGG(xmlelement(e, '- ' || nom).extract('/*')
                ORDER BY
                    nom
            ) INDENT),
                                               1),
                           '<.+?>') AS list
        FROM
            (
                SELECT DISTINCT
                    doc.nom
                FROM
                         ach_procediment pro
                    JOIN ach_tipusdocument_procediment docobligatori ON pro.id = docobligatori.procediment_id
                    JOIN ach_tipusdocumental           doc ON doc.id = docobligatori.tipusdocumental_id
                WHERE
                        pro.seriedocumental_id = serie.id
                    AND docobligatori.obligatori = 1
            )
    ) AS documents_obligatoris,
    (
            SELECT
                regexp_replace(dbms_xmlgen.convert(XMLSERIALIZE(CONTENT XMLAGG(xmlelement(e, '- ' || nom).extract('/*')
                    ORDER BY
                        nom
                ) INDENT),
                                                   1),
                               '<.+?>') AS list
            FROM
                (
                    SELECT DISTINCT
                        doc.nom
                    FROM ach_tipusdocument_serie docobligatori
                        JOIN ach_tipusdocumental           doc ON doc.id = docobligatori.tipusdocumental_id
                    WHERE
                            serie.id = docobligatori.seriedocumental_id
                        AND docobligatori.obligatori = 1
                )
    ) AS docs_obligatoris_agrupacions,
    (
        SELECT
            regexp_replace(dbms_xmlgen.convert(XMLSERIALIZE(CONTENT XMLAGG(xmlelement(e, '- ' || apl.nom).extract('/*')
                ORDER BY
                    apl.nom
            ) INDENT),
                                               1),
                           '<.+?>') AS list
        FROM
                 ach_aplicacio_serie apl_serie
            JOIN ach_aplicacio apl ON apl.id = apl_serie.aplicacio_id
        WHERE
            apl_serie.seriedocumental_id = serie.id
    )                                   AS aplicacions,
    (
        SELECT
            regexp_replace(dbms_xmlgen.convert(XMLSERIALIZE(CONTENT XMLAGG(xmlelement(e, '- '
                                                                                         || nvl(serierel.nom, argenrel.codi
                                                                                                              || ' '
                                                                                                              || argenrel.nom)).extract
                                                                                                              ('/*')
                ORDER BY
                    nvl(serierel.nom, argenrel.codi)
            ) INDENT),
                                               1),
                           '<.+?>') AS list
        FROM
                 ach_serierelacionada rel
            JOIN ach_tipusrelacio    trel ON trel.id = rel.tipusrelacio_id
            LEFT OUTER JOIN ach_seriedocumental serierel ON serierel.id = rel.serie_relacionada
            LEFT OUTER JOIN ach_serieargen      argenrel ON rel.serieargen_relacionada = argenrel.id
        WHERE
                rel.seriedocumental_id = serie.id
            AND trel.codi = 'TEM'
    )                                   AS series_relacionades,
    (
        SELECT
            regexp_replace(dbms_xmlgen.convert(XMLSERIALIZE(CONTENT XMLAGG(xmlelement(e, '- '
                                                                                         || serierel.codi
                                                                                         || ' '
                                                                                         || serierel.nom).extract('/*')
                ORDER BY
                    serierel.codi
            ) INDENT),
                                               1),
                           '<.+?>') AS list
        FROM
                 ach_serierelacionada rel
            JOIN ach_tipusrelacio    trel ON trel.id = rel.tipusrelacio_id
            LEFT OUTER JOIN ach_seriedocumental serierel ON serierel.id = rel.serie_relacionada
        WHERE
                rel.seriedocumental_id = serie.id
            AND trel.codi = 'RCP'
    )                                   AS documents_recapitulatius,
    nvl(dic.acciodictaminada,
        CASE tdic.codi
            WHEN 'CP' THEN
                tdic.nom
            WHEN 'ET' THEN
                tdic.nom
                || ' als '
				                     /* Expressió per passar termini a text */
                || regexp_replace(dic.termini, '[^[:digit:]]', '')
                ||
                CASE upper(regexp_replace(dic.termini, '[^[:alpha:]]', ''))
                        WHEN 'A'  THEN
                            ' anys'
                        WHEN 'M'  THEN
                            ' mesos'
                        WHEN 'DH' THEN
                            ' dies hàbils'
                        WHEN 'DN' THEN
                            ' dies naturals'
                        WHEN 'D'  THEN
                            ' dies'
                        WHEN 'H'  THEN
                            ' hores'
                END
                || ' de la finalització de l''expedient'
            WHEN 'EP' THEN
				                        /* Consulta sobre els documents específics del dictamen */
                tdic.nom
                || CHR(13)
                || CHR(10)
                || 'Conservació: '
                || CHR(13)
                || CHR(10)
                || to_char((
                    SELECT
                        regexp_replace(dbms_xmlgen.convert(XMLSERIALIZE(CONTENT XMLAGG(xmlelement(e, '- '
                                                                                                     || doc.nom
                                                                                                     || ' ('
                                                                                                     || regexp_replace(nvl(dicdoc.termini
                                                                                                     , dic.termini), '[^[:digit:]]', ''
                                                                                                     )
                                                                                                     ||
                            CASE upper(regexp_replace(nvl(dicdoc.termini, dic.termini), '[^[:alpha:]]', ''))
                                WHEN 'A' THEN
                                    ' anys'
                                WHEN 'M' THEN
                                    ' mesos'
                                WHEN 'DH' THEN
                                    ' dies hàbils'
                                WHEN 'DN' THEN
                                    ' dies naturals'
                                WHEN 'D' THEN
                                    ' dies'
                                WHEN 'H' THEN
                                    ' hores'
                            END
                                                                                                     || ')').extract('/*')
                            ORDER BY
                                doc.nom
                        ) INDENT),
                                                           1),
                                       '<.+?>') AS list
                    FROM
                             ach_dictamen_tipusdocumental dicdoc
                        JOIN ach_tipusdocumental doc ON doc.id = dicdoc.tipusdocumental_id
                    WHERE
                            dic.id = dicdoc.dictamen_id
                        AND dicdoc.conservable = 1
                ))
				                     /* NOTA: Hay que tomar una decisión de qué significa cada columna en el caso de Eliminación parcial. Termini del dictamen es el periodo por defecto de eliminación o de conservación?
				                     TAl vez hay que tener dos periodos a nivel de dictamen (uno para eliminación y otro para conservación). Se asume que los plazos de eliminación van específicos para cada tipo documental (dicdoc) */
                || 'Eliminació: '
                || CHR(13)
                || CHR(10)
                || to_char((
                    SELECT
                        regexp_replace(dbms_xmlgen.convert(XMLSERIALIZE(CONTENT XMLAGG(xmlelement(e, '- '
                                                                                                     || doc.nom
                                                                                                     || ' ('
                                                                                                     || regexp_replace(nvl(dicdoc.termini
                                                                                                     , dic.termini), '[^[:digit:]]', ''
                                                                                                     )
                                                                                                     ||
                            CASE upper(regexp_replace(nvl(dicdoc.termini, dic.termini), '[^[:alpha:]]', ''))
                                WHEN 'A' THEN
                                    ' anys'
                                WHEN 'M' THEN
                                    ' mesos'
                                WHEN 'DH' THEN
                                    ' dies hàbils'
                                WHEN 'DN' THEN
                                    ' dies naturals'
                                WHEN 'D' THEN
                                    ' dies'
                                WHEN 'H' THEN
                                    ' hores'
                            END
                                                                                                     || ')').extract('/*')
                            ORDER BY
                                doc.nom
                        ) INDENT),
                                                           1),
                                       '<.+?>') AS list
                    FROM
                             ach_dictamen_tipusdocumental dicdoc
                        JOIN ach_tipusdocumental doc ON doc.id = dicdoc.tipusdocumental_id
                    WHERE
                            dic.id = dicdoc.dictamen_id
                        AND dicdoc.conservable = 0
                ))
            ELSE
                tdic.nom
        END
    )                                   AS resolucio_aka_acciodictaminada,
    CASE
        WHEN acces.id = 1 THEN
            acces.nom
        WHEN acces.id IN ( 2, 3 ) THEN
            acces.nom
            ||
            CASE
                WHEN dic.destinatarisrestringits IS NOT NULL THEN
                        ': ' || dic.destinatarisrestringits
                ELSE
                    ''
            END
        ELSE
            NULL  /*Tal vegada hauria de posar 'Libre' */
    END                                 AS acces_serie,
    lopd.nom                            nivell_lopd,
    ens.nom                             nivell_ens,
    (
        SELECT
            regexp_replace(dbms_xmlgen.convert(XMLSERIALIZE(CONTENT XMLAGG(xmlelement(e, '- ' || nor.nom).extract('/*')
                ORDER BY
                    nor.nom
            ) INDENT),
                                               1),
                           '<.+?>') AS list
        FROM
                 ach_normativa_dictamen nordic
            JOIN ach_normativa nor ON nor.id = nordic.normativa_id
        WHERE
            dic.id = nordic.dictamen_id
    )                                   AS fonamentacio_juridica,
    serie.dir3_promotor,
				         /* Si existeix dictamen previ (LAG) vigent ordenat per data aprovació, el mostram...Si no, el del propi dictamen */
    dic.id,
    nvl((
        SELECT
            MIN(dicprevi.aprovacio)
        FROM
            ach_dictamen dicprevi
        WHERE
                dicprevi.seriedocumental_id = serie.id
            AND dicprevi.estat = 'VIGENT'
    ),
        dic.aprovacio)                  AS data_aprovacio,
    CASE
        WHEN (
            SELECT
                1
            FROM
                ach_dictamen dicprevi
            WHERE
                    dicprevi.seriedocumental_id = serie.id
                AND dic.id <> dicprevi.id
                AND dic.aprovacio > dicprevi.aprovacio
                AND dicprevi.estat = 'VIGENT'
        ) IS NULL THEN
            NULL
        ELSE
            dic.aprovacio
    END                                 AS data_actualitzacio,
				         /*CASE WHEN LAG(CASE WHEN dic.estat = 'VIGENT' THEN dic.aprovacio ELSE NULL END, 1, NULL) OVER (ORDER BY CASE WHEN dic.estat = 'VIGENT' THEN 0 ELSE 1 END, dic.aprovacio) IS NOT NULL THEN dic.aprovacio
				         ELSE NULL END AS data_actualitzacio*/
    dic.codi
    || '_v'
    || ROW_NUMBER()
       OVER(PARTITION BY dic.codi
            ORDER BY
                dic.codi, dic.aprovacio
       )                                   AS guardar_como,
    (
        SELECT
            regexp_replace(dbms_xmlgen.convert(XMLSERIALIZE(CONTENT XMLAGG(xmlelement(e, '- '
                                                                                         || pro.nom
                                                                                         || CASE
                WHEN pro.codisia IS NULL THEN
                    ''
                ELSE ' (SIA: '
                     || pro.codisia
                     || ')'
            END || CHR(10), xmlagg(xmlelement(e, '@tab@' || '- '
                                      || doc.nom))).extract('/*')
                ORDER BY
                    pro.nom
            ) INDENT),
                                               1),
                           '<.+?>') AS list
        FROM
                 ach_procediment pro
            JOIN ach_procediment_serie prs ON  prs.procediment_id = pro.id
            JOIN ach_tipusdocument_procediment docobligatori ON pro.id = docobligatori.procediment_id
            JOIN ach_tipusdocumental           doc ON doc.id = docobligatori.tipusdocumental_id
        WHERE
                prs.seriedocumental_id = serie.id
            AND docobligatori.obligatori = 0
        GROUP BY
            pro.nom,
            pro.codisia
    ) AS documents_opcionals_comuns,
    (
        SELECT
            regexp_replace(dbms_xmlgen.convert(XMLSERIALIZE(CONTENT XMLAGG(xmlelement(e, '- ' || nom).extract('/*')
                ORDER BY
                    nom
            ) INDENT),
                                               1),
                           '<.+?>') AS list
        FROM
            (
                SELECT DISTINCT
                    doc.nom
                FROM
                         ach_procediment pro
                    JOIN ach_tipusdocument_procediment docobligatori ON pro.id = docobligatori.procediment_id
                    JOIN ach_tipusdocumental           doc ON doc.id = docobligatori.tipusdocumental_id
                WHERE
                        pro.seriedocumental_id = serie.id
                    AND docobligatori.obligatori = 0
            )
    )                                   AS documents_opcionals,
    (
                SELECT
                    regexp_replace(dbms_xmlgen.convert(XMLSERIALIZE(CONTENT XMLAGG(xmlelement(e, '- ' || nom).extract('/*')
                        ORDER BY
                            nom
                    ) INDENT),
                                                       1),
                                   '<.+?>') AS list
                FROM
                    (
                        SELECT DISTINCT
                            doc.nom
                        FROM ach_tipusdocument_serie docobligatori
                            JOIN ach_tipusdocumental           doc ON doc.id = docobligatori.tipusdocumental_id
                        WHERE
                                serie.id = docobligatori.seriedocumental_id
                            AND docobligatori.obligatori = 0
                    )
    ) AS docs_opcionals_agrupacions,

    dic.estat                           estat,
    to_char(dic.fi, 'DD-MM-YYYY')       data_obsolet,
    to_char(norpub.vigor, 'DD-MM-YYYY') data_publicacio
FROM
         ach_dictamen dic
    JOIN ach_seriedocumental serie ON serie.id = dic.seriedocumental_id
    LEFT OUTER JOIN ach_funcio          fun ON serie.funcio_id = fun.id
    LEFT OUTER JOIN ach_tipusdictamen   tdic ON tdic.id = dic.tipusdictamen_id
    LEFT OUTER JOIN ach_tipusserie      tipusserie ON serie.tipusserie_id = tipusserie.id
    LEFT OUTER JOIN ach_tipusacces      acces ON acces.id = dic.tipusacces_id
    LEFT OUTER JOIN ach_lopd            lopd ON dic.lopd_id = lopd.id
    LEFT OUTER JOIN ach_ens             ens ON dic.ens_id = ens.id
    LEFT OUTER JOIN ach_normativa       norpub ON dic.normativa_aprovacio_id = norpub.id
WHERE
    ( ( :idDictamen IS NOT NULL
        AND lower(dic.id) = lower(:idDictamen) )
      OR :idDictamen IS NULL )
ORDER BY
    dic.codi,
    data_actualitzacio DESC NULLS LAST