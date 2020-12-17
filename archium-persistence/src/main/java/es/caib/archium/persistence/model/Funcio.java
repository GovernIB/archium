 package es.caib.archium.persistence.model;

import es.caib.archium.persistence.funcional.ObsolescenteAbstract;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


 /**
  * The persistent class for the ACH_FUNCIO database table.
  *
  */
 @Entity
 @Table(name="ACH_FUNCIO")
 @NamedQuery(name="Funcio.findAll", query="SELECT f FROM Funcio f")
 public class Funcio extends ObsolescenteAbstract {
     private static final long serialVersionUID = 1L;

     @Id
     @SequenceGenerator(name="ACH_FUNCIO_ID_GENERATOR", sequenceName="ACH_FUNCIO_SEQ", allocationSize = 1)
     @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACH_FUNCIO_ID_GENERATOR")
     private Long id;

     private String codi;

     private String estat;

     @Temporal(TemporalType.TIMESTAMP)
     private Date fi;

     @Temporal(TemporalType.TIMESTAMP)
     private Date inici;

     @Temporal(TemporalType.TIMESTAMP)
     private Date modificacio;

     private String nom;

     private String nomcas;

     private BigDecimal ordre;

     private String nodeId;

     @Column(name="SYNCHRONIZED")
     private Boolean isSynchronized;

     //bi-directional many-to-one association to Funcio
     @ManyToOne
     @JoinColumn(name="FUNCIO_PARE")
     private Funcio achFuncio;

     //bi-directional many-to-one association to Funcio
     @OneToMany(mappedBy="achFuncio",
             cascade = CascadeType.REMOVE)
     private List<Funcio> achFuncios;

     //bi-directional many-to-one association to Quadreclassificacio
     @ManyToOne
     @JoinColumn(name="QUADRECLASSIFICACIO_ID")
     private Quadreclassificacio achQuadreclassificacio;

     //bi-directional many-to-one association to Tipusserie
     @ManyToOne
     @JoinColumn(name="TIPUSSERIE_ID")
     private Tipusserie achTipusserie;

     //bi-directional many-to-one association to Seriedocumental
     @OneToMany(mappedBy="achFuncio",
             cascade = CascadeType.REMOVE)
     private List<Seriedocumental> achSeriedocumentals;

     public Funcio() {
     }

     public String getNodeId() {
         return nodeId;
     }

     public void setNodeId(String nodeId) {
         this.nodeId = nodeId;
     }

     public Boolean isSynchronized() {
         return isSynchronized == null ? false : isSynchronized;
     }

     public void setSynchronized(Boolean aSynchronized) {
         isSynchronized = aSynchronized;
     }

     public Long getId() {
         return this.id;
     }

     public void setId(Long id) {
         this.id = id;
     }

     public String getCodi() {
         return this.codi;
     }

     public void setCodi(String codi) {
         this.codi = codi;
     }

     public String getEstat() {
         return this.estat;
     }

     public void setEstat(String estat) {
         this.estat = estat;
     }

     @Override
     public Date getFi() {
         return this.fi;
     }

     public void setFi(Date fi) {
         this.fi = fi;
     }

     public Date getInici() {
         return this.inici;
     }

     public void setInici(Date inici) {
         this.inici = inici;
     }

     public Date getModificacio() {
         return this.modificacio;
     }

     public void setModificacio(Date modificacio) {
         this.modificacio = modificacio;
     }

     public String getNom() {
         return this.nom;
     }

     public void setNom(String nom) {
         this.nom = nom;
     }

     public String getNomcas() {
         return this.nomcas;
     }

     public void setNomcas(String nomcas) {
         this.nomcas = nomcas;
     }

     public BigDecimal getOrdre() {
         return this.ordre;
     }

     public void setOrdre(BigDecimal ordre) {
         this.ordre = ordre;
     }

     public Funcio getAchFuncio() {
         return this.achFuncio;
     }

     public void setAchFuncio(Funcio achFuncio) {
         this.achFuncio = achFuncio;
     }

     public List<Funcio> getAchFuncios() {
         return this.achFuncios;
     }

     public void setAchFuncios(List<Funcio> achFuncios) {
         this.achFuncios = achFuncios;
     }

     public Funcio addAchFuncio(Funcio achFuncio) {
         getAchFuncios().add(achFuncio);
         achFuncio.setAchFuncio(this);

         return achFuncio;
     }

     public Funcio removeAchFuncio(Funcio achFuncio) {
         getAchFuncios().remove(achFuncio);
         achFuncio.setAchFuncio(null);

         return achFuncio;
     }

     public Quadreclassificacio getAchQuadreclassificacio() {
         return this.achQuadreclassificacio;
     }

     public void setAchQuadreclassificacio(Quadreclassificacio achQuadreclassificacio) {
         this.achQuadreclassificacio = achQuadreclassificacio;
     }

     public Tipusserie getAchTipusserie() {
         return this.achTipusserie;
     }

     public void setAchTipusserie(Tipusserie achTipusserie) {
         this.achTipusserie = achTipusserie;
     }

     public List<Seriedocumental> getAchSeriedocumentals() {
         return this.achSeriedocumentals;
     }

     public void setAchSeriedocumentals(List<Seriedocumental> achSeriedocumentals) {
         this.achSeriedocumentals = achSeriedocumentals;
     }

     public Seriedocumental addAchSeriedocumental(Seriedocumental achSeriedocumental) {
         getAchSeriedocumentals().add(achSeriedocumental);
         achSeriedocumental.setAchFuncio(this);

         return achSeriedocumental;
     }

     public Seriedocumental removeAchSeriedocumental(Seriedocumental achSeriedocumental) {
         getAchSeriedocumentals().remove(achSeriedocumental);
         achSeriedocumental.setAchFuncio(null);

         return achSeriedocumental;
     }

     /**
      * La funcion esta obsoleta si tiene fecha fin anterior a la actual o si el estado es obsoleto
      *
      * @return
      */
     @Override
     public boolean isObsolete() {
         boolean superResult = super.isObsolete();
         if(superResult && !"Obsolet".equalsIgnoreCase(this.estat)){
             return true;
         }else{
             return false;
         }
     }
 }