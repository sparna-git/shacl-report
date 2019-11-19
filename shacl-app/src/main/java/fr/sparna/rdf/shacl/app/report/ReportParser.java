package fr.sparna.rdf.shacl.app.report;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.eclipse.rdf4j.query.AbstractTupleQueryResultHandler;
import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.query.TupleQuery;
import org.eclipse.rdf4j.query.TupleQueryResultHandlerException;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryConnection;

public class ReportParser {

	protected Repository repo;
	
	

	public ReportParser(Repository repo) {
		super();
		this.repo = repo;
	}


	public List<ValidationResult> getResults() {
		List<ValidationResult> results = new ArrayList<ValidationResult>();
		
		try(RepositoryConnection c = repo.getConnection()) {
			String sparqlRequest = IOUtils.toString(this.getClass().getResource("shacl-report.rq"), "UTF-8");
			TupleQuery query = c.prepareTupleQuery(sparqlRequest);
			query.evaluate(new AbstractTupleQueryResultHandler() {
				@Override
				public void handleSolution(BindingSet bindingSet) throws TupleQueryResultHandlerException {
					ValidationResult result = new ValidationResult();
					
					// pretty node URI
					String nodeUriString = bindingSet.getValue("nodeUri").stringValue();
					result.setFocusNode(nodeUriString);
					repo.getConnection().getNamespaces().asList().stream().filter(n -> nodeUriString.startsWith(n.getName())).findFirst().ifPresent(n -> {
						result.setFocusNode(nodeUriString.replace(n.getName(), n.getPrefix()+":"));
					});					
					
					result.setResultMessage(bindingSet.getValue("message").stringValue());
					
					// pretty result path
					if(bindingSet.getBindingNames().contains("resultPath")) {
						String resultPathString = bindingSet.getValue("resultPath").stringValue();
						result.setResultPath(resultPathString);
						repo.getConnection().getNamespaces().asList().stream().filter(n -> resultPathString.startsWith(n.getName())).findFirst().ifPresent(n -> {
							result.setResultPath(resultPathString.replace(n.getName(), n.getPrefix()+":"));
						});	
					}
					
					result.setResultSeverity(bindingSet.getValue("resultSeverityPretty").stringValue());
					result.setSourceConstraintComponent(bindingSet.getValue("sourceConstraintComponentPretty").stringValue());
					
					// pretty value String
					if(bindingSet.getBindingNames().contains("value")) {
						String valueString = bindingSet.getValue("value").stringValue();
						result.setValue(valueString);
						repo.getConnection().getNamespaces().asList().stream().filter(n -> valueString.startsWith(n.getName())).findFirst().ifPresent(n -> {
							result.setValue(valueString.replace(n.getName(), n.getPrefix()+":"));
						});	
					}
					
					// pretty source shape
					String sourceShapeString = bindingSet.getValue("sourceShape").stringValue();
					result.setSourceShape(sourceShapeString);
					repo.getConnection().getNamespaces().asList().stream().filter(n -> sourceShapeString.startsWith(n.getName())).findFirst().ifPresent(n -> {
						result.setSourceShape(sourceShapeString.replace(n.getName(), n.getPrefix()+":"));
					});	

					results.add(result);
				}
			});
		} catch (IOException ignore) {
			ignore.printStackTrace();
		}
		return results;
	}
}
