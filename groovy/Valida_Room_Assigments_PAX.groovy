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
log.info ("VALIDA ROOM ASSIGMENT");


def i
def assertionList = []
def id, validate
def numAssig=0


// For each ROOM HOTEL package,>> validatete room assignaments
i=0
while (jsonSlurper.flightHotelPackage.hotels[i] != null ){
	j=0
	log.info ("VERIFICATION 1: Room has, al least, one assigned person");
	while (jsonSlurper.flightHotelPackage.hotels[i].rooms[j] != null ){
		numAssig = numAssig + jsonSlurper.flightHotelPackage.hotels[i].rooms[j].personAssignaments.size()
		//VERIFICATION 1: Room has, al least, one assigned person
		log.info("numAssig en flightHotelPackage.hotels["+i+"].rooms["+j+"]: " + numAssig);
		validate = (numAssig != 0)  ? log.info("VERIFICATION 1 en room["+j+"] OK") : assertionList.add(" VERIFICATION 1: Room["+j+"] without assigned people " )
	j++		
	}
i++
}



//VERIFICATION 2: NUMBER Rooms assignment IS matching with pax
log.info ("VERIFICATION 2: NUMBER Rooms assignment IS matching with pax");
def numPax= 0;
p=0
while (jsonSlurper.paxes[p] != null ){
	//if (jsonSlurper.paxes[p].personType!="INF")
		numPax++;
	p++
}

log.info ("numPax: " + numPax + " - numAssig:" + numAssig);
validate = (numAssig == numPax)  ? " VERIFICATION 2: Ok" : assertionList.add(" VERIFICATION 2: Pax is not matching: " +  numPax + " with RoomAssigned: " + numAssig  )



//VERIFICATION 3: For each person in a Pax >> validate personNumber
log.info ("VERIFICATION 3: For each person in a Pax >> validate paxNumber");
i=0
def paxNumber
while (jsonSlurper.paxes[i] != null ){
		numAssig=0
	     paxNumber = jsonSlurper.paxes[i].paxNumber
	     //jsonSlurper.find { it.paxNumber == '2' }?.key == '0'
          //assert json.findResult { k, v -> v?.projectKey == 'BEST' ? k : null } == '0'
	     //log.info ("PersonNumber: " + paxNumber)
		//numAssig = numAssig + jsonSlurper.flightHotelPackage.hotels[i].rooms[0].personAssignaments.size()
		//log.info ("numAssig: " + numAssig)
		//validate = (paxNumber == numAssig)  ? "" : assertionList.add(" VERIFICATION 3: Rooms Not asssigned correctly" )
		//VERIFICATION 1: Room has, al least, one assigned person
		//validate = (numAssig != 0)  ? "Ok" : assertionList.add(" VERIFICATION 1: Room without assigned people " )
i++
}

log.warn("VERIFICATION 3: For each person in a Pax >> validate paxNumber" )
//add all Err assertions in the log info
if (!assertionList.empty)
	log.error("***** lista final de errores *****");
for (m in assertionList) {
    log.error( m);
}
assert assertionList.size() == 0

