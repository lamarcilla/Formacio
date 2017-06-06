import groovy.xml.StreamingMarkupBuilder
//grab the response
def ResponseMessage = messageExchange.response.responseContent
//define a XmlSlurper
def responseXml = new XmlSlurper().parseText(ResponseMessage)
//verify the slurper isn't empty
if (responseXml==null) {
	log.error ("La respuesta de la consulta SQL está vacía. ResponseXML es null");
}

//--esto es por si queremos consultar de forma individual un campo en concreto
//import com.eviware.soapui.support.XmlHolder
//def holder = new XmlHolder( messageExchange.responseContentAsXml )
//def ID_BOOKING = holder.getNodeValue("//Results/ResultSet/Row/SETTLEMENT.ID_BOOKING")

def assertionList = [];
def arrayClaves = ['SETTLEMENT.STR_POSTTOWN','SETTLEMENT.STR_COUNTRY'];


def value;
responseXml.breadthFirst().each {
	value = it.toString();
	if (value.empty)
		log.warn(it.name() + ": " + value)
	else
		log.info(it.name() + ": " + value)
		
	if (arrayClaves.contains(it.name())) {
		asercion = (value.empty) ? it.name()+" distinto de null" : assertionList.add(it.name()+" no tiene valor.");
	}
}

log.info("*** claves a buscar ***" + arrayClaves);

//add all Err assertions in the log info
if (!assertionList.empty) {
	log.error("*********** claves sin valor que están en la lista ***********");
}
for (m in assertionList) {
    log.error( m);
}
assert assertionList.size() == 0