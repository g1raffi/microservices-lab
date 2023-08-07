package ch.puzzle.mm.kafka.stock.stock.control;

import ch.puzzle.mm.kafka.stock.exception.ArticleOutOfStockException;
import ch.puzzle.mm.kafka.stock.stock.entity.ArticleOrderDTO;
import ch.puzzle.mm.kafka.stock.stock.entity.ArticleStock;
import org.eclipse.microprofile.opentracing.Traced;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@Traced
@ApplicationScoped
public class ArticleStockService {

    private final Logger log = LoggerFactory.getLogger(ArticleStockService.class.getName());

    @Inject
    ArticleStockRepository articleStockRepository;

    @Transactional
    public void orderArticle(ArticleOrderDTO articleOrderDTO) throws ArticleOutOfStockException {
        ArticleStock articleStock = articleStockRepository.find("article_id", articleOrderDTO.articleId).singleResult();
        if (articleStock.getCount() < articleOrderDTO.amount)
            throw new ArticleOutOfStockException("Article with id " + articleOrderDTO.articleId + " is out of stock.");
        articleStock.setCount(articleStock.getCount() - articleOrderDTO.amount);
        log.info("Article with id {} processed", articleOrderDTO.articleId);
    }

    public void orderArticles(List<ArticleOrderDTO> articles) throws ArticleOutOfStockException {
        for (ArticleOrderDTO article : articles) {
            orderArticle(article);
        }
    }
}
