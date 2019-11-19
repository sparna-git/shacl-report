package fr.sparna.rdf.shacl.app.owl2shacl;

import java.io.FileOutputStream;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFLanguages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.topbraid.shacl.rules.RuleUtil;

import fr.sparna.rdf.shacl.app.CliCommandIfc;
import fr.sparna.rdf.shacl.app.InputModelReader;
import fr.sparna.rdf.shacl.validator.Slf4jProgressMonitor;

public class Owl2Shacl implements CliCommandIfc {

	private Logger log = LoggerFactory.getLogger(this.getClass().getName());
	
	@Override
	public void execute(Object args) throws Exception {
		ArgumentsOwl2Shacl a = (ArgumentsOwl2Shacl)args;
		
		// read input file or URL
		Model dataModel = InputModelReader.readInputModel(a.getInput());
		
		// read shapes file
		OntModel shapesModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);		
		shapesModel.read(
				this.getClass().getResourceAsStream(a.getStyle().getResourcePath()),
				null,
				RDFLanguages.filenameToLang(a.getStyle().getResourcePath(), Lang.RDFXML).getName()
		);

		// do the actual rule execution
		Model results = RuleUtil.executeRules(
				dataModel,
				shapesModel,
				null,
				new Slf4jProgressMonitor("OWL to SHACL", log)
		);
		
		// set some default prefixes
		results.setNsPrefix("sh", "http://www.w3.org/ns/shacl#");
		results.setNsPrefix("owl", "http://www.w3.org/2002/07/owl#");
		results.setNsPrefix("xsd", "http://www.w3.org/2001/XMLSchema#");
		results.setNsPrefix("skos", "http://www.w3.org/2004/02/skos/core#");
		
		results.write(
				new FileOutputStream(a.getOutput()),
				RDFLanguages.filenameToLang(a.getOutput().getName(), Lang.RDFXML).getName()
		);		
	}

}
