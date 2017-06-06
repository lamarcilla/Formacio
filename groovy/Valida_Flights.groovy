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
 

 hay q validar:
(p) response de prebooking | (c) response de confirm
 -Flight supplier ref (only editable for new flight services)
flightHotelPackage.productSupplier? or flightHotelPackage.flights.productSupplier?
 -DS Cost Price (this field will only be completed if the flight contains a distressed seat. not editable ) Rejected
???
 -Cost and DS cost difference (cost price - ds cost price. not editable)Rejected Ana Campins 02/08
???
 -DS Adjustament (Sell Price- Cost Price)
 ???
 -Price parity
flightHotelPackage.pairPrice? or flightHotelPackage.flights.pairPrice? 
 -Sell price (cost price+markups)
 ???
 -Discount (if it is modified the price summary flight line should be updated )
 ???


 
 *****************************************************************************
 */
	
def assertionList = []
def valida, idNotNull

log.info ("                                 ");
log.info ("*********************************");
log.info ("VALIDA FLIGHT DATA");


//**** Check flightHotelPackage exist, flights
def flightHotelPackage, flights, flightSegments, flightLegs
def i,j,k
		
		

valida=jsonSlurper.flightHotelPackage
idNotNull = (valida!=null) ? "" : assertionList.add(" flightHotelPackage is null")     

