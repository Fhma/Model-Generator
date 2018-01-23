package uk.ac.york.epsilon.emg.model.generator.engine;

import org.apache.commons.math3.random.RandomDataGenerator;
import org.eclipse.epsilon.emg.EmgModule;
import org.eclipse.epsilon.erl.IErlModule;

public class EmgEngine extends EpsilonStandaloneEngine {

	private long seed = 0;

    @Override
    public IErlModule createModule() {
        EmgModule module = new EmgModule();
        module.setSeed(getSeed());
        module.setUseSeed(true);
		return module;
    }

    /**
     * @param seed the seed to set
     */
    public void setSeed(long seed) {
    	this.seed = seed;
        //((EmgModule) getModule()).setSeed(seed);
        //((EmgModule) getModule()).setUseSeed(true);
    }

    /**
     * @param seed
     */
    public long getSeed() {
    	if (seed != 0) {
    		return seed;
    	}
    	RandomDataGenerator rnd = new RandomDataGenerator();
    	long preseed = rnd.nextLong(0, Long.MAX_VALUE);
    	RandomDataGenerator r = new RandomDataGenerator();
    	r.reSeed(preseed);
    	seed = r.nextLong(0, Long.MAX_VALUE);
//        ((EmgModule) getModule()).setSeed(seed);
//        ((EmgModule) getModule()).setUseSeed(true);
        return seed;
    }

    
    
    
}
