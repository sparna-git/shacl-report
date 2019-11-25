package fr.sparna.rdf.shacl.printer.report;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.riot.RDFLanguages;
import org.apache.jena.vocabulary.RDF;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

public class ValidationReportDatatableSummaryWriter implements ValidationReportWriter {

	@Override
	public void write(ValidationReport results, OutputStream out) {		
		List<PrintableSHResultSummaryEntry> entries = new ArrayList<>();
		QueryExecution execution = null;
		try {
			execution = QueryExecutionFactory.create(IOUtils.toString(this.getClass().getResource(this.getClass().getSimpleName()+".rq"), "UTF-8"), results.getResultsModel());
			ResultSet resultSet = execution.execSelect();
			resultSet.forEachRemaining(solution -> {
				entries.add(new PrintableSHResultSummaryEntry(SHResultSummaryEntry.fromQuerySolution(solution)));
			});
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			execution.close();
		}
		
		JtwigModel model = JtwigModel.newModel();
		model.with("data", entries);
		model.with("contentTemplate", "classpath:/views/"+this.getClass().getSimpleName()+".twig");

		JtwigTemplate template = JtwigTemplate.classpathTemplate("/views/DatatableView.twig");
		template.render(model, out);
	}
	
	@Override
	public ValidationReportOutputFormat getFormat() {
		return ValidationReportOutputFormat.HTML_SUMMARY;
	}
	
	public static void main(String... args) throws Exception {
		File resultFile = new File(args[0]);
		File output = new File(ValidationReportDatatableSummaryWriter.class.getSimpleName()+".html");
		ValidationReportDatatableSummaryWriter me = new ValidationReportDatatableSummaryWriter();
		Model m = ModelFactory.createDefaultModel();
		m.read(new FileInputStream(resultFile), RDF.uri, RDFLanguages.filenameToLang(resultFile.getName()).getName());
		me.write(new ValidationReport(m), new FileOutputStream(output));
	}

}
