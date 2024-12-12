// ********RoostGPT********
/*
Test generated by RoostGPT for test javaSpringtestCases using AI Type Open AI and AI Model gpt-4
ROOST_METHOD_HASH=findByIsbn_69e61535d1
ROOST_METHOD_SIG_HASH=findByIsbn_e67363ceec
"""
  Scenario 1: Test to find a book by ISBN when the book exists in the repository
  Details:
    TestName: testFindBookByIsbnWhenBookExists
    Description: This test is meant to check the findByIsbn method when a book with the provided ISBN exists in the repository. The test targets the scenario where the book is present and should be successfully returned.
  Execution:
    Arrange: Mock the book repository to return a specific book object when findByIsbn is called with a specific ISBN.
    Act: Invoke the findByIsbn method with the specific ISBN.
    Assert: Use JUnit assertions to compare the actual book resource returned by the method against the expected book resource.
  Validation:
    The assertion aims to verify that the findByIsbn method correctly retrieves and returns the book resource associated with the provided ISBN. The expected result is the specific book resource, as it is the one associated with the provided ISBN in the mocked repository. This test is significant as it validates the core functionality of the findByIsbn method in retrieving books.
  Scenario 2: Test to find a book by ISBN when the book does not exist in the repository
  Details:
    TestName: testFindBookByIsbnWhenBookDoesNotExist
    Description: This test is meant to check the findByIsbn method when a book with the provided ISBN does not exist in the repository. The test targets the scenario where the book is not present and an error should be thrown.
  Execution:
    Arrange: Mock the book repository to return null when findByIsbn is called with a specific ISBN.
    Act: Invoke the findByIsbn method with the specific ISBN.
    Assert: Use JUnit assertions to expect an exception to be thrown.
  Validation:
    The assertion aims to verify that the findByIsbn method correctly throws an error when no book associated with the provided ISBN is found. The expected result is an exception, as no book with the provided ISBN exists in the mocked repository. This test is significant as it validates the error handling of the findByIsbn method when no book is found.
  Scenario 3: Test to find a book by ISBN when the ISBN provided is null
  Details:
    TestName: testFindBookByIsbnWhenIsbnIsNull
    Description: This test is meant to check the findByIsbn method when the ISBN provided is null. The test targets the scenario where the ISBN is null and an error should be thrown.
  Execution:
    Arrange: No arrangement is needed as no valid ISBN is provided.
    Act: Invoke the findByIsbn method with a null ISBN.
    Assert: Use JUnit assertions to expect an exception to be thrown.
  Validation:
    The assertion aims to verify that the findByIsbn method correctly throws an error when the provided ISBN is null. The expected result is an exception, as a null ISBN is not a valid input. This test is significant as it validates the error handling of the findByIsbn method when a null ISBN is provided.
"""
*/
// ********RoostGPT********
package com.baeldung.web.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import com.baeldung.model.Book;
import javax.persistence.EntityNotFoundException;
import com.baeldung.web.resource.BookResource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.baeldung.model.BookView;
import com.baeldung.persistence.BookRepository;
import com.baeldung.web.error.Checks;
import com.fasterxml.jackson.annotation.JsonView;
import org.junit.experimental.categories.Category;

@Category({ Categories.findByIsbn.class, Categories.addBookToCart.class })
@RunWith(MockitoJUnitRunner.class)
public class BookControllerFindByIsbnTest {

	@InjectMocks
	private BookController bookController;

	@Mock
	private BookRepository repo;

	@Test
	@Category(Categories.valid.class)
	public void testFindBookByIsbnWhenBookExists() {
		String isbn = "1234567890";
		Book mockBook = new Book();
		mockBook.setIsbn(isbn);
		when(repo.findByIsbn(isbn)).thenReturn(mockBook);
		BookResource actualBookResource = bookController.findByIsbn(isbn);
		assertEquals(new BookResource(mockBook), actualBookResource);
	}

	@Test(expected = EntityNotFoundException.class)
	@Category(Categories.invalid.class)
	public void testFindBookByIsbnWhenBookDoesNotExist() {
		String isbn = "1234567890";
		when(repo.findByIsbn(isbn)).thenReturn(null);
		bookController.findByIsbn(isbn);
	}

	@Test(expected = EntityNotFoundException.class)
	@Category(Categories.boundary.class)
	public void testFindBookByIsbnWhenIsbnIsNull() {
		String isbn = null;
		bookController.findByIsbn(isbn);
	}

}