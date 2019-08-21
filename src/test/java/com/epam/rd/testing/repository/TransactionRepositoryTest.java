package com.epam.rd.testing.repository;

import com.epam.rd.testing.SpringTests;
import com.epam.rd.testing.repository.entity.Transaction;
import com.epam.rd.testing.repository.enums.Currency;
import com.epam.rd.testing.repository.enums.TransactionStatus;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

@DataJpaTest
@TestExecutionListeners({
        TransactionalTestExecutionListener.class,
        DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
@DatabaseSetup(value = "/data.xml", type = DatabaseOperation.INSERT)
public class TransactionRepositoryTest extends SpringTests {

    @Autowired
    private TransactionRepository transactionRepository;

    @Test
    public void testTransactionRepositoryFindTransactionsByStatus() {
        Collection<Transaction> pendingTransactions =
                transactionRepository.findTransactionByTransactionStatus(TransactionStatus.PENDING);
        assertThat(pendingTransactions, hasSize(1));

        Collection<Transaction> completedTransactions =
                transactionRepository.findTransactionByTransactionStatus(TransactionStatus.COMPLETED);
        assertThat(completedTransactions, hasSize(2));
    }

    @Test
    public void testTransactionRepositoryFindTransactionsByProfile() {
        Collection<Transaction> pendingTransactions =
                transactionRepository.findByCurrencyOrderByLocalDateTimeAsc(Currency.EUR);
        assertThat(pendingTransactions, hasSize(2));
    }
}
