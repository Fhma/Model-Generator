package uk.ac.york.epsilon.emg.model.generator.engine;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.epsilon.common.parse.problem.ParseProblem;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.Variable;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.eol.types.EolAnyType;
import org.eclipse.epsilon.erl.IErlModule;

public abstract class EpsilonStandaloneEngine {

	/** The EXL source URI. */
    private URI sourceURI;

    /** The models. */
    private List<IModel> models = new ArrayList<IModel>();

    /** The module. */
    private IErlModule module;

    /** The result. */
    private Object result;

    private Map<String, Object> globalVariables = new HashMap<>();

    /**
     * Adds the model to the engine
     *
     * @param model the model
     */
    public void addModel(IModel model) {
        getModels().add(model);

    }

    public void addGlobalVariable(String name, Object value) {
    	globalVariables.put(name, value);
    }


    /**
     * Creates the module used by the engine to execute the ExL source. Specific
     * ExL language engines should provide a specific module.
     *
     * @return the EXL executable module
     */
    public abstract IErlModule createModule();

    /**
     * Dispose models removes the loaded modules in the {@link #module}. This
     * method should be used if the extending class has overridden {@link #execute()}
     * to provide a more fined grained control over the module execution.
     */
    public void disposeModels() {

        getModule().getContext().getModelRepository().dispose();
    }

    /**
     * Executes the Epsilon source on the loaded models using the engine.
     * Parses the Epsilon source for errors, loads the models to the engine's
     * repository, invokes the {@link #preProcess()} method, executes the engine
     * and the invokes the {@link #postProcess()} method. The result of executing
     * the engine is saved in the {@see #result} field. Models are disposed (i.e.
     * un-loaded) after execution.
     *
     * @throws ParseException If syntax errors were detected in the source file.
     *         Error details will be printed in the System.err stream.
     *         TODO Provide a better way of getting the errors, log4j or other
     *         method in case the host application has not console
     * @throws SourceLoadException If there was an error loading the source file.
     * @throws ExecutionException If there was an error executing the source
     */
    public void execute() throws EpsilonException {

        setModule(createModule());
        try {
            getModule().parse(getSourceURI());
        } catch (Exception e1) {
            throw new EpsilonException("There was an error loading the source.", e1.getCause());
        }

        /*catch (URISyntaxException e) {
            throw new EpsilonStandaloneException("Error parsing source. " + e.getMessage());
        } catch (Exception e) {
            throw new EpsilonStandaloneException("Error parsing source. " + e.getMessage());
        }*/

        if (getModule().getParseProblems().size() > 0) {
            System.err.println("Parse errors occured...");
            for (ParseProblem problem : getModule().getParseProblems()) {
                System.err.println(problem.toString());
            }
            throw new EpsilonException("Parse errors occured. See stack trace for details.");
        }

        for (IModel model : getModels()) {
            getModule().getContext().getModelRepository().addModel(model);
        }

        preProcess();
        try {
            setResult(execute(getModule()));
        } catch (EolRuntimeException e) {
            throw new EpsilonException(e.getMessage(),e.getCause());
        }
        postProcess();

        getModule().getContext().getModelRepository().dispose();
    }

    /**
     * Gets the list of models available to the engine.
     *
     * @return the model list
     */
    public List<IModel> getModels() {
        return models;
    }

    /**
     * Gets the module.
     *
     * @return the module
     */
    public IErlModule getModule() {
        return module;
    }


    /**
     * Gets the models loaded to the engine. The loaded models can be different
     * to the list of models because some models for example if a model fails
     * to load.
     *
     * @return the module models
     */
    public List<IModel> getModuleModels() {
        return getModule().getContext().getModelRepository().getModels();
    }


    /**
     * Gets the result.
     *
     * @return the result
     */
    public Object getResult() {
        return result;
    };

    /**
     * Gets the ExL source.
     *
     * @return the ExL source's UIR
     */
    public URI getSource() {
        return getSourceURI();
    };

    /**
     * Gets the source uri.
     *
     * @return the sourceURI
     */
    public URI getSourceURI() {
        return sourceURI;
    }


    /**
     * This method is invoked after the engine has executed if there were no
     * exceptions. Implementations should override this method o provide any
     * required post-processing after the engine has executed. For example
     * modifying the result object. This method should be called after module
     * execution if the extending class has overridden {@link #execute()} to
     * provide a more fined grained control over the module execution.
     */
    public void postProcess() {}

    /**
     * This method is invoked after the ExL source is parsed and the
     * models are loaded to the engine's context. Implementations should
     * override this method to provide any required pre-processing before the
     * engine is executed. This method should be called after module execution
     * if the extending class has overridden {@link #execute()}
     * to provide a more fined grained control over the module execution.
     */
    public void preProcess() {
    	for (Entry<String, Object> e : globalVariables.entrySet()) {
    		getModule().getContext().getFrameStack().putGlobal(new Variable(e.getKey(),e.getValue(), EolAnyType.Instance, false));
    	}
    }




    /**
     * Gets the file, given the name. The file is searched by the class loader.
     *
     * @return the file
     */
    /*
    protected File getFile(String fileName) throws URISyntaxException {

        URI binUri = EpsilonStandaloneEngine.class.getResource(fileName).toURI();
        URI uri = null;

        if (binUri.toString().indexOf("bin") > -1) {
            uri = new URI(binUri.toString().replaceAll("bin", "src"));
        }
        else {
            uri = binUri;
        }

        return new File(uri);
    }
    */

    /**
     * Removes the model from the engine
     *
     * @param model the model
     * @return true, if successful
     */
    public boolean removeModel(IModel model) {
        return getModels().remove(model);
    }

    public void reset() {
    	getModels().clear();
    	globalVariables.clear();
    	setSourceURI(null);
    }

    /**
     * Sets the models available to the engine.
     *
     * @param models the new models
     */
    public void setModels(List<IModel> models) {
        this.models = models;
    }

    /**
     * Sets the module.
     *
     * @param module the module to set
     */
    public void setModule(IErlModule module) {
        this.module = module;
    }

    /**
     * Sets the result.
     *
     * @param result the result to set
     */
    public void setResult(Object result) {
        this.result = result;
    }

    /**
     * Sets the source uri.
     *
     * @param sourceURI the sourceURI to set
     */
    public void setSourceURI(URI sourceURI) {
        this.sourceURI = sourceURI;
    }

    /**
     * Execute. The actual module execution.
     * Depending on the specific executor this method may be implemented
     * differently.
     *
     * @param module the module
     * @return the object
     * @throws EolRuntimeException if there is an exception during parsing, loading
     * of the models or execution of the engine.
     */
    protected Object execute(IErlModule module) throws EolRuntimeException {
        return module.execute();
    }

}
