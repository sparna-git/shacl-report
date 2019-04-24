package fr.sparna.rdf.shacl.report.generate;

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

import fr.sparna.rdf.shacl.report.ReportCliCommandIfc;


public class GenerateReport implements ReportCliCommandIfc {

	@Override
	public void execute(Object o) throws Exception {
		ArgumentsGenerateReport args = (ArgumentsGenerateReport)o;		
		
		Repository r = new SailRepository(new ForwardChainingRDFSInferencer(new MemoryStore()));
		r.initialize();

		try(RepositoryConnection c = r.getConnection()) {
			c.add(
					new FileInputStream((args).getInput()), 
					RDF.NAMESPACE,
					Rio.getParserFormatForFileName((args).getInput()).orElse(RDFFormat.RDFXML)
			);
		}
		
		ReportParser parser= new ReportParser(r);
		JtwigModel model = JtwigModel.newModel();
		model.with("data", parser.getResults());
		JtwigTemplate template = JtwigTemplate.classpathTemplate("views/report.twig");
		
		File outputFile = new File(args.getOutput()+"/report.html");
		// create output dir if not existing
		File outputDir = outputFile.getParentFile();
		if(!outputDir.exists()) {
			outputDir.mkdirs();
		}
		
		template.render(model, new FileOutputStream(outputFile));
	}

}