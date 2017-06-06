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
def totalFlightLegCalculate = 0;
def priceFlight = 0;
def amtMarketPriceFlight = 0;
def totalPriceAncillaries = 0;
def amtMarketPriceAncillaries = 0;
def totalMarkupAncillaries = 0;
def priceSeat=0;
def amtMarketPriceSeat=0;
def totalMarkupSeat=0;


// Price CURRENCY
def currency=jsonSlurper.currency
asercion = (currency!=null) ? "(currency is not null" : assertionList.add("currency is null")
log.info ("Currency: " + currency )

// Price HOTEL
def hotel
hotel=jsonSlurper.flightHotelPackage.hotels[0].amtMarketPrice
asercion = (hotel!=null) ? "(Hotel amtMarketPrice is not null" : assertionList.add("Hotel amtMarketPrice is null")
log.info ("***HOTEL*** -------------------------------->amtMarketPrice Hotel: " + hotel )	


//numero de PAXES
log.info("***PAXES***");
paxes=0
p=0
asercion = (jsonSlurper.paxes[p]!=null) ? "paxes is not null" : assertionList.add("paxes is null")
while (jsonSlurper.paxes[p] != null ){
	personType = jsonSlurper.paxes[p].personType
	log.info("Pasajero["+p+"]. personType: " + jsonSlurper.paxes[p].personType);
	if (jsonSlurper.paxes[p].personType!="INF") {
		paxes++;
	}
	p++
}
log.info("Num. pasajeros (adultos y niños) contabilizados: " + paxes);