//***** Valida the all package
if ( valida != null  ) {

	//******  Check if flights exist
	flightHotelPackage = jsonSlurper.flightHotelPackage.size()	
	valida=jsonSlurper.flightHotelPackage.flights	
	idNotNull = (valida!=null) ? "" : assertionList.add(" flights is null")     
	if (idNotNull=="") flights = jsonSlurper.flightHotelPackage.flights.size()
	log.info(" Nº flights: " + flights)
	
	//****** Valida flightHotelPackage
	log.info("**** Valida flightHotelPackage: ")

	//price
	valida=jsonSlurper.flightHotelPackage.price
	log.info(" price: " + valida)
	idNotNull = (valida!=null) ? "(valida is not null" : assertionList.add(" flightHotelPackage.price is null")  
	
	//code
	valida=jsonSlurper.flightHotelPackage.code
	log.info(" code: " + valida)
	idNotNull = (valida!=null) ? "(valida is not null" : assertionList.add(" flightHotelPackage.code is null")     
	
	/*description
	valida=jsonSlurper.flightHotelPackage.description
	log.info(" description: " + valida)
	idNotNull = (valida!=null) ? "(valida is not null" : assertionList.add(" flightHotelPackage.description is null")   */  
	
	//productMarkup
	valida=jsonSlurper.flightHotelPackage.productMarkup
	log.info(" productMarkup: " + valida)
	idNotNull = (valida!=null) ? "(valida is not null" : assertionList.add(" flightHotelPackage.productMarkup is null")			
	
	//STATUS: The status should remain all time = Active... Otherwise we return an Err.
	valida=jsonSlurper.flightHotelPackage.status
	log.info(" status: " + valida)
	idNotNull = (valida!=null) ? "(Status is not null" : assertionList.add(" flightHotelPackage.status is null")	
	//idNotNull = (valida=="ACTIVE") ? "Status is active" : assertionList.add(" flightHotelPackage.status is not ACTIVE")     	

	/*programType
	valida=jsonSlurper.flightHotelPackage.programType
	log.info(" programType: " + valida)
	idNotNull = (valida!=null) ? "(valida is not null" : assertionList.add(" flightHotelPackage.programType is null")  */
	
	//tourOperator
	valida=jsonSlurper.flightHotelPackage.tourOperator
	log.info(" tourOperator: " + valida)
	idNotNull = (valida!=null) ? "(valida is not null" : assertionList.add(" flightHotelPackage.tourOperator is null")  	
 
	/*travelType
	valida=jsonSlurper.flightHotelPackage.travelType
	log.info(" travelType: " + valida)
	idNotNull = (valida!=null) ? "(valida is not null" : assertionList.add(" flightHotelPackage.travelType is null")   */
	

		//****** Valida flightHotelPackage.flights
		log.info("**** Valida flightHotelPackage.flights: ")
		k=0
		while (jsonSlurper.flightHotelPackage.flights[k] != null ){
			//******  Valida Flight data
			log.info("**** Valida flight: ")	
			
			//code	
			valida=jsonSlurper.flightHotelPackage.flights[k].code 
			log.info(" code: " + valida)
			idNotNull = (valida!=null) ? "(valida is not null" : assertionList.add(" flightHotelPackage.flights["+k+"].code is null")     		   
			
			/*description
			valida=jsonSlurper.flightHotelPackage.flights[k].description
			log.info(" description: " + valida)
			idNotNull = (valida!=null) ? "(valida is not null" : assertionList.add(" flightHotelPackage.flights["+k+"].description is null")     
			*/
			//productMarkup. Lo validamos en Valida_pricing&Sumatories
							
			//STATUS: The status should remain all time = Active... Otherwise we return an Err.
			valida=jsonSlurper.flightHotelPackage.flights[k].status
			log.info(" status: " + valida)
			//idNotNull = (valida=="ACTIVE") ? "Status is active" : assertionList.add(" flightHotelPackage.flights["+k+"].status is not ACTIVE")     	
			idNotNull = (valida!=null) ? "(flightHotelPackage.flights["+k+"].status is not null" : assertionList.add(" flightHotelPackage.flights["+k+"].status is null")	

			   
				//*******  Valida flightSegments x flight 	
				valida=jsonSlurper.flightHotelPackage.flights[k].flightSegments
				idNotNull = (valida!=null  ) ? "" : assertionList.add(" Flight Itinerari number: " + k +  " is null")     		
			
				//****** Valida flightHotelPackage.flights[k].flightSegments
				log.info("**** Valida flightHotelPackage.flights[k].flightSegments: ")
				if (idNotNull != null) flightSegments = jsonSlurper.flightHotelPackage.flights[k].flightSegments.size()
				i=0
				while (jsonSlurper.flightHotelPackage.flights[k].flightSegments[i] != null ){
					log.info("**** Valida flightSegments: ")	
					
					//arrivalDateTime
					valida=jsonSlurper.flightHotelPackage.flights[k].flightSegments[i].arrivalDateTime
					log.info(" arrivalDateTime: " + valida)
					idNotNull = (valida!=null) ? "(valida is not null" : assertionList.add(" flightHotelPackage.flights["+k+"].flightSegments["+i+"].arrivalDateTime is null")     

					//departureDateTime
					valida=jsonSlurper.flightHotelPackage.flights[k].flightSegments[i].departureDateTime
					log.info(" departureDateTime: " + valida)
					idNotNull = (valida!=null) ? "(valida is not null" : assertionList.add(" flightHotelPackage.flights["+k+"].flightSegments["+i+"].departureDateTime is null")   

					//departureAirportIATA
					valida=jsonSlurper.flightHotelPackage.flights[k].flightSegments[i].departureAirportIATA
					log.info(" departureAirportIATA: " + valida)
					idNotNull = (valida!=null) ? "(valida is not null" : assertionList.add(" flightHotelPackage.flights["+k+"].flightSegments["+i+"].departureAirportIATA is null")  					
				
							//****** Valida flightHotelPackage.flights[k].flightSegments[i].flightLegs[
							log.info("**** Valida flightHotelPackage.flights[k].flightSegments[i].flightLegs[: ")
							j=0 	
							while (jsonSlurper.flightHotelPackage.flights[k].flightSegments[i].flightLegs[j] != null ){
								log.info("**** Valida flightLegs: ")	
								//*******  Valida flightLegs x flightSegments                  	     
								valida=jsonSlurper.flightHotelPackage.flights[k].flightSegments[i].flightLegs
								idNotNull = (valida!=null) ? "" : assertionList.add("flightLeg from flightSegment " +  i +  " from flight: " + k +  " is null")    

								
								if (idNotNull != 0) flightLegs = jsonSlurper.flightHotelPackage.flights[k].flightSegments[i].flightLegs         	     	    	                  
								//********* Valida flightLeg data
								log.info(" Valida flightLeg data, from flightLeg: " + j )
								
								//flightLegNumber
								valida=jsonSlurper.flightHotelPackage.flights[k].flightSegments[i].flightLegs[j].flightLegNumber
								log.info(" flightLegNumber: " + valida)
								idNotNull = (valida!=null) ? "(valida is not null" : assertionList.add(" flightHotelPackage.flights["+k+"].flightSegments["+i+"].flightLegs["+j+"].flightLegNumber is null ")     
											
								//airline
								valida=jsonSlurper.flightHotelPackage.flights[k].flightSegments[i].flightLegs[j].airline
								log.info(" airline: " + valida)
								idNotNull = (valida!=null) ? "(valida is not null" : assertionList.add(" flightHotelPackage.flights["+k+"].flightSegments["+i+"].flightLegs["+j+"].airline is null ")     

								//departureAirportIATA
								valida=jsonSlurper.flightHotelPackage.flights[k].flightSegments[i].flightLegs[j].departureAirportIATA
								log.info(" departureAirportIATAne: " + valida)
								idNotNull = (valida!=null) ? "(valida is not null" : assertionList.add(" flightHotelPackage.flights["+k+"].flightSegments["+i+"].flightLegs["+j+"].departureAirportIATA is null ")     			

								//departureDateTime
								valida=jsonSlurper.flightHotelPackage.flights[k].flightSegments[i].flightLegs[j].departureDateTime
								log.info(" departureDateTime: " + valida)
								idNotNull = (valida!=null) ? "(valida is not null" : assertionList.add(" flightHotelPackage.flights["+k+"].flightSegments["+i+"].flightLegs["+j+"].departureDateTime is null ")     			
								
								//arrivalAirportIATA
								valida=jsonSlurper.flightHotelPackage.flights[k].flightSegments[i].flightLegs[j].arrivalAirportIATA
								log.info(" arrivalAirportIATA: " + valida)
								idNotNull = (valida!=null) ? "(valida is not null" : assertionList.add(" flightHotelPackage.flights["+k+"].flightSegments["+i+"].flightLegs["+j+"].arrivalAirportIATA is null ")     			

								//arrivalDateTime
								valida=jsonSlurper.flightHotelPackage.flights[k].flightSegments[i].flightLegs[j].arrivalDateTime
								log.info(" arrivalDateTime: " + valida)
								idNotNull = (valida!=null) ? "(valida is not null" : assertionList.add(" flightHotelPackage.flights["+k+"].flightSegments["+i+"].flightLegs["+j+"].arrivalDateTime is null ")     			
								
								//bookingClass
								valida=jsonSlurper.flightHotelPackage.flights[k].flightSegments[i].flightLegs[j].bookingClass
								log.info(" bookingClass: " + valida)
								idNotNull = (valida!=null) ? "(valida is not null" : assertionList.add(" flightHotelPackage.flights["+k+"].flightSegments["+i+"].flightLegs["+j+"].bookingClass is null ")
								
								/*flightCode
								valida=jsonSlurper.flightHotelPackage.flights[k].flightSegments[i].flightLegs[j].flightCode
								log.info(" flightCode: " + valida)
								idNotNull = (valida!=null) ? "(valida is not null" : assertionList.add(" flightHotelPackage.flights["+k+"].flightSegments["+i+"].flightLegs["+j+"].flightCode is null ")*/
								
								//flightNumber (Outbound/Inbound )
 								valida=jsonSlurper.flightHotelPackage.flights[k].flightSegments[i].flightLegs[j].flightNumber
								log.info(" flightNumber: " + valida)
								idNotNull = (valida!=null) ? "(valida is not null" : assertionList.add(" flightHotelPackage.flights["+k+"].flightSegments["+i+"].flightLegs["+j+"].flightNumber is null ")
								
								/*tarifClass
 								valida=jsonSlurper.flightHotelPackage.flights[k].flightSegments[i].flightLegs[j].tarifClass
								log.info(" tarifClass: " + valida)
								idNotNull = (valida!=null) ? "(valida is not null" : assertionList.add(" flightHotelPackage.flights["+k+"].flightSegments["+i+"].flightLegs["+j+"].tarifClass is null ")*/
								
								//flightLegPrice
 								valida=jsonSlurper.flightHotelPackage.flights[k].flightSegments[i].flightLegs[j].flightLegPrice
								log.info(" flightLegPrice: " + valida)
								idNotNull = (valida!=null) ? "(valida is not null" : assertionList.add(" flightHotelPackage.flights["+k+"].flightSegments["+i+"].flightLegs["+j+"].flightLegPrice is null ")

								j++
							}  //end  flightHotelPackage.flights[k].flightSegments[i].flightLegs 
					log.info(" flight: " + k + " flightSegments: " + i + " No. flightLegs" +  jsonSlurper.flightHotelPackage.flights[k].flightSegments[i].flightLegs.size() )
					i++
				} //end flightHotelPackage.flights[k].flightSegments
			k++
		} //end flightHotelPackage.flights

} // end if





//add all Err assertions in the log info
if (!assertionList.empty)
	log.error("***** lista final de errores *****");
for (m in assertionList) {
    log.error( m);
}

log.warn("Pending validate: Pax number and type (Adults / Children / Infants)")

assert assertionList.size() == 0