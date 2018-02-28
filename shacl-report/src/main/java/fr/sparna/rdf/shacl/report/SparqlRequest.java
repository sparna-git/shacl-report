package fr.sparna.rdf.shacl.report;

public class SparqlRequest {

	public static String results(){
		
	String request="PREFIX col:   <http://www.nakala.fr/collection/11280/> "
				+ "PREFIX owl:   <http://www.w3.org/2002/07/owl#> "
				+ "PREFIX xsd:   <http://www.w3.org/2001/XMLSchema#> "
				+ "PREFIX skos:  <http://www.w3.org/2004/02/skos/core#> "
				+ "PREFIX rdfs:  <http://www.w3.org/2000/01/rdf-schema#>  "
				+ "PREFIX ns3:   <http://www.nakala.fr/scheme/11280/>  "
				+ "PREFIX ns5:   <http://www.nakala.fr/account/11280/>  "
				+ "PREFIX ns13:  <https://www.nakala.fr/nakala/data/11280/>  "
				+ "PREFIX dct:   <http://purl.org/dc/terms/>  "
				+ "PREFIX ns12:  <http://www.nakala.fr/user/>  "
				+"PREFIX dat:   <http://www.nakala.fr/data/11280/>  "
				+"PREFIX ns17:  <http://purl.org/poi/crdo.vjf.cnrs.fr/>  "
				+"PREFIX ns16:  <https://www.nakala.fr/data/11280/> "
				+"PREFIX ns15:  <http://www.iiac.cnrs.fr/CentreEdgarMorin/spip.php?> "
				+"PREFIX ns14:  <http://medihal.archives-ouvertes.fr/>  "
				+"PREFIX ns19:  <http://lexvo.org/id/iso639-3/>  "
				+"PREFIX foaf:  <http://xmlns.com/foaf/0.1/>  "
				+"PREFIX ns18:  <https:www.nakala.fr/data/11280/>  "
				+"PREFIX res:   <http://www.nakala.fr/resource/11280/>  "
				+"PREFIX ore:   <http://www.openarchives.org/ore/terms/>  "
				+"PREFIX ns20:  <http://www.lexvo.org/id/iso639-3/>  "
				+"PREFIX ns24:  <http://demomed.org/>  "
				+"PREFIX ns23:  <http://iremam.cnrs.fr/spip.php?>  "
				+"PREFIX rdf:   <http://www.w3.org/1999/02/22-rdf-syntax-ns#>  "
				+"PREFIX ns22:  <http://dumas.ccsd.cnrs.fr/>  "
				+"PREFIX vcard2006: <http://www.w3.org/2006/vcard/ns#>  "
				+"PREFIX ns21:  <https://docramses.hypotheses.org/>  "
				+"PREFIX ns28:  <https://www.nakala.fr/nakala/data/>  "
				+"PREFIX ns27:  <http://purl.org/dc/dcmitype/> "
				+"PREFIX ns26:  <http://inspire.ec.europa.eu/theme/>  "
				+"PREFIX ns25:  <http://catalogue.bnf.fr/ark:/12148/>  "
				+"PREFIX ns29:  <http://nakala.fr/schema#>  "
				
				
				+"  SELECT ?message ?nodeUri ?sourceShape ?resultPath ?resultSeverity ?sourceConstraintComponent ?value "+
				" WHERE {"+
				" ?s a <http://www.w3.org/ns/shacl#ValidationResult>; "
				+ "	   <http://www.w3.org/ns/shacl#focusNode>  ?nodeUri; "
				+ "    <http://www.w3.org/ns/shacl#resultMessage> ?message; "
				+ "	   <http://www.w3.org/ns/shacl#resultPath> ?resultPath; "
				+ "    <http://www.w3.org/ns/shacl#resultSeverity> ?resultSeverity; "
				+ "    <http://www.w3.org/ns/shacl#sourceConstraintComponent> ?sourceConstraintComponent; "
				+ "    <http://www.w3.org/ns/shacl#value> ?value;"
				+ "    <http://www.w3.org/ns/shacl#sourceShape> ?sourceShape;"+
				" }";
		
		return request;
	}
	
}