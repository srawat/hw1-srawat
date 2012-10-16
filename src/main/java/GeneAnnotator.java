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
 * Gene TAG Annotator. It used Ling Pipe toolkit to perform Gene TAG classification
 */
public class GeneAnnotator extends JCasAnnotator_ImplBase {

	public static String LingPipeModelPath = "";

	public void initialize(UimaContext aContext)
			throws ResourceInitializationException {
		super.initialize(aContext);
		// Get config. parameter values
		LingPipeModelPath = (String) aContext
				.getConfigParameterValue("LingPipeModelPath");
	}

	public void process(JCas aJCas) {
		// get document text
		String docText = aJCas.getDocumentText();
		int spaceIndex = docText.indexOf(' ');
		String docID = docText.substring(0, spaceIndex);
		String text = docText.substring(spaceIndex + 1);

		File modelFile = new File(LingPipeModelPath);
		Chunker chunker = null;
		try {
			chunker = (Chunker) AbstractExternalizable.readObject(modelFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Chunking chunking = chunker.chunk(text);
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
