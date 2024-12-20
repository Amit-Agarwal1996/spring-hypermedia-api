// ********RoostGPT********
/*
Test generated by RoostGPT for test javaSpringtestCases using AI Type Open AI and AI Model gpt-4
ROOST_METHOD_HASH=findAllSummary_5400856f42
ROOST_METHOD_SIG_HASH=findAllSummary_21abcdc927
"""
Scenario 1: Test to validate the list of books returned by the method findAllSummary.
Details:
  TestName: testFindAllSummaryReturnsCorrectList.
  Description: This test is meant to verify the functionality of the findAllSummary method. The test checks if the method returns the correct list of book summaries.
Execution:
  Arrange: Mock the BookRepository to return a list of books when findAll is called.
  Act: Invoke the findAllSummary method.
  Assert: Use JUnit assertions to check if the returned list matches the expected book summaries list.
Validation:
  The assertion aims to verify that the findAllSummary method correctly fetches all book summaries from the repository and maps them to a list of BookResource. This test is significant to ensure that the application can retrieve all book summaries correctly.
Scenario 2: Test to validate the response when no books are in the repository.
Details:
  TestName: testFindAllSummaryWithEmptyRepository.
  Description: This test checks if the findAllSummary method returns an empty list when there are no books in the repository.
Execution:
  Arrange: Mock the BookRepository to return an empty list when findAll is called.
  Act: Invoke the findAllSummary method.
  Assert: Use JUnit assertions to check if the returned list is empty.
Validation:
  The assertion aims to verify that the findAllSummary method returns an empty list when there are no books in the repository. This test is significant to ensure that the application handles the scenario correctly when no books are present.
Scenario 3: Test to validate the exception handling when the repository access fails.
Details:
  TestName: testFindAllSummaryWithRepositoryAccessFailure.
  Description: This test checks if the findAllSummary method throws an exception when there is a failure accessing the repository.
Execution:
  Arrange: Mock the BookRepository to throw an exception when findAll is called.
  Act: Invoke the findAllSummary method.
  Assert: Use JUnit assertions to check if the expected exception is thrown.
Validation:
  The assertion aims to verify that the findAllSummary method throws the appropriate exception when there is a failure accessing the repository. This test is significant to ensure that the application handles repository access failures correctly.
"""
*/
// ********RoostGPT********
package com.baeldung.web.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doThrow;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.experimental.categories.Category;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import com.baeldung.model.Book;
import com.baeldung.persistence.BookRepository;
import com.baeldung.web.error.Checks;
import com.baeldung.web.resource.BookResource;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.baeldung.model.BookView;
import com.fasterxml.jackson.annotation.JsonView;
import org.junit.experimental.categories.Category;

@Category({ Categories.findAllSummary.class, Categories.findAll.class })
@RunWith(MockitoJUnitRunner.class)
public class BookControllerFindAllSummaryTest {

	@InjectMocks
	private BookController bookController;

	@Mock
	private BookRepository repo;

	private Book book1;

	private Book book2;

	private List<Book> bookList;

	@Before
	public void setUp() {
		book1 = new Book("Author1", "Publisher1", "Summary1");
		book2 = new Book("Author2", "Publisher2", "Summary2");
		bookList = Arrays.asList(book1, book2);
	}

	@Test
    @Category(Categories.valid.class)
    public void testFindAllSummaryReturnsCorrectList() {
        when(repo.findAll()).thenReturn(bookList);
        List<BookResource> result = bookController.findAllSummary();
        assertEquals(2, result.size());
        assertEquals("123", result.get(0).getBook().getIsbn());
        assertEquals("456", result.get(1).getBook().getIsbn());
    }

	@Test
    @Category(Categories.valid.class)
    public void testFindAllSummaryWithEmptyRepository() {
        when(repo.findAll()).thenReturn(Collections.emptyList());
        List<BookResource> result = bookController.findAllSummary();
        assertEquals(0, result.size());
    }

	@Test(expected = RuntimeException.class)
	@Category(Categories.invalid.class)
	public void testFindAllSummaryWithRepositoryAccessFailure() {
		doThrow(new RuntimeException()).when(repo).findAll();
		bookController.findAllSummary();
	}

}