//asercion del precio total de los FLIGHTS
//flights. obtenemos los vuelos.
f=0	
asercion = (jsonSlurper.flightHotelPackage.flights[f]!=null) ? "flightHotelPackage.flights is not null" : assertionList.add("flightHotelPackage.flights is null")
while (jsonSlurper.flightHotelPackage.flights[f] != null ){
   //obtenemos el precio del flight[f]
   asercion = (jsonSlurper.flightHotelPackage.flights[f].price!=null) ? "flightHotelPackage.flights["+f+"].price is not null" : assertionList.add("flightHotelPackage.flights["+f+"].price is null")
   priceFlight = jsonSlurper.flightHotelPackage.flights[f].price;
   amtMarketPriceFlight = jsonSlurper.flightHotelPackage.flights[f].amtMarketPrice;
   log.info("***FLIGHTS*** -------------------------------->flights["+f+"].amtMarketPriceFlight: " + amtMarketPriceFlight)
   //flightSegments. obtenemos los flightSegments del flight[f]
   i=0	
   asercion = (jsonSlurper.flightHotelPackage.flights[f].flightSegments[i]!=null) ? "flightHotelPackage.flights.flightSegments["+i+"] is not null" : assertionList.add("flightHotelPackage.flights.flightSegments["+i+"] is null")
   while (jsonSlurper.flightHotelPackage.flights[f].flightSegments[i] != null ){
		//flightLegs. obtenemos los flightLegs del flightSegments[i] del flight[f]	
		def flightLegPrice = 0;
		s=0
		asercion = (jsonSlurper.flightHotelPackage.flights[f].flightSegments[i].flightLegs[s]!=null) ? "flightHotelPackage.flights["+f+"].flightSegments["+i+"].flightLegs["+s+"] is not null" : assertionList.add("flightHotelPackage.flights["+f+"].flightSegments["+i+"].flightLegs["+s+"] is null")
		while (jsonSlurper.flightHotelPackage.flights[f].flightSegments[i].flightLegs[s] != null ){		
			//obtenemos el precio del flightLegs[s]
			asercion = (jsonSlurper.flightHotelPackage.flights[f].flightSegments[i].flightLegs[s].flightLegPrice!=null) ? "flightHotelPackage.flights["+f+"].flightSegments["+i+"].flightLegs["+s+"].flightLegPrice is not null" : assertionList.add("flightHotelPackage.flights["+f+"].flightSegments["+i+"].flightLegs["+s+"].flightLegPrice is null")	
			flightLegPrice = jsonSlurper.flightHotelPackage.flights[f].flightSegments[i].flightLegs[s].flightLegPrice;			
			
			
			//Seats 
			log.info("***seats*** (vuelo "+f+" segmento "+i+" leg "+s+")");		
			t=0
			if (jsonSlurper.flightHotelPackage.flights[f].flightSegments[i].flightLegs[s].selectedSeats!=null) {
				while (jsonSlurper.flightHotelPackage.flights[f].flightSegments[i].flightLegs[s].selectedSeats[t] != null ){
					//Price seat
					priceSeat= priceSeat + jsonSlurper.flightHotelPackage.flights[f].flightSegments[i].flightLegs[s].selectedSeats[t].price
					amtMarketPriceSeat = amtMarketPriceSeat + jsonSlurper.flightHotelPackage.flights[f].flightSegments[i].flightLegs[s].selectedSeats[t].amtMarketPrice
					asercion = (priceSeat!=null) ? "" : assertionList.add("priceSeat is null")
					
					//productMarkup seat (no es un error que sea null)
					def productMarkupSeat = 0;
					if (jsonSlurper.flightHotelPackage.flights[f].flightSegments[i].flightLegs[s].selectedSeats[t].productMarkup!=null)
						productMarkupSeat = jsonSlurper.flightHotelPackage.flights[f].flightSegments[i].flightLegs[s].selectedSeats[t].productMarkup;
					totalMarkupSeat = totalMarkupSeat + productMarkupSeat;
					
					log.info("seat["+t+"]. price = "+jsonSlurper.flightHotelPackage.flights[f].flightSegments[i].flightLegs[s].selectedSeats[t].price+ ", productMarkup = "+jsonSlurper.flightHotelPackage.flights[f].flightSegments[i].flightLegs[s].selectedSeats[t].productMarkup);
					t++;
				}
			}
			log.info("------------------->totalPriceSeat = "+ priceSeat +" ,totalMarkupSeat = "+totalMarkupSeat);


			//ancillaries
			log.info("***anc***   (vuelo "+f+" segmento "+i+" leg "+s+")");
			a=0
			if (jsonSlurper.flightHotelPackage.flights[f].flightSegments[i].flightLegs[s].selectedAncillaries!=null) {
				while (jsonSlurper.flightHotelPackage.flights[f].flightSegments[i].flightLegs[s].selectedAncillaries[a] != null ){

					log.info("ancillarie["+a+"]. code: ("+ jsonSlurper.flightHotelPackage.flights[f].flightSegments[i].flightLegs[s].selectedAncillaries[a].code +"), precio: ("+ jsonSlurper.flightHotelPackage.flights[f].flightSegments[i].flightLegs[s].selectedAncillaries[a].price +"), productMarkup: ("+ jsonSlurper.flightHotelPackage.flights[f].flightSegments[i].flightLegs[s].selectedAncillaries[a].productMarkup +"), quantity: ("+ jsonSlurper.flightHotelPackage.flights[f].flightSegments[i].flightLegs[s].selectedAncillaries[a].quantity +"), flightLegReference: ("+ jsonSlurper.flightHotelPackage.flights[f].flightSegments[i].flightLegs[s].selectedAncillaries[a].flightLegReference +")");
						
					asercion = (jsonSlurper.flightHotelPackage.flights[f].flightSegments[i].flightLegs[s].selectedAncillaries[a].price!=null) ? "selectedAncillaries["+a+"].price is not null" : assertionList.add("selectedAncillaries.price is null")
					if (asercion==true) log.error("selectedAncillaries["+a+"].price es NULL ");
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
					totalPriceAncillaries = totalPriceAncillaries + (jsonSlurper.flightHotelPackage.flights[f].flightSegments[i].flightLegs[s].selectedAncillaries[a].price * jsonSlurper.flightHotelPackage.flights[f].flightSegments[i].flightLegs[s].selectedAncillaries[a].quantity)
					amtMarketPriceAncillaries = amtMarketPriceAncillaries + (jsonSlurper.flightHotelPackage.flights[f].flightSegments[i].flightLegs[s].selectedAncillaries[a].amtMarketPrice * jsonSlurper.flightHotelPackage.flights[f].flightSegments[i].flightLegs[s].selectedAncillaries[a].quantity)
					a++;
				}
			}
			log.info("------------------->amtMarketPriceAncillaries = "+ amtMarketPriceAncillaries +" ,totalMarkupAncillaries = "+totalMarkupAncillaries);

			
			
			//paxFees. obtenemos los paxFees del flightLegs[s] del flightSegments[i] del flight[f]
			def precioPaxFee = 0;
			log.info("***paxFee***   (vuelo "+f+" segmento "+i+" leg "+s+")");
			j=0
			asercion = (jsonSlurper.flightHotelPackage.flights[f].flightSegments[i].flightLegs[s].paxFees[j]!=null) ? "flightHotelPackage.flights["+f+"].flightSegments["+i+"].flightLegs["+s+"].paxFees["+j+"] is not null" : assertionList.add("flightHotelPackage.flights["+f+"].flightSegments["+i+"].flightLegs["+s+"].paxFees["+j+"] is null")
			while (jsonSlurper.flightHotelPackage.flights[f].flightSegments[i].flightLegs[s].paxFees[j] != null ){	
				//obtenemos los precios de los flightlegs por cada pasajero
				asercion = (jsonSlurper.flightHotelPackage.flights[f].flightSegments[i].flightLegs[s].paxFees[j].price!=null) ? "flightHotelPackage.flights["+f+"].flightSegments["+i+"].flightLegs["+s+"].paxFees["+j+"].price is not null" : assertionList.add("flightHotelPackage.flights["+f+"].flightSegments["+i+"].flightLegs["+s+"].paxFees["+j+"].price is null")
				log.info ("flight["+f+"].flightSegments["+i+"].flightLegs["+s+"].paxFees["+j+"].price: " + jsonSlurper.flightHotelPackage.flights[f].flightSegments[i].flightLegs[s].paxFees[j].price )			
				precioPaxFee = precioPaxFee + jsonSlurper.flightHotelPackage.flights[f].flightSegments[i].flightLegs[s].paxFees[j].price
				j++;
			}
			log.info("Hay coincidencia:  flightLegPrice="+flightLegPrice+ " / precioPaxFee="+precioPaxFee);
			
			//sumamos el precio del flightLeg por desglose al acumulado
			totalFlightLegCalculate = totalFlightLegCalculate + precioPaxFee;	

			//comprobamos que el precio del flightLeg coincide con el de los desglosados
			asercion = (flightLegPrice == precioPaxFee) ? "El precio total del leg "+flightLegPrice+" coincide con el desglosado: "+precioPaxFee : assertionList.add("*** El precio total del leg ("+flightLegPrice+") no coincide con el desglosado ("+precioPaxFee+")")
			if (flightLegPrice != precioPaxFee) {
				log.warn("*** No coincide la suma de los precios desglosados por pasajero con la del flightLeg");
			}

			s++			
		}
		i++
	}
	log.info("------------------->Precio total de los legs del flight["+f+"] (sin flightFees): " + totalFlightLegCalculate)
	
   //flightFees. obtenemos los flightFees del flight[f]
   i=0	
   def precioFlightFees = 0;
   asercion = (jsonSlurper.flightHotelPackage.flights[f].flightFees[i]!=null) ? "flightHotelPackage.flights.flightFees["+i+"] is not null" : assertionList.add("flightHotelPackage.flights.flightFees["+i+"] is null")
   while (jsonSlurper.flightHotelPackage.flights[f].flightFees[i] != null ){
		//obtenemos el precio del flightFees[i]
							
		//SSC nos lo saltamos pq ya lo contamos en los asientos.
		if (jsonSlurper.flightHotelPackage.flights[f].flightFees[i].code=='SSC') {
			log.info ("-flight["+f+"].flightFees["+i+"]. Code: " + jsonSlurper.flightHotelPackage.flights[f].flightFees[i].code + " - Precio: " + jsonSlurper.flightHotelPackage.flights[f].flightFees[i].price + " (NO LO TENEMOS EN CUENTA EN LA SUMA, ya sumamos los seats por separado!).")			
			if (jsonSlurper.flightHotelPackage.flights[f].flightFees[i].price==priceSeat) {
				log.info("Correcto. totalPriceSeat coincide con el del SSC")
			}
			else { 
				assertionList.add("*** priceSeat ("+priceSeat+") no coincide con el precio del SSC ("+jsonSlurper.flightHotelPackage.flights[f].flightFees[i].price+")"); 
				log.warn("totalPriceSeat NO coincide con el del SSC");
			}
			i++;
			continue;
		}
					
		asercion = (jsonSlurper.flightHotelPackage.flights[f].flightFees[i].price!=null) ? "flightHotelPackage.flights["+f+"].flightFees["+i+"].price is not null" : assertionList.add("flightHotelPackage.flights["+f+"].flightFees["+i+"].price is null")
		precioFlightFees = precioFlightFees + jsonSlurper.flightHotelPackage.flights[f].flightFees[i].price;
		log.info ("-flight["+f+"].flightFees["+i+"]. Code: " + jsonSlurper.flightHotelPackage.flights[f].flightFees[i].code + " - Precio: " + jsonSlurper.flightHotelPackage.flights[f].flightFees[i].price)			
		i++
	}
	
	//sumamos al precio calculado del vuelo el precio de los fees
	totalFlightCalculate = totalFlightLegCalculate + precioFlightFees + totalPriceAncillaries;

	//resumen de precios y comparativa entre el precio del flight[f] informado y el que hemos calculado nosotros
	log.info("------------------->Precio total informado (payload) del flight["+f+"]: " + priceFlight);
	log.info("------------------->Precio total calculado del flight["+f+"] (leg ("+totalFlightLegCalculate+") + fees ("+precioFlightFees+") + anc ("+totalPriceAncillaries+") ): " + totalFlightCalculate);
	asercion = (priceFlight == totalFlightCalculate) ? "El precio total "+priceFlight+" del flight[" + f + "] coincide con el calculado: "+totalFlightCalculate : assertionList.add("El precio total ("+priceFlight+") del vuelo " + f + " no coincide con el calculado ("+totalFlightCalculate+")")
	if (priceFlight != totalFlightCalculate) 
		log.error("*** El precio total ("+priceFlight+") del vuelo " + f + " no coincide con el calculado ("+totalFlightCalculate+")");
	
	f++
}


