
import java.io.Reader;
import java.util.HashSet;
import java.util.Set;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.Analyzer.TokenStreamComponents;
import org.apache.lucene.analysis.core.LetterTokenizer;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.en.PorterStemFilter;
import org.apache.lucene.analysis.shingle.ShingleFilter;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.util.Version;


/**
 * Base class analyzer from which Unigram and Ngram analyzers are overridden. It sets few flags to denote typr of analyzer
 */
public class BaseAnalyzer extends Analyzer
{
	/**
	 * Enum to check what type of analyzer the overridden analyzer should/can take 
	 *
	 */
	public enum TYPE
	{
		CustomStandardAnalyzer,
		CustomNgramAnalyzer
	}
	
	private TYPE _type;
	private int _gram;
	
	/**
	 * Constructor setting type and gram 
	 * @param type : enum as above 
	 * @param gram : based on Ngram that is created( 0 if CustomStandardAnalyzer) 
	 */
	public BaseAnalyzer(TYPE type,int gram) 
	{
		_type = type;
		_gram = gram;
	}
	
	/**
	 * @return : returns type of analyzer used
	 */
	public TYPE getType()
	{
		return _type;
	}
	
	/**
	 * @return : number of grams created 
	 */
	public int getGram()
	{
		return _gram;
	}
	
	/* Mandatory Overriden function with options of analyzer 
	 * @see Analyzer#createComponents(java.lang.String, java.io.Reader)
	 */
	@Override
	protected TokenStreamComponents createComponents(String fieldName, Reader reader) {
		/* Letter tokenizer */
		Tokenizer source = new LetterTokenizer(reader);
	    
		/* Lower case filter */
		TokenStream filter = new LowerCaseFilter(source);                  
	    
		/* porter stemmer */
		filter = new PorterStemFilter(filter);
		
	    return new TokenStreamComponents(source,filter);
	}
};