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
 * VALIDA PROMOCODES
******************************************************************************
 */

// In case we have promocodes we should verify: Pending to define
 

def assertionList = []

log.info ("                                 ");
log.info ("-----------------------------------------");
log.info ("Valida PROMOCODES");
log.info ("-----------------------------------------");


//controllING Err
def error, errorMessage
error=jsonSlurper.errorCode
errorMessage=jsonSlurper.errorMessage
idNotNull = (!error) ? "(valida is not null" : assertionList.add(" Err:" + error + " " + errorMessage )  

if (!error ){   

	//price
	valida=jsonSlurper.promocode.price
	log.info(" promocodes: " + valida)
	idNotNull = (valida!=null) ? "(valida is not null" : assertionList.add(" price is null")     


	//Currency
	valida=jsonSlurper.promocode.currency
	log.info(" currency: " + valida)
	idNotNull = (valida!=null) ? "(valida is not null" : assertionList.add(" currency is null")   
	
}

log.warn("description is always NULL  ")

//add all Err assertions in the log info
if (!assertionList.empty)
	log.error("***** lista final de errores *****");
for (m in assertionList) {
    log.error( m);
}
assert assertionList.size() == 0