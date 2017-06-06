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
log.info ("******** VALIDA PRICING *********");
log.info ("*********************************");
log.info ("                                 ");


def a,i,j,p,f,s,x,t
def assertionList = []
def asercion
def paxes
def totalFlightCalculate = 0;
def amtMarketPriceFlight = 0;
def pairPriceFlight = 0;
def pairPriceAncillaries = 0;
def pairPriceSeats=0;
def amtMarketPriceAncillaries = 0;
def totalMarkupAncillaries = 0;
def amtMarketPriceSeat=0;
def totalMarkupSeat=0;
def cardFeeFlight=0;
def cardFeeSeats=0;
def cardFeeAncillaries=0;

def pairPriceFlightHotelPackage = 0;
pairPriceFlightHotelPackage = jsonSlurper.flightHotelPackage.pairPrice;
log.info("flightHotelPackage.pairPrice: " + pairPriceFlightHotelPackage)


//HOTEL
def hotel
hotel=jsonSlurper.flightHotelPackage.hotels[0].amtMarketPrice
def pairPriceHotel = 0;
pairPriceHotel = jsonSlurper.flightHotelPackage.hotels[0].pairPrice;
def cardFeeHotel=0;
if (jsonSlurper.flightHotelPackage.hotels[0].cardFee != null) cardFeeHotel = jsonSlurper.flightHotelPackage.hotels[0].cardFee;

asercion = (hotel!=null) ? "(Hotel amtMarketPrice is not null" : assertionList.add("Hotel amtMarketPrice is null")
log.info ("***HOTEL*** -------------------------------->amtMarketPrice Hotel: " + hotel + ". pairPriceHotel: " + pairPriceHotel)	
log.info("cardFeeHotel: " + cardFeeHotel);


