package fr.sparna.rdf.shacl.printer.report;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.XSD;
import org.topbraid.jenax.util.JenaUtil;
import org.topbraid.shacl.model.SHFactory;
import org.topbraid.shacl.model.SHResult;
import org.topbraid.shacl.vocabulary.SH;

public class ValidationReport {

	protected Model resultsModel;
	
	private List<SHResult> results;

	public ValidationReport(Model resultsModel) {
		super();
		this.resultsModel = resultsModel;
		// ensure namespaces
		this.resultsModel.setNsPrefix("sh", SH.BASE_URI);
		this.resultsModel.setNsPrefix("xsd", XSD.NS);
	}
	

	public synchronized List<SHResult> getResults() {
		if(results == null) {
			// Collect all results			
			Set<Resource> results = JenaUtil.getAllInstances(resultsModel.getResource(SH.ValidationResult.getURI()));
			
			// Turn the results Resource objects into SHResult instances
			this.results = new LinkedList<SHResult>();
			for(Resource candidate : results) {
				SHResult result = SHFactory.asResult(candidate);
				this.results.add(result);
			}
		}
		
		return results;
	}
	
	public long getNumberOfViolations() {
		return getResults().stream().filter(vr -> vr.getResultSeverity().equals(SH.Violation)).count();
	}
	
	public long getNumberOfWarnings() {
		return getResults().stream().filter(vr -> vr.getResultSeverity().equals(SH.Warning)).count();
	}
	
	public long getNumberOfInfos() {
		return getResults().stream().filter(vr -> vr.getResultSeverity().equals(SH.Info)).count();
	}

	public Model getResultsModel() {
		return resultsModel;
	}
	
	
}
