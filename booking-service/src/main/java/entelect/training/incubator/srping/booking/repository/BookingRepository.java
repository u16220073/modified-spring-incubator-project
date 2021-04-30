package entelect.training.incubator.srping.booking.repository;

import entelect.training.incubator.srping.booking.models.Booking;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends CrudRepository<Booking, Integer> {

    List<Booking> findByCustomerId(Integer customerId);
    List<Booking> findByReferenceNumber(String referenceNumber);
}
