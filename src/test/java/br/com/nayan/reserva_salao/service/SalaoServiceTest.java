//package br.com.nayan.reserva_salao.service;
//
//import br.com.nayan.reserva_salao.dto.SalaoRequestDTO;
//import br.com.nayan.reserva_salao.dto.SalaoResponseDTO;
//import br.com.nayan.reserva_salao.entity.CondominioEntity;
//import br.com.nayan.reserva_salao.entity.SalaoEntity;
//import br.com.nayan.reserva_salao.repository.SalaoRepository;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//class SalaoServiceTest {
//
//    @Mock
//    private CondominioService condominioService;
//
//    @Mock
//    private SalaoRepository salaoRepository;
//
//    @InjectMocks
//    private SalaoService salaoService;
//
//    @Test
//    @DisplayName("Create a saloon sucessfuly")
//    void createSaloonSucessfuly(){
//
//        SalaoRequestDTO salaoRequestDTO = buildSalaoDTO();
//        SalaoEntity salaoEntity = buildSalao();
//        CondominioEntity condominioEntity = buildCondominio();
//
//        when(salaoRepository.findByArea(salaoRequestDTO.getArea()))
//                .thenReturn(Optional.of(salaoEntity));
//        when(condominioService.getByName(salaoRequestDTO.getCondominio()))
//                .thenReturn(condominioEntity);
//        when(salaoRepository.save(any()))
//                .thenReturn(salaoEntity);
//
//        SalaoResponseDTO salaoResponseDTO = salaoService.create(salaoRequestDTO);
//
//        assertThat(salaoResponseDTO).isNotNull();
//        assertThat(salaoResponseDTO.getId()).isEqualTo(salaoEntity.getId());
//    }
//
//    private SalaoRequestDTO buildSalaoDTO(){
//
//            SalaoRequestDTO salaoRequestDTO = new SalaoRequestDTO();
//            salaoRequestDTO.setArea(buildSalao().getArea());
//            salaoRequestDTO.setCondominio("Residencial Planalto I");
//
//            return salaoRequestDTO;
//    }
//
//    private SalaoEntity buildSalao(){
//
//        SalaoEntity salaoEntity = new SalaoEntity();
//        salaoEntity.setId(1L);
//        salaoEntity.setArea("Churrasqueira");
//        salaoEntity.setCondominioEntity(buildCondominio());
//
//        return salaoEntity;
//    }
//
//    private CondominioEntity buildCondominio(){
//
//        CondominioEntity condominioEntity = new CondominioEntity();
//        condominioEntity.setNome("Residencial Planalto I");
//
//        return condominioEntity;
//    }
//}