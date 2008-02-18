//$
package org.hibernate.search.reader;

import java.io.IOException;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.MultiReader;
import org.hibernate.search.SearchException;

/**
 * MultiReader ensuring equals returns true if the underlying readers are the same (and in the same order)
 * Especially useful when using {@link org.apache.lucene.search.CachingWrapperFilter}
 *
 * @author Emmanuel Bernard
 */
public class CacheableMultiReader extends MultiReader {
	private IndexReader[] subReaders;

	public CacheableMultiReader(IndexReader[] subReaders) throws IOException {
		super( subReaders );
		this.subReaders = subReaders;
	}

	/** only available since 2.3 */
	/*
	public CacheableMultiReader(IndexReader[] subReaders, boolean closeSubReaders) {
		super( subReaders, closeSubReaders );
		this.subReaders = subReaders;
	}
	 */

	@Override
	public boolean equals(Object obj) {
		if ( this == obj ) return true;
		if ( ! ( obj instanceof CacheableMultiReader ) ) return false;
		CacheableMultiReader that = ( CacheableMultiReader ) obj;
		int length = this.subReaders.length;
		if ( length != that.subReaders.length ) return false;
		for (int index = 0 ; index < length ; index++) {
			if ( ! this.subReaders[index].equals( that.subReaders[index] ) ) return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int result = 0;
		for (Object reader : this.subReaders) {
			result = 31*result + reader.hashCode();
		}
		return result;
	}
}
