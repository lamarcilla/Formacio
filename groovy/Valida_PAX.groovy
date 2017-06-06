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
log.info ("VALIDA PAX INFO");


def i,j,k, l
def assertionList = []

//Valida passengers
valida=jsonSlurper.paxes
idNotNull = (valida!=null) ? "" : assertionList.add(" Passengers is null")     

i=0
//for each pax, 
while (jsonSlurper.paxes[i] != null ){

    
	//If we are doing a prebooking, we should validate only: paxNumber
	//If the reservation is confirmed, we have to validate all the PAX
	
	
	if (jsonSlurper.status == "PENDING"){
				log.info ("// status es PENDING");
				
			   //age (solo si es CHILDREN)
			   if (jsonSlurper.paxes[i].personType=='CHD') {
				   valida=jsonSlurper.paxes[i].age
				   log.info(" paxes["+i+"].age: " + valida)
				   idNotNull = (valida!=null) ? "(paxes["+i+"].age is not null" : assertionList.add(" paxes["+i+"].age is null")  	 
			   }			   
		      
	} //end status=PENDING
	

	if (jsonSlurper.status == "CONFIRMED"){
			   log.info ("// status es CONFIRMED");
			   
			   //adresses
			   log.info ("// VALIDA adresses for each pax");
			   j=0
			   while (jsonSlurper.paxes[i].addresses[j] != null ){
					
					//adresses.mail
					valida=jsonSlurper.paxes[i].addresses[j].mail
					log.info(" paxes["+i+"].addresses["+j+"].mail: " + valida)
					idNotNull = (valida!=null) ? "(paxes["+i+"].addresses["+j+"].mail is not null" : assertionList.add(" paxes["+i+"].addresses["+j+"].mail is null")  
					
					//adresses.telephones
					t=0
					while (jsonSlurper.paxes[i].addresses[j].telephones[t] != null ){
							//adresses.telephones.number
							valida=jsonSlurper.paxes[i].addresses[j].telephones[t].number
							log.info(" paxes["+i+"].addresses["+j+"].telephones["+t+"].number: " + valida)
							idNotNull = (valida!=null) ? "(paxes["+i+"].addresses["+j+"].telephones["+t+"].number is not null" : assertionList.add(" paxes["+i+"].addresses["+j+"].telephones["+t+"].number is null")  
							
							//adresses.telephones.type
							valida=jsonSlurper.paxes[i].addresses[j].telephones[t].type
							log.info(" paxes["+i+"].addresses["+j+"].telephones["+t+"].type: " + valida)
							idNotNull = (valida!=null) ? "(paxes["+i+"].addresses["+j+"].telephones["+t+"].type is not null" : assertionList.add(" paxes["+i+"].addresses["+j+"].telephones["+t+"].type is null") 
							
							t++
					}
										
					j++ 
			   } //end of 'jsonSlurper.paxes[i].addresses[j]'
			   
			   //firstName	
			   valida=jsonSlurper.paxes[i].firstName
			   log.info(" paxes["+i+"].firstName: " + valida)
			   idNotNull = (valida!=null) ? "(paxes["+i+"]. is not null" : assertionList.add(" paxes["+i+"].firstName is null")    

			   //lastName	
			   valida=jsonSlurper.paxes[i].lastName
			   log.info(" paxes["+i+"].lastName: " + valida)
			   idNotNull = (valida!=null) ? "(paxes["+i+"].lastName is not null" : assertionList.add(" paxes["+i+"].lastName is null")    
			   
			   //salutationType	
			   valida=jsonSlurper.paxes[i].salutationType
			   log.info(" paxes["+i+"].salutationType: " + valida)
			   idNotNull = (valida!=null) ? "(paxes["+i+"].salutationType is not null" : assertionList.add(" paxes["+i+"].salutationType is null") 
			   
			   //age
			   if (jsonSlurper.paxes[i].personType=='CHD') {
				   valida=jsonSlurper.paxes[i].age
				   log.info(" paxes["+i+"].age: " + valida)
				   idNotNull = (valida!=null) ? "(paxes["+i+"].age is not null" : assertionList.add(" paxes["+i+"].age is null")  	 
			   }
			   			   
			   //gender	
			   valida=jsonSlurper.paxes[i].gender
			   log.info(" paxes["+i+"].gender: " + valida)
			   idNotNull = (valida!=null) ? "(paxes["+i+"].gender is not null" : assertionList.add(" paxes["+i+"].gender is null")    
			   //hasInfant 
			   valida=jsonSlurper.paxes[i].hasInfant
			   log.info(" paxes["+i+"].hasInfant: " + valida)
			   idNotNull = (valida!=null) ? "(paxes["+i+"].hasInfant is not null" : assertionList.add(" paxes["+i+"].hasInfant should not be null") 
   
   } // end of 'jsonSlurper.status == "CONFIRMED"'
   
 
   //personType 
   valida=jsonSlurper.paxes[i].personType
   log.info(" paxes["+i+"].personType: " + valida)
   idNotNull = (valida!=null) ? "(paxes["+i+"].personType is not null" : assertionList.add(" paxes["+i+"].personType should not be null") 
			   
   //paxNumber	(solo si es distinto a INF)
   if (jsonSlurper.paxes[i].personType != "INF"){
	   valida=jsonSlurper.paxes[i].paxNumber
	   log.info(" paxes["+i+"].paxNumber: " + valida)
	   idNotNull = (valida!=null) ? "(paxes["+i+"].paxNumber is not null" : assertionList.add(" paxes["+i+"].paxNumber should not be null")  
   }
   
   //isChipped
   valida=jsonSlurper.paxes[i].isChipped
   log.info(" paxes["+i+"].isChipped: " + valida)
   idNotNull = (valida!=null) ? "(paxes["+i+"].isChipped is not null" : assertionList.add(" paxes["+i+"].isChipped should not be null") 

   i++
}     


//add all Err assertions in the log info
if (!assertionList.empty)
	log.error("***** lista final de errores *****");
for (m in assertionList) {
    log.error( m);
}
assert assertionList.size() == 0
