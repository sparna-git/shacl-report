package fr.sparna.rdf.shacl.cli.validate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.util.List;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFLanguages;
import org.apache.jena.vocabulary.RDF;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.sparna.rdf.shacl.cli.CliCommandIfc;
import fr.sparna.rdf.shacl.cli.report.ValidationReportHtmlWriter;
import fr.sparna.rdf.shacl.cli.report.ValidationReportRdfWriter;
import fr.sparna.rdf.shacl.result.ValidationReportFormat;
import fr.sparna.rdf.shacl.result.ValidationReportWriterRegistry;
import fr.sparna.rdf.shacl.validator.ShaclValidator;
import fr.sparna.rdf.shacl.validator.Slf4jProgressMonitor;

public class Validate implements CliCommandIfc {

	private Logger log = LoggerFactory.getLogger(this.getClass().getName());
	
	@Override
	public void execute(Object args) throws Exception {
		ArgumentsValidate a = (ArgumentsValidate)args;
		
		// read input file or URL
		Model dataModel = readInputModel(a.getInput());
		
		// read shapes file
		OntModel shapesModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
		System.out.println(a.getShapes().toPath());
		shapesModel.read(
				new FileInputStream(a.getShapes()),
				a.getShapes().toPath().toAbsolutePath().getParent().toUri().toString(),
				RDFLanguages.filenameToLang(a.getShapes().getName(), Lang.RDFXML).getName()
		);
		
		// read extra model
		Model extraModel = null;
		if(a.getExtra() != null) {
			extraModel = ModelFactory.createDefaultModel();
			RDFDataMgr.read(extraModel, new FileInputStream(a.getExtra()), RDF.getURI(), RDFLanguages.filenameToLang(a.getShapes().getName(), Lang.RDFXML));
		}
		
		// run the validator
		ShaclValidator validator = new ShaclValidator(shapesModel, extraModel);
		validator.setProgressMonitor(new Slf4jProgressMonitor("SHACL validator", log));
		Model validationResults = validator.validate(dataModel);
		
		ValidationReportWriterRegistry.getInstance().register(new ValidationReportHtmlWriter());
		ValidationReportWriterRegistry.getInstance().register(new ValidationReportRdfWriter(Lang.TTL));
		ValidationReportWriterRegistry.getInstance().register(new ValidationReportRdfWriter(Lang.RDFXML));
		ValidationReportWriterRegistry.getInstance().register(new ValidationReportRdfWriter(Lang.JSONLD));
		ValidationReportWriterRegistry.getInstance().register(new ValidationReportRdfWriter(Lang.NT));
		
		for(File outputFile : a.getOutput()) {
			ValidationReportWriterRegistry.getInstance().getWriter(ValidationReportFormat.forFileName(outputFile.getName()))
			.orElse(new ValidationReportRdfWriter(Lang.TTL))
			.write(validationResults, new FileOutputStream(outputFile));
		}		
	}

	
	protected Model readInputModel(List<File> inputs) throws MalformedURLException {
		Model inputModel = null;
		
		for (File inputFile : inputs) {
			log.debug("Input parameter found as a local file : "+inputFile.getAbsolutePath());
			if(RDFLanguages.filenameToLang(inputFile.getName()) != null) {
				log.debug("Determined RDF syntax : "+RDFLanguages.filenameToLang(inputFile.getName()).getName());
				try {
					inputModel = ModelFactory.createDefaultModel();
					RDFDataMgr.read(inputModel, new FileInputStream(inputFile), RDF.getURI(), RDFLanguages.filenameToLang(inputFile.getName()));
				} catch (FileNotFoundException ignore) {
					ignore.printStackTrace();
				}
			} else {
				log.error("Unknown RDF format for file "+inputFile.getAbsolutePath());
			}
		} 	
		
		return inputModel;
	}
}
