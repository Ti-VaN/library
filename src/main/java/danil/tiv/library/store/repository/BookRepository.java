package danil.tiv.library.store.repository;

import danil.tiv.library.store.entities.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {

}
