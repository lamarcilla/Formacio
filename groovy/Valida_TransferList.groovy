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
 * VALIDA TRASNFER LIST
 *****************************************************************************
 */

log.info ("                                 ");
log.info ("*********************************");
log.info ("VALIDA transfer list");


def i,j,k
def assertionList = []
def value, idNotNull

//PENDENT DEFINIR QUINS CAMPS HEM DE VALIDAR.

//Price
valida=jsonSlurper.price
log.info(" price: " + valida)
idNotNull = (valida!=null) ? "(valida is not null" : assertionList.add(" price is null")     

// Validar purchasetoken. el id_carrito es crea la primera vegada que afegim el transfer
// Un cop ja el tenim, hem d'anar afegint el mateix token als altres transfers


//add all Err assertions in the log info
if (!assertionList.empty)
	log.error("***** lista final de errores *****");
for (m in assertionList) {
    log.error( m);
}
assert assertionList.size() == 0
