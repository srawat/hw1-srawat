/* First created by JCasGen Mon Oct 15 19:20:01 EDT 2012 */

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.impl.FSGenerator;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;
import org.apache.uima.jcas.tcas.Annotation_Type;

/**
 * Annotation representing the GENE Named Entity Updated by JCasGen Tue Oct 16
 * 16:31:39 EDT 2012
 * 
 * @generated
 */
public class GeneTAG_Type extends Annotation_Type {
	/** @generated */
	@Override
	protected FSGenerator getFSGenerator() {return fsGenerator;}
	/** @generated */
	private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (GeneTAG_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = GeneTAG_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new GeneTAG(addr, GeneTAG_Type.this);
  			   GeneTAG_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new GeneTAG(addr, GeneTAG_Type.this);
  	  }
    };
	/** @generated */
	@SuppressWarnings("hiding")
	public final static int typeIndexID = GeneTAG.typeIndexID;
	/**
	 * @generated
	 * @modifiable
	 */
	@SuppressWarnings("hiding")
	public final static boolean featOkTst = JCasRegistry.getFeatOkTst("GeneTAG");

	/** @generated */
	final Feature casFeat_SentenceID;
	/** @generated */
	final int casFeatCode_SentenceID;

	/** @generated */
	public String getSentenceID(int addr) {
        if (featOkTst && casFeat_SentenceID == null)
      jcas.throwFeatMissing("SentenceID", "GeneTAG");
    return ll_cas.ll_getStringValue(addr, casFeatCode_SentenceID);
  }
	/** @generated */
	public void setSentenceID(int addr, String v) {
        if (featOkTst && casFeat_SentenceID == null)
      jcas.throwFeatMissing("SentenceID", "GeneTAG");
    ll_cas.ll_setStringValue(addr, casFeatCode_SentenceID, v);}
    
  



	/**
	 * initialize variables to correspond with Cas Type and Features
	 * 
	 * @generated
	 */
	public GeneTAG_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_SentenceID = jcas.getRequiredFeatureDE(casType, "SentenceID", "uima.cas.String", featOkTst);
    casFeatCode_SentenceID  = (null == casFeat_SentenceID) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_SentenceID).getCode();

  }
}