//Total Package
def totalPackage
totalPackage = amtMarketPriceFlight + hotel + amtMarketPriceAncillaries;
log.info("***Total Package*** -------------------------> totalPackage: " + totalPackage + "  (amtMarketPriceFlight "+amtMarketPriceFlight+" + hotel "+hotel+" + amtMarketPriceAncillaries " + amtMarketPriceAncillaries + " )");
if (totalPackage == jsonSlurper.flightHotelPackage.amtMarketPrice) {
	log.info("El amtMarketPrice total del flightHotelPackage "+jsonSlurper.flightHotelPackage.amtMarketPrice+" coincide con el calculado: "+totalPackage)
}
else { 
	assertionList.add("*** El amtMarketPrice total del flightHotelPackage "+jsonSlurper.flightHotelPackage.amtMarketPrice+" NO coincide con el calculado: "+totalPackage)
	log.warn("El amtMarketPrice total del flightHotelPackage "+jsonSlurper.flightHotelPackage.amtMarketPrice+" NO coincide con el calculado: "+totalPackage)
}


//Transfers
log.info("***transfers***");
s=0
def priceTransfer=0, totalMarkupTransfer=0
while (jsonSlurper.transfers[s] != null ){
    //price transfer
	priceTransfer= priceTransfer + jsonSlurper.transfers[s].price
	asercion = (priceTransfer!=null) ? "" : assertionList.add("priceTransfer is null")
	log.info ("------------------->Price TRansfers: " + priceTransfer )	

	//markup transfer
	log.info("------------------->Transfer productMarkup: " + jsonSlurper.transfers[s].productMarkup);
	asercion = (jsonSlurper.transfers[s].productMarkup!=null) ? "transfers productMarkup is not null" : assertionList.add("transfers productMarkup is null")	
	totalMarkupTransfer = totalMarkupTransfer + jsonSlurper.transfers[s].productMarkup

    s++;
}


