package study.guilhermerodrigues17.study_libraryapi.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TransactionsTest {

    @Autowired
    TransactionService transactionService;

    @Test
    void simpleTransactionTest() {
        transactionService.executeTransaction();
    }
}