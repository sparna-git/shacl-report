package fr.sparna.rdf.shacl.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.List;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFLanguages;
import org.apache.jena.vocabulary.RDF;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InputModelReader {

	private static Logger log = LoggerFactory.getLogger(InputModelReader.class.getName());
	
	public static Model readInputModel(List<File> inputs) throws MalformedURLException {
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
