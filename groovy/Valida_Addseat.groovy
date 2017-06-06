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

valida=jsonSlurper.seats
idNotNull = (valida!=null) ? "(seats is not null" : assertionList.add(" seats is null")      


i=0
//for each pax, 
while (jsonSlurper.seats[i] != null ){

	//price
	valida=jsonSlurper.seats[i].price
	log.info(" seats["+i+"].price: " + valida)
	idNotNull = (valida!=null) ? " seats["+i+"].price is not null" : assertionList.add(" seats["+i+"].price is null") 
	
	//productMarkup
	valida=jsonSlurper.seats[i].productMarkup
	log.info(" seats["+i+"].productMarkup: " + valida)
	idNotNull = (valida!=null) ? " seats["+i+"].productMarkup is not null" : assertionList.add(" seats["+i+"].productMarkup is null")
	
	//seatNumber
	valida=jsonSlurper.seats[i].seatNumber
	log.info(" seats["+i+"].seatNumber: " + valida)
	idNotNull = (valida!=null) ? " seats["+i+"].seatNumber is not null" : assertionList.add(" seats["+i+"].seatNumber is null")  	
	
	//flightNumber
	valida=jsonSlurper.seats[i].flightNumber
	log.info(" seats["+i+"].flightNumber: " + valida)
	idNotNull = (valida!=null) ? " seats["+i+"].flightNumber is not null" : assertionList.add(" seats["+i+"].flightNumber is null")    
	
	//flightLegReference
	valida=jsonSlurper.seats[i].flightLegReference
	log.info(" seats["+i+"].flightLegReference: " + valida)
	idNotNull = (valida!=null) ? " seats["+i+"].flightLegReference is not null" : assertionList.add(" seats["+i+"].flightLegReference is null")    
	
	//status
	valida=jsonSlurper.seats[i].status
	log.info(" seats["+i+"].status: " + valida)
	idNotNull = (valida=='ACTIVE') ? " seats["+i+"].status is ACTIVE" : assertionList.add(" seats["+i+"].status is not ACTIVE")    
	

   i++
}






//add all Err assertions in the log info
if (!assertionList.empty)
	log.error("***** lista final de errores *****");
for (m in assertionList) {
    log.error( m);
}
assert assertionList.size() == 0
