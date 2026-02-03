SELECT    td.CODI CODI
     , td.NOM NOM
     , td.NOMCAS NOM_ES
     , nti.CODI CODI_NTI
     , nti.NOM NOM_NTI
     , nti.NOMCAS NOM_NTI_ES
     , td.DEFINICIO DEFINICIO
     , td.DEFINICIOCAS DEFINICIO_ES
  FROM ach_tipusdocumental td
  JOIN archium.ach_tipusnti nti
    ON nti.id = td.tipusnti_id
 WHERE LOWER(td.ESTAT) = :estatTd
   AND td.quadretipusdocumental_id = :codiQuadre
 ORDER BY 1