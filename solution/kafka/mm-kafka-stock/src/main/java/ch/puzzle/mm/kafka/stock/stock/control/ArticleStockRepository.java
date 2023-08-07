package ch.puzzle.mm.kafka.stock.stock.control;

import ch.puzzle.mm.kafka.stock.stock.entity.ArticleStock;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.eclipse.microprofile.opentracing.Traced;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@Traced
public class ArticleStockRepository implements PanacheRepository<ArticleStock> {
}
