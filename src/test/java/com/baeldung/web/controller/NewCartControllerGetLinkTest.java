// ********RoostGPT********
/*
Test generated by RoostGPT for test javaSpringtestCases using AI Type Open AI and AI Model gpt-4
ROOST_METHOD_HASH=getLink_d8e67d7db3
ROOST_METHOD_SIG_HASH=getLink_f75e3a4a49
"""
Scenario 1: Validate the generation of the correct link for a given book
Details:
  TestName: testGetLinkForValidBook.
  Description: This test aims to verify that the getLink method correctly generates a link for a valid book object.
Execution:
  Arrange: Create a mock Book object with a valid ISBN.
  Act: Call the getLink method with the mock Book object as the parameter.
  Assert: Compare the returned link with the expected link.
Validation:
  The assertion checks if the generated link matches the expected link, which should include the book's ISBN. This test is crucial to ensure that the book links are correctly formed for valid books.
Scenario 2: Validate the handling of null book object
Details:
  TestName: testGetLinkForNullBook.
  Description: This test aims to verify that the getLink method correctly handles a null book object.
Execution:
  Arrange: Pass a null Book object.
  Act: Call the getLink method with the null Book object as the parameter.
  Assert: Expect a NullPointerException or a custom exception indicating that the book object cannot be null.
Validation:
  The assertion checks if a NullPointerException or a custom exception is thrown when a null book object is passed. This test is crucial to ensure that the method can handle null inputs gracefully.
Scenario 3: Validate the handling of a book object with null ISBN
Details:
  TestName: testGetLinkForBookWithNullIsbn.
  Description: This test aims to verify that the getLink method correctly handles a book object with a null ISBN.
Execution:
  Arrange: Create a mock Book object with a null ISBN.
  Act: Call the getLink method with the mock Book object as the parameter.
  Assert: Expect a NullPointerException or a custom exception indicating that the ISBN cannot be null.
Validation:
  The assertion checks if a NullPointerException or a custom exception is thrown when a book object with a null ISBN is passed. This test is crucial to ensure that the method can handle book objects with null ISBNs gracefully.
"""
*/
// ********RoostGPT********
package com.baeldung.web.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.hateoas.Link;
import com.baeldung.model.Book;
import com.baeldung.web.error.Checks;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.baeldung.persistence.BookRepository;
import com.baeldung.web.resource.BookResource;
import com.baeldung.web.resource.CartResource;
import com.baeldung.web.resource.NewCartResource;
import jersey.repackaged.com.google.common.collect.Lists;
import org.junit.experimental.categories.Category;

@Category({ Categories.getLink.class })
public class NewCartControllerGetLinkTest {

	private NewCartController newCartController = new NewCartController();

	@Test
	@Category(Categories.valid.class)
	public void testGetLinkForValidBook() {
		// Arrange
		Book mockBook = mock(Book.class);
		when(mockBook.getIsbn()).thenReturn("1234567890");
		// Act
		Link actualLink = newCartController.getLink(mockBook);
		// Assert
		Link expectedLink = linkTo(BookController.class).slash(mockBook.getIsbn()).withSelfRel();
		assertEquals(expectedLink, actualLink);
	}

	@Test(expected = NullPointerException.class)
	@Category(Categories.invalid.class)
	public void testGetLinkForNullBook() {
		// Arrange
		Book mockBook = null;
		// Act
		newCartController.getLink(mockBook);
		// Assert
		// Expect a NullPointerException to be thrown
	}

	@Test(expected = Checks.EntityNotFoundException.class)
	@Category(Categories.invalid.class)
	public void testGetLinkForBookWithNullIsbn() {
		// Arrange
		Book mockBook = mock(Book.class);
		when(mockBook.getIsbn()).thenReturn(null);
		// Act
		newCartController.getLink(mockBook);
		// Assert
		// Expect a Checks.EntityNotFoundException to be thrown
	}

}