//flights. obtenemos los vuelos.
f=0	
asercion = (jsonSlurper.flightHotelPackage.flights[f]!=null) ? "flightHotelPackage.flights is not null" : assertionList.add("flightHotelPackage.flights is null")
while (jsonSlurper.flightHotelPackage.flights[f] != null ){
   //obtenemos el precio del flight[f] , del pairPrice y del cardFee
   amtMarketPriceFlight = amtMarketPriceFlight + jsonSlurper.flightHotelPackage.flights[f].amtMarketPrice;
   pairPriceFlight = pairPriceFlight + jsonSlurper.flightHotelPackage.flights[f].pairPrice;
   log.info("***FLIGHTS*** -------------------------------->flights["+f+"].amtMarketPriceFlight: " + amtMarketPriceFlight + ". pairPriceFlight: " + pairPriceFlight)
   //cardFee
   if (jsonSlurper.flightHotelPackage.flights[f].cardFee != null) cardFeeFlight = cardFeeFlight + jsonSlurper.flightHotelPackage.flights[f].cardFee;
  
   //flightSegments. obtenemos los flightSegments del flight[f]
   i=0	
   asercion = (jsonSlurper.flightHotelPackage.flights[f].flightSegments[i]!=null) ? "flightHotelPackage.flights.flightSegments["+i+"] is not null" : assertionList.add("flightHotelPackage.flights.flightSegments["+i+"] is null")
   while (jsonSlurper.flightHotelPackage.flights[f].flightSegments[i] != null ){
		//flightLegs. obtenemos los flightLegs del flightSegments[i] del flight[f]	
		s=0
		asercion = (jsonSlurper.flightHotelPackage.flights[f].flightSegments[i].flightLegs[s]!=null) ? "flightHotelPackage.flights["+f+"].flightSegments["+i+"].flightLegs["+s+"] is not null" : assertionList.add("flightHotelPackage.flights["+f+"].flightSegments["+i+"].flightLegs["+s+"] is null")
		while (jsonSlurper.flightHotelPackage.flights[f].flightSegments[i].flightLegs[s] != null ){		
			
			
			//Seats 
			log.info("***seats*** (vuelo "+f+" segmento "+i+" leg "+s+")");		
			t=0
			if (jsonSlurper.flightHotelPackage.flights[f].flightSegments[i].flightLegs[s].selectedSeats!=null) {
				while (jsonSlurper.flightHotelPackage.flights[f].flightSegments[i].flightLegs[s].selectedSeats[t] != null ){
					   
					//cardFees
					if (jsonSlurper.flightHotelPackage.flights[f].flightSegments[i].flightLegs[s].selectedSeats[t].cardFee != null) cardFeeSeats = cardFeeSeats + jsonSlurper.flightHotelPackage.flights[f].flightSegments[i].flightLegs[s].selectedSeats[t].cardFee;  
					
					//pairPrice	
					pairPriceSeats = pairPriceSeats + jsonSlurper.flightHotelPackage.flights[f].flightSegments[i].flightLegs[s].selectedSeats[t].pairPrice;
					
					//amtMarketPrice seat
					amtMarketPriceSeat = amtMarketPriceSeat + jsonSlurper.flightHotelPackage.flights[f].flightSegments[i].flightLegs[s].selectedSeats[t].amtMarketPrice
					
					//productMarkup seat (no es un error que sea null)
					def productMarkupSeat = 0;
					if (jsonSlurper.flightHotelPackage.flights[f].flightSegments[i].flightLegs[s].selectedSeats[t].productMarkup!=null)
						productMarkupSeat = jsonSlurper.flightHotelPackage.flights[f].flightSegments[i].flightLegs[s].selectedSeats[t].productMarkup;
					totalMarkupSeat = totalMarkupSeat + productMarkupSeat;
					
					log.info("seat["+t+"]. amtMarketPrice = "+jsonSlurper.flightHotelPackage.flights[f].flightSegments[i].flightLegs[s].selectedSeats[t].amtMarketPrice+ ", productMarkup = "+jsonSlurper.flightHotelPackage.flights[f].flightSegments[i].flightLegs[s].selectedSeats[t].productMarkup);
					t++;
				}
			}
			log.info("------------------->cardFeeSeats (acumulado): " + cardFeeSeats); 
			log.info("------------------->pairPriceSeats (acumulado): " + pairPriceSeats);

			//Ancillaries
			log.info("***anc***   (vuelo "+f+" segmento "+i+" leg "+s+")");
			a=0
			if (jsonSlurper.flightHotelPackage.flights[f].flightSegments[i].flightLegs[s].selectedAncillaries!=null) {
				while (jsonSlurper.flightHotelPackage.flights[f].flightSegments[i].flightLegs[s].selectedAncillaries[a] != null ){

					log.info("ancillarie["+a+"]. code: ("+ jsonSlurper.flightHotelPackage.flights[f].flightSegments[i].flightLegs[s].selectedAncillaries[a].code +"), precio: ("+ jsonSlurper.flightHotelPackage.flights[f].flightSegments[i].flightLegs[s].selectedAncillaries[a].amtMarketPrice +"), productMarkup: ("+ jsonSlurper.flightHotelPackage.flights[f].flightSegments[i].flightLegs[s].selectedAncillaries[a].productMarkup +"), quantity: ("+ jsonSlurper.flightHotelPackage.flights[f].flightSegments[i].flightLegs[s].selectedAncillaries[a].quantity +"), flightLegReference: ("+ jsonSlurper.flightHotelPackage.flights[f].flightSegments[i].flightLegs[s].selectedAncillaries[a].flightLegReference +")");
					//cardFees
					if (jsonSlurper.flightHotelPackage.flights[f].flightSegments[i].flightLegs[s].selectedAncillaries[a].cardFee != null) cardFeeAncillaries = cardFeeAncillaries + jsonSlurper.flightHotelPackage.flights[f].flightSegments[i].flightLegs[s].selectedAncillaries[a].cardFee;
					
					//pairPrice	
					pairPriceAncillaries = pairPriceAncillaries + jsonSlurper.flightHotelPackage.flights[f].flightSegments[i].flightLegs[s].selectedAncillaries[a].pairPrice;

					asercion = (jsonSlurper.flightHotelPackage.flights[f].flightSegments[i].flightLegs[s].selectedAncillaries[a].amtMarketPrice!=null) ? "selectedAncillaries["+a+"].amtMarketPrice is not null" : assertionList.add("selectedAncillaries.amtMarketPrice is null")
					if (asercion==true) log.error("selectedAncillaries["+a+"].amtMarketPrice es NULL ");
					
					//existen ancillaries que pueden ser nulas. En cambio, LUG,SEO,SEC,WGT y SSC no pueden serlo.
					def markupAncillary = 0;
					def lstAncillaries = ['LUG','SEO','SEC','WGT','SSC']; 
					if (lstAncillaries.contains(jsonSlurper.flightHotelPackage.flights[f].flightSegments[i].flightLegs[s].selectedAncillaries[a].code)) {
						asercion = (jsonSlurper.flightHotelPackage.flights[f].flightSegments[i].flightLegs[s].selectedAncillaries[a].productMarkup!=null) ? "selectedAncillaries["+a+"].productMarkup is not null" : assertionList.add("selectedAncillaries["+a+"].productMarkup (code: "+jsonSlurper.flightHotelPackage.flights[f].flightSegments[i].flightLegs[s].selectedAncillaries[a].code+") es NULL. Ancillaries LUG,SEO,SEC,WGT y SSC no pueden serlo.")
						if (asercion==true) log.error("selectedAncillaries["+a+"].productMarkup (code: "+jsonSlurper.flightHotelPackage.flights[f].flightSegments[i].flightLegs[s].selectedAncillaries[a].code+") es NULL. Ancillaries LUG,SEO,SEC,WGT y SSC no pueden serlo.");
					}
					if (jsonSlurper.flightHotelPackage.flights[f].flightSegments[i].flightLegs[s].selectedAncillaries[a].productMarkup!=null)
						markupAncillary = jsonSlurper.flightHotelPackage.flights[f].flightSegments[i].flightLegs[s].selectedAncillaries[a].productMarkup;
					asercion = (jsonSlurper.flightHotelPackage.flights[f].flightSegments[i].flightLegs[s].selectedAncillaries[a].quantity!=null) ? "selectedAncillaries["+a+"].quantity is not null" : assertionList.add("selectedAncillaries.quantity is null")
					if (asercion==true) log.error("selectedAncillaries["+a+"].quantity es NULL ");
					
					//suma total de markups ancillaries
					totalMarkupAncillaries = totalMarkupAncillaries + (markupAncillary * jsonSlurper.flightHotelPackage.flights[f].flightSegments[i].flightLegs[s].selectedAncillaries[a].quantity)
					//suma total de precios ancillaries
					amtMarketPriceAncillaries = amtMarketPriceAncillaries + (jsonSlurper.flightHotelPackage.flights[f].flightSegments[i].flightLegs[s].selectedAncillaries[a].amtMarketPrice * jsonSlurper.flightHotelPackage.flights[f].flightSegments[i].flightLegs[s].selectedAncillaries[a].quantity)
					a++;
				}
			}
            log.info("------------------->cardFeeAncillaries (acumulado): " + cardFeeAncillaries);   
		    log.info("------------------->pairPriceAncillaries (acumulado): " + pairPriceAncillaries);
			s++			
		}
		i++
	}
	
	f++
}
 log.info("cardFeeFlight (acumulado): " + cardFeeFlight); 

