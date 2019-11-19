package fr.sparna.rdf.shacl.app.summary;


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

public class ReportSummaryParser {

	protected Repository repo;
	
	

	public ReportSummaryParser(Repository repo) {
		super();
		this.repo = repo;
	}


	public List<ValidationSummaryResult> getResults() {
		List<ValidationSummaryResult> results = new ArrayList<>();
		
		try(RepositoryConnection c = repo.getConnection()) {
			String sparqlRequest = IOUtils.toString(this.getClass().getResource("shacl-report-summary.rq"), "UTF-8");
			TupleQuery query = c.prepareTupleQuery(sparqlRequest);
			query.evaluate(new AbstractTupleQueryResultHandler() {
				@Override
				public void handleSolution(BindingSet bindingSet) throws TupleQueryResultHandlerException {
					ValidationSummaryResult result = new ValidationSummaryResult();
					
					// pretty source shape
					String sourceShapeString = bindingSet.getValue("sourceShape").stringValue();
					result.setSourceShape(sourceShapeString);
					repo.getConnection().getNamespaces().asList().stream().filter(n -> sourceShapeString.startsWith(n.getName())).findFirst().ifPresent(n -> {
						result.setSourceShape(sourceShapeString.replace(n.getName(), n.getPrefix()+":"));
					});	
					
					result.setSourceConstraintComponent(bindingSet.getValue("sourceConstraintComponentPretty").stringValue());
					
					result.setResultSeverity(bindingSet.getValue("resultSeverityPretty").stringValue());
					
					// pretty result path
					if(bindingSet.getBindingNames().contains("resultPath")) {
						String resultPathString = bindingSet.getValue("resultPath").stringValue();
						result.setResultPath(resultPathString);
						repo.getConnection().getNamespaces().asList().stream().filter(n -> resultPathString.startsWith(n.getName())).findFirst().ifPresent(n -> {
							result.setResultPath(resultPathString.replace(n.getName(), n.getPrefix()+":"));
						});	
					}
					
					result.setResultMessage(bindingSet.getValue("message").stringValue());
					
					// count
					result.setCount(Integer.parseInt(bindingSet.getValue("count").stringValue()));
					
					// pretty sample value URI
					if(bindingSet.getBindingNames().contains("sampleValue")) {
						String sampleValueString = bindingSet.getValue("sampleValue").stringValue();
						result.setSampleValue(sampleValueString);
						repo.getConnection().getNamespaces().asList().stream().filter(n -> sampleValueString.startsWith(n.getName())).findFirst().ifPresent(n -> {
							result.setSampleValue(sampleValueString.replace(n.getName(), n.getPrefix()+":"));
						});
					}
					
					// pretty sample focus node
					if(bindingSet.getBindingNames().contains("sampleFocusNode")) {
						String sampleFocsNodeString = bindingSet.getValue("sampleFocusNode").stringValue();
						result.setSampleFocusNode(sampleFocsNodeString);
						repo.getConnection().getNamespaces().asList().stream().filter(n -> sampleFocsNodeString.startsWith(n.getName())).findFirst().ifPresent(n -> {
							result.setSampleFocusNode(sampleFocsNodeString.replace(n.getName(), n.getPrefix()+":"));
						});
					}

					results.add(result);
				}
			});
		} catch (IOException ignore) {
			ignore.printStackTrace();
		}
		return results;
	}
}
