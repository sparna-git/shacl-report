package fr.sparna.rdf.nakala.report;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.Rio;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;


public class Report implements ReportCliCommandIfc{

	@Override
	public void execute(Object o) throws Exception {
		// TODO Auto-generated method stub
		ArgumentsReportCli args=(ArgumentsReportCli)o;
		RepositorySparql r=new RepositorySparql();
		r.init();
		r.storeRepository(new FileInputStream((args).getInput()), Rio.getParserFormatForFileName((args).getInput()).orElse(RDFFormat.RDFXML));
		ReportParser parser= new ReportParser(r.getRepository());
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
