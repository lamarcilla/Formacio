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
 * VALIDA SEATPLAN LIST
 *****************************************************************************
 */

log.info ("                                 ");
log.info ("*********************************");
log.info ("VALIDA SEATPLAN list");


def i,j,k
def assertionList = []
def value, idNotNull

//PENDENT DEFINIR QUINS CAMPS HEM DE VALIDAR.

log.info(jsonSlurper)

//Price
valida=jsonSlurper.rows[0].blocks[0].seats[0].price
log.info(" price: " + valida)
idNotNull = (valida!=null) ? "(valida is not null" : assertionList.add(" price is null")     



//add all Err assertions in the log info
if (!assertionList.empty)
	log.error("***** lista final de errores *****");
for (m in assertionList) {
    log.error( m);
}
assert assertionList.size() == 0
