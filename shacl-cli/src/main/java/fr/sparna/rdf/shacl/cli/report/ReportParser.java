package fr.sparna.rdf.shacl.cli.report;

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
		} catch (IOException ignore) {
			ignore.printStackTrace();
		}
		return results;
	}
}