//Total Package
def totalPackage
totalPackage = amtMarketPriceFlight + hotel;
log.info("***Total Package*** -------------------------> totalPackage: " + totalPackage + "  (amtMarketPriceFlight ("+amtMarketPriceFlight+") + hotel ("+hotel+") )");
	
if (totalPackage == jsonSlurper.flightHotelPackage.amtMarketPrice) {
	log.info("El flightHotelPackage "+jsonSlurper.flightHotelPackage.amtMarketPrice+" coincide con el calculado: "+totalPackage)
}
else {
	if (jsonSlurper.flightHotelPackage.amtMarketPrice - totalPackage > 0.01)
		assertionList.add("*** El amtMarketPrice total del flightHotelPackage "+jsonSlurper.flightHotelPackage.amtMarketPrice+" NO coincide con el calculado: "+totalPackage)
	log.warn("El flightHotelPackage "+jsonSlurper.flightHotelPackage.amtMarketPrice+" NO coincide con el calculado: "+totalPackage)
}


//Transfers
log.info("***transfers***");
s=0
def priceTransfer=0;
def totalMarkupTransfer=0;
def pairPriceTransfers=0;
def cardFeeTransfer=0;

//pairPrice	
while (jsonSlurper.transfers[s] != null ){
	pairPriceTransfers = pairPriceTransfers + jsonSlurper.transfers[s].pairPrice;
	log.info ("transfers["+s+"].pairPrice: " + jsonSlurper.transfers[s].pairPrice )	
	
	//cardFee
    if (jsonSlurper.transfers[s].cardFee != null) cardFeeTransfer = cardFeeTransfer + jsonSlurper.transfers[s].cardFee; 
   
    //amtMarketPrice transfer
	priceTransfer= priceTransfer + jsonSlurper.transfers[s].amtMarketPrice
	asercion = (priceTransfer!=null) ? "" : assertionList.add("priceTransfer is null")
	log.info ("transfers["+s+"].amtMarketPrice: " + jsonSlurper.transfers[s].amtMarketPrice )	

	//markup transfer
	asercion = (jsonSlurper.transfers[s].productMarkup!=null) ? "transfers productMarkup is not null" : assertionList.add("transfers productMarkup is null")	
	totalMarkupTransfer = totalMarkupTransfer + jsonSlurper.transfers[s].productMarkup
	log.info ("transfers["+s+"].productMarkup: " + jsonSlurper.transfers[s].productMarkup )	

    s++;
}
log.info("------------------->cardFeeTransfer (acumulado): " + cardFeeTransfer); 
log.info("------------------->amtMarketPrice Transfers (acumulado): " + priceTransfer )	
log.info("------------------->pairPriceTransfers (acumulado): " + pairPriceTransfers);
log.info("------------------->productMarkup Transfers(acumulado): " + totalMarkupTransfer);


