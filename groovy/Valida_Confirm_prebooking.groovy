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
 * VALIDA FLIGHT DATA
 *****************************************************************************
 */
	
def assertionList = []
def valida, idNotNull

log.info ("                                 ");
log.info ("*********************************");
log.info ("*** VALIDA CONFIRM PREBOOKING ***");
log.info ("*********************************");


//status. Ha de ser 'CONFIRMED'
def status=jsonSlurper.status
log.info ("status: " + status )
asercion = (status=="CONFIRMED") ? "status is Booked (CONFIRMED)" : assertionList.add("status is not Booked(not CONFIRMED)")

//subStatus
def subStatus=jsonSlurper.subStatus
log.info ("subStatus: " + subStatus )
asercion = (subStatus!=null) ? "(subStatus is not null" : assertionList.add("subStatus is null")
//asercion = (status!="PAID") ? "subStatus is not PAID (not Confirm)" : assertionList.add("subStatus is PAID (Confirm)")

//flightHotelPackage
def flightHotelPackage=jsonSlurper.flightHotelPackage
asercion = (flightHotelPackage!=null) ? "(flightHotelPackage is not null" : assertionList.add("flightHotelPackage is null")
log.info ("flightHotelPackage: " + flightHotelPackage )

//flights
def flights=jsonSlurper.flightHotelPackage.flights
asercion = (flights!=null) ? "(flightHotelPackage.flights is not null" : assertionList.add("flightHotelPackage.flights is null")

//bookingNumber
f=0	
asercion = (jsonSlurper.flightHotelPackage.flights[f]!=null) ? "flightHotelPackage.flights[] is not null" : assertionList.add("flightHotelPackage.flights[] is null")
while (jsonSlurper.flightHotelPackage.flights[f] != null ){
	asercion = (jsonSlurper.flightHotelPackage.flights[f].bookingNumber!=null) ? "flightHotelPackage.flights["+f+"].bookingNumber is not null" : assertionList.add("flightHotelPackage.flights["+f+"].bookingNumber is null")
	log.info ("bookingNumber: " + jsonSlurper.flightHotelPackage.flights[f].bookingNumber )
	f++
}




//add all Err assertions in the log info
if (!assertionList.empty)
	log.error("***** lista final de errores *****");
for (m in assertionList) {
    log.error( m);
}
assert assertionList.size() == 0