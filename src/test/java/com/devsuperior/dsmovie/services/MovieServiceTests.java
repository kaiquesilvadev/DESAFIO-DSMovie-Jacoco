package com.devsuperior.dsmovie.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.devsuperior.dsmovie.dto.MovieDTO;
import com.devsuperior.dsmovie.entities.MovieEntity;
import com.devsuperior.dsmovie.repositories.MovieRepository;
import com.devsuperior.dsmovie.tests.MovieFactory;

@ExtendWith(SpringExtension.class)
public class MovieServiceTests {

	@InjectMocks
	private MovieService service;

	@Mock
	private MovieRepository repository;

	private PageImpl<MovieEntity> pageImpl;
	private PageRequest pageRequest;
	private MovieDTO movieDTO;
	private MovieEntity movieEntity;

	@BeforeEach
	void setup() throws Exception {
		// var
		movieDTO = MovieFactory.createMovieDTO();
		movieEntity = MovieFactory.createMovieEntity();
		pageRequest = PageRequest.of(0, 12);
		pageImpl = new PageImpl<>(List.of(movieEntity), pageRequest, 1);

		// repository
		Mockito.when(repository.searchByTitle(any(), any())).thenReturn(pageImpl);
	}

	@DisplayName("findAll deve retornar PagedMovieDTO")
	@Test
	public void findAllShouldReturnPagedMovieDTO() {
		Page<MovieDTO> resultado = service.findAll("Test Movie", pageRequest);

		assertNotNull(resultado);
		assertNotNull(resultado.iterator().next().getId());
		assertNotNull(resultado.iterator().next().getScore());
		assertNotNull(resultado.iterator().next().getCount());
		assertNotNull(resultado.iterator().next().getImage());
		assertNotNull(resultado.iterator().next().getTitle());

	}

	@DisplayName("")
	@Test
	public void findByIdShouldReturnMovieDTOWhenIdExists() {
	}

	@DisplayName("")
	@Test
	public void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
	}

	@DisplayName("")
	@Test
	public void insertShouldReturnMovieDTO() {
	}

	@DisplayName("")
	@Test
	public void updateShouldReturnMovieDTOWhenIdExists() {
	}

	@DisplayName("")
	@Test
	public void updateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
	}

	@DisplayName("")
	@Test
	public void deleteShouldDoNothingWhenIdExists() {
	}

	@DisplayName("")
	@Test
	public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
	}

	@DisplayName("")
	@Test
	public void deleteShouldThrowDatabaseExceptionWhenDependentId() {
	}
}
