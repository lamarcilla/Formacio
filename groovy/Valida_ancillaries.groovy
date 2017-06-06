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
log.info ("VALIDA ANCILLARIES: credit card + INFANTS");


def i,j,k, l
def founIt
def assertionList = []

//Valida ancilleries
valida=jsonSlurper.ancillaries
idNotNull = (valida!=null) ? "" : assertionList.add(" ancilleries is null")     

log.info ("----- VALIDA ANCILLARIES: credit card");
//IF paymentType" = credit card we should have ancillaries = CCB
paymenType= "CC"
//paymentType= jsonSlurper.formOfPayment.paymentType
log.warn( "Al no tener paymentType, de momento suponemos que todo pago es con crecit card (CC)") 

if ( paymenType== "CC") {
	//Validate ancillaries ccb
	i=0
	foundIt = false
   	while (jsonSlurper.ancillaries[i] != null ){
   		if (jsonSlurper.ancillaries[i].code == "CCB"){
   			founIt=true
   			//Valida price
			valida=jsonSlurper.ancillaries[i].price
			log.info(" price: " + valida)
			idNotNull = (valida!=null) ? "" : assertionList.add(" price is null")     

   			//Valida description
//			valida=jsonSlurper.ancillaries[i].description
//			log.info(" description: " + valida)
//			idNotNull = (valida!=null) ? "" : assertionList.add(" description is null")     

   			//Valida productMarkup
			valida=jsonSlurper.ancillaries[i].productMarkup
			log.info(" productMarkup: " + valida)
			idNotNull = (valida!=null) ? "" : assertionList.add(" productMarkup is null")     

   			//Valida status
//			valida=jsonSlurper.ancillaries[i].status
//			log.info(" status: " + valida)
//			idNotNull = (valida!=null) ? "" : assertionList.add(" status is null")     			
 			

			//Valida quantity
			valida=jsonSlurper.ancillaries[i].quantity
			log.info(" quantity: " + valida)
			idNotNull = (valida==1) ? "" : assertionList.add(" quantity SHOULD BE 1")    

			//Valida type
			valida=jsonSlurper.ancillaries[i].type
			log.info(" type: " + valida)
			idNotNull = (valida!=null) ? "" : assertionList.add(" type is null")  

			//Valida units
//			valida=jsonSlurper.ancillaries[i].units
//			log.info(" units: " + valida)
//			idNotNull = (valida!=null) ? "" : assertionList.add(" units is null")  

 

   		}
		
		if (jsonSlurper.ancillaries[i].code == "SEO" || jsonSlurper.ancillaries[i].code == "SEC"){
		
			//Valida price
			valida=jsonSlurper.ancillaries[i].price
			log.info(" price: " + valida)
			idNotNull = (valida!=null) ? "" : assertionList.add(" price is null")  
			
			//Valida code
			valida=jsonSlurper.ancillaries[i].code
			log.info(" code: " + valida)
			idNotNull = (valida!=null) ? "" : assertionList.add(" code is null")  
			
			//Valida description
			valida=jsonSlurper.ancillaries[i].description
			log.info(" description: " + valida)
			idNotNull = (valida!=null) ? "" : assertionList.add(" description is null")  
			
			//Valida productMarkup
			valida=jsonSlurper.ancillaries[i].productMarkup
			log.info(" productMarkup: " + valida)
			idNotNull = (valida!=null) ? "" : assertionList.add(" productMarkup is null") 
			
			//Valida quantity
			valida=jsonSlurper.ancillaries[i].quantity
			log.info(" quantity: " + valida)
			idNotNull = (valida!=null) ? "" : assertionList.add(" quantity is null")  
			
			//Valida type
			valida=jsonSlurper.ancillaries[i].type
			log.info(" type: " + valida)
			idNotNull = (valida!=null) ? "" : assertionList.add(" type is null")  
			
			/*Valida units
			valida=jsonSlurper.ancillaries[i].units
			log.info(" units: " + valida)
			idNotNull = (valida!=null) ? "" : assertionList.add(" units is null")  */
			
			//Valida flightLegReference
			valida=jsonSlurper.ancillaries[i].flightLegReference
			log.info(" flightLegReference: " + valida)
			idNotNull = (valida!=null) ? "" : assertionList.add(" flightLegReference is null")  
			
			//Valida referenceNumber
			valida=jsonSlurper.ancillaries[i].referenceNumber
			log.info(" referenceNumber: " + valida)
			idNotNull = (valida!=null) ? "" : assertionList.add(" referenceNumber is null")  
		}
		
   	i++	
   	}		   		   
	idNotNull = (founIt != false ) ? "" : assertionList.add(" NOT CCB ancilleries")     
}

log.info ("----- VALIDA ANCILLARIES: INFANTS");
i=0
founIt = false
// If some passenger has an assigned Infant, validate if ancillarie=INF is created
log.warn("Pending to validate: If some passenger has an assigned Infant, validate if ancillarie=INF is created")
while (jsonSlurper.paxes[i] != null ){
    if (jsonSlurper.paxes[i].hasInfant == true) {
    	  //Pending Validate than quatity of Infants in ancillary =  hasInfant= true 
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
