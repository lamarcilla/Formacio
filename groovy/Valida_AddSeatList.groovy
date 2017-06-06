//imports
import groovy.json.JsonSlurper
//grab the response
def ResponseMessage = messageExchange.response.responseContent
//define a JsonSlurper

def jsonSlurper = new JsonSlurper().parseText(ResponseMessage)


//verify the slurper isn't empty
assert !(jsonSlurper.isEmpty())

/*
******************************************************************************
 * VALIDA ADD SEAT 
 *****************************************************************************
 */

log.info ("                                 ");
log.info ("*********************************");
log.info ("VALIDA ADD seat ");


def i,j,k
def assertionList = []
def value, idNotNull



i=0
//for each pax, 
while (jsonSlurper[i] != null ){

	log.info(" *** Seat ["+i+"]");

	//price
	valida=jsonSlurper[i].price
	log.info(" seat ["+i+"].price: " + valida)
	idNotNull = (valida!=null) ? " seat ["+i+"].price is not null" : assertionList.add(" seat ["+i+"].price is null") 

	//productMarkup
	valida=jsonSlurper[i].productMarkup
	log.info(" seat ["+i+"].productMarkup: " + valida)
	idNotNull = (valida!=null) ? " seat ["+i+"].productMarkup is not null" : assertionList.add(" seat ["+i+"].productMarkup is null")
	
	//seatNumber
	valida=jsonSlurper[i].seatNumber
	log.info(" seat ["+i+"].seatNumber: " + valida)
	idNotNull = (valida!=null) ? " seat ["+i+"].seatNumber is not null" : assertionList.add(" seat ["+i+"].seatNumber is null")  	
	
	//flightNumber
	valida=jsonSlurper[i].flightNumber
	log.info(" seat ["+i+"].flightNumber: " + valida)
	idNotNull = (valida!=null) ? " seat ["+i+"].flightNumber is not null" : assertionList.add(" seat ["+i+"].flightNumber is null")    
	
	//flightLegReference
	valida=jsonSlurper[i].flightLegReference
	log.info(" seat ["+i+"].flightLegReference: " + valida)
	idNotNull = (valida!=null) ? " seat ["+i+"].flightLegReference is not null" : assertionList.add(" seat ["+i+"].flightLegReference is null")    
	
	//status
	valida=jsonSlurper[i].status
	log.info(" seat ["+i+"].status: " + valida)
	idNotNull = (valida=='PENDING') ? " seat ["+i+"].status is PENDING" : assertionList.add(" seat ["+i+"].status is not PENDING")  

   i++
}






//add all Err assertions in the log info
if (!assertionList.empty)
	log.error("***** lista final de errores *****");
for (m in assertionList) {
    log.error( m);
}
assert assertionList.size() == 0
