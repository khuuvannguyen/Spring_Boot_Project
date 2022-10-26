package com.vannguyen.SpringBootProject.domain.services;

import com.vannguyen.SpringBootProject.application.responses.CategoryResponse;
import com.vannguyen.SpringBootProject.configurations.exceptions.ResourceNotFoundException;
import com.vannguyen.SpringBootProject.domain.repositories.CategoryRepository;
import com.vannguyen.SpringBootProject.domain.services.implementations.CategoryService;
import com.vannguyen.SpringBootProject.fakeDatas.fakeData;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CategoryServiceTest {

    @Mock
    CategoryRepository _repoMock;

    @InjectMocks
    CategoryService _serviceMock;

    private final String CATEGORY_1 = "Category 1";
    private final String CATEGORY_2 = "Category 2";

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void get_returnList() {
        given(_repoMock.findAll()).willReturn(fakeData.getCategoryList());

        List<CategoryResponse> responses = _serviceMock.get();

        Assertions.assertThat(responses).isNotNull();
        Assertions.assertThat(responses.get(0).getName()).isEqualTo(this.CATEGORY_1);
        Assertions.assertThat(responses.get(1).getName()).isEqualTo(this.CATEGORY_2);
    }

    @Test
    public void get_returnEmptyList() {
        given(_repoMock.findAll()).willReturn(new ArrayList<>());

        List<CategoryResponse> responses = _serviceMock.get();

        Assertions.assertThat(responses).isNullOrEmpty();
    }

    @Test
    public void getById_returnObject() {
        given(_repoMock.findById(ArgumentMatchers.any()))
                .willReturn(Optional.of(fakeData.getCategory(this.CATEGORY_1)));

        CategoryResponse response = _serviceMock.get(ArgumentMatchers.any());

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getName()).isEqualTo(this.CATEGORY_1);
    }

    @Test
    public void getById_throw_notFound() {
        given(_repoMock.findById(ArgumentMatchers.any()))
                .willReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> {
            UUID id = UUID.randomUUID();
            _serviceMock.get(id);
        }).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    public void create_success() {
        given(_repoMock.save(ArgumentMatchers.any()))
                .willReturn(fakeData.getCategory(this.CATEGORY_1));

        CategoryResponse response = _serviceMock.create(fakeData.getCategoryRequest(this.CATEGORY_1));

        verify(_repoMock, times(1)).save(ArgumentMatchers.any());
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getName()).isEqualTo(this.CATEGORY_1);
    }

    @Test
    public void update_success() {
        given(_repoMock.findById(ArgumentMatchers.any()))
                .willReturn(Optional.of(fakeData.getCategory(this.CATEGORY_1)));
        given(_repoMock.save(ArgumentMatchers.any()))
                .willReturn(fakeData.getCategory(this.CATEGORY_1));

        CategoryResponse response = _serviceMock.update(UUID.randomUUID(), fakeData.getCategoryRequest(this.CATEGORY_1));

        verify(_repoMock, times(1)).save(ArgumentMatchers.any());
        Assertions.assertThat(response).isNotNull();
    }

    @Test
    public void update_throw_notFound() {
        given(_repoMock.findById(ArgumentMatchers.any()))
                .willReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> {
            _serviceMock.update(UUID.randomUUID(), fakeData.getCategoryRequest(this.CATEGORY_1));
        }).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    public void delete_success() {
        given(_repoMock.findById(ArgumentMatchers.any()))
                .willReturn(Optional.of(fakeData.getCategory(this.CATEGORY_1)));

        _serviceMock.delete(UUID.randomUUID());

        verify(_repoMock, times(1)).deleteById(ArgumentMatchers.any());
    }

    @Test
    public void delete_throw_notFound() {
        given(_repoMock.findById(ArgumentMatchers.any()))
                .willReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> {
            _serviceMock.delete(UUID.randomUUID());
        }).isInstanceOf(ResourceNotFoundException.class);
    }
}