package fr.sparna.rdf.shacl.app.summary;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.sail.SailRepository;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.Rio;
import org.eclipse.rdf4j.sail.inferencer.fc.ForwardChainingRDFSInferencer;
import org.eclipse.rdf4j.sail.memory.MemoryStore;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import fr.sparna.rdf.shacl.app.CliCommandIfc;


public class GenerateSummary implements CliCommandIfc {

	@Override
	public void execute(Object o) throws Exception {
		ArgumentsGenerateSummary args = (ArgumentsGenerateSummary)o;		
		
		Repository r = new SailRepository(new ForwardChainingRDFSInferencer(new MemoryStore()));
		r.initialize();

		try(RepositoryConnection c = r.getConnection()) {
			c.add(
					new FileInputStream((args).getInput()), 
					RDF.NAMESPACE,
					Rio.getParserFormatForFileName((args).getInput()).orElse(RDFFormat.RDFXML)
			);
		}
		
		ReportSummaryParser parser= new ReportSummaryParser(r);
		JtwigModel model = JtwigModel.newModel();
		model.with("data", parser.getResults());
		JtwigTemplate template = JtwigTemplate.classpathTemplate("views/report-summary.twig");
		
		File outputFile = new File(args.getOutput());
		
		// create output dir if not existing
		File outputDir = outputFile.getParentFile();
		if(outputFile.getParentFile() != null && !outputDir.exists()) {
			outputDir.mkdirs();
		}
		
		template.render(model, new FileOutputStream(outputFile));
	}

}
