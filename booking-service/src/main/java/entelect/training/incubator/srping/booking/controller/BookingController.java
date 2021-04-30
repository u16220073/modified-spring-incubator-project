package entelect.training.incubator.srping.booking.controller;

import entelect.training.incubator.srping.booking.models.Booking;
import entelect.training.incubator.srping.booking.models.BookingSearchRequest;
import entelect.training.incubator.srping.booking.service.BookingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("bookings")
public class BookingController {
    private final Logger LOGGER = LoggerFactory.getLogger(BookingController.class);
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping
    public ResponseEntity<?> getAllBookings() {
        LOGGER.info("Fetching all bookings");
        List<Booking> bookings = bookingService.getBookings();
        if (bookings.isEmpty()) {
            LOGGER.info("No Booking found!");
            return new ResponseEntity<>("No Booking found", HttpStatus.NOT_FOUND);
        } else {
            LOGGER.trace("Booking found!");
            return new ResponseEntity<>(bookings, HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity<?> makeBooking(@RequestBody Booking newBooking) {
        try {
            LOGGER.info("Processing booking creation request for booking={}", newBooking);
            newBooking.setReferenceNumber(generateReferenceNumber());
            newBooking.setBookingDate(new Date() );
            final Booking savedBooking = bookingService.makeBooking(newBooking);

            LOGGER.trace("Booking made!");
            return new ResponseEntity<>(savedBooking, HttpStatus.CREATED);
        } catch (Exception exception) {
            return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
        }
    }

    private String generateReferenceNumber() {
        String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String numbers = "0123456789";
        StringBuilder reference = new StringBuilder(7);

        for (int char_index = 0; char_index < 3; char_index++) {
            int index = (int) (letters.length() * Math.random());
            reference.append(letters.charAt(index));
        }

        for (int digit_index = 0; digit_index < 4; digit_index++) {
            int index = (int) (numbers.length() * Math.random());
            reference.append(numbers.charAt(index));
        }
        return reference.toString();
    }


    @GetMapping("{id}")
    public ResponseEntity<?> getBookingById(@PathVariable Integer id) {

        try {
            LOGGER.info("Processing booking creation request for id={}", id);
            Booking booking = this.bookingService.getBooking(id);

            if (booking != null) {
                LOGGER.trace("Booking Found!");
                return new ResponseEntity<>(booking, HttpStatus.OK);
            } else {
                LOGGER.trace("No Booking Found!");
                return new ResponseEntity<>("No Booking Found!", HttpStatus.NOT_FOUND);
            }
        } catch (Exception exception) {
            return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/search")
    public ResponseEntity<?> searchBooking(@RequestBody BookingSearchRequest searchRequest) {
        try {
            LOGGER.info("Processing booking search request for request {}", searchRequest);

            List<Booking> bookings = bookingService.searchBookings(searchRequest);
            if (!bookings.isEmpty()) {
                LOGGER.trace("Booking(s) Found!");
                return new ResponseEntity<>(bookings, HttpStatus.OK);
            } else {
                LOGGER.trace("No Booking(s) Found!");
                return new ResponseEntity<>("No Booking(s) Found!", HttpStatus.NOT_FOUND);
            }
        } catch (Exception exception) {
            return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
        }
    }
}
