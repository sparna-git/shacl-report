package fr.sparna.rdf.shacl.report;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.rdf4j.query.AbstractTupleQueryResultHandler;
import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.query.TupleQueryResultHandlerException;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryConnection;

import fr.sparna.rdf.rdf4j.toolkit.query.Perform;

public class ReportParser {

	protected Repository repo;


	public ReportParser(Repository repo) {
		super();
		this.repo = repo;
	}


	public List<Result> getResults(){
		List<Result> results = new ArrayList<Result>();

		String sparqlRequest = SparqlRequest.results();

		try(RepositoryConnection c = repo.getConnection()) {

			Perform.on(c).select(sparqlRequest, new AbstractTupleQueryResultHandler() {
				@Override
				public void handleSolution(BindingSet bindingSet) throws TupleQueryResultHandlerException {					
			
						Result result=new Result();
						result.setFocusNode(bindingSet.getValue("nodeUri").stringValue());
						result.setResultMessage(bindingSet.getValue("message").stringValue());
						result.setResultPath(bindingSet.getValue("resultPath").stringValue());
						result.setResultSeverity(bindingSet.getValue("resultSeverity").stringValue());
						result.setSourceConstraintComponent(bindingSet.getValue("sourceConstraintComponent").stringValue());
						result.setValue(bindingSet.getValue("value").stringValue());
						result.setSourceShape(bindingSet.getValue("sourceShape").stringValue());
						results.add(result);

				
				}
			});
		}
		return results;
	}
}