//markups
log.info("***markups***");
def totalPriceMarkups = 0;
//mpk
asercion = (jsonSlurper.flightHotelPackage.productMarkup!=null) ? "flightHotelPackage.productMarkup is not null" : assertionList.add("flightHotelPackage.productMarkup is null")
log.info("flightHotelPackage.productMarkup: " + jsonSlurper.flightHotelPackage.productMarkup);
//mflight
asercion = (jsonSlurper.flightHotelPackage.flights[0].productMarkup!=null) ? "flightHotelPackage.flights[0].productMarkup is not null" : assertionList.add("flightHotelPackage.flights[0].productMarkup is null")
log.info("flightHotelPackage.flights[0].productMarkup: " + jsonSlurper.flightHotelPackage.flights[0].productMarkup);
//mhotel
def mhotel = 0;
if (jsonSlurper.flightHotelPackage.hotels[0] != null) {
  if (jsonSlurper.flightHotelPackage.hotels[0].productMarkup != null) {
	mhotel = jsonSlurper.flightHotelPackage.hotels[0].productMarkup;
  }
  asercion = (jsonSlurper.flightHotelPackage.hotels[0].productMarkup!=null) ? "flightHotelPackage.hotels[0].productMarkup is not null" : assertionList.add("flightHotelPackage.hotels[0].productMarkup is null")
  log.info("flightHotelPackage.hotels[0].productMarkup: " + mhotel );
}


