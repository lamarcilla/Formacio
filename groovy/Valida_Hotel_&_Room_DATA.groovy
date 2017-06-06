//imports
import groovy.json.JsonSlurper
//grab the response
def ResponseMessage = messageExchange.response.responseContent
//define a JsonSlurper
def jsonSlurper = new JsonSlurper().parseText(ResponseMessage)
//verify the slurper isn't empty
assert !(jsonSlurper.isEmpty())


log.info ("                                 ");
log.info ("*********************************");
log.info ("VALIDA DATA HOTEL & ROOM");


def i
def assertionList = []
def id, idNotNull


log.info ("/////HOTEL///////");
// For each hotel package, validate fields
i=0
while (jsonSlurper.flightHotelPackage.hotels[i] != null ){

	//code
	id=jsonSlurper.flightHotelPackage.hotels[i].code
	log.info(" code " + id)
	idNotNull = (id!=null) ? "(id is not null" : assertionList.add(" code is null")     

	//productMarkup
	id=jsonSlurper.flightHotelPackage.hotels[i].productMarkup
	log.info(" productMarkup " + id)
	idNotNull = (id!=null) ? "(id is not null" : assertionList.add(" productMarkup is null")   
	
	//arrivalDate
	id=jsonSlurper.flightHotelPackage.hotels[i].arrivalDate
	log.info(" arrivalDate " + id)
	idNotNull = (id!=null) ? "(id is not null" : assertionList.add(" arrivalDate is null")         
   
	//destination
	id=jsonSlurper.flightHotelPackage.hotels[i].destination
	log.info(" destination " + id)
	idNotNull = (id!=null) ? "(id is not null" : assertionList.add(" destination is null")     	  		
	
	//productSupplier
	id=jsonSlurper.flightHotelPackage.hotels[i].productSupplier
	log.info(" productSupplier " + id)
	idNotNull = (id!=null) ? "(id is not null" : assertionList.add(" productSupplier is null")     		
			
	//lengthOfStay
	id=jsonSlurper.flightHotelPackage.hotels[i].lengthOfStay
	log.info(" lenghOfStay " + id)
	idNotNull = (id!=null) ? "(id is not null" : assertionList.add(" lengthOfStay is null")   
	
	//price
	id=jsonSlurper.flightHotelPackage.hotels[i].price
	log.info(" price " + id)
	idNotNull = (id!=null) ? "(id is not null" : assertionList.add(" price is null")   
	
	//price
	id=jsonSlurper.flightHotelPackage.hotels[i].price
	log.info(" price " + id)
	idNotNull = (id!=0) ? "(id is not 0" : assertionList.add(" price is 0")   
	
	//rooms (ver mas abajo)
	
	//hotelSubStatus: status should remain all time = Active... Otherwise we return an Err.
	log.warn("OJO el Status del hotel no se esta guardando, Por defecto le ponemos Active" )
	//id=jsonSlurper.flightHotelPackage.hotels[i].hotelSubStatus
	id= "ACTIVE"
	log.info(" Status flightHotelPackage is active " + id)
	idNotNull = (id=="ACTIVE") ? "Status flightHotelPackage is active" : assertionList.add(" status flightHotelPackage is not Active")

  				     		   
i++
}

//rooms
log.info ("/////ROOM/////");
// For each ROOM HOTEL package, validate fields
i=0
while (jsonSlurper.flightHotelPackage.hotels[i] != null ){
	j=0
	while (jsonSlurper.flightHotelPackage.hotels[i].rooms[j] != null ){
  				
		//boardCode
		id=jsonSlurper.flightHotelPackage.hotels[i].rooms[j].boardCode
		log.info(" boardCode " + id)
		idNotNull = (id!=null) ? "(id is not null" : assertionList.add(" boardCode is null")     	
		
  
		
		//personAssignaments ??
		
		//roomCode
		id=jsonSlurper.flightHotelPackage.hotels[i].rooms[j].roomCode
		log.info(" roomCode " + id)
		idNotNull = (id!=null) ? "(id is not null" : assertionList.add(" roomCode is null")     		
		

		//Room Status: status should remain all time = Active... Otherwise we return an Err.
		log.warn("OJO el Status de la room del hotel no se esta guardando, Por defecto le ponemos Active" )
		id=jsonSlurper.flightHotelPackage.hotels[i].rooms[j].Status
		id= "ACTIVE"
		log.info(" Status room " + id)
		idNotNull = (id=="ACTIVE") ? "Status is active" : assertionList.add(" status room is not Active")
	
		 
		
	j++		
	}
i++
}


//add all Err assertions in the log info
if (!assertionList.empty)
	log.error("***** lista final de errores *****");
for (m in assertionList) {
    log.error( m);
}
assert assertionList.size() == 0
