package com.smt.poker.handparser;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.smt.poker.domain.hand.Hand;

/**
 * Parses the hands store in a file 
 * @author Mingtao Sun
 */
public interface HandParser {
	
	/**
	 * Parses the hands store in the specified file
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public List<Hand> parseHands(File file) throws IOException;
	
}