//serviceFee
log.info("***serviceFee***");
def priceFee = 0;
def serviceFeeProductMarkup = 0;
if (jsonSlurper.serviceFees!=null) {
	s=0
	while (jsonSlurper.serviceFees[s] != null ){
		log.info("serviceFees["+s+"].amtMarketPrice: " + jsonSlurper.serviceFees[s].amtMarketPrice);
		priceFee = priceFee + jsonSlurper.serviceFees[s].amtMarketPrice;
		log.info("serviceFees["+s+"].productMarkup: " + jsonSlurper.serviceFees[s].productMarkup);
		serviceFeeProductMarkup = serviceFeeProductMarkup + jsonSlurper.serviceFees[s].productMarkup;
		s++;
	}
	log.info("------------------->total priceFee = "+ priceFee +" ,total serviceFeeProductMarkup = "+serviceFeeProductMarkup);
}

//totalPriceMarkups
totalPriceMarkups = jsonSlurper.flightHotelPackage.productMarkup + jsonSlurper.flightHotelPackage.flights[0].productMarkup + mhotel + totalMarkupAncillaries + serviceFeeProductMarkup + totalMarkupSeat + totalMarkupTransfer;
log.info("------------------->totalPriceMarkups ("+ totalPriceMarkups +") = flightHotelPackage.productMarkup ("+ jsonSlurper.flightHotelPackage.productMarkup +") + flightHotelPackage.flights[0].productMarkup ("+ jsonSlurper.flightHotelPackage.flights[0].productMarkup +") + flightHotelPackage.hotels[0].productMarkup ("+ mhotel +") + totalMarkupAncillaries ("+ totalMarkupAncillaries +") + totalMarkupSeat ("+ totalMarkupSeat +") + totalMarkupTransfer ("+ totalMarkupTransfer +") + serviceFeeProductMarkup ("+ serviceFeeProductMarkup +")");


//ATOL
log.info("***ATOL***");
def priceAtol = 0;
if (jsonSlurper.atol!=null) {
	log.info("------------------->atol.price: " + jsonSlurper.atol.price * jsonSlurper.atol.quantity );
	priceAtol = jsonSlurper.atol.price * jsonSlurper.atol.quantity;
}
else {
	log.info("------------------->ATOL is NULL, por tanto el priceAtol=0");
}

//pricePromocode
log.info("***promocode***");
def pricePromocode = 0;
if (jsonSlurper.promocode!=null) {
	pricePromocode = jsonSlurper.promocode.price;
	log.info("------------------->promocode.price: " + pricePromocode);
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
totalPriceCalculado = totalPackage + totalPriceMarkups + amtMarketPriceSeat + priceTransfer + priceFee + pricePromocode + priceAtol;
log.info ("------------------->*** totalPriceCalculado ("+totalPriceCalculado+") = totalPackage ("+totalPackage+") + totalPriceMarkups ("+totalPriceMarkups+") + amtMarketPriceSeat ("+amtMarketPriceSeat+") + priceTransfer ("+priceTransfer+") + priceFee ("+priceFee+") + pricePromocode ("+pricePromocode+") + priceAtol ("+priceAtol+")" )

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