//serviceFee
log.info("***serviceFee***");
def amtMarketPriceServiceFee = 0;
def serviceFeeProductMarkup = 0;
def pairPriceServiceFees = 0;
def cardFeeServiceFee = 0;
if (jsonSlurper.serviceFees!=null) {
	s=0
	while (jsonSlurper.serviceFees[s] != null ){
		log.info("serviceFees["+s+"].amtMarketPrice: " + jsonSlurper.serviceFees[s].amtMarketPrice);
		amtMarketPriceServiceFee = amtMarketPriceServiceFee + jsonSlurper.serviceFees[s].amtMarketPrice;
		log.info("serviceFees["+s+"].productMarkup: " + jsonSlurper.serviceFees[s].productMarkup);
		serviceFeeProductMarkup = serviceFeeProductMarkup + jsonSlurper.serviceFees[s].productMarkup;
		log.info("serviceFees["+s+"].pairPrice: " + jsonSlurper.serviceFees[s].pairPrice);
		pairPriceServiceFees = pairPriceServiceFees + jsonSlurper.serviceFees[s].pairPrice;
		log.info("serviceFees["+s+"].cardFee: " + jsonSlurper.serviceFees[s].cardFee);		
		if (jsonSlurper.serviceFees[s].cardFee != null) cardFeeServiceFee = cardFeeServiceFee + jsonSlurper.serviceFees[s].cardFee;
		s++;
	}
}
log.info("------------------->amtMarketPriceServiceFee (acumulado): "+ amtMarketPriceServiceFee +" ,serviceFeeProductMarkup (acumulado): "+serviceFeeProductMarkup +" ,pairPriceServiceFees (acumulado): "+pairPriceServiceFees);
log.info("------------------->cardFeeServiceFee (acumulado): " + cardFeeServiceFee); 


