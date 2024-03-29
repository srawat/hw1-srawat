import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.collection.CasConsumer_ImplBase;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.ResourceProcessException;
import org.apache.uima.util.ProcessTrace;

/**
 * Gene CAS Consumer. <br>
 * It prints to an output file all annotations in the CAS. <br>
 * Parameters
 * <ol>
 * <li>"outputFile" : file to which the output files should be written.</li>
 * </ol>
 * <br>
 * These parameters are set in the initialize method to the values specified in
 * the descriptor file. <br>
 * 
 */

public class GeneConsumer extends CasConsumer_ImplBase {

	/*
	 * File Handler and File Writer for writing to the output file
	 */
	public static File outFile;
	public static FileWriter fileWriter;

	/**
	 * Initializes this CAS Consumer with the parameters specified in the
	 * descriptor.
	 * 
	 * @throws ResourceInitializationException
	 *             if there is error in initializing the resources
	 */
	public void initialize() throws ResourceInitializationException {

		// extract configuration parameter settings
		String oPath = (String) getUimaContext().getConfigParameterValue(
				"OutputFilePath");

		// Output file should be specified in the descriptor
		if (oPath == null) {
			throw new ResourceInitializationException(
					ResourceInitializationException.CONFIG_SETTING_ABSENT,
					new Object[] { "OutputFilePath" });
		}

		// If specified output directory does not exist, try to create it
		outFile = new File(oPath.trim());
		if (outFile.getParentFile() != null
				&& !outFile.getParentFile().exists()) {
			if (!outFile.getParentFile().mkdirs())
				throw new ResourceInitializationException(
						ResourceInitializationException.RESOURCE_DATA_NOT_VALID,
						new Object[] { oPath, "OutputFilePath" });
		}
		try {
			fileWriter = new FileWriter(outFile);
		} catch (IOException e) {
			throw new ResourceInitializationException(e);
		}
	}

	/**
	 * Count the number of Non White Space Characters in the string This is done
	 * to find location of the gene based on non white space characters.
	 * 
	 * @param str
	 * @return count of non white space characters
	 */
	public int countNonWS(String str) {
		int count = 0;
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) != ' ')
				count++;
		}
		return count;
	}

	/**
	 * Processes the CasContainer which was populated by the GeneAnnotator with
	 * GeneTAG annotations. <br>
	 * Here the CAS index is iterated over selected annotations and printed out
	 * into the output file
	 * 
	 * @param aCAS
	 *            CasContainer which has been populated by the GeneAnnotator
	 * 
	 * @throws ResourceProcessException
	 *             if there is an error in processing the Resource
	 * 
	 * @see org.apache.uima.collection.base_cpm.CasObjectProcessor#processCas(CAS)
	 */
	public void processCas(CAS aCAS) throws ResourceProcessException {
		JCas jcas;
		try {
			jcas = aCAS.getJCas();
		} catch (CASException e) {
			throw new ResourceProcessException(e);
		}

		// iterate and print annotations
		Iterator annotationIter = jcas.getAnnotationIndex(GeneTAG.type)
				.iterator();
		while (annotationIter.hasNext()) {
			GeneTAG annot = (GeneTAG) annotationIter.next();

			// get the text that is enclosed within the annotation in the CAS
			String DocText = annot.getCAS().getDocumentText();
			String aText = annot.getCoveredText();

			/*
			 * Find the location of the gene tag in the sentence based on the
			 * number of non White space characters after the end of Sentence
			 * ID. Dump the information into the output file in the format.
			 * SentenceID|<start> <end>|<GENETAG>
			 */
			try {
				int nonWSBegin = countNonWS(DocText.substring(annot
						.getSentenceID().length() + 1, annot.getBegin()));
				int nonWSEnd = countNonWS(DocText.substring(annot
						.getSentenceID().length() + 1, annot.getEnd()-1));
				fileWriter.write(annot.getSentenceID() + "|" + nonWSBegin + " "
						+ nonWSEnd + "|" + aText + "\n");
				fileWriter.flush();
			} catch (IOException e) {
				throw new ResourceProcessException(e);
			}
		}
	}

	/**
	 * Called when the entire collection is completed.
	 * 
	 * @param aTrace
	 *            ProcessTrace object that will log events in this method.
	 * @throws ResourceProcessException
	 *             if there is an error in processing the Resource
	 * @throws IOException
	 *             if there is an IO Error
	 * @see org.apache.uima.collection.CasConsumer#collectionProcessComplete(ProcessTrace)
	 */
	public void collectionProcessComplete(ProcessTrace aTrace)
			throws ResourceProcessException, IOException {
		if (fileWriter != null) {
			fileWriter.close();
		}
	}

}
