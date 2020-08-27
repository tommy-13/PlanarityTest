package io.safeLoad;


import java.io.IOException;

import embedding.Embedding;

/**
 * Class to safe and load data.
 * @author tommy
 *
 */
public class Export {
	
	
	/**
	 * Exports the specified embedding to a gml-file with the specified name.
	 * @param emb		embedding
	 * @param safePath	target file name
	 * @return			true, if exporting was successful
	 */
	public static boolean exportChoosePath(Embedding emb, String safePath) {	
		GMLWriter gmlWriter = new GMLWriter(safePath, emb);
		try {
			gmlWriter.write();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
