package entelect.training.incubator.srping.booking.repository;

import entelect.training.incubator.srping.booking.models.Booking;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends CrudRepository<Booking, Integer> {

    List<Booking> findByCustomerId(Integer customer_id);
    Optional<Booking> findByReferenceNumber(String reference_number);
}
