package com.devsuperior.dsmovie.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.devsuperior.dsmovie.dto.MovieDTO;
import com.devsuperior.dsmovie.entities.MovieEntity;
import com.devsuperior.dsmovie.repositories.MovieRepository;
import com.devsuperior.dsmovie.services.exceptions.ResourceNotFoundException;
import com.devsuperior.dsmovie.tests.MovieFactory;

import jakarta.persistence.EntityNotFoundException;

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

	private Long idExistente, idInexistente , idDependente;

	@BeforeEach
	void setup() throws Exception {
		// var
		idExistente = 1L;
		idInexistente = 1000L;

		movieDTO = MovieFactory.createMovieDTO();
		movieEntity = MovieFactory.createMovieEntity();
		pageRequest = PageRequest.of(0, 12);
		pageImpl = new PageImpl<>(List.of(movieEntity), pageRequest, 1);

		// repository
		Mockito.when(repository.searchByTitle(any(), any())).thenReturn(pageImpl);

		Mockito.when(repository.findById(idExistente)).thenReturn(Optional.of(movieEntity));
		Mockito.when(repository.findById(idInexistente)).thenReturn(Optional.empty());

		Mockito.when(repository.save(any())).thenReturn(movieEntity);
		
		Mockito.when(repository.getReferenceById(idExistente)).thenReturn(movieEntity);
		Mockito.when(repository.getReferenceById(idInexistente)).thenThrow(EntityNotFoundException.class);

		Mockito.when(repository.existsById(idExistente)).thenReturn(true);
		Mockito.when(repository.existsById(idInexistente)).thenReturn(false);
		Mockito.when(repository.existsById(idDependente)).thenReturn(true);
		
		Mockito.doNothing().when(repository).deleteById(idExistente);
		Mockito.doThrow(DataIntegrityViolationException.class).when(repository).deleteById(idDependente);
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

	@DisplayName("findById deve retornar MovieDTO quando o ID existir")
	@Test
	public void findByIdShouldReturnMovieDTOWhenIdExists() {

		MovieDTO resultado = service.findById(idExistente);

		assertNotNull(resultado);
		assertNotNull(resultado.getId());
		assertEquals(resultado.getId(), idExistente);

	}

	@DisplayName("findById deve lançar ResourceNotFoundException quando o ID não existe")
	@Test
	public void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {

		assertThrows(ResourceNotFoundException.class, () -> {
			@SuppressWarnings("unused")
			MovieDTO resultado = service.findById(idInexistente);
		});
	}

	@DisplayName("insert deve retornar MovieDTO")
	@Test
	public void insertShouldReturnMovieDTO() {
		
		MovieDTO resultado = service.insert(movieDTO);
		
		assertNotNull(resultado);
		assertNotNull(resultado.getId());
		assertEquals(resultado.getId(), idExistente);
		
	}

	@DisplayName("update deve retornar MovieDTO quando o ID existir")
	@Test
	public void updateShouldReturnMovieDTOWhenIdExists() {
		
		MovieDTO resultado = service.update(idExistente, movieDTO);
		
		assertNotNull(resultado);
		assertNotNull(resultado.getId());
		assertEquals(resultado.getId(), idExistente);
	}

	@DisplayName("update deve lançar ResourceNotFoundException quando o ID não existe")
	@Test
	public void updateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
		
		assertThrows(ResourceNotFoundException.class, () -> {
			@SuppressWarnings("unused")
			MovieDTO resultado = service.update(idInexistente, movieDTO);
		});
	}

	@DisplayName("delete Não deve fazer nada quando o ID existir")
	@Test
	public void deleteShouldDoNothingWhenIdExists() {
		
		assertDoesNotThrow(() -> {
			
			service.delete(idExistente);
		});
	}

	@DisplayName("delete deve lançar ResourceNotFoundException quando o ID não existir")
	@Test
	public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
	}

	@DisplayName("delete deve lançar DatabaseException quando ID dependente")
	@Test
	public void deleteShouldThrowDatabaseExceptionWhenDependentId() {
	}
}
