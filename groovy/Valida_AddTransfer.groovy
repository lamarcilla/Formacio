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
 * VALIDA ADD TRASNFER 
 *****************************************************************************
 */

log.info ("                                 ");
log.info ("*********************************");
log.info ("VALIDA ADD transfer ");


def i,j,k
def assertionList = []
def value, idNotNull

// In case we have transfers we should verify: Pending to define

//Exist transfers
valida=jsonSlurper.transfers
idNotNull = (valida!=null) ? "(TRANSFERS is not null" : assertionList.add(" TRANSFERS is null")     


i=0
//for each pax, 
while (jsonSlurper.transfers[i] != null ){


	//transferInfo
	valida=jsonSlurper.transfers[i].transferInfo
	idNotNull = (valida!=null) ? "(transfers["+i+"].transferInfo is not null" : assertionList.add(" transfers["+i+"].transferInfo is null")  

	//transferInfo.vehicleType
	valida=jsonSlurper.transfers[i].transferInfo.vehicleType
	log.info(" transfers["+i+"].vehicleType: " + valida)
	idNotNull = (valida!=null) ? "(transfers["+i+"].transferInfo.vehicleType is not null" : assertionList.add(" transfers["+i+"].transferInfo.vehicleType is null")  	
	
	/*productSupplier
	valida=jsonSlurper.transfers[i].productSupplier
	log.info(" transfers["+i+"].productSupplier: " + valida)
	idNotNull = (valida!=null) ? "(transfers["+i+"].productSupplier is not null" : assertionList.add(" transfers["+i+"].productSupplier is null")  	*/
	
	//price
	valida=jsonSlurper.transfers[i].price
	log.info(" transfers["+i+"].price: " + valida)
	idNotNull = (valida!=null) ? " transfers["+i+"].price is not null" : assertionList.add(" transfers["+i+"].price is null")  

	//code
	valida=jsonSlurper.transfers[i].code
	log.info(" transfers["+i+"].code: " + valida)
	idNotNull = (valida!=null) ? " transfers["+i+"].code is not null" : assertionList.add(" transfers["+i+"].code  is null")  

	//productMarkup
	valida=jsonSlurper.transfers[i].productMarkup
	log.info(" transfers["+i+"].productMarkup: " + valida)
	idNotNull = (valida!=null) ? " transfers["+i+"].productMarkup is not null" : assertionList.add(" transfers["+i+"].productMarkup  is null")  


	//sTATUS should be always active
	valida=jsonSlurper.transfers[i].status
	log.info(" transfers["+i+"].status: " + valida)
	idNotNull = (valida=="ACTIVE") ? " transfers["+i+"].status is Active" : assertionList.add(" transfers["+i+"].status is not Active")  
	
	//pickUp date
	valida=jsonSlurper.transfers[i].pickUp.date
	log.info(" transfers["+i+"].pickUp.date: " + valida)
	idNotNull = (valida!=null) ? " transfers["+i+"].pickUp.date is not null" : assertionList.add(" transfers["+i+"]..pickUp.date is null")

	//pickUp time
	valida=jsonSlurper.transfers[i].pickUp.time
	log.info(" transfers["+i+"].pickUp.time: " + valida)
	idNotNull = (valida!=null) ? " transfers["+i+"].pickUp.time is not null" : assertionList.add(" transfers["+i+"]..pickUp.time is null")	


   i++
}





//add all Err assertions in the log info
if (!assertionList.empty)
	log.error("***** lista final de errores *****");
for (m in assertionList) {
    log.error( m);
}
assert assertionList.size() == 0
