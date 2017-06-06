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
 * VALIDA MAIN PREBOOKING DATA
 *****************************************************************************
 */
def assertionList = []

log.info ("                                 ");
log.info ("---------------------------------");
log.info ("NEW BOOKING");
log.info ("---------------------------------");


//bookingCode
valida=jsonSlurper.bookingCode
log.info(" bookingCode: " + valida)
idNotNull = (valida!=null) ? "(valida is not null" : assertionList.add(" bookingCode is null")     

log.info ("                                 ");
log.info ("*********************************");
log.info ("VALIDA MAIN BOOKING DATA");

//description
valida=jsonSlurper.description
log.info(" description: " + valida)
idNotNull = (valida!=null) ? "(valida is not null" : assertionList.add(" description is null")

//market
valida=jsonSlurper.market
log.info(" market: " + valida)
idNotNull = (valida!=null) ? "(valida is not null" : assertionList.add(" market is null")

//category
valida=jsonSlurper.category
log.info(" category: " + valida) 
idNotNull = (valida!=null) ? "(valida is not null" : assertionList.add(" category is null")   

//currency
valida=jsonSlurper.currency
log.info(" currency: " + valida)
idNotNull = (valida!=null) ? "(valida is not null" : assertionList.add(" currency is null")   

//origin
valida=jsonSlurper.origin
log.info(" origin: " + valida)
idNotNull = (valida!=null) ? "(valida is not null" : assertionList.add(" origin is null")   

//destination
valida=jsonSlurper.destination
log.info(" destination: " + valida)
idNotNull = (valida!=null) ? "(valida is not null" : assertionList.add(" destination is null")   

//Status: For prebooking status should be Pending and Substatus = null and for booking should be Confirmed
//Comprobamos el bookingNumber para saber si ha sido confirmado.
def bookingNumber = jsonSlurper.flightHotelPackage.flights[0].bookingNumber;
log.info("jsonSlurper.flightHotelPackage.flights[0].bookingNumber: " + jsonSlurper.flightHotelPackage.flights[0].bookingNumber)
valida=jsonSlurper.status
log.info(" Status: " + valida)
if (bookingNumber==null) { //esto es un prebooking
	idNotNull = (valida=="PENDING") ? "Status is Pending" : assertionList.add(" Status should be Pending")  
}
else {	//esto es un booking
	idNotNull = (valida=="CONFIRMED") ? "Status is Confirmed" : assertionList.add(" Status should be Confirmed")  
}


//bookingBeginningDate
valida=jsonSlurper.bookingBeginningDate
log.info(" bookingBeginningDate: " + valida)
idNotNull = (valida!=null) ? "(valida is not null" : assertionList.add(" bookingBeginningDate is null")     

//bookingEndDate
valida=jsonSlurper.bookingEndDate
log.info(" bookingEndDate: " + valida)
idNotNull = (valida!=null) ? "(valida is not null" : assertionList.add(" bookingEndDate is null")     

//codUserCreated:
valida=jsonSlurper.codUserCreated
log.info(" codUserCreated: " + valida)
idNotNull = (valida!=null) ? "codUserCreated is not null" : assertionList.add(" codUserCreated is null")   

//datUserCreated: 
valida=jsonSlurper.datUserCreated
log.info(" datUserCreated: " + valida)
idNotNull = (valida!=null) ? "datUserCreated is not null" : assertionList.add(" datUserCreated is null")   
    

//Valida 1: bookingEndDate > bookingBeginningDate
idNotNull = (jsonSlurper.bookingBeginningDate <= jsonSlurper.bookingEndDate) ? "OK" : assertionList.add(" Valida 1: bookingEndDate can't be before bookingBeginningDate")     

//Valida 2: Segment data
def i,j,k
def departure, arrival
k=0

while (jsonSlurper.flightHotelPackage.flights[k] != null ){
   i=0
   
    	while (jsonSlurper.flightHotelPackage.flights[k].flightSegments[i] != null ){
        j=0	
	    while (jsonSlurper.flightHotelPackage.flights[k].flightSegments[i].flightLegs[j] != null ){
		
	       	departure=jsonSlurper.flightHotelPackage.flights[k].flightSegments[i].flightLegs[j].departureDateTime
	       	arrival=jsonSlurper.flightHotelPackage.flights[k].flightSegments[i].flightLegs[j].arrivalDateTime
	       	log.info(" departureDateTime: " + departure)
	       	log.info(" arrivalDateTime: " + arrival)
         		idNotNull = (departure < arrival ) ? "OK" : assertionList.add(" Valida 2: arrivalDateTime can't be before departureDateTime")	
         		j++
		}     
	i++
	}
k++
}


//add all Err assertions in the log info
if (!assertionList.empty)
	log.error("***** lista final de errores *****");
for (m in assertionList) {
    log.error( m)
}

assert assertionList.size() == 0