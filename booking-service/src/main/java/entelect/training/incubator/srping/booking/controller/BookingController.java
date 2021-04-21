package entelect.training.incubator.srping.booking.controller;

import entelect.training.incubator.srping.booking.models.Booking;
import entelect.training.incubator.srping.booking.service.BookingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("bookings")
public class BookingController {
    private final Logger LOGGER = LoggerFactory.getLogger(BookingController.class);
    private final BookingService bookingService;
    private String bookings_found;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping
    public ResponseEntity<?> getBookings() {
        LOGGER.info("Fetching all bookings");
        List<Booking> bookings = bookingService.getBookings();
        if (bookings.isEmpty()) {
            LOGGER.info("No Booking found");
            return new ResponseEntity<>("No Booking found", HttpStatus.NOT_FOUND);
        } else {
            LOGGER.trace(bookings_found);
            return new ResponseEntity<>(bookings, HttpStatus.OK);
        }

    }
}
