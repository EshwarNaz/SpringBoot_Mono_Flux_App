package in.eshwarnaz.rest;

import java.time.Duration;
import java.util.Date;
import java.util.stream.Stream;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import in.eshwarnaz.binding.CoustmerEvent;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

@RestController
public class CoustmerRestController {
	@GetMapping("/event")
	public Mono<CoustmerEvent> getCustomerEvent() {

		CoustmerEvent event = new CoustmerEvent("Smith", new Date());
		
		Mono<CoustmerEvent> mono = Mono.just(event);

		return mono;
	}	
	

	@GetMapping(value = "/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public ResponseEntity<Flux<CoustmerEvent>> getCustomerEvents() {

		// Creating Customer data in the form of object
		CoustmerEvent event = new CoustmerEvent("Smith", new Date());

		// Create Stream object to send the data
		Stream<CoustmerEvent> customerStream = Stream.generate(() -> event);

		// Create Flux object with Stream
		Flux<CoustmerEvent> customerFlux = Flux.fromStream(customerStream);

		// Setting Response Interval
		Flux<Long> interval = Flux.interval(Duration.ofSeconds(3));

		// Combine Flux Interval and Customer Flux
		Flux<Tuple2<Long, CoustmerEvent>> zip = Flux.zip(interval, customerFlux);

		// Getting Flux value from the zip
		Flux<CoustmerEvent> fluxMap = zip.map(Tuple2::getT2);

		// Returning Flux Response
		return new ResponseEntity<>(fluxMap, HttpStatus.OK);
	}
}
