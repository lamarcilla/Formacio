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
 * VALIDA CLIENT INFO
 *****************************************************************************
 */

log.info ("                                 ");
log.info ("*********************************");
log.info ("VALIDA CLIENT INFO");


def i,j,k
def assertionList = []
def value, idNotNull, primary


//comments
valida=jsonSlurper.client.comments
log.info(" comments: " + valida)
idNotNull = (valida!=null) ? "(valida is not null" : assertionList.add(" comments is null")     

//adresses
valida=jsonSlurper.client.addresses
log.info(" adresses: " + valida)
idNotNull = (valida!=null) ? "(valida is not null" : assertionList.add(" adresses is null")     

//Verify Only one primary address
//if (jsonSlurper.status == "Confirm"){}   
   //adresses
   log.info ("Verify Only one primary address");
   j=0
   primary=0
   while (jsonSlurper.client.addresses[j] != null ){
		if (jsonSlurper.client.addresses[j].primary == true) { 
			primary=primary + 1
			log.info(" 333322222")
			if (primary > 1) assertionList.add("There are more than one primary address") 
	     }
     j++
   }
			  
//dateOfBirth
valida=jsonSlurper.client.dateOfBirth
log.info(" dateOfBirth: " + valida)
idNotNull = (valida!=null) ? "(valida is not null" : assertionList.add(" dateOfBirth is null")     

//firstName
valida=jsonSlurper.client.firstName
log.info(" firstName: " + valida)
idNotNull = (valida!=null) ? "(valida is not null" : assertionList.add(" firstName is null")     

//gender
valida=jsonSlurper.client.gender
log.info(" gender: " + valida)
idNotNull = (valida!=null) ? "(valida is not null" : assertionList.add(" gender is null") 

//lastName
valida=jsonSlurper.client.lastName
log.info(" lastName: " + valida)
idNotNull = (valida!=null) ? "(valida is not null" : assertionList.add(" lastName is null")     

//middleName
valida=jsonSlurper.client.middleName
log.info(" middleName: " + valida)
idNotNull = (valida!=null) ? "(valida is not null" : assertionList.add(" middleName is null")     

//travelDocumentData ??



//add all Err assertions in the log info
if (!assertionList.empty)
	log.error("***** lista final de errores *****");
for (m in assertionList) {
    log.error( m);
}
assert assertionList.size() == 0