//TOTALPriceMarkups
log.info("***markups***");
//productMarkup flightHotelPackage
asercion = (jsonSlurper.flightHotelPackage.productMarkup!=null) ? "flightHotelPackage.productMarkup is not null" : assertionList.add("flightHotelPackage.productMarkup is null")
//productMarkup flights
asercion = (jsonSlurper.flightHotelPackage.flights[0].productMarkup!=null) ? "flightHotelPackage.flights[0].productMarkup is not null" : assertionList.add("flightHotelPackage.flights[0].productMarkup is null")
//productMarkup hotels
def productMarkupHotel = 0;
if (jsonSlurper.flightHotelPackage.hotels[0] != null) {
  if (jsonSlurper.flightHotelPackage.hotels[0].productMarkup != null) {
	productMarkupHotel = jsonSlurper.flightHotelPackage.hotels[0].productMarkup;
  }
  asercion = (jsonSlurper.flightHotelPackage.hotels[0].productMarkup!=null) ? "flightHotelPackage.hotels[0].productMarkup is not null" : assertionList.add("flightHotelPackage.hotels[0].productMarkup is null")
}
def totalPriceMarkups = 0;
totalPriceMarkups = jsonSlurper.flightHotelPackage.productMarkup + jsonSlurper.flightHotelPackage.flights[0].productMarkup + productMarkupHotel + totalMarkupAncillaries + serviceFeeProductMarkup + totalMarkupSeat + totalMarkupTransfer;
log.info("------------------->totalPriceMarkups ("+ totalPriceMarkups +") = flightHotelPackage.productMarkup ("+ jsonSlurper.flightHotelPackage.productMarkup +") + flightHotelPackage.flights[0].productMarkup ("+ jsonSlurper.flightHotelPackage.flights[0].productMarkup +") + flightHotelPackage.hotels[0].productMarkup ("+ productMarkupHotel +") + totalMarkupAncillaries ("+ totalMarkupAncillaries +") + totalMarkupSeat ("+ totalMarkupSeat +") + totalMarkupTransfer ("+ totalMarkupTransfer +") + serviceFeeProductMarkup ("+ serviceFeeProductMarkup +")");


//ATOL
log.info("***ATOL***");
def priceAtol = 0;
def cardFeeAtol = 0;
def productMarkupAtol = 0;
if (jsonSlurper.atol!=null) {
	log.info("------------------->atol.amtMarketPrice: " + jsonSlurper.atol.amtMarketPrice * jsonSlurper.atol.quantity );
	priceAtol = jsonSlurper.atol.amtMarketPrice * jsonSlurper.atol.quantity;
	//cardFee
    if (jsonSlurper.atol.cardFee != null) cardFeeAtol = jsonSlurper.atol.cardFee;
    log.info("cardFeeAtol: " + cardFeeAtol);  
	//productMarkupAtol
    if (jsonSlurper.atol.productMarkup != null) productMarkupAtol = jsonSlurper.atol.productMarkup * jsonSlurper.atol.quantity;
    log.info("productMarkupAtol: " + productMarkupAtol); 
	//sumamos el markup al priceAtol
	priceAtol = priceAtol + productMarkupAtol;
	log.info("priceAtol+productMarkupAtol: " + priceAtol); 

}
else {
	log.info("------------------->ATOL is NULL, por tanto el priceAtol=0");
}

