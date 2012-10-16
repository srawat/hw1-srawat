import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.collection.CollectionReader_ImplBase;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Progress;
import org.apache.uima.util.ProgressImpl;

/**
 * A simple file reader that reads an Input File containing individual sentences
 * and delivers each sentences as a separate document(CAS) to process.
 * <ul>
 * <li><code>InputFile</code> - path to the input file</li>
 * </ul>
 */
public class FileReader extends CollectionReader_ImplBase {

	/**
	 * Name of configuration parameter that must be set to the path of Input
	 * File
	 */

	public static final String PARAM_INPUTFILE = "InputFile";

	// Stores all the sentences in the input file
	private ArrayList<String> mSentences;

	// Current Sentence being processed
	private int mCurrentIndex;

	/**
	 * Initializes the FileReader.
	 * 
	 * @see org.apache.uima.collection.CollectionReader_ImplBase#initialize()
	 */
	public void initialize() throws ResourceInitializationException {
		// Get the input file
		File inputFile = new File(
				((String) getConfigParameterValue(PARAM_INPUTFILE)).trim());

		// Initialize the current index to zero
		mCurrentIndex = 0;

		// if input file does not exist or is not a directory, throw exception
		if (!inputFile.exists() || inputFile.isDirectory()) {
			System.out.println("Input File Not Found");
			throw new ResourceInitializationException();
		}

		// Read the sentences from file and store them in a list
		mSentences = new ArrayList<String>();
		addInstancesFromFile(inputFile);
	}

	/**
	 * Reads sentences from input file and stores them in mSentences array list
	 * 
	 * @param File
	 *            File handler for the Input file
	 */
	private void addInstancesFromFile(File input) {
		BufferedReader buff = null;
		try {
			buff = new BufferedReader(new InputStreamReader(
					new FileInputStream(input)));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String s = "";
		try {
			while ((s = buff.readLine()) != null) {
				mSentences.add(s);
			}
			buff.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Checks if any other sentence needs to be processed
	 * 
	 * @see org.apache.uima.collection.CollectionReader#hasNext()
	 */
	public boolean hasNext() {
		if (mCurrentIndex < mSentences.size()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Gets the next element of the collection. The element will be stored in
	 * the provided CAS object.
	 * 
	 * @param aCAS
	 *            the CAS to populate with the next element of the collection
	 * 
	 * @see org.apache.uima.collection.CollectionReader#getNext(org.apache.uima.cas.CAS)
	 */
	public void getNext(CAS aCAS) throws IOException, CollectionException {
		JCas jcas;
		try {
			jcas = aCAS.getJCas();
		} catch (CASException e) {
			throw new CollectionException(e);
		}

		// Each sentences is our document
		String text = (String) mSentences.get(mCurrentIndex++);

		// put document in CAS
		jcas.setDocumentText(text);
	}

	/**
	 * @see org.apache.uima.collection.base_cpm.BaseCollectionReader#close()
	 */
	public void close() throws IOException {
	}

	/**
	 * @see org.apache.uima.collection.base_cpm.BaseCollectionReader#getProgress()
	 */
	public Progress[] getProgress() {
		return new Progress[] { new ProgressImpl(mCurrentIndex,
				mSentences.size(), Progress.ENTITIES) };
	}

	/**
	 * Gets the total number of sentences in the current input file.
	 * 
	 * @return the number of documents in the collection
	 */
	public int getNumberOfDocuments() {
		return mSentences.size();
	}

}
