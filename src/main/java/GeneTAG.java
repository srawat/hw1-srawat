

/* First created by JCasGen Mon Oct 15 19:20:01 EDT 2012 */

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** Annotation representing the GENE Named Entity
 * Updated by JCasGen Tue Oct 16 16:31:39 EDT 2012
 * XML source: /home/srawat/workspace/hw1-srawat/src/main/resources/typeSystemDescriptor.xml
 * @generated */
public class GeneTAG extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(GeneTAG.class);
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int type = typeIndexID;
  /** @generated  */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected GeneTAG() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public GeneTAG(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public GeneTAG(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public GeneTAG(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** <!-- begin-user-doc -->
    * Write your own initialization here
    * <!-- end-user-doc -->
  @generated modifiable */
  private void readObject() {/*default - does nothing empty block */}
     
  //*--------------*
  //* Feature: SentenceID

  /** getter for SentenceID - gets Stores the ID of the sentence which contains the current TAG
   * @generated */
  public String getSentenceID() {
    if (GeneTAG_Type.featOkTst && ((GeneTAG_Type)jcasType).casFeat_SentenceID == null)
      jcasType.jcas.throwFeatMissing("SentenceID", "GeneTAG");
    return jcasType.ll_cas.ll_getStringValue(addr, ((GeneTAG_Type)jcasType).casFeatCode_SentenceID);}
    
  /** setter for SentenceID - sets Stores the ID of the sentence which contains the current TAG 
   * @generated */
  public void setSentenceID(String v) {
    if (GeneTAG_Type.featOkTst && ((GeneTAG_Type)jcasType).casFeat_SentenceID == null)
      jcasType.jcas.throwFeatMissing("SentenceID", "GeneTAG");
    jcasType.ll_cas.ll_setStringValue(addr, ((GeneTAG_Type)jcasType).casFeatCode_SentenceID, v);}    
  }

    