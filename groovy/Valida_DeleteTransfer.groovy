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
 * VALIDA DELETE TRASNFER 
 *****************************************************************************
 */

log.info ("                                 ");
log.info ("*********************************");
log.info ("VALIDA DELETE transfer ");



//Detec any controlled Err
def error, errorMessage
def assertionList = []

error=jsonSlurper.errorCode
errorMessage=jsonSlurper.errorMessage
log.info(" errorCode: " +  error + ": " + errorMessage )
idNotNull = (error==null) ? "(NO Error" : assertionList.add(" Err:" + error + " " + errorMessage )  




//add all Err assertions in the log info
if (!assertionList.empty)
	log.error("***** lista final de errores *****");
for (m in assertionList) {
    log.error( m);
}
assert assertionList.size() == 0
