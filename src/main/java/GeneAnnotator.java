import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

//Import files from lingpipe
import com.aliasi.chunk.Chunker;
import com.aliasi.chunk.Chunking;
import com.aliasi.chunk.Chunk;

import com.aliasi.util.AbstractExternalizable;

/**
 * Gene TAG Annotator. It uses the Ling Pipe toolkit to perform Gene TAG
 * Annotation. The model that we are using for this implementation is called
 * pos-en-bio-medpost.HiddenMarkovModel. It is trained on GenTAG dataset and
 * uses a Hidden Markov Model for named entiry recognition. The model can be
 * downloaded from here
 * http://alias-i.com/lingpipe/demos/models/ne-en-bio-genetag.HmmChunker
 * 
 */
public class GeneAnnotator extends JCasAnnotator_ImplBase {

	/*
	 * Parameter value for the LingPipe Model path location
	 */
	public static String LingPipeModelPathParam = "LingPipeModelPath";

	/*
	 * LingPipe Model file path and Initialized LingPipe chunker
	 */
	public static File LingPipeModel;
	public static Chunker LingPipeGeneChunker;

	/**
	 * Initializes this CAS Annotator with the parameters specified in the
	 * descriptor.It also loads the ling pipe model file and Initialize the Ling
	 * Pipe Gene Chunker module.
	 * 
	 * @param UimaContext
	 * 
	 */

	public void initialize(UimaContext aContext)
			throws ResourceInitializationException {
		super.initialize(aContext);
		// Get configuration parameter values
		LingPipeModel = new File(
				(String) aContext
						.getConfigParameterValue(LingPipeModelPathParam));
		LingPipeGeneChunker = null;
		try {
			LingPipeGeneChunker = (Chunker) AbstractExternalizable
					.readObject(LingPipeModel);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Process JCas contents by using the Ling Pipe Model to generate
	 * annotations.
	 * 
	 * @param JCas
	 *            JCas object to be processed by the annotator
	 */
	public void process(JCas aJCas) {
		// get document text
		String docText = aJCas.getDocumentText();
		int spaceIndex = docText.indexOf(' ');
		String docID = docText.substring(0, spaceIndex);
		String text = docText.substring(spaceIndex + 1);

		Chunking chunking = LingPipeGeneChunker.chunk(text);
		int offset = docID.length();
		for (Chunk chunk : chunking.chunkSet()) {
			// found one - create annotation
			GeneTAG annotation = new GeneTAG(aJCas);
			annotation.setBegin(offset + chunk.start() + 1);
			annotation.setEnd(offset + chunk.end());
			annotation.setSentenceID(docID);
			// System.out.println(docID+" "+chunk.start()+" "+chunk.end()+" "+text.substring(chunk.start(),
			// chunk.end()));
			annotation.addToIndexes();
		}
	}

}
