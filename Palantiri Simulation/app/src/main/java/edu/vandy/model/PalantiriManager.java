package edu.vandy.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;

/**
 * Defines a mechanism that mediates concurrent access to a fixed
 * number of available Palantiri.  This class uses a "fair" Semaphore,
 * a HashMap, and synchronized statements to mediate concurrent access
 * to the Palantiri.  This class implements a variant of the "Pooling"
 * pattern (kircher-schwanninger.de/michael/publications/Pooling.pdf).
 */
public class PalantiriManager {
    /**
     * Debugging tag used by the Android logger.
     */
    protected final static String TAG = 
        PalantiriManager.class.getSimpleName();

    /**
     * A counting Semaphore that limits concurrent access to the fixed
     * number of available palantiri managed by the PalantiriManager.
     */
    private Semaphore mAvailablePalantiri;

    /**
     * A map that associates the @a Palantiri key to the @a boolean
     * values that keep track of whether the key is available.
     */
    protected ConcurrentHashMap<Palantir, Boolean> mPalantiriMap;

    /**
     * Constructor creates a PalantiriManager for the List of @a
     * palantiri passed as a parameter and initializes the fields.
     */
    public PalantiriManager(List<Palantir> palantiri) {
        // Create a new HashMap, iterate through the List of Palantiri
        // and initialize each key in the HashMap with "true" to
        // indicate it's available, and initialize the Semaphore to
        // use a "fair" implementation that mediates concurrent access
        // to the given number of Palantiri.
        // TODO -- you fill in here.
    	
    	mPalantiriMap = new ConcurrentHashMap<Palantir, Boolean>();
    	
    	for(Palantir paladin : palantiri) {
    		mPalantiriMap.put(paladin, true);
    	}
    	
    	//semaphore action required
    	mAvailablePalantiri = new Semaphore(palantiri.size(), true);
    }

    /**
     * Get a Palantir from the PalantiriManager, blocking until one is
     * available.
     */
    public Palantir acquire() {
        // Acquire the Semaphore uninterruptibly and then iterate
        // through the HashMap in a thread-safe manner to find the
        // first key in the HashMap whose value is "true" (which
        // indicates it's available for use).  Replace the value of
        // this key with "false" to indicate the Palantir isn't
        // available and then return that palantir to the client.
        // TODO -- you fill in here.
    	
    	boolean initialFound = false;
    	
    	mAvailablePalantiri.acquireUninterruptibly();
    	
    	for (Map.Entry<Palantir, Boolean> entry : mPalantiriMap.entrySet()) {
    		
    	    Palantir key = entry.getKey();
    	    Boolean value = entry.getValue();
    	    
    	    if(value && initialFound == false) {
    	    	entry.setValue(false);
    	    	initialFound = true;
    	    	return key;
    	    }
    	   
    	}

        return null; 
    }

    /**
     * Returns the designated @code palantir to the PalantiriManager
     * so that it's available for other Threads to use.
     */
    public void release(final Palantir palantir) {
        // Put the "true" value back into HashMap for the palantir key
        // in a thread-safe manner and release the Semaphore if all
        // works properly.
        // TODO -- you fill in here.
    	
    	boolean initialReplace = false;
    	
    	for (Map.Entry<Palantir, Boolean> entry : mPalantiriMap.entrySet()) {
    		
    	    Palantir key = entry.getKey();
    	    Boolean value = entry.getValue();
    	    
    	    if(!value && initialReplace == false) {
    	    	entry.setValue(true);
    	    	initialReplace = true;
    	    }
    	    
    	}
    	
    	if(initialReplace)
    		mAvailablePalantiri.release();
    		
    	
    }

    /*
     * The following method is just intended for use by the regression
     * tests, not by applications.
     */

    /**
     * Returns the number of available permits on the semaphore.
     */
    public int availablePermits() {
        return mAvailablePalantiri.availablePermits();
    }
}