//Promocode
log.info("***promocode***");
def pricePromocode = 0;
def cardFeePromocode = 0;
if (jsonSlurper.promocode!=null) {
	pricePromocode = jsonSlurper.promocode.amtMarketPrice;
	log.info("------------------->promocode.amtMarketPrice: " + pricePromocode);
	
	//cardFee
    if (jsonSlurper.promocode.cardFee != null) cardFeePromocode = jsonSlurper.promocode.cardFee;
    log.info("cardFeePromocode: " + cardFeePromocode); 
}
else {
	log.info("------------------->promocode is NULL, por tanto el pricePromocode=0");
}

log.info("-------------------------------------------------------------------------------------");


//totalPrice (el que viene en la respuesta)
def totalPrice = jsonSlurper.totalPrice
asercion = (totalPrice!=null) ? "(totalPrice is not null" : assertionList.add("totalPrice is null")
log.info ("------------------->*** totalPrice: " + totalPrice )

//totalPriceCalculado (calculado a partir de las sumas de todo lo anterior)
def totalPriceCalculado = 0;
totalPriceCalculado = totalPackage + pairPriceFlightHotelPackage + pairPriceFlight + pairPriceHotel + totalPriceMarkups + amtMarketPriceSeat + pairPriceSeats + priceTransfer + pairPriceTransfers + amtMarketPriceServiceFee + pairPriceServiceFees + pricePromocode + priceAtol + amtMarketPriceAncillaries + pairPriceAncillaries + cardFeeFlight + cardFeeHotel + cardFeeServiceFee + cardFeeTransfer + cardFeeSeats + cardFeeAncillaries + cardFeeAtol + cardFeePromocode;
log.info ("------------------->*** totalPriceCalculado ("+totalPriceCalculado+") = totalFlightHotelPackage ("+totalPackage+")+ pairPriceFlightHotelPackage ("+pairPriceFlightHotelPackage+") + pairPriceFlight ("+pairPriceFlight+")+ pairPriceHotel ("+pairPriceHotel+") + productMarkups ("+totalPriceMarkups+") + seats ("+amtMarketPriceSeat+") + pairPriceSeats ("+pairPriceSeats+") + transfers ("+priceTransfer+") + pairPriceTransfers ("+pairPriceTransfers+") + serviceFee ("+amtMarketPriceServiceFee+") + pairPriceServiceFees ("+pairPriceServiceFees+") + pricePromocode ("+pricePromocode+") + priceAtol ("+priceAtol+") + amtMarketPriceAncillaries ("+amtMarketPriceAncillaries+") + pairPriceAncillaries ("+pairPriceAncillaries+") + cardFeeFlight ("+cardFeeFlight+") + cardFeeHotel ("+cardFeeHotel+") + cardFeeServiceFee ("+cardFeeServiceFee+") + cardFeeTransfer ("+cardFeeTransfer+") + cardFeeSeats ("+cardFeeSeats+") + cardFeeAncillaries ("+cardFeeAncillaries+") + cardFeeAtol ("+cardFeeAtol+") + cardFeePromocode ("+cardFeePromocode+")")


//ASERCION precios totales
asercion = (totalPrice == totalPriceCalculado) ? "El precio total "+totalPrice+" coincide con el calculado: "+totalPriceCalculado : assertionList.add("El precio total ("+totalPrice+") no coincide con el calculado ("+totalPriceCalculado+")")
if (totalPrice != totalPriceCalculado) {
	def diferencia = totalPriceCalculado-totalPrice;
	log.warn("*** La diferencia es de (totalPriceCalculado-totalPrice): " + diferencia);
	assertionList.add("*** La diferencia es de (totalPriceCalculado-totalPrice): " + diferencia);
}


//add all Err assertions in the log info
if (!assertionList.empty) {
	log.error("*********** lista final de errores ***********");
	log.error("-------------------------------------------------");
}
for (m in assertionList) {
    log.error( m);
}
assert assertionList.size() == 0