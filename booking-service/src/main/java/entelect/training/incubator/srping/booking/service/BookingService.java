package entelect.training.incubator.srping.booking.service;

import entelect.training.incubator.srping.booking.models.Booking;
import entelect.training.incubator.srping.booking.models.BookingSearchRequest;
import entelect.training.incubator.srping.booking.models.SearchType;
import entelect.training.incubator.srping.booking.repository.BookingRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Supplier;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public Booking makeBooking(Booking newBooking){
        return bookingRepository.save(newBooking);
    }

    public List<Booking> getBookings(){
        Iterable<Booking> bookingIterable = bookingRepository.findAll();
        List<Booking> bookings = new ArrayList<>();
        bookingIterable.forEach(bookings::add);
        return bookings;
    }

    public Booking getBooking(Integer id){
        Optional<Booking> bookingOptional = bookingRepository.findById(id);
        return bookingOptional.orElse(null);
    }

    public List<Booking> searchBookings(BookingSearchRequest searchRequest){

        Map<SearchType, Supplier<?>> searchStrategies = new HashMap<>();

        searchStrategies.put(SearchType.CUSTOMER_ID_SEARCH, ()-> bookingRepository
                .findByCustomerId(searchRequest.getCustomerId()));

        searchStrategies.put(SearchType.REFERENCE_NUMBER_SEARCH, ()-> bookingRepository
                .findByReferenceNumber(searchRequest.getReferenceNumber()));
        List<Booking> bookings = (List<Booking>) searchStrategies.get(searchRequest.getSearchType()).get();
        return bookings;
    }

    public List<Booking> getBookingsByCustomerId(Integer customer_id){
        Iterable<Booking> bookingIterable = bookingRepository.findByCustomerId(customer_id);
        List<Booking> bookings = new ArrayList<>();
        bookingIterable.forEach(bookings::add);
        return bookings;
    }

//    public Booking getBookingByReferenceNumber(String reference_number){
//        Optional<Booking> bookingOptional = bookingRepository.findByReferenceNumber(reference_number);
//        return bookingOptional.orElse(null);
//    }

}
