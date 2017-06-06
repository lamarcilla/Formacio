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
 * CHECK PREBOOKING TEST CASES WITH ERRORS
 *****************************************************************************
 */
 
//var definition
def error, errorMessage, errorCode, message, status 

//get response values
error = jsonSlurper.error
errorCode = jsonSlurper.errorCode
errorMessage = jsonSlurper.errorMessage
errorDetailedMessage = jsonSlurper.errorDetailedMessage
bookingCode = jsonSlurper.bookingCode
message = jsonSlurper.message
status = jsonSlurper.status

//check the status value in uncontrolled errors (status != null --> uncontrolled)
if (status == 400) { 
  //uncontrolled error:
  log.info (" ");
  log.info (" --------------------------------");
  log.info (" UNCONTROLLED ERROR              ");
  log.info (" Status: " + status + " ( " + error + " ) --> " + message )
  log.info (" --------------------------------");
  assert (false)
  }
  else
	if (bookingCode != null) {
      //not found errors:
	  log.info (" ");
	  log.info (" -----------------------------------------------------------------------");
	  log.info (" NOT FOUND SCHEDULED ERRORS! CHECK ASSOCIATED PAYLOAD ");
	  log.info (" errorCode: " +  errorCode + " ( " + errorMessage + " ) --> " + errorDetailedMessage + "   " )
	  log.info (" -----------------------------------------------------------------------");
	  assert (false) 
    }
  else
	if (status == 500) {
      //uncontrolled error:
	  log.info (" ");
	  log.info (" --------------------------------------------------------------------------------");
	  log.info (" INTERNAL SERVER ERROR! PLEASE CONTACT YOUR ADMINISTRATOR ");
	  log.info (" errorCode: " +  errorCode + " ( " + errorMessage + " ) --> " + errorDetailedMessage + "   " )
	  log.info (" --------------------------------------------------------------------------------");
	  assert (false) 
    }  
  else {
    //controlled errors:
	log.info (" ");
    log.info (" --------------------------------");
    log.info (" CONTROLLED ERROR                ");
    log.info (" errorCode: " +  errorCode + " ( " + errorMessage + " ) --> " + errorDetailedMessage + "   " )
	log.info (" --------------------------------");
	assert (true)
  